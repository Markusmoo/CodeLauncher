package ca.tonsaker.codelauncher;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import ca.tonsaker.codelauncher.ProjectLoader.Project;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class CodeLauncherMain implements ActionListener,TreeSelectionListener {

	public static String FILE_DIR;
	
	private JFrame frmMarkus;
	private ProjectLoader pLoader;
	private DefaultMutableTreeNode treeList;
	
	private JTree tree;
	private RSyntaxTextArea textArea;
	
	private JButton btnRun;
	
	private HashMap<String, DefaultMutableTreeNode> projects = new HashMap<String, DefaultMutableTreeNode>();

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
		createTree();
	}
	
	private void createTree(){
		ArrayList<Project> jars = pLoader.projects;
		for(Project jar : jars){
			this.addProjectNode(jar.getJar().getName());
			for(File p : jar.getClasses()){
				this.addNode(jar.getJar().getName(), p.getName());
			}
		}
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
		
		JPanel panel = new JPanel();
		panel.setBounds(180, 11, 444, 536);
		
		textArea = new RSyntaxTextArea(33, 56);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setEditable(false);
		textArea.setText("No source available");
		RTextScrollPane sp = new RTextScrollPane(textArea);
		panel.add(sp);
		
		frmMarkus.getContentPane().add(panel);
		
		//JList<String> tree = new JList<String>(pLoader.getJarFileNames());
		tree = new JTree(); //TODO for appbuilder
		treeList = new DefaultMutableTreeNode("Projects");
		tree.addTreeSelectionListener(this);
		
		tree.setModel(new DefaultTreeModel(treeList));
		
		JScrollPane scroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 11, 160, 570);
		scroll.getViewport().setView(tree);
		frmMarkus.getContentPane().add(scroll);
		
		btnRun = new JButton("Run");
		btnRun.setBounds(180, 558, 444, 23);
		frmMarkus.getContentPane().add(btnRun);
		btnRun.addActionListener(this);
		
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
	
	public void addProjectNode(String name){
		DefaultMutableTreeNode def = new DefaultMutableTreeNode(name);
		projects.put(name, def);
		treeList.add(def);
	}
	
	public void addNode(String project, String name){
		DefaultMutableTreeNode def = new DefaultMutableTreeNode(name);
		projects.get(project).add(def);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src.equals(btnRun)){
			
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		
		if(node == null) return;
		
		Project selProject;
		boolean isJar;
		File selFile = null;
		
		String sel = node.getUserObject().toString();

		outerloop: for(Project pro : pLoader.projects){
			if(pro.getJar().getName().equals(sel)){
				selProject = pro;
				selFile = pro.getJar();
				isJar = true;
				break;
			}
			for(File classFile : pro.getClasses()){
				if(classFile.getName().equals(sel)){
					selProject = pro;
					selFile = classFile;
					isJar = false;
					break outerloop;
				}
			}
		}
		
		System.out.println(selFile);
		
		if(selFile != null && selFile.isFile() && selFile.getName().toLowerCase().contains(".java")){
			textArea.setText("");
			try(BufferedReader br = new BufferedReader(new FileReader(selFile))) {
				for(String line; (line = br.readLine()) != null; ) {
			    	textArea.append(line+"\n");
			    }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//if() //TODO read source files and prep jar for running
	}
}
