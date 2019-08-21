package event;

import javafx.application.Application;
import javafx.stage.Stage;

public class TriggerHandler extends Application implements TriggerInterface {

	@Override
	public void handleEvent(String url) {
		getHostServices().showDocument(url);		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

}
