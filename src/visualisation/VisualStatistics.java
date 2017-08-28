package visualisation;


import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * This class produces the scatter plot
 */
public class VisualStatistics {
	private ArrayList<Double> arrayList;
	private XYSeriesCollection dataset;
	private XYSeries series;
	private int branchCount;
/**
 * This method sets the required fields and values for the scatter plot and then returns it
 * @return chart
 */
	public synchronized JFreeChart createStateSpaceGraph() {

		XYDataset dataset = createDataset(arrayList);

		JFreeChart chart = ChartFactory.createScatterPlot(
				"State Space Time Scatter Plot",
				"Time Discovered", "Best Time/Cost Estimate", dataset);

		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(new Color(201,216,215));

		return chart;

	}
/**
 * This creates a dataset for the scatter plot
 * @param arrayList
 * @return
 */
	private synchronized XYDataset createDataset(ArrayList<Double> arrayList) {
		dataset = new XYSeriesCollection();
		series = new XYSeries("Current Best Schedule");
		setBranchNumber(0);

		dataset.addSeries(series);
		return dataset;
	}
/**
 * Method sets the array list according to the input array list values for the scatter plot points
 * @param arrayList
 */
	public synchronized void setInput(ArrayList<Double> arrayList){
		this.arrayList = arrayList;
	}

/**
 * This increments the number of branches field
 */
	public synchronized void incrementBranchNumber(){
		branchCount++;
	}

	public synchronized void setBranchNumber(int count){
		this.branchCount=count;
	}

	public synchronized void updatePlot(double a, double time){
		//setInput(arrayList);
		series.add(time, a);
		incrementBranchNumber();
	}


}
