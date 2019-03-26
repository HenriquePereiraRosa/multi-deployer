import controller.Layout1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MultiDeployer extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Layout1.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		primaryStage.setOnCloseRequest(event -> {
			Layout1Controller layout1Ctrl = new Layout1Controller();
			layout1Ctrl.closeWindowEvent(event);
		});;
	}

}
