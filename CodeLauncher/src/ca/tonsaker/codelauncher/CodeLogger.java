package ca.tonsaker.codelauncher;

import javax.swing.JOptionPane;

public abstract class CodeLogger {
	
	public static void showErrorMessage(String message){
		JOptionPane.showMessageDialog(null, "Report this error to Markus..\n\n" + message, "An Unexpected Error Occured", JOptionPane.ERROR_MESSAGE);
	}

}
