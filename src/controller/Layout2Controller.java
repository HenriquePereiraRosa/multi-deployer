package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import multideployer.MultiDeployer;


public class Layout2Controller {
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

	@FXML
	void initialize() {
		assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'Layout2.fxml'.";
	}

    @FXML
    void goToScreen1(ActionEvent event) {    	
    	MultiDeployer.changeScene(MultiDeployer.LAYOUT1);
    }
    
    
	
}
