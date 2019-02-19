import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiDeployer extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent parent = new Pane();
				
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

}
