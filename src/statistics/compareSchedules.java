package statistics;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Created by DarthPenguin on 23/08/17.
 */
public class compareSchedules {


    public JFreeChart createStateSpaceGraph() {
    	XYSeries series = new XYSeries("State Space Time Graph");
		   series.add(1, 1);
		   series.add(1, 2);
		   series.add(2, 1);
		   series.add(3, 9);
		   series.add(4, 10);

		// Add the series to your data set
		   XYSeriesCollection dataset = new XYSeriesCollection();
		   dataset.addSeries(series);

		// Generate the graph
		   JFreeChart chart = ChartFactory.createXYLineChart(
		   "State Space Time Graph", // Title
		   "State Space", // x-axis Label
		   "Time", // y-axis Label
		   dataset, // Dataset
		   PlotOrientation.VERTICAL, // Plot Orientation
		   true, // Show Legend
		   true, // Use tooltips
		   false // Configure chart to generate URLs?
		);
		return chart;
    }
}
