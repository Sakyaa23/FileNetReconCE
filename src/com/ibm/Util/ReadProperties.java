package com.ibm.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ReadProperties {
	public Properties readPropertiesFile() throws IOException {
		String currentDirectory = System.getProperty("user.dir");
		 System.out.println("currentDirectory : "+currentDirectory);
		 String filepath = currentDirectory + "/FileNetReconCE.properties";
		 //String workingDirectory = Paths.get("").toAbsolutePath().toString();
		 //System.out.println(workingDirectory);
		//String filepath="\\\\dc04dwvfns306\\Shared\\L2 FileNet Support\\Gauri\\Sakya\\config.properties";
		///daas2808////String filepath="C:\\Monitoring\\Phase2\\FileNetReconCE.properties";
		//working//String filepath="\\\\dc04dwvfns306\\Shared\\L2 FileNet Support\\Gauri\\Sakya\\dont run\\nn\\FileNetReconCE.properties";
		Properties prop=null;
		FileInputStream fis = new FileInputStream(filepath);
		try {
			prop = new Properties();
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

}
