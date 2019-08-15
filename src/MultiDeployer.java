import controller.Layout1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MultiDeployer extends Application {

	public static void main(String[] args) throws ClassNotFoundException {
		Application.launch(MultiDeployer.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Layout1.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("INSPER - Multi Devices Deployer");
		
		try {
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/logo.png")));
			} catch (NullPointerException e) {
			System.out.println("logo.png not found.");
			e.printStackTrace();
		}

		primaryStage.show();
		
		primaryStage.setOnCloseRequest(event -> {
			Layout1Controller layout1Ctrl = new Layout1Controller();
			layout1Ctrl.closeWindowEvent(event);
		});
		;
	}
}
