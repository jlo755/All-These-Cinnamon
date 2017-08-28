package visualisation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import scheduling.DVScheduler;
import scheduling.LaunchScheduler;
import scheduling.ScheduleFactory;
import scheduling.ScheduleWorker;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;


public class ProcessorScreen extends JFrame {

	private JPanel contentPane;
	private String _file;
	private JTextField processorCountTextField;
	private Integer _processors;
	private JPanel graphPanel;
	private JPanel  statisticsPanel;
	private VisualGraph _visualGraph;
	private VisualController vc;
	JLabel lblNewLabel;
	JLabel lblEndTime;
	JLabel lblCurrentBestCost;
	JLabel lblMemoryUsage;

	/**
	 * Launch the application.
	 */
	public void beginProcessing() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ProcessorScreen frame = new ProcessorScreen();
					setVisible(true);
					if(ScheduleFactory.getInstance().getParallelise() > 1) {
						DVScheduler _scheduler = LaunchScheduler._scheduler;
						_scheduler.setVisualController(vc);
						vc.setVisualModel(_scheduler);
						ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
						threadExecutor.submit(new Runnable(){

							@Override
							public void run() {
								_scheduler.schedule();
							}

						});
					} else {
						ScheduleWorker sw = new ScheduleWorker(LaunchScheduler._noOfProcessors, vc);
						sw.execute();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException
	 */
	public ProcessorScreen() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1194, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//VisualStatistics chart = new VisualStatistics("School Vs Years" , "Number of Schools vs years");

		//chart.pack( );
		//RefineryUtilities.centerFrameOnScreen( chart );
		//chart.setVisible( true );

		graphPanel = new JPanel(new GridLayout()){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(460,567);
			}
		};
		graphPanel.setBounds(10, 11, 460, 640);
		contentPane.add(graphPanel);

		statisticsPanel = new JPanel();
		statisticsPanel.setBounds(492, 11, 572, 435);
		contentPane.add(statisticsPanel);
		statisticsPanel.setLayout(null);

		vc = new VisualController(LaunchScheduler.dotParser.getNodeMap(), graphPanel, statisticsPanel, this);

		LaunchScheduler ls = new LaunchScheduler();
		//_noOfProcessors = Integer.parseInt(processorCountTextField.getText());
		//ls.setFileName(_fileName);
		//ls.setProcessor(_noOfProcessors);


		//compareSchedules compare = new compareSchedules();
		LineGraph compareLine = new LineGraph();
		JFreeChart chartLine = compareLine.createLineGraph();

		//VisualStatistics compare = new VisualStatistics();

		//JFreeChart chart = compare.createStateSpaceGraph();




		JPanel ProcessingDetailsPanel = new JPanel();
		ProcessingDetailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProcessingDetailsPanel.setBounds(492, 450, 572, 201);
		contentPane.add(ProcessingDetailsPanel);
		ProcessingDetailsPanel.setLayout(null);

		LaunchScheduler launchS = new LaunchScheduler();
		_file = launchS._fileName;
		_processors = launchS._noOfProcessors;
		JLabel lblNodeName = new JLabel("Number Of Processors: "+ _processors);
		lblNodeName.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblNodeName.setBounds(22, 11, 550, 14);
		ProcessingDetailsPanel.add(lblNodeName);

		JLabel lblStartTime = new JLabel("Processing File: " + _file);
		lblStartTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblStartTime.setBounds(22, 36, 550, 21);
		ProcessingDetailsPanel.add(lblStartTime);

		lblEndTime = new JLabel("Current Status: Processing...");
		lblEndTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblEndTime.setBounds(22, 60, 550, 28);
		ProcessingDetailsPanel.add(lblEndTime);

		lblCurrentBestCost = new JLabel("Current Best Cost Found:");
		lblCurrentBestCost.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblCurrentBestCost.setBounds(22, 99, 550, 14);
		ProcessingDetailsPanel.add(lblCurrentBestCost);

		lblNewLabel = new JLabel("Number of States Processed:");
		lblNewLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblNewLabel.setBounds(22, 135, 550, 14);
		ProcessingDetailsPanel.add(lblNewLabel);

		lblMemoryUsage = new JLabel("Memory Usage:");
		lblMemoryUsage.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblMemoryUsage.setBounds(22, 170, 550, 20);
		ProcessingDetailsPanel.add(lblMemoryUsage);


		JButton ScatterButton = new JButton("<html><center>State Space Time Plot</center></html>");
		JButton LineButton = new JButton("<html><center>Schedules Time Chart</center></html>");

		ScatterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LineButton.setEnabled(true);
				//		 		compareSchedules compare = new compareSchedules();
				//		       // LineGraph compare = new LineGraph();
				//				JFreeChart chart = compare.createStateSpaceGraph();
				//				 JPanel scatterPanel = new JPanel();
				//				 scatterPanel.setBounds(492, 11, 572, 435);
				//				 contentPane.add(scatterPanel);
				//				 scatterPanel.setLayout(null);
				//				 ChartPanel CP = new ChartPanel(chart);
				//				 CP.setBorder(new LineBorder(new Color(0, 0, 0)));
				//				 CP.setBounds(0, 0, 572, 434);
				//				 scatterPanel.add(CP);
				ScatterButton.setEnabled(false);


			}
		});
		LineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScatterButton.setEnabled(true);

				LineButton.setEnabled(false);

			}
		});
		ScatterButton.setVerticalAlignment(SwingConstants.TOP);
		ScatterButton.setFont(new Font("Leelawadee", Font.PLAIN, 14));


		ScatterButton.setBounds(1074, 11, 94, 81);
		contentPane.add(ScatterButton);


		LineButton.setVerticalAlignment(SwingConstants.TOP);
		LineButton.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		LineButton.setBounds(1074, 100, 94, 81);
		contentPane.add(LineButton);




		//BufferedImage myPicture = ImageIO.read(new File("./P1"));
		//JLabel imageLabel = new JLabel(new ImageIcon(myPicture));
		//imageLabel.setBounds(10, 608, 109, 14);
		//getContentPane().add(imageLabel);




	}

	public void setNewLabel(String lblNewLabel, Double bestCost){
		this.lblNewLabel.setText("Number of states processed: "+lblNewLabel);
		this.lblCurrentBestCost.setText("Current Best Cost Found: " + bestCost);
	}

	public void setNewLabel2(String status, String string){
		this.lblEndTime.setText(status);
		this.lblMemoryUsage.setText("Memory Usage: " + string);
	}

	public int getProcessorCount(){
		return _processors;
	}


}