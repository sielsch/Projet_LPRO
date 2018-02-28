package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import util.ConfigUtil;
import util.Constant;

public class VigileRootController {

	ObservableList<Media> videoList;
	MediaPlayer player;

	File videoDirectory;

	@FXML
	MediaView videoView;

	@FXML
	TableColumn<Media, String> nomVideoColumn;

	@FXML
	TableView<Media> videoTable;

	File f;
	Media media;
	MediaView mv;
	MediaPlayer mp;

	int index;

	public VigileRootController() {
		Properties properties = ConfigUtil.getProperty(Constant.VIDEO_DIRECTORY);
		
    	if(properties!=null){
    		videoDirectory = new File(properties.getProperty("directory"));   		
    	} else {
    		choixDossier();
    	}
    	
		index = 0;
		initVideoList();
	}

	@FXML
	public void initialize() {
		// f = new File("/Users/stephaneielsch/Movies/20180109_110138.mp4");
		// media = new Media(f.toURI().toString());
		mp = new MediaPlayer(videoList.get(0));
		videoView.setMediaPlayer(mp);
		nomVideoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSource()));
		populateVideoTable();
		initKeyListener();
	}

	public void initVideoList() {
		videoList = FXCollections.observableArrayList();
		for (File f : videoDirectory.listFiles()) {
			if (f.getName().endsWith("mp4")) {
				videoList.add(new Media(f.toURI().toString()));
			}
		}
	}

	public void populateVideoTable() {
		videoTable.setItems(videoList);
	}

	public void playVideo() {
		mp.play();
	}

	public void stopVideo() {
		mp.stop();
	}

	public void pauseVideo() {
		mp.pause();
	}

	@FXML
	public void precedent() {
		if (index > 0) {

			index--;
			loadVideo(index);
		}
	}

	@FXML
	public void suivant() {

		if (index < videoList.size()) {
			index++;
			loadVideo(index);
		}
	}

	public void loadVideo(int index) {
		if (mp != null) {
			mp.stop();
		}
		mp = null;
		mp = new MediaPlayer(videoList.get(index));
		videoView.setMediaPlayer(mp);
	}

	@FXML
	public void rechercher() {

	}
	

	public void initKeyListener(){
		videoTable.setOnKeyPressed((KeyEvent t)-> {
		    KeyCode key=t.getCode();
		    if (key==KeyCode.ENTER){
		        int pos=videoTable.getSelectionModel().getSelectedIndex();
		        loadVideo(pos);
		    }
		});
	}

	@FXML
	public void choixDossier(){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select the video folder");
		File folder=directoryChooser.showDialog(null);
		Properties prop = new Properties();
		prop.setProperty("directory", folder.getAbsolutePath());
		ConfigUtil.setProperty(prop, Constant.VIDEO_DIRECTORY);
		videoDirectory=new File(folder.getAbsolutePath());
		initVideoList();
		populateVideoTable();
	}
	
}
