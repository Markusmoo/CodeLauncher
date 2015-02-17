package ca.tonsaker.codelauncher;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


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
		try {
			FILE_DIR = ProjectLoader.loadDir();
		} catch (IOException e) {
			FILE_DIR = JOptionPane.showInputDialog("Enter folder directory (Directory will be remembered):", "C:\\");
			try {
				ProjectLoader.saveDir(FILE_DIR);
			} catch (IOException e1) {
				e1.printStackTrace(); //TODO Error Logger
			}
			if(FILE_DIR == null){
				System.exit(0);
			}
		}
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
		//JList<String> list_1 = new JList<String>(); //TODO for appbuilder
		
		//TODO add syntax textbox https://github.com/bobbylight/RSyntaxTextArea
		
		JScrollPane scroll = new JScrollPane(list_1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 11, 160, 570);
		scroll.getViewport().setView(list_1);
		frmMarkus.getContentPane().add(scroll);
		
		//list_1.
		
		JMenuBar menuBar = new JMenuBar();
		frmMarkus.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmChangeFolderDirectory = new JMenuItem("Change Folder Directory");
		mnFile.add(mntmChangeFolderDirectory);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnCode = new JMenu("Code");
		menuBar.add(mnCode);
		
		JMenuItem mntmRun = new JMenuItem("Run");
		mnCode.add(mntmRun);
		
		JMenuItem mntmViewSourceCode = new JMenuItem("View Source Code");
		mnCode.add(mntmViewSourceCode);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmViewErrorLogs = new JMenuItem("View Error Logs");
		mnHelp.add(mntmViewErrorLogs);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		frmMarkus.pack();
	}
}
