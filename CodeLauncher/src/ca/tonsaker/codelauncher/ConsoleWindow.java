package ca.tonsaker.codelauncher;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import ca.tonsaker.codelauncher.ProjectLoader.Project;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;

public class ConsoleWindow implements Runnable{
	
	protected String javaDir;
	
	protected Project project;
	protected Thread runThread;

	protected JFrame frame;
	protected JScrollPane scroll;
	protected JTextArea textPane;
	
	public ConsoleWindow(Project pro, String javaDir) {
		project = pro;
		this.javaDir = javaDir;
		runThread = new Thread(this);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	protected void initialiseGUI(){
		frame = new JFrame("Console Window for "+project.getJar().getName());
		frame.setLocation(100, 100);
		frame.setPreferredSize(new Dimension(480, 480));
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
	
		textPane = new JTextArea();
		scroll = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scroll, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scroll, 5, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scroll, -5, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scroll, -5, SpringLayout.EAST, frame.getContentPane());
		scroll.add(textPane);
		frame.getContentPane().add(scroll);
	}

	@Override
	public void run() {
		ProcessBuilder pb = new ProcessBuilder(javaDir, "-jar", project.getJar().getAbsolutePath());
		pb.directory(new File(project.getProjectDir()));
		Process p = null;
		InputStreamReader outRead;
		InputStreamReader errRead;
		try {
			p = pb.start();
			outRead = new InputStreamReader(p.getInputStream());
			errRead = new InputStreamReader(p.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int outBuff;
		char[] outBufferChar = new char[1024];
		StringBuffer outSBuffer = new StringBuffer();
		try {
			while((outBuff = outRead.read(outBufferChar)) > 0) {
				outSBuffer.append(outBufferChar, 0, outBuff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int errBuff;
		char[] errBufferChar = new char[1024];
		StringBuffer errSBuffer = new StringBuffer();
		try {
			while((errBuff = errRead.read(errBufferChar)) > 0) {
				errSBuffer.append(errBufferChar, 0, errBuff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
