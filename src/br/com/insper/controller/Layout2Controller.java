package br.com.insper.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.insper.event.Trigger;
import br.com.insper.event.TriggerHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import br.com.insper.MultiDeployer;
import br.com.insper.resources.strings.StringResources;


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
        assert btnClose != null : StringResources.BTN_CLOSE_LAYOUT2;
        assert hlinkHashimoto != null : StringResources.HLINK_HASHIMOTO;
        assert hlinkHenriqueRosa != null : StringResources.HLINK_HENRIQUEROSA;

		trigger = new Trigger();
		triggerHandler = new TriggerHandler();
	}

    @FXML
    void goToScreen1(ActionEvent event) {    	
    	MultiDeployer.changeScene(StringResources.LAYOUT1);
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
