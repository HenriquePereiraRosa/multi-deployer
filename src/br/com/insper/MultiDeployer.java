package br.com.insper;



import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import br.com.insper.resources.strings.StringResources;
import br.com.insper.controller.Layout1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

public class MultiDeployer extends Application {
	private Layout1Controller layout1Ctrl;
	private static AnchorPane root;
	private static Stage stage;
	public  static ResourceBundle i18nBundle;


	public static void main(String[] args) throws ClassNotFoundException {
		Application.launch(MultiDeployer.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		MultiDeployer.stage = primaryStage;
		layout1Ctrl = new Layout1Controller();

		try {
			MultiDeployer.stage.getIcons().add(new Image(getClass().getResourceAsStream(StringResources.LOGO)));
			} catch (NullPointerException e) {
			System.out.println("logo.png not found.");
			e.printStackTrace();
		}
		
		try {
			MultiDeployer.i18nBundle = ResourceBundle.getBundle("br.com.insper.Bundle", new Locale("en", "US"));
			MultiDeployer.root = FXMLLoader.load(getClass().getClassLoader().getResource(StringResources.LAYOUT1), i18nBundle);
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setTitle(i18nBundle.getString("UPPERBAR_TITLE"));
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e) {
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
			root = (AnchorPane) FXMLLoader.load(MultiDeployer.class.getClassLoader().getResource(path),i18nBundle);
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void changeLanguage(String language, String country){

		try {
			// Clear last language
			ResourceBundle.clearCache();
			// Start the new one
			MultiDeployer.i18nBundle = ResourceBundle.getBundle("br.com.insper.Bundle", new Locale(language, country));

			//Reload Scene to get new language
			root.getChildren().removeAll(root.getChildren());
			root = (AnchorPane) FXMLLoader.load(MultiDeployer.class.getClassLoader().getResource(StringResources.LAYOUT1),i18nBundle);
			Scene scene = new Scene(root);
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	}
