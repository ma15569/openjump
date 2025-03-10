package com.vividsolutions.jump.workbench.plugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlugInClassLoader extends URLClassLoader {

  public PlugInClassLoader(ClassLoader parent) {
    super(new URL[0], parent);
    // up to java8 we could get the classpath from the app classloader
    if (parent instanceof URLClassLoader) {
      addUrls(((URLClassLoader) parent).getURLs());
    }
    // that changed in java9, now we build it from the java property
    else {
      List<URL> ucp = new ArrayList<>();
//      System.out.println(parent.getClass());
      String cp = System.getProperty ("java.class.path","");
//      System.out.println(cp);
      addClassPathToUCP(cp, ucp);
//      System.out.println(ucp);
      
      addUrls(ucp.toArray(new URL[] {}));
    }
  }

  public PlugInClassLoader(URL[] urls) {
    super(urls);
  }

  /**
   * not really necessary now, but we keep it for reference for a future
   * classloader per extension for allowing extensions to use differently
   * versioned dependency jars in separate subfolders under
   * lib/ext/<extension_subfolder>/
   */
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    // if (name.matches("(?i).*PlugInClassLoader"))
    // System.out.println("foo");
    Class<?> c = findLoadedClass(name);

    // skip the default classloader which we replace and
    // try it's parent to load java system jars and such
    if (c == null) {
      try {
        //TODO why do we load grand-parent and not parent ?
        c = getParent().getParent().loadClass(name);
      } catch (ClassNotFoundException e) {
      }
    }

    // we prefer this class loader to the sun.misc.Launcher one to have all OJ
    // classes within one classloader, advantages are:
    // - instanceof does not work over different classloaders
    // - we override some classes from extension jars (wfs, deegree), which is
    // only possible if they are found before the ones in the jars
    // Note:
    // exception is this class which is already instantiated with
    // sun.misc.Launcher so we keep it that way
    if (c == null && !name.equals("com.vividsolutions.jump.workbench.plugin.PlugInClassLoader")) {
      try {
        c = findClass(name);
      } catch (ClassNotFoundException e) {
      }
    }

    // this classloader is always loaded by the default cl, so find it there
    if (c == null && name.equals("com.vividsolutions.jump.workbench.plugin.PlugInClassLoader")) {
      try {
        c = getParent().loadClass(name);
      } catch (ClassNotFoundException e) {
      }
    }

    return c;
  }

  /**
   * THIS IS A WORKAROUND! java9 fails to resolve if the resource contains a leading slash
   * e.g.   /org/freevoice/jumpdbqueryextension/dbquerystrings.properties
   *      fails with 
   *        null
   *      while
   *        org/freevoice/jumpdbqueryextension/dbquerystrings.properties
   *      will properly resolve to
   *        jar:file:lib/plus/jumpdbquery.jar!/org/freevoice/jumpdbqueryextension/dbquerystrings.properties
   */
  @Override
  public URL getResource(String name) {
    if (name.startsWith("/"))
      name = name.replaceAll("^/+", "");
    return super.getResource(name);
  }

  /**
   * allow adding urls, any time
   * 
   * @param urls array of URLs to add
   */
  public void addUrls(URL[] urls) {
    for (URL url : urls) {
      addURL(url);
    }
  }

  /**
   * Converts the elements in the given class path to file URLs and adds them to
   * the given URL List.
   */
  private static void addClassPathToUCP(String cp, List<URL> ucp) {
    int off = 0;
    int next;
    while ((next = cp.indexOf(File.pathSeparator, off)) != -1) {
      URL url = toFileURL(cp.substring(off, next));
      if (url != null)
        ucp.add(url);
      off = next + 1;
    }

    // remaining
    URL url = toFileURL(cp.substring(off));
    if (url != null)
      ucp.add(url);
  }

  /**
   * Attempts to convert the given string to a file URL.
   *
   * @apiNote This is called by the VM
   */
  private static URL toFileURL(String s) {
    try {
      return Paths.get(s).toRealPath().toUri().toURL();
    } catch (InvalidPathException | IOException ignore) {
      // malformed path string or class path element does not exist
      // we _cannot_ use Logger during VM init, so we have to print to STDERR
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ignore.printStackTrace(pw);
      String stack = sw.toString();
      // print first line of stack only containing Exception class name and message
      int index = stack.indexOf("\n");
      System.err.println("Problem adding classpath entry '" + s + "': " +
              stack.substring(0, index>0?index:stack.length()));
      return null;
    }
  }

  /**
   * This class loader supports dynamic additions to the class path
   * at runtime.
   *
   * @see java.lang.instrument.Instrumentation#appendToSystemClassLoaderSearch
   */
  private  void appendToClassPathForInstrumentation(String path) {
    assert(Thread.holdsLock(this));

    // addURL is a no-op if path already contains the URL
    super.addURL( getFileURL(new File(path)) );
  }

  private static URL getFileURL(File file) {
    try {
      file = file.getCanonicalFile();
    } catch (IOException e) {}

    try {
      return file.toURI().toURL();
    } catch (MalformedURLException e) {
      // Should never happen since we specify the protocol...
      throw new InternalError();
    }
  }
};