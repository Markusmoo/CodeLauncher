package ca.tonsaker.codelauncher;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Toolkit;


public class CodeLauncherMain {

	public static String FILE_DIR;
	
	private JFrame frmMarkus;
	private ProjectLoader pLoader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CodeLauncherMain window = new CodeLauncherMain();
					window.frmMarkus.setVisible(true);
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
		frmMarkus = new JFrame();
		frmMarkus.setIconImage(Toolkit.getDefaultToolkit().getImage(CodeLauncherMain.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmMarkus.setTitle("Markus Tonsaker's Code Launcher");
		frmMarkus.setResizable(false);
		frmMarkus.getContentPane().setLayout(null);
		frmMarkus.setPreferredSize(new Dimension(640, 640));
		frmMarkus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMarkus.setLocation(100, 100);
		
		JList<String> list_1 = new JList<String>(pLoader.getFileNames());
		//list_1.setBounds(10, 11, 132, 317);
		
		JScrollPane scroll = new JScrollPane(list_1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 11, 132, 590);
		scroll.getViewport().setView(list_1);
		frmMarkus.getContentPane().add(scroll);
		
		frmMarkus.pack();
	}
}
