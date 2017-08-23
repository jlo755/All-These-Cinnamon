package visualisation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import statistics.compareSchedules;

import javax.swing.JTable;

public class ProcessorScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void beginProcessing() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessorScreen frame = new ProcessorScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProcessorScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//compareSchedules chart = new compareSchedules("School Vs Years" , "Number of Schools vs years");

		//chart.pack( );
		 //RefineryUtilities.centerFrameOnScreen( chart );
		 //chart.setVisible( true );


		compareSchedules compare = new compareSchedules();

		JFreeChart chart = compare.createDataset();

		 JPanel panel = new JPanel();
		 panel.setLayout(new java.awt.BorderLayout());
		 panel.setBounds(83, 66, 782, 632);
		 contentPane.add(panel);
		 ChartPanel CP = new ChartPanel(chart);

		 panel.add(CP,BorderLayout.CENTER);
		 panel.validate();
	}
}
