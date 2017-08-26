package statistics;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import com.sun.prism.paint.Color;

/**
 * Created by DarthPenguin on 23/08/17.
 */
public class compareSchedules {


	public JFreeChart createStateSpaceGraph() {


	    // Create dataset
	    XYDataset dataset = createDataset();

	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "State Space Time Graph",
	        "State Space Branches", "Time", dataset);


	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    //plot.setBackgroundPaint(new Color(255,228,196));


	    // Create Panel
	   return chart;
	  }

	  private XYDataset createDataset() {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries("State Space Time");
	    series1.add(1, 72.9);
	    series1.add(2, 81.6);
	    series1.add(3, 88.9);
	    series1.add(4, 96);
	    series1.add(5, 102.1);
	    series1.add(6, 108.5);
	    series1.add(7, 113.9);
	    series1.add(8, 119.3);
	    series1.add(9, 123.8);
	    series1.add(10, 124.4);

	    dataset.addSeries(series1);


	    return dataset;
	  }

}
