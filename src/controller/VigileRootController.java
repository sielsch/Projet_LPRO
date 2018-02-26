package controller;

import java.io.File;
import java.util.ArrayList;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

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
		videoDirectory = new File("/Users/stephaneielsch/Movies");
		index =0;
		initVideoList();
	}

	@FXML
	public void initialize() {
//		f = new File("/Users/stephaneielsch/Movies/20180109_110138.mp4");
//		media = new Media(f.toURI().toString());
		mp = new MediaPlayer(videoList.get(0));
		videoView.setMediaPlayer(mp);
		nomVideoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSource()));
		populateVideoTable();
	}

	public void initVideoList() {
		videoList = FXCollections.observableArrayList();
		for (File f : videoDirectory.listFiles()) {
			if (f.getName().endsWith("mp4")) {
				videoList.add(new Media(f.toURI().toString()));
			}
		}
	}
	
	public void populateVideoTable(){
		videoTable.setItems(videoList);
	}

	public void playVideo() {
		mp.play();
	}

	public void stopVideo(){
		mp.stop();
	}
	
	@FXML
	public void precedent() {
		if(index>0){
			index--;
			mp = new MediaPlayer(videoList.get(index));
			videoView.setMediaPlayer(mp);
		}
	}

	@FXML
	public void suivant() {
		if(index<videoList.size()){
			index++;
			mp = new MediaPlayer(videoList.get(index));
			videoView.setMediaPlayer(mp);
		}
	}

	@FXML
	public void rechercher() {

	}

}
