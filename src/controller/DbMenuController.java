package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ConfigUtil;

public class DbMenuController {

	@FXML
	TextField serverText;

	@FXML
	TextField userText;

	@FXML
	TextField pswText;

	private Stage configStage;
	
	@FXML
	private void initialize() {
//		Properties prop = new Properties();
//		InputStream input = null;
//		File configFile = new File("config.properties");
//		if(configFile.exists()){
//			try {
//
//				input = new FileInputStream("config.properties");
//
//				prop.load(input);
//
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			} finally {
//				if (input != null) {
//					serverText.setText(prop.getProperty("server"));
//					userText.setText(prop.getProperty("user"));
//					pswText.setText(prop.getProperty("password"));
//					try {
//						input.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		
		Properties prop = ConfigUtil.getDbProperty();
		if(prop!=null){
			serverText.setText(prop.getProperty("server"));
			userText.setText(prop.getProperty("user"));
			pswText.setText(prop.getProperty("password"));
		}



	}

	@FXML
	private void validerDb() {
		
//
//		Properties prop = new Properties();
//		OutputStream output = null;
//		
//		try {
//			File configFile = new File("config.properties");
//			configFile.createNewFile();
//			output = new FileOutputStream(configFile);
//
//			prop.setProperty("server", serverText.getText());
//			prop.setProperty("user", userText.getText());
//			prop.setProperty("password", userText.getText());
//
//			prop.store(output, null);
//
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			configStage.close();
//		}
		
		Properties prop = new Properties();
		prop.setProperty("server", serverText.getText());
		prop.setProperty("user", userText.getText());
		prop.setProperty("password", userText.getText());
		ConfigUtil.setDbProperty(prop);
		configStage.close();
	}
	
	@FXML
	private void annuler(){
		configStage.close();
	}

	public Stage getConfigStage() {
		return configStage;
	}

	public void setConfigStage(Stage configStage) {
		this.configStage = configStage;
	}
	
	
}
