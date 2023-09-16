package com.example.BubbleBobble;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class to run the application
 */
public class Main extends Application {
	public static final int UNIT_SIZE = 20;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 34;

	private static Scene scene;

	/**
	 * The first start method to invoke the stage
	 * @param stage the current stage
	 * @throws IOException throws null pointer exception
	 */
	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("StartScreen"), 730, 650);
		stage.setTitle("Bubble Bobble");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	/**
	 * Method to loadFXML for convenience of not specifying all the paths.
	 * @param fxml the file name of the fxml file
	 * @return the loads of an object hierarchy from a FXML document.
	 * @throws IOException throws null pointer exception
	 */
	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/"+fxml+".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}
}