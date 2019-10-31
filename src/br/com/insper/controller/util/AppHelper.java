package br.com.insper.controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppHelper {

	private String appPath;
	private String adbPath;
	private String activityName;
	private String packageName;

	public AppHelper() {}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getAdbPath() {
		return adbPath;
	}

	public void setAdbPath(String adbPath) {
		this.adbPath = adbPath;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String findAdbPath(String OS){

		Process p;
		try {
			if(OS.equals(("windows"))){
				p = Runtime.getRuntime().exec("where adb");

			}else {
				if (OS.equals(("linux")) || OS.equals(("mac"))) {
					p = Runtime.getRuntime().exec("which adb");
				}else{
					return null;
				}
			}

			p.waitFor();
			BufferedReader reader=new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
				return line;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String extractActivity (String manifest) {
		int firstIndex, lastIndex = 0;
		boolean check = false;
		char seq [] = manifest.toCharArray();
		
		firstIndex = manifest.indexOf("<activity", 0);
		
		if (firstIndex == -1)
			return "ERRO"; // TODO: Throw an Exception
		
		for (int i = firstIndex; i < seq.length; i++) {
			if (check && (manifest.charAt(i) == '"')) {
				lastIndex = i;
				break;
			}
			if (manifest.substring(i + 1, i + 8).equals(":name=\"") && !check) {
				firstIndex = i + ":name=\"".length() + 1;
				i += ":name=\"".length() + 1;
				check = true;
			}
		}			
		return manifest.substring(firstIndex, lastIndex);
	}
	
	
	public String extractPackage (String manifest) {
		int firstIndex, lastIndex = 0;
		char seq [] = manifest.toCharArray();
		
		firstIndex = manifest.indexOf("package=\"", 0) + "package=\"".length();
		
		if (firstIndex == -1)
			return null;
		
		for (int i = firstIndex + 10; i < seq.length; i++) {
			if (manifest.charAt(i) == '"') {
				lastIndex = i;
				break;
			}
		}			
		return manifest.substring(firstIndex, lastIndex);
	}
	
	

}
