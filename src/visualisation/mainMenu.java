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
import javax.swing.SwingConstants;

public class mainMenu extends JFrame {

	private JPanel contentPane;
	private String _fileName;
	private Integer _noOfProcessors;

	/**
	 * Launch the application.
	 */
	public static void beginLaunch() {
		 try {
	            // Set cross-platform Java L&F (also called "Metal")
	       // UIManager.setLookAndFeel(
	        //		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		setBounds(100, 100, 1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Start Processing");
		btnNewButton.setFont(new Font("Leelawadee", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				ProcessorScreen p = new ProcessorScreen();
				p.beginProcessing();
				dispose();

			}
		});
		btnNewButton.setBounds(391, 541, 359, 58);
		contentPane.add(btnNewButton);

	}
}
