package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigUtil {

	
	public static Properties getDbProperty(){
		Properties prop = new Properties();
		InputStream input = null;
		File configFile = new File(Constant.DB_CONFIG_FILES);
		if(configFile.exists()){
			try {
				input = new FileInputStream(Constant.DB_CONFIG_FILES);
				prop.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return prop;
		}
		return null;
	}
	
	public static void setDbProperty(Properties prop){
		
		OutputStream output = null;
		
		try {
			File configFile = new File(Constant.DB_CONFIG_FILES);
			configFile.createNewFile();
			output = new FileOutputStream(configFile);
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
