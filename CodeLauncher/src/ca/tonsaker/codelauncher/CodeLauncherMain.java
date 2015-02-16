package ca.tonsaker.codelauncher;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;


public class CodeLauncherMain {

	public static String FILE_DIR;
	
	private JFrame frame;
	private ProjectLoader pLoader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CodeLauncherMain window = new CodeLauncherMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CodeLauncherMain() {
		FILE_DIR = JOptionPane.showInputDialog("Enter list of file directories (One Time):", "C:\\");
		pLoader = new ProjectLoader(FILE_DIR);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		//for()
		//listModel.addElement(element);
		JList<String> list = new JList<String>(listModel);
	}

}
