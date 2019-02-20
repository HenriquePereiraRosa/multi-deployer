package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Layout1Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtFieldFileAddress;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button btnDeploy;

    @FXML
    private TextArea txaLog;

    @FXML
    private ComboBox<?> cbxDevices;

    @FXML
    void initialize() {
        assert txtFieldFileAddress != null : "fx:id=\"txtFieldFileAddress\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert btnDeploy != null : "fx:id=\"btnDeploy\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert txaLog != null : "fx:id=\"txaLog\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert cbxDevices != null : "fx:id=\"cbxDevices\" was not injected: check your FXML file 'Layout1.fxml'.";

    }
    @FXML
    void selectFile(MouseEvent event) {
    	
    	LocalDate localDate = LocalDate.now();
    	LocalTime localTime = LocalTime.now();
    	
    	txaLog.appendText("Mouse clicked in TxtFieldFileAddress. On " + localDate + "_" + localTime + ".\n");
    }
    
}
