package com.gamewolf.usbback;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("文件系统热备份工具");
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("mainui.fxml"));
			primaryStage.setScene(new Scene(root, 800, 640));
		    primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
    }

    public static void main(String[] args) {
        launch();
    }

}