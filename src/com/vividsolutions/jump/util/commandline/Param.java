/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI 
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

package com.vividsolutions.jump.util.commandline;

import java.util.Vector;

/**
 * The parameters for an instance of an option occurring in a command.
 */
public class Param {
  private ParamSpec optSpec;
  private Vector<String> args = new Vector<>(); // the actual option args found

  public Param(ParamSpec spec, String[] _args) {
    optSpec = spec;
    for (String string : _args) {
      args.add(string);
    }
  }

  public final ParamSpec getSpec() {
    return optSpec;
  }

  public void addArg(String value) {
    args.add(value);
  }

  public int getNumArgs() {
    return args.size();
  }

  public String getArg(int i) {
    return args.get(i);
  }

  public String[] getArgs() {
    return args.toArray(new String[] {});
  }
}
