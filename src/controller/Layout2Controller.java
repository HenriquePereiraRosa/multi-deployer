package controller;

import java.net.URL;
import java.util.ResourceBundle;

import event.Trigger;
import event.TriggerHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import multideployer.MultiDeployer;
import resources.strings.StringResources;


public class Layout2Controller {
	

	private Trigger trigger;
	private TriggerHandler triggerHandler;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Hyperlink hlinkHashimoto;

    @FXML
    private Hyperlink hlinkHenriqueRosa;
    
	@FXML
	void initialize() {
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'Layout2.fxml'.";
        assert hlinkHashimoto != null : "fx:id=\"hlinkHashimoto\" was not injected: check your FXML file 'Layout2.fxml'.";
        assert hlinkHenriqueRosa != null : "fx:id=\"hlinkHenriqueRosa\" was not injected: check your FXML file 'Layout2.fxml'.";

		trigger = new Trigger();
		triggerHandler = new TriggerHandler();
	}

    @FXML
    void goToScreen1(ActionEvent event) {    	
    	MultiDeployer.changeScene(MultiDeployer.LAYOUT1);
    }

    @FXML
    void openHashLattes(ActionEvent event) {
    	trigger.addListener(triggerHandler);
    	trigger.triggerEvent(StringResources.HASHIMOTO_LATTES);
    }

    @FXML
    void openLinkedin(ActionEvent event) {
    	trigger.addListener(triggerHandler);
    	trigger.triggerEvent(StringResources.HENRIQUE_LINKEDIN);
    }

    @FXML
    void goToInsper(ActionEvent event) {
    	trigger.addListener(triggerHandler);
    	trigger.triggerEvent(StringResources.INSPER);
    }
	
}
