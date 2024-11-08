package org.openjump.core.ui.plot;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.math.plot.PlotPanel;
import org.math.plot.canvas.PlotCanvas;
import org.math.plot.plots.Plot;

import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

/**
 * BSD License
 *
 * @author Yann RICHET
 */

/**
 * Class suitable for plotting 2D data on a panel, to be added to a swing
 * container.
 *
 * Class for ascending compatibility
 *
 * @author Yann Richet
 */
public class Plot2DPanelOJ extends PlotPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for Plot2DPanel. Create a new blank instance of Plot2DPanel,
     * to be added to a swing component.
     */
    public Plot2DPanelOJ() {
        super(new Plot2DCanvasOJ());
    }

    public Plot2DPanelOJ(double[] min, double[] max, String[] axesScales,
            String[] axesLabels) {
        super(new Plot2DCanvasOJ(min, max, axesScales, axesLabels));
    }

    public Plot2DPanelOJ(PlotCanvas _canvas, String legendOrientation) {
        super(_canvas, legendOrientation);
    }

    public Plot2DPanelOJ(PlotCanvas _canvas) {
        super(_canvas);
    }

    public Plot2DPanelOJ(String legendOrientation) {
        super(new Plot2DCanvasOJ(), legendOrientation);
    }

    /**
     * Adds a scatter plot (each data point is plotted as a single dot marker)
     * to the current plot panel.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            Pairs of array of double. First one contains the X position of
     *            the data points, second contains Y position. <br>
     *            Each array of the pair must be of the same length; if not a
     *            ArrayIndexOutOfBoundsException exception will be thrown. <br>
     *            Each data set must come in <b>pair</b> of array of double; if
     *            not a ArrayIndexOutOfBoundsException exception will be thrown.
     * @return the index of the plot in the panel (int).
     * @see #addLinePlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][], double[][])
     * @see #addHistogramPlot(String, Color, double[][], double[])
     * @see #addStaircasePlot(String, Color, double[]...)
     */
    public int addScatterPlot(String name, Color color, double[][] XY) {
        return ((Plot2DCanvasOJ) plotCanvas).addScatterPlot(name, color, XY);
    }

    public int addScatterPlot(String name, Color color, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addScatterPlot(name, color, Y);
    }

    public int addScatterPlot(String name, Color color, double[] X, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addScatterPlot(name, color, X, Y);
    }

    public int addScatterPlot(String name, double[][] XY) {
        return addScatterPlot(name, getNewColor(), XY);
    }

    /**
     * [sstein] method for OpenJUMP
     * 
     * @param name name of the plot
     * @param XY data as a 2-dimensional array of double
     * @param fID feature identifiers as an array of integers
     * @param context the PlugInContext
     * @param layer layer
     */
    public int addScatterPlotOJ(String name, double[][] XY, int[] fID,
            PlugInContext context, Layer layer) {
        return ((Plot2DCanvasOJ) plotCanvas).addScatterPlotOJ(name,
                getNewColor(), XY, fID, context, layer);
    }

    public int addScatterPlot(String name, double[] Y) {
        return addScatterPlot(name, getNewColor(), Y);
    }

    public int addScatterPlot(String name, double[] X, double[] Y) {
        return addScatterPlot(name, getNewColor(), X, Y);
    }

    /**
     * Adds a line plot (each data point is connected to the next one by a solid
     * line) to the current plot panel.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            Pairs of array of double. First one contains the X position of
     *            the data points, second contains Y position. <br>
     *            Each array of the pair must be of the same length; if not a
     *            ArrayIndexOutOfBoundsException exception will be thrown. <br>
     *            Each data set must come in <b>pair</b> of array of double; if
     *            not a ArrayIndexOutOfBoundsException exception will be thrown.
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][])
     * @see #addHistogramPlot(String, Color, double[][])
     * @see #addStaircasePlot(String, Color, double[])
     */
    public int addLinePlot(String name, Color color, double[][] XY) {
        return ((Plot2DCanvasOJ) plotCanvas).addLinePlot(name, color, XY);
    }

    public int addLinePlot(String name, Color color, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addLinePlot(name, color, Y);
    }

    public int addLinePlot(String name, Color color, double[] X, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addLinePlot(name, color, X, Y);
    }

    public int addLinePlot(String name, double[][] XY) {
        return addLinePlot(name, getNewColor(), XY);
    }

    public int addLinePlot(String name, double[] Y) {
        return addLinePlot(name, getNewColor(), Y);
    }

    public int addLinePlot(String name, double[] X, double[] Y) {
        return addLinePlot(name, getNewColor(), X, Y);
    }

    /**
     * Adds a bar plot (each data point is shown as a dot marker connected to
     * the horizontal axis by a vertical line) to the current plot panel.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            Pairs of array of double. First one contains the X position of
     *            the data points, second contains Y position. <br>
     *            Each array of the pair must be of the same length; if not a
     *            ArrayIndexOutOfBoundsException exception will be thrown. <br>
     *            Each data set must come in <b>pair</b> of array of double; if
     *            not a ArrayIndexOutOfBoundsException exception will be thrown.
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addLinePlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][])
     * @see #addHistogramPlot(String, Color, double[][])
     * @see #addStaircasePlot(String, Color, double[]...)
     */
    public int addBarPlot(String name, Color color, double[][] XY) {
        return ((Plot2DCanvasOJ) plotCanvas).addBarPlot(name, color, XY);
    }

    public int addBarPlot(String name, Color color, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addBarPlot(name, color, Y);
    }

    public int addBarPlot(String name, Color color, double[] X, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addBarPlot(name, color, X, Y);
    }

    public int addBarPlot(String name, double[][] XY) {
        return addBarPlot(name, getNewColor(), XY);
    }

    public int addBarPlot(String name, double[] Y) {
        return addBarPlot(name, getNewColor(), Y);
    }

    public int addBarPlotOJ(String name, double[] Y, int[] fID,
            PlugInContext context, Layer layer) {
        return ((Plot2DCanvasOJ) plotCanvas).addBarPlotOJ(name, getNewColor(),
                Y, fID, context, layer);
    }

    public int addBarPlot(String name, double[] X, double[] Y) {
        return addBarPlot(name, getNewColor(), X, Y);
    }

    /**
     * Adds a staircase plot (each data point is connected to the following one
     * by a horizontal line then a vertical line) to the current plot panel.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            Pairs of array of double. First one contains the X position of
     *            the data points, second contains Y position. <br>
     *            Each array of the pair must be of the same length; if not a
     *            ArrayIndexOutOfBoundsException exception will be thrown. <br>
     *            Each data set must come in <b>pair</b> of array of double; if
     *            not a ArrayIndexOutOfBoundsException exception will be thrown.
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][], double[][])
     * @see #addHistogramPlot(String, Color, double[][], double[])
     * @see #addLinePlot(String, Color, double[]...)
     */
    public int addStaircasePlot(String name, Color color, double[][] XY) {
        return ((Plot2DCanvasOJ) plotCanvas).addStaircasePlot(name, color, XY);
    }

    public int addStaircasePlot(String name, Color color, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas).addStaircasePlot(name, color, Y);
    }

    public int addStaircasePlot(String name, Color color, double[] X, double[] Y) {
        return ((Plot2DCanvasOJ) plotCanvas)
                .addStaircasePlot(name, color, X, Y);
    }

    public int addStaircasePlot(String name, double[][] XY) {
        return addStaircasePlot(name, getNewColor(), XY);
    }

    public int addStaircasePlot(String name, double[] Y) {
        return addStaircasePlot(name, getNewColor(), Y);
    }

    public int addStaircasePlot(String name, double[] X, double[] Y) {
        return addStaircasePlot(name, getNewColor(), X, Y);
    }

    /**
     * Adds a box plot to the current plot panel. Each data point is plotted as
     * a dot marker at the center of a rectangle.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            m*2 array of array of double. Contains the x,y coordinates of
     *            the m boxes' center (m lines, 2 rows).
     * @param dXdY
     *            m*2 array of array of double. Contains the width and heigth of
     *            the m boxes (m lines, 2 rows).
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addStaircasePlot(String, Color, double[]...)
     * @see #addHistogramPlot(String, Color, double[][], double[])
     * @see #addLinePlot(String, Color, double[]...)
     */
    public int addBoxPlot(String name, Color color, double[][] XY,
            double[][] dXdY) {
        return ((Plot2DCanvasOJ) plotCanvas).addBoxPlot(name, color, XY, dXdY);
    }

    public int addBoxPlot(String name, double[][] XY, double[][] dXdY) {
        return addBoxPlot(name, getNewColor(), XY, dXdY);
    }

    /**
     * Adds a box plot to the current plot panel. Each data point is plotted as
     * a dot marker at the center of a rectangle.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XYdXdY
     *            m*4 array of array of double. Contains the x,y coordinates of
     *            the m boxes' center and the boxes width and heigth (m lines, 4
     *            rows).
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addStaircasePlot(String, Color, double[]...)
     * @see #addHistogramPlot(String, Color, double[][], double[])
     * @see #addLinePlot(String, Color, double[]...)
     */
    public int addBoxPlot(String name, Color color, double[][] XYdXdY) {
        return ((Plot2DCanvasOJ) plotCanvas).addBoxPlot(name, color, XYdXdY);
    }

    public int addBoxPlot(String name, double[][] XYdXdY) {
        return addBoxPlot(name, getNewColor(), XYdXdY);
    }

    /**
     * Adds a histogram plot to the current plot panel. Each data point is as
     * vertical bar which width can be set.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param XY
     *            m*2 array of array of double. Contains the x coordinate and
     *            the heigth of each bar (m lines, 2 rows).
     * @param dX
     *            Array of double. Contains the width each bar (m lines).
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addStaircasePlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][])
     * @see #addLinePlot(String, Color, double[]...)
     */
    public int addHistogramPlot(String name, Color color, double[][] XY,
            double[] dX) {
        return ((Plot2DCanvasOJ) plotCanvas).addHistogramPlot(name, color, XY,
                dX);
    }

    public int addHistogramPlot(String name, double[][] XY, double[] dX) {
        return addHistogramPlot(name, getNewColor(), XY, dX);
    }

    public int addHistogramPlot(String name, Color color, double[][] XYdX) {
        return ((Plot2DCanvasOJ) plotCanvas)
                .addHistogramPlot(name, color, XYdX);
    }

    /**
     * Adds a histogram plot to the current plot panel. Each data point is as
     * vertical bar which width can be set.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param XYdX
     *            m*3 array of array of double. Contains the x coordinate, the
     *            heigth of each bar and the width of each bar (m lines, 3
     *            rows).
     * @return the index of the plot in the panel (int).
     * @see #addScatterPlot(String,Color,double[]...)
     * @see #addBarPlot(String, Color, double[]...)
     * @see #addStaircasePlot(String, Color, double[]...)
     * @see #addBoxPlot(String, Color, double[][])
     * @see #addLinePlot(String, Color, double[]...)
     */
    public int addHistogramPlot(String name, double[][] XYdX) {
        return addHistogramPlot(name, getNewColor(), XYdX);
    }

    /**
     * Adds a plot of the statistical repartition of a sample, as a histogram.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param sample
     *            Array of double containing the data which statistics will be
     *            plotted.
     * @param n
     *            Bin number for the statistics (int).
     * @return the index of the plot in the panel (int).
     */
    public int addHistogramPlot(String name, Color color, double[] sample, int n) {
        return ((Plot2DCanvasOJ) plotCanvas).addHistogramPlot(name, color,
                sample, n);
    }

    public int addHistogramPlot(String name, double[] X, int n) {
        return addHistogramPlot(name, getNewColor(), X, n);
    }

    /**
     * [sstein] method for OJUMP
     * 
     * @param name name of the plot
     * @param X data as a double array
     * @param n number of classes
     * @param context the PlugInContext
     * @param layer the Layer
     * @param attrName name of the attribute to plot
     */
    public int addHistogramPlotOJ(String name, double[] X, int n,
            PlugInContext context, Layer layer, String attrName) {
        return ((Plot2DCanvasOJ) plotCanvas).addHistogramPlotOJ(name,
                getNewColor(), X, n, context, layer, attrName);
    }

    /**
     * Adds a plot of the statistical repartition of a sample, as a histogram.
     * The bins' limits can be set.
     * 
     * @param name
     *            Name for the plot, which will be used in the legend. (String)
     * @param color
     *            Plot color. (Color)
     * @param sample
     *            Array of double containing the data which statistics will be
     *            plotted.
     * @param bounds
     *            Specify the limits for the bins' boundaries.
     * @return the index of the plot in the panel (int).
     */
    public int addHistogramPlot(String name, Color color, double[] sample,
            double... bounds) {
        return ((Plot2DCanvasOJ) plotCanvas).addHistogramPlot(name, color,
                sample, bounds);
    }

    public int addHistogramPlot(String name, double[] X, double... bounds) {
        return addHistogramPlot(name, getNewColor(), X, bounds);
    }

    public int addHistogramPlot(String name, Color color, double[] X,
            double min, double max, int n) {
        return ((Plot2DCanvasOJ) plotCanvas).addHistogramPlot(name, color, X,
                min, max, n);
    }

    public int addHistogramPlot(String name, double[] X, double min,
            double max, int n) {
        return addHistogramPlot(name, getNewColor(), X, min, max, n);
    }

    public int addCloudPlot(String name, Color color, double[][] sampleXY,
            int nX, int nY) {
        return ((Plot2DCanvasOJ) plotCanvas).addCloudPlot(name, color,
                sampleXY, nX, nY);
    }

    public int addCloudPlot(String name, double[][] sampleXY, int nX, int nY) {
        return addCloudPlot(name, getNewColor(), sampleXY, nX, nY);
    }

    @Override
    public int addPlot(String type, String name, Color color, double[]... XY) {
        if (type.equalsIgnoreCase(SCATTER)) {
            return addScatterPlot(name, color, XY);
        } else if (type.equalsIgnoreCase(LINE)) {
            return addLinePlot(name, color, XY);
        } else if (type.equalsIgnoreCase(BAR)) {
            return addBarPlot(name, color, XY);
        } else if (type.equalsIgnoreCase(STAIRCASE)) {
            return addStaircasePlot(name, color, XY);
        } else if (type.equalsIgnoreCase(HISTOGRAM)) {
            return addHistogramPlot(name, color, XY);
        } else if (type.equalsIgnoreCase(BOX)) {
            return addBoxPlot(name, color, XY);
        } else {
            throw new IllegalArgumentException("Plot type is unknown : " + type);
        }
    }

    /**
     * Gets an array with data from Y. In the case of Histogram getYData method
     * it returns the absolute frequency per classes
     * 
     * @return array Object[]
     */
    public Object[] getYData() {
        double[][] dataTableDouble = null;
        Object[][] dataTableObject = null;
        for (final Plot plot2 : ((Plot2DCanvasOJ) plotCanvas).getPlots()) {
            dataTableDouble = plot2.getData();
        }
        dataTableObject = ((Plot2DCanvasOJ) plotCanvas)
                .reverseMapedData(dataTableDouble);
        final Object[] column = new String[dataTableObject.length];

        final Locale specialLocale = new Locale("en", "EN");
        final String formatPattern = "###";
        final DecimalFormat nf = (DecimalFormat) NumberFormat
                .getNumberInstance(specialLocale);
        nf.applyPattern(formatPattern);

        for (int i = 0; i < column.length; i++) {
            final double value = ((Double) dataTableObject[i][1]).doubleValue();
            final String stringValue = nf.format(value);
            column[i] = stringValue;
        }
        return column;
    }

    /**
     * returns the relative frequancy of Y data
     * 
     * @return array Object[]
     */

    public Object[] getYData_RelativeFrequency() {
        double[][] dataTableDouble = null;
        Object[][] dataTableObject = null;
        for (final Plot plot2 : ((Plot2DCanvasOJ) plotCanvas).getPlots()) {
            dataTableDouble = plot2.getData();
        }
        dataTableObject = ((Plot2DCanvasOJ) plotCanvas)
                .reverseMapedData(dataTableDouble);
        final Object[] column = new String[dataTableObject.length];
        final Locale specialLocale = new Locale("en", "EN");
        final String formatPattern = "###.#####";
        final DecimalFormat nf = (DecimalFormat) NumberFormat
                .getNumberInstance(specialLocale);
        nf.applyPattern(formatPattern);
        double sum = 0.0D;
        for (int i = 0; i < column.length; i++) {
            final double value = ((Double) dataTableObject[i][1]).doubleValue();
            sum += value;
        }
        for (int i = 0; i < column.length; i++) {
            final double value = ((Double) dataTableObject[i][1]).doubleValue();
            final double frqValue = (value / sum) * 100;
            final String stringValue = nf.format(frqValue);
            column[i] = stringValue;

        }

        return column;
    }

    /**
     * returns the cumulative frequancy of Y data
     * 
     * @return array Object[]
     */

    public Object[] getYData_CumulativeFrequency() {
        double[][] dataTableDouble = null;
        Object[][] dataTableObject = null;
        for (final Plot plot2 : ((Plot2DCanvasOJ) plotCanvas).getPlots()) {
            dataTableDouble = plot2.getData();
        }
        dataTableObject = ((Plot2DCanvasOJ) plotCanvas)
                .reverseMapedData(dataTableDouble);
        final Object[] column = new String[dataTableObject.length];
        final Locale specialLocale = new Locale("en", "EN");
        final String formatPattern = "###.###";
        final DecimalFormat nf = (DecimalFormat) NumberFormat
                .getNumberInstance(specialLocale);
        nf.applyPattern(formatPattern);
        double frqValue = 0;
        for (int i = 0; i < column.length; i++) {
            final double value = ((Double) dataTableObject[i][1]).doubleValue();
            frqValue += value;
            final Integer integer = (int) frqValue;
            ;
            final String stringValue = String.valueOf(integer);
            column[i] = stringValue;

        }

        return column;
    }

    /**
     * Gets an array with data from X. In the case of Histogram getYData method
     * returns the media value of the range fro each interval (for instance: 50
     * (interval between 0-100), 150 (interval between 100-200) etc)
     * 
     * @return array Object[]
     */
    public Object[] getXData() {
        double[][] dataTableDouble = null;
        Object[][] dataTableObject = null;
        for (final Plot plot2 : ((Plot2DCanvasOJ) plotCanvas).getPlots()) {
            dataTableDouble = plot2.getData();
        }
        dataTableObject = ((Plot2DCanvasOJ) plotCanvas)
                .reverseMapedData(dataTableDouble);
        final Object[] column = new String[dataTableObject.length];
        final Locale specialLocale = new Locale("en", "EN");
        final String formatPattern = "###.##";
        final DecimalFormat nf = (DecimalFormat) NumberFormat
                .getNumberInstance(specialLocale);
        nf.applyPattern(formatPattern);

        double value;
        for (int i = 0; i < column.length; i++) {

            value = ((Double) dataTableObject[i][0]).doubleValue();

            final String stringValue = nf.format(value);
            column[i] = stringValue;
        }

        return column;
    }

    /**
     * gets the limit values of each interval of X data. maxvalue true= the
     * upper value, maxvalue false= the lower value
     * 
     * @param maxvalue whether the method return max values (true) or min values (false)
     * @return max or min values of each class
     */
    public Object[] getXData_limits(boolean maxvalue) {
        double[][] dataTableDouble = null;
        Object[][] dataTableObject = null;
        for (final Plot plot2 : ((Plot2DCanvasOJ) plotCanvas).getPlots()) {
            dataTableDouble = plot2.getData();
        }
        dataTableObject = ((Plot2DCanvasOJ) plotCanvas)
                .reverseMapedData(dataTableDouble);
        final Object[] column = new String[dataTableObject.length];
        final Locale specialLocale = new Locale("en", "EN");
        final String formatPattern = "###.##";
        final DecimalFormat nf = (DecimalFormat) NumberFormat
                .getNumberInstance(specialLocale);
        nf.applyPattern(formatPattern);

        final double interval = (((Double) dataTableObject[0][0]).doubleValue() - ((Double) dataTableObject[1][0])
                .doubleValue()) / 2;
        double value;
        for (int i = 0; i < column.length; i++) {
            if (maxvalue) {
                value = ((Double) dataTableObject[i][0]).doubleValue()
                        - interval;
            } else {
                value = ((Double) dataTableObject[i][0]).doubleValue()
                        + interval;
            }
            final String stringValue = nf.format(value);
            column[i] = stringValue;
        }

        return column;
    }

}
