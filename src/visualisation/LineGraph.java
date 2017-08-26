package visualisation;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

public class LineGraph {



public JFreeChart createLineGraph() {


	    JFreeChart lineChart = ChartFactory.createLineChart(
	            "Number of Traversed Schedules Over Time",
	            "Time","Number of Schedules",
	            createDataset(),
	            PlotOrientation.VERTICAL,
	            true,true,false);

	     // chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      return lineChart;
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 15 , "Schedules" , "1970" );
      dataset.addValue( 30 , "Schedules" , "1980" );
      dataset.addValue( 60 , "Schedules" ,  "1990" );
      dataset.addValue( 120 , "Schedules" , "2000" );
      dataset.addValue( 240 , "Schedules" , "2010" );
      dataset.addValue( 300 , "Schedules" , "2014" );
      return dataset;
   }


}