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
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


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
	private JLabel lblProcessor_1;
	private JLabel lblProcessor_2;
	private JLabel lblProcessor_3;
	private JLabel lblProcessor_4;
	private JLabel lblProcessor_5;
	private JLabel lblProcessor_6;
	private JLabel lblProcessor_7;

	/**
	 * Launch the application.
	 */
	public void beginProcessing() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ProcessorScreen frame = new ProcessorScreen();
					setVisible(true);
					setProcessorLabels();
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
		LaunchScheduler launchS = new LaunchScheduler();
		_file = launchS._fileName;
		_processors = launchS._noOfProcessors;
		if(_processors < 1){
			JOptionPane.showMessageDialog(this,
				    "Please change the processor count input to something valid",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}else{
		setTitle("Processor Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1089, 778);
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
		ProcessingDetailsPanel.setBounds(492, 450, 572, 279);
		contentPane.add(ProcessingDetailsPanel);
		ProcessingDetailsPanel.setLayout(null);

		JLabel lblNodeName = new JLabel("Number Of Processors: "+ _processors);
		lblNodeName.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblNodeName.setBounds(22, 11, 550, 21);
		ProcessingDetailsPanel.add(lblNodeName);

		JLabel lblStartTime = new JLabel("Processing File: " + _file);
		lblStartTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblStartTime.setBounds(22, 44, 550, 21);
		ProcessingDetailsPanel.add(lblStartTime);

		lblEndTime = new JLabel("Current Status: Processing...");
		lblEndTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblEndTime.setBounds(22, 111, 550, 21);
		ProcessingDetailsPanel.add(lblEndTime);

		lblCurrentBestCost = new JLabel("Current Best Cost Found:");
		lblCurrentBestCost.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblCurrentBestCost.setBounds(22, 143, 550, 21);
		ProcessingDetailsPanel.add(lblCurrentBestCost);

		lblNewLabel = new JLabel("Number of States Processed:");
		lblNewLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblNewLabel.setBounds(22, 175, 550, 21);
		ProcessingDetailsPanel.add(lblNewLabel);

		lblMemoryUsage = new JLabel("Memory Usage:");
		lblMemoryUsage.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblMemoryUsage.setBounds(22, 207, 550, 21);
		ProcessingDetailsPanel.add(lblMemoryUsage);

		JLabel lblTimeTaken = new JLabel("Time Taken:");
		lblTimeTaken.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblTimeTaken.setBounds(22, 79, 550, 21);
		ProcessingDetailsPanel.add(lblTimeTaken);

		lblProcessor_1 = new JLabel("Processor 1");
		lblProcessor_1.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_1.setBounds(10, 662, 73, 14);
		lblProcessor_1.setForeground(new Color(220,40,80));
		contentPane.add(lblProcessor_1);
		lblProcessor_1.setVisible(false);

		lblProcessor_2 = new JLabel("Processor 2");
		lblProcessor_2.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_2.setBounds(10, 687, 73, 14);
		lblProcessor_2.setForeground(new Color(37,128,57));
		contentPane.add(lblProcessor_2);
		lblProcessor_2.setVisible(false);


		lblProcessor_3 = new JLabel("Processor 3");
		lblProcessor_3.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_3.setBounds(10, 712, 73, 14);
		lblProcessor_3.setForeground(new Color(255,20,147));
		contentPane.add(lblProcessor_3);
		lblProcessor_3.setVisible(false);


		lblProcessor_4 = new JLabel("Processor 4");
		lblProcessor_4.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_4.setBounds(93, 662, 73, 14);
		lblProcessor_4.setForeground(new Color(72,151,216));
		contentPane.add(lblProcessor_4);
		lblProcessor_4.setVisible(false);


		lblProcessor_5 = new JLabel("Processor 5");
		lblProcessor_5.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_5.setBounds(93, 688, 73, 14);
		lblProcessor_5.setForeground(new Color(139,0,139));
		contentPane.add(lblProcessor_5);
		lblProcessor_5.setVisible(false);


		lblProcessor_6 = new JLabel("Processor 6");
		lblProcessor_6.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_6.setBounds(93, 713, 73, 14);
		lblProcessor_6.setForeground(new Color(255,140,0));
		contentPane.add(lblProcessor_6);
		lblProcessor_6.setVisible(false);


		lblProcessor_7 = new JLabel("Processor 7");
		lblProcessor_7.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		lblProcessor_7.setBounds(176, 663, 73, 14);
		lblProcessor_7.setForeground(new Color(128,128,0));
		contentPane.add(lblProcessor_7);
		lblProcessor_7.setVisible(false);

		}


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
	public void setProcessorLabels(){
		if(_processors > 7){
			lblProcessor_1.setText("Live updates are not supported for more than 7 processors");
			lblProcessor_1.setForeground(new Color(0,0,0));
		}
			for(int i =1; i<= _processors; i++){
				switch (i) {

				case 1:{
					lblProcessor_1.setVisible(true);
					continue;
				}
				case 2:{

					lblProcessor_2.setVisible(true);
					continue;
				}

				case 3:{

					lblProcessor_3.setVisible(true);
					continue;
				}

				case 4:{

					lblProcessor_4.setVisible(true);
					continue;

				}
				case 5:{

					lblProcessor_5.setVisible(true);
					continue;

				}
				case 6:{

					lblProcessor_6.setVisible(true);
					break;

				}

				case 7:{

					lblProcessor_7.setVisible(true);
					continue;
				}
				default:{
					break;
				}
			}
		}
	}
}
