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

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ProjectLoader {
	
	public class Project{
		
		private String proDir;
		
		private boolean valid;
		
		private File zip;
		private File jar;
		private ArrayList<File> javaClasses;
		
		public Project(String zipDir){
			javaClasses = new ArrayList<File>();
			proDir = getProjectDir(zipDir);
			valid = unpackageZip(zipDir, proDir);
			if(valid) valid = setup();
		}
		
		protected String getProjectDir(String zip){
			zip = zip.substring(0, zip.lastIndexOf('.'));
			new File(zip).mkdirs();
			return zip;
		}
		
		private boolean setup(){
			File folder = new File(proDir);
			for(File f : folder.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File e, String s) {
					return s.toLowerCase().endsWith(".jar");
				}
				
			})) jar = f;
			
			for(File f : folder.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File e, String s) {
					return s.toLowerCase().endsWith(".java");
				}
				
			})) javaClasses.add(f);
			
			return true;
		}
		
		public boolean unpackageZip(String zipDir, String destDir){
			try{
				ZipFile zip = new ZipFile(zipDir);
				if(!zip.isValidZipFile() || zip.isEncrypted()){
					throw new IOException("Zip File at "+ zipDir +" is not valid or is encrypted.");
				}
				zip.extractAll(destDir);
			}catch (ZipException e){
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		public File getJar(){
			return jar;
		}
		
		public ArrayList<File> getClasses(){
			return javaClasses;
		}
		
		public File getZip(){
			return zip;
		}
		
		public boolean isValid(){
			return valid;
		}
		
	}
	
	public String dir;
	public ArrayList<Project> projects = new ArrayList<Project>();
	
	public ProjectLoader(String dir){
		this.dir = dir;
		this.loadFiles();
		for(String s : getJarFileNames()) System.out.println("Loaded: "+s);
	}

	public String[] getJarFileNames(){
		String[] fileNames = new String[projects.size()];
		int idx = 0;
		for(Project i : projects){
			fileNames[idx] = i.getJar().getName();
			idx++;
		}
		return fileNames;
	}
	
	public boolean loadFiles(){
		File tempFile = new File(dir);
		if(!tempFile.exists()){
			return false;
		}else{
			for(File f : tempFile.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File e, String s) {
					return s.toLowerCase().endsWith(".zip");
				}
				
			})){
				projects.add(new Project(f.getAbsolutePath()));
			}
		}
		return true;
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
		
		System.out.println("Directory loaded: "+loadedDir);
		return loadedDir;
	}
	
}
