package visualisation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jgrapht.ext.ImportException;

import scheduling.LaunchScheduler;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;

public class mainMenu extends JFrame {

	private JPanel contentPane;
	private String _fileName;
	private JTextField processorCountTextField;
	private Integer _noOfProcessors;

	/**
	 * Launch the application.
	 */
	public static void beginLaunch() {
		 try {
	            // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel(
	        		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainMenu frame = new mainMenu();
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
	public mainMenu() {
		setTitle("Processor Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		LaunchScheduler ls = new LaunchScheduler();


		JLabel currentFileNameLabel = new JLabel("No File Chosen");
		currentFileNameLabel.setFont(new Font("Leelawadee", Font.PLAIN, 12));
		currentFileNameLabel.setBounds(532, 225, 139, 15);
		contentPane.add(currentFileNameLabel);

		processorCountTextField = new JTextField();
		processorCountTextField.setBounds(661, 290, 86, 20);
		contentPane.add(processorCountTextField);
		processorCountTextField.setColumns(10);

		JButton btnNewButton = new JButton("Start Processing");
		btnNewButton.setFont(new Font("Leelawadee", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_noOfProcessors = Integer.parseInt(processorCountTextField.getText());
				ls.setFileName(_fileName);
				ls.setProcessor(_noOfProcessors);
				try {
					ls.beginScheduling();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ImportException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//this will move on to the next screen for processing and showing data

				ProcessorScreen p = new ProcessorScreen();
				p.beginProcessing();
				dispose();

			}
		});
		btnNewButton.setBounds(461, 541, 359, 58);
		contentPane.add(btnNewButton);

		JButton btnChooseFile = new JButton("Choose File...");
		btnChooseFile.setFont(new Font("Leelawadee", Font.PLAIN, 15));
		btnChooseFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("dot file", "dot");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					_fileName = chooser.getSelectedFile().getName();
				}
				if (_fileName != null) {
					currentFileNameLabel.setText(_fileName);
				}
			}
		});
		btnChooseFile.setBounds(510, 194, 222, 25);
		contentPane.add(btnChooseFile);

		JLabel processorCountLabel = new JLabel("Number of Processors:");
		processorCountLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		processorCountLabel.setBounds(510, 287, 141, 25);
		contentPane.add(processorCountLabel);

	}
}
