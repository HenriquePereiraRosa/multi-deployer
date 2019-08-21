package multideployer;



import java.io.IOException;

import controller.Layout1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MultiDeployer extends Application {
	
	private static String LOGO = "/resources/img/logo.png";
	public static String LAYOUT1 = "view/Layout1.fxml";
	public static String LAYOUT2 = "view/Layout2.fxml";
	public static String LAYOUT3 = "view/Layout3.fxml";
	
	private Layout1Controller layout1Ctrl;
	private static AnchorPane root;
	private static Stage stage;


	public static void main(String[] args) throws ClassNotFoundException {
		Application.launch(MultiDeployer.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		MultiDeployer.stage = primaryStage;
		layout1Ctrl = new Layout1Controller();

		try {
			MultiDeployer.stage.getIcons().add(new Image(getClass().getResourceAsStream(LOGO)));
			} catch (NullPointerException e) {
			System.out.println("logo.png not found.");
			e.printStackTrace();
		}
		
		try {
			MultiDeployer.root = FXMLLoader.load(getClass().getClassLoader().getResource(LAYOUT1));
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setTitle(" Multi Deployer Of Apps");
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
			root = (AnchorPane) FXMLLoader.load(MultiDeployer.class.getClassLoader().getResource(path));
			Scene scene = new Scene(root);
			
			MultiDeployer.stage.setScene(scene);
			MultiDeployer.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
