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
 * Created by DarthPenguin on 23/08/17.
 */
public class VisualStatistics {
	private ArrayList<Double> arrayList;
	private XYSeriesCollection dataset2;
	private XYSeries series1;
	private int count;

	public JFreeChart createStateSpaceGraph() {

	    XYDataset dataset = createDataset(arrayList);

	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "State Space Time Graph",
	        "State Space Branches", "Time", dataset);

	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));

	   return chart;

	  }

	  private XYDataset createDataset(ArrayList<Double> arrayList) {
	    dataset2 = new XYSeriesCollection();
	    series1 = new XYSeries("State Space Time");
	    setBranchNumber(0);

	    dataset2.addSeries(series1);
	    return dataset2;
	  }

	  public void setInput(ArrayList<Double> arrayList){
		this.arrayList = arrayList;
	  }


	  public void incrementBranchNumber(){
	  	count++;
	  }

	  public void setBranchNumber(int count){
	  	this.count=count;
	  }

	  public void updatePlot(ArrayList<Double> arrayList){
	  	setInput(arrayList);

	  	for(Double a :arrayList){
				series1.add(count, a);
			incrementBranchNumber();
		}


	  }


}
