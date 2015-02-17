package ca.tonsaker.codelauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ProjectLoader {
	
	public String dir;
	public ArrayList<File> files = new ArrayList<File>();
	
	public ProjectLoader(String dir){
		this.dir = dir;
		this.loadFiles();
		for(String s : getFileNames()) System.out.println("Loaded: "+s);
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
			return;
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
	
	public static boolean saveDir(String directory) throws IOException{
		String pathDir = System.getenv("APPDATA")+"\\CodeLauncher";
		String path = pathDir+"\\options.dat";
		File file = new File(path);
		if(!file.exists()){
			File tmp = new File(pathDir);
			if(!tmp.exists()) tmp.mkdirs(); 
			file.createNewFile();
		}
		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		
		writer.write(directory);
		writer.flush();
		writer.close();
		
		System.out.println("Directory saved to: "+path); 
		return true;
	}
	
	public static String loadDir() throws IOException{
		String path = System.getenv("APPDATA")+"\\CodeLauncher\\options.dat";
		FileInputStream file = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		String loadedDir = reader.readLine();
		
		reader.close();
		file.close();
		
		System.out.println("Directory loaded: "+path);
		return loadedDir;
	}
	
}
