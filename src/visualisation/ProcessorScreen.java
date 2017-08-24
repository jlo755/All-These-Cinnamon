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
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

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
		setBounds(100, 100, 1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//compareSchedules chart = new compareSchedules("School Vs Years" , "Number of Schools vs years");

		//chart.pack( );
		 //RefineryUtilities.centerFrameOnScreen( chart );
		 //chart.setVisible( true );


		compareSchedules compare = new compareSchedules();

		JFreeChart chart = compare.createStateSpaceGraph();

		 JPanel scatterPanel = new JPanel();
		 scatterPanel.setBounds(492, 30, 582, 426);
		 contentPane.add(scatterPanel);
		 scatterPanel.setLayout(null);
		 ChartPanel CP = new ChartPanel(chart);
		 CP.setBorder(new LineBorder(new Color(0, 0, 0)));
		 CP.setBounds(0, 0, 582, 426);
		 scatterPanel.add(CP);

		 JPanel ProcessingDetailsPanel = new JPanel();
		 ProcessingDetailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		 ProcessingDetailsPanel.setBounds(492, 467, 582, 184);
		 contentPane.add(ProcessingDetailsPanel);
		 ProcessingDetailsPanel.setLayout(null);

		 JLabel lblNodeName = new JLabel("Node Name:");
		 lblNodeName.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblNodeName.setBounds(22, 11, 107, 14);
		 ProcessingDetailsPanel.add(lblNodeName);

		 JLabel lblStartTime = new JLabel("Start Time:");
		 lblStartTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblStartTime.setBounds(22, 56, 74, 21);
		 ProcessingDetailsPanel.add(lblStartTime);

		 JLabel lblEndTime = new JLabel("End Time:");
		 lblEndTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblEndTime.setBounds(22, 101, 74, 28);
		 ProcessingDetailsPanel.add(lblEndTime);

		 JLabel lblProcessor = new JLabel("Processor:");
		 lblProcessor.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblProcessor.setBounds(22, 159, 74, 14);
		 ProcessingDetailsPanel.add(lblProcessor);

		 JLabel NodeNameOuputLabel = new JLabel("-");
		 NodeNameOuputLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		 NodeNameOuputLabel.setBounds(139, 11, 418, 14);
		 ProcessingDetailsPanel.add(NodeNameOuputLabel);

		 JLabel StartTimeOutputLabel = new JLabel("-");
		 StartTimeOutputLabel.setBounds(139, 59, 433, 14);
		 ProcessingDetailsPanel.add(StartTimeOutputLabel);

		 JLabel EndTimeOutputLabel = new JLabel("-");
		 EndTimeOutputLabel.setBounds(137, 108, 435, 14);
		 ProcessingDetailsPanel.add(EndTimeOutputLabel);

		 JLabel ProcessorOuputLabel = new JLabel("-");
		 ProcessorOuputLabel.setBounds(137, 159, 435, 14);
		 ProcessingDetailsPanel.add(ProcessorOuputLabel);

		 JPanel graphPanel = new JPanel();
		 graphPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		 graphPanel.setBounds(10, 30, 472, 567);
		 contentPane.add(graphPanel);

		 JPanel timerPanel = new JPanel();
		 FlowLayout flowLayout = (FlowLayout) timerPanel.getLayout();
		 flowLayout.setAlignment(FlowLayout.LEFT);
		 timerPanel.setBounds(10, 608, 472, 43);
		 contentPane.add(timerPanel);

		 JLabel lblTime = new JLabel("Time Elapsed:  00:00:00");
		 lblTime.setFont(new Font("Leelawadee", Font.PLAIN, 17));
		 timerPanel.add(lblTime);
	}
}
