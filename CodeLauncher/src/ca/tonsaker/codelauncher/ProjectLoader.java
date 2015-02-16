package ca.tonsaker.codelauncher;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ProjectLoader {
	
	public String dir;
	public ArrayList<File> files = new ArrayList<File>();
	
	public ProjectLoader(String dir){
		this.dir = dir;
		loadFiles();
		for(String s : getFileNames()) System.out.println(s);
	}

	public String[] getFileNames(){
		String[] fileNames = new String[files.size()];
		int idx = 0;
		for(File i : files){
			fileNames[idx] = i.getName();
			idx++;
		}
		return fileNames;
	}
	
	public void loadFiles(){
		File tempFile = new File(dir);
		if(!tempFile.exists()){
			tempFile.mkdirs();
		}else{
			for(File f : tempFile.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File e, String s) {
					return s.toLowerCase().endsWith(".jar");
				}
				
			})){
				files.add(f);
			}
		}
	}
	
}
