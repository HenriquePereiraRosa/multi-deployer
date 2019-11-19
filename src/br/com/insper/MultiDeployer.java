package br.com.insper;



import java.io.IOException;

import br.com.insper.resources.strings.StringResources;
import br.com.insper.controller.Layout1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MultiDeployer extends Application {
	private Layout1Controller layout1Ctrl;
	private static AnchorPane root;
	private static Stage stage;


	public static void main(String[] args) {
		Application.launch(MultiDeployer.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		MultiDeployer.stage = primaryStage;
		layout1Ctrl = new Layout1Controller();

		try {
			MultiDeployer.stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream(StringResources.LOGO)));
			} catch (NullPointerException e) {
			System.out.println("logo.png not found.");
			e.printStackTrace();
		}
		
		try {
			MultiDeployer.root = FXMLLoader.load(getClass().getClassLoader().getResource(StringResources.LAYOUT1));
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setTitle(StringResources.UPPERBAR_TITLE);
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaa");
			e.printStackTrace();
		}
		
		MultiDeployer.stage.setOnCloseRequest(event -> {
			layout1Ctrl.closeWindowEvent(event);
		});
		;
	}
	
	public static void changeScene(String path) {
		try {
			root.getChildren().removeAll(root.getChildren());
			root = (AnchorPane) FXMLLoader.load(MultiDeployer.class.getClassLoader().getResource(path));
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
