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
	private JLabel noOfProcessorsLabel;
	private JLabel endTimeLabel;
	private JLabel currentBestCostLabel;
	private JLabel memoryUsageLabel;
	private JLabel legendLabel1;
	private JLabel legendLabel2;
	private JLabel legendLabel3;
	private JLabel legendLabel4;
	private JLabel legendLabel5;
	private JLabel legendLabel6;
	private JLabel legendLabel7;
	private JLabel finalTimeTakenLabel;

	/**
	 * Launch the application GUI along with instantiating the ScheduleWorker that implements a SwingWorker to run the
	 * the algorithm in the background when the GUI is being executed on the ED thread
	 */
	public void beginProcessing() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
					//sets the legend for the graph
					setProcessorLabels();
					if(ScheduleFactory.getInstance().getParallelise() > 1) {
						DVScheduler _scheduler = LaunchScheduler._scheduler;
						_scheduler.setVisualController(_vc);
						_vc.setVisualModel(_scheduler);
						//scheduling is done on a seperate thread
						ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
						threadExecutor.submit(new Runnable(){

							@Override
							public void run() {
								_scheduler.schedule();
							}

						});
					} else {
						//overridden SwingWorker implementation
						ScheduleWorker sw = new ScheduleWorker(LaunchScheduler._noOfProcessors, _vc);
						//runs the algorithm
						sw.execute();
					}
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProcessorScreen() throws IOException {
		LaunchScheduler launchS = new LaunchScheduler();

		//gets arguments from the command line 
		_file = launchS._fileName;
		_processorCount = launchS._noOfProcessors;

		if(_processorCount < 1){
			JOptionPane.showMessageDialog(this,
				    "Please change the processor count input to something valid",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		else {
		setTitle("Processor Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1079, 778);
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

		LaunchScheduler ls = new LaunchScheduler();
		//_noOfProcessors = Integer.parseInt(processorCountTextField.getText());
		//ls.setFileName(_fileName);
		//ls.setProcessor(_noOfProcessors);


		//compareSchedules compare = new compareSchedules();

		//VisualStatistics compare = new VisualStatistics();

		//JFreeChart chart = compare.createStateSpaceGraph();

		JPanel ProcessingDetailsPanel = new JPanel();
		ProcessingDetailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProcessingDetailsPanel.setBounds(480, 450, 572, 279);
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

		endTimeLabel = new JLabel("Current Status: Processing...");
		endTimeLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		endTimeLabel.setBounds(22, 111, 550, 21);
		ProcessingDetailsPanel.add(endTimeLabel);

		currentBestCostLabel = new JLabel("Current Best Cost Found:");
		currentBestCostLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		currentBestCostLabel.setBounds(22, 143, 550, 21);
		ProcessingDetailsPanel.add(currentBestCostLabel);

		noOfProcessorsLabel = new JLabel("Number of States Processed:");
		noOfProcessorsLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		noOfProcessorsLabel.setBounds(22, 175, 550, 21);
		ProcessingDetailsPanel.add(noOfProcessorsLabel);

		memoryUsageLabel = new JLabel("Memory Usage:");
		memoryUsageLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		memoryUsageLabel.setBounds(22, 207, 550, 21);
		ProcessingDetailsPanel.add(memoryUsageLabel);

		finalTimeTakenLabel = new JLabel("Time Taken:");
		finalTimeTakenLabel.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		finalTimeTakenLabel.setBounds(22, 79, 550, 21);
		ProcessingDetailsPanel.add(finalTimeTakenLabel);

		legendLabel1 = new JLabel("Processor 1");
		legendLabel1.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel1.setBounds(10, 662, 73, 14);
		legendLabel1.setForeground(new Color(220,40,80));
		_contentPane.add(legendLabel1);
		legendLabel1.setVisible(false);

		legendLabel2 = new JLabel("Processor 2");
		legendLabel2.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel2.setBounds(10, 687, 73, 14);
		legendLabel2.setForeground(new Color(37,128,57));
		_contentPane.add(legendLabel2);
		legendLabel2.setVisible(false);


		legendLabel3 = new JLabel("Processor 3");
		legendLabel3.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel3.setBounds(10, 712, 73, 14);
		legendLabel3.setForeground(new Color(253,110,41));
		_contentPane.add(legendLabel3);
		legendLabel3.setVisible(false);


		legendLabel4 = new JLabel("Processor 4");
		legendLabel4.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel4.setBounds(93, 662, 73, 14);
		legendLabel4.setForeground(new Color(72,151,216));
		_contentPane.add(legendLabel4);
		legendLabel4.setVisible(false);


		legendLabel5 = new JLabel("Processor 5");
		legendLabel5.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel5.setBounds(93, 688, 73, 14);
		legendLabel5.setForeground(new Color(139,0,139));
		_contentPane.add(legendLabel5);
		legendLabel5.setVisible(false);


		legendLabel6 = new JLabel("Processor 6");
		legendLabel6.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel6.setBounds(93, 713, 73, 14);
		legendLabel6.setForeground(new Color(255,140,0));
		_contentPane.add(legendLabel6);
		legendLabel6.setVisible(false);


		legendLabel7 = new JLabel("Processor 7");
		legendLabel7.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		legendLabel7.setBounds(176, 663, 73, 14);
		legendLabel7.setForeground(new Color(128,128,0));
		_contentPane.add(legendLabel7);
		legendLabel7.setVisible(false);

		}


		//BufferedImage myPicture = ImageIO.read(new File("./P1"));
		//JLabel imageLabel = new JLabel(new ImageIcon(myPicture));
		//imageLabel.setBounds(10, 608, 109, 14);
		//getContentPane().add(imageLabel);


	}


	public void setNewLabel(String lblNewLabel, Double bestCost){
		this.noOfProcessorsLabel.setText("Number of states processed: "+lblNewLabel);
		this.currentBestCostLabel.setText("Current Best Cost Found: " + bestCost);

	}
	public void setLabelTime(Double overallTimer){
		this.finalTimeTakenLabel.setText("Time Taken: " + overallTimer + " Seconds" );

	}


	public void setNewLabel2(String status, String string){
		this.endTimeLabel.setText(status);
		this.memoryUsageLabel.setText("Memory Usage: " + string);
	}

	public int getProcessorCount(){
		return _processorCount;
	}
	public void setProcessorLabels(){
		if(_processorCount > 7){
			legendLabel1.setText("Live updates are not supported for more than 7 processors");
			legendLabel1.setForeground(new Color(0,0,0));
		}
			for(int i = 1; i<= _processorCount; i++){
				switch (i) {

				case 1:{
					legendLabel1.setVisible(true);
					continue;
				}
				case 2:{

					legendLabel2.setVisible(true);
					continue;
				}

				case 3:{

					legendLabel3.setVisible(true);
					continue;
				}

				case 4:{

					legendLabel4.setVisible(true);
					continue;

				}
				case 5:{

					legendLabel5.setVisible(true);
					continue;

				}
				case 6:{

					legendLabel6.setVisible(true);
					break;

				}

				case 7:{

					legendLabel7.setVisible(true);
					continue;
				}
				default:{
					break;
				}
			}
		}
	}
}
