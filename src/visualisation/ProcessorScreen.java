package visualisation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jgrapht.ext.ImportException;


import com.sun.prism.Image;

import visualisation.VisualGraph;

import scheduling.LaunchScheduler;
import statistics.compareSchedules;

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
import java.awt.event.ActionEvent;


public class ProcessorScreen extends JFrame {

	private JPanel contentPane;
	private String _fileName;
	private JTextField processorCountTextField;
	private Integer _noOfProcessors;

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
	 * @throws IOException
	 */
	public ProcessorScreen() throws IOException {
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

        JPanel graphPanel = new JPanel(new GridLayout()){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(460,567);
            }
        };
        graphPanel.setBounds(10, 30, 460, 567);
        contentPane.add(graphPanel);

        LaunchScheduler ls = new LaunchScheduler();
        //_noOfProcessors = Integer.parseInt(processorCountTextField.getText());
        //ls.setFileName(_fileName);
        //ls.setProcessor(_noOfProcessors);
        try {
            ls.beginScheduling(graphPanel);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ImportException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



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
		 ProcessingDetailsPanel.setBounds(492, 467, 582, 130);
		 contentPane.add(ProcessingDetailsPanel);
		 ProcessingDetailsPanel.setLayout(null);

		 JLabel lblNodeName = new JLabel("Node Name:");
		 lblNodeName.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblNodeName.setBounds(22, 11, 107, 14);
		 ProcessingDetailsPanel.add(lblNodeName);

		 JLabel lblStartTime = new JLabel("Start Time:");
		 lblStartTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblStartTime.setBounds(22, 36, 74, 21);
		 ProcessingDetailsPanel.add(lblStartTime);

		 JLabel lblEndTime = new JLabel("End Time:");
		 lblEndTime.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblEndTime.setBounds(22, 60, 74, 28);
		 ProcessingDetailsPanel.add(lblEndTime);

		 JLabel lblProcessor = new JLabel("Processor:");
		 lblProcessor.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		 lblProcessor.setBounds(22, 95, 74, 14);
		 ProcessingDetailsPanel.add(lblProcessor);

		 JLabel NodeNameOuputLabel = new JLabel("-");
		 NodeNameOuputLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		 NodeNameOuputLabel.setBounds(139, 11, 418, 14);
		 ProcessingDetailsPanel.add(NodeNameOuputLabel);

		 JLabel StartTimeOutputLabel = new JLabel("-");
		 StartTimeOutputLabel.setBounds(139, 41, 433, 14);
		 ProcessingDetailsPanel.add(StartTimeOutputLabel);

		 JLabel EndTimeOutputLabel = new JLabel("-");
		 EndTimeOutputLabel.setBounds(139, 69, 435, 14);
		 ProcessingDetailsPanel.add(EndTimeOutputLabel);

		 JLabel ProcessorOuputLabel = new JLabel("-");
		 ProcessorOuputLabel.setBounds(139, 97, 435, 14);
		 ProcessingDetailsPanel.add(ProcessorOuputLabel);

		 JPanel timerPanel = new JPanel();
		 FlowLayout flowLayout = (FlowLayout) timerPanel.getLayout();
		 flowLayout.setAlignment(FlowLayout.LEFT);
		 timerPanel.setBounds(677, 628, 169, 30);
		 contentPane.add(timerPanel);

		 JLabel lblNewLabel = new JLabel("Time Elapsed:");
		 lblNewLabel.setFont(new Font("Leelawadee", Font.PLAIN, 16));
		 timerPanel.add(lblNewLabel);

		 JLabel lblTime = new JLabel("00:00:00");
		 lblTime.setFont(new Font("Leelawadee", Font.PLAIN, 16));
		 timerPanel.add(lblTime);



		 /// START PROCESSING




		 JButton btnStartProcessing = new JButton("Start Processing");
		 btnStartProcessing.setVisible(false);
		 btnStartProcessing.setFont(new Font("Leelawadee", Font.PLAIN, 16));
		 btnStartProcessing.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {







		 	}
		 });
		 btnStartProcessing.setBounds(856, 628, 218, 23);
		 contentPane.add(btnStartProcessing);




		 //BufferedImage myPicture = ImageIO.read(new File("./P1"));
		 //JLabel imageLabel = new JLabel(new ImageIcon(myPicture));
		 //imageLabel.setBounds(10, 608, 109, 14);
		 //getContentPane().add(imageLabel);




	}
}