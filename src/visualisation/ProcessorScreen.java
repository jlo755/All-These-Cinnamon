package visualisation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import scheduling.DVScheduler;
import scheduling.LaunchScheduler;
import scheduling.ScheduleFactory;
import scheduling.ScheduleWorker;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.GridLayout;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ProcessorScreen extends JFrame {

	private JPanel _contentPane;
	private String _file;
	private Integer _processorCount;
	private JPanel _graphPanel;
	private JPanel _statisticsPanel;
	private VisualController _vc;
	private JLabel lblProcessorCount;
	private JLabel lblEndTime;
	private JLabel lblCurrentBestCost;
	private JLabel lblMemoryUsage;
	private JLabel processorLegendLabel1;
	private JLabel processorLegendLabel2;
	private JLabel processorLegendLabel3;
	private JLabel processorLegendLabel4;
	private JLabel processorLegendLabel5;
	private JLabel processorLegendLabel6;
	private JLabel processorLegendLabel7;
	private JLabel lblTimeTaken;

	/**
	 * Launch the application.
	 */
	public void beginProcessing() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
					setProcessorLabels();
					if(ScheduleFactory.getInstance().getParallelise() > 1) {
						DVScheduler _scheduler = LaunchScheduler._scheduler;
						_scheduler.setVisualController(_vc);
						_vc.setVisualModel(_scheduler);
						ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
						threadExecutor.submit(new Runnable(){

							@Override
							public void run() {
								_scheduler.schedule();
							}

						});
					} else {
						ScheduleWorker sw = new ScheduleWorker(LaunchScheduler._noOfProcessors, _vc);
						sw.execute();
					}
				} catch (Exception e) {

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
		_processorCount = launchS._noOfProcessors;
		if(_processorCount < 1){
			JOptionPane.showMessageDialog(this,
				    "Please change the processor count input to something valid",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}else{
		setTitle("Data Visualization of Scheduler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1079, 740);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);

		_graphPanel = new JPanel(new GridLayout()){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(460,567);
			}
		};
		_graphPanel.setBounds(10, 11, 460, 640);
		_contentPane.add(_graphPanel);

		_statisticsPanel = new JPanel();
		_statisticsPanel.setBounds(480, 11, 572, 435);
		_contentPane.add(_statisticsPanel);
		_statisticsPanel.setLayout(null);

		_vc = new VisualController(LaunchScheduler.dotParser.getNodeMap(), _graphPanel, _statisticsPanel, this);


		JPanel ProcessingDetailsPanel = new JPanel();
		ProcessingDetailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProcessingDetailsPanel.setBounds(480, 450, 572, 251);
		_contentPane.add(ProcessingDetailsPanel);
		ProcessingDetailsPanel.setLayout(null);

		JLabel lblNodeName = new JLabel("Number Of Processors: "+ _processorCount);
		lblNodeName.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblNodeName.setBounds(22, 11, 550, 21);
		ProcessingDetailsPanel.add(lblNodeName);

		JLabel lblStartTime = new JLabel("Processing File: " + _file);
		lblStartTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblStartTime.setBounds(22, 44, 550, 21);
		ProcessingDetailsPanel.add(lblStartTime);

		lblEndTime = new JLabel("Current Status: Processing...");
		lblEndTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblEndTime.setBounds(22, 76, 550, 21);
		ProcessingDetailsPanel.add(lblEndTime);

		lblCurrentBestCost = new JLabel("Current Best Cost Found:");
		lblCurrentBestCost.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblCurrentBestCost.setBounds(22, 108, 550, 21);
		ProcessingDetailsPanel.add(lblCurrentBestCost);

		lblProcessorCount = new JLabel("Number of States Processed:");
		lblProcessorCount.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblProcessorCount.setBounds(22, 140, 550, 21);
		ProcessingDetailsPanel.add(lblProcessorCount);

		lblMemoryUsage = new JLabel("Memory Usage: ...");
		lblMemoryUsage.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblMemoryUsage.setBounds(22, 172, 550, 21);
		ProcessingDetailsPanel.add(lblMemoryUsage);

		lblTimeTaken = new JLabel("Time Taken: ... ");
		lblTimeTaken.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		lblTimeTaken.setBounds(22, 204, 550, 21);
		ProcessingDetailsPanel.add(lblTimeTaken);

		processorLegendLabel1 = new JLabel("Processor 1");
		processorLegendLabel1.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel1.setBounds(10, 662, 73, 14);
		processorLegendLabel1.setForeground(new Color(220,40,80));
		_contentPane.add(processorLegendLabel1);
		processorLegendLabel1.setVisible(false);

		processorLegendLabel2 = new JLabel("Processor 2");
		processorLegendLabel2.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel2.setBounds(93, 662, 73, 14);
		processorLegendLabel2.setForeground(new Color(37,128,57));
		_contentPane.add(processorLegendLabel2);
		processorLegendLabel2.setVisible(false);


		processorLegendLabel3 = new JLabel("Processor 3");
		processorLegendLabel3.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel3.setBounds(176, 662, 73, 14);
		processorLegendLabel3.setForeground(new Color(253,110,41));
		_contentPane.add(processorLegendLabel3);
		processorLegendLabel3.setVisible(false);


		processorLegendLabel4 = new JLabel("Processor 4");
		processorLegendLabel4.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel4.setBounds(259, 662, 73, 14);
		processorLegendLabel4.setForeground(new Color(72,151,216));
		_contentPane.add(processorLegendLabel4);
		processorLegendLabel4.setVisible(false);


		processorLegendLabel5 = new JLabel("Processor 5");
		processorLegendLabel5.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel5.setBounds(342, 662, 73, 14);
		processorLegendLabel5.setForeground(new Color(139,0,139));
		_contentPane.add(processorLegendLabel5);
		processorLegendLabel5.setVisible(false);


		processorLegendLabel6 = new JLabel("Processor 6");
		processorLegendLabel6.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel6.setBounds(10, 687, 73, 14);
		processorLegendLabel6.setForeground(new Color(199,68,183));
		_contentPane.add(processorLegendLabel6);
		processorLegendLabel6.setVisible(false);


		processorLegendLabel7 = new JLabel("Processor 7");
		processorLegendLabel7.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		processorLegendLabel7.setBounds(93, 687, 73, 14);
		processorLegendLabel7.setForeground(new Color(128,128,0));
		_contentPane.add(processorLegendLabel7);
		processorLegendLabel7.setVisible(false);
		setResizable(false);

		}


	}


	/**
	 * Launch the application.
	 */
	public void setNewLabel(String lblNewLabel, Double bestCost){
		this.lblProcessorCount.setText("Number of states processed: "+lblNewLabel);
		this.lblCurrentBestCost.setText("Current Best Cost Found: " + bestCost);

	}

	/**
	 * Launch the application.
	 */
	public void setLabelTime(Double overallTimer){
		this.lblTimeTaken.setText("Time Taken: " + overallTimer + " Seconds" );

	}

	/**
	 * Launch the application.
	 */
	public void setNewLabel2(String status, String string){
		this.lblEndTime.setText(status);
		this.lblMemoryUsage.setText("Memory Usage: " + string);
	}

	/**
	 * Launch the application.
	 */
	public int getProcessorCount(){
		return _processorCount;
	}

	/**
	 * Launch the application.
	 */
	public void setProcessorLabels(){
		if(_processorCount > 7){
			processorLegendLabel1.setText("Live updates are not supported for more than 7 processors");
			processorLegendLabel1.setForeground(new Color(0,0,0));
		}
			for(int i = 1; i<= _processorCount; i++){
				switch (i) {

				case 1:{
					processorLegendLabel1.setVisible(true);
					continue;
				}
				case 2:{

					processorLegendLabel2.setVisible(true);
					continue;
				}

				case 3:{

					processorLegendLabel3.setVisible(true);
					continue;
				}

				case 4:{

					processorLegendLabel4.setVisible(true);
					continue;

				}
				case 5:{

					processorLegendLabel5.setVisible(true);
					continue;

				}
				case 6:{

					processorLegendLabel6.setVisible(true);
					break;

				}

				case 7:{

					processorLegendLabel7.setVisible(true);
					continue;
				}
				default:{
					break;
				}
			}
		}
	}
}
