package br.com.insper.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.insper.event.Trigger;
import br.com.insper.event.TriggerHandler;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;
import br.com.insper.MultiDeployer;
import br.com.insper.resources.strings.StringResources;


public class Layout3Controller {
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose, btnCopy;

    @FXML
    private Hyperlink hlinkIssue;

    @FXML
    private Label lblAdvice;
    
	private Trigger trigger;
	private TriggerHandler triggerHandler;
	
           
	public Button getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(Button btnClose) {
		this.btnClose = btnClose;
	}

	public Label getLblAdvice() {
		return lblAdvice;
	}

	public void setLblAdvice(Label lblAdvice) {
		this.lblAdvice = lblAdvice;
	}


	@FXML
	void initialize() {
		assert btnClose != null : StringResources.BTN_CLOSE_LAYOUT3;
		assert btnCopy != null : StringResources.BTN_COPY_LAYOUT3;
		assert hlinkIssue != null : StringResources.HLINK_ISSUE;
		assert lblAdvice != null : StringResources.LBL_ADVICE;
        this.initLayout();
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

    /**
     * <p> Open the repository issue page.
     * <a href=StringResouges.ISSUE></a>
     * </p>
     * @param ActionEvent triggered in FXML
     * @return void
     * @see <a href=""></a>
     * @since 1.0
     */
    @FXML
    void openIssue(ActionEvent event) {
    	trigger.addListener(triggerHandler);
    	trigger.triggerEvent(StringResources.ISSUE);
	}
    
    /**
     * <p>Method to copy the email to clipboard.
     * </p>
     * @param MouseEvent
     * @return void
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    @FXML
    void doCopy(ActionEvent event) {
    	
    	FadeTransition fade = new FadeTransition(Duration.millis(2000), lblAdvice);
    	fade.setFromValue(0.0);
    	fade.setToValue(1.0);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
    	fade.play();
    	
    	final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(StringResources.ISSUE);
        clipboard.setContent(content);
		
    }
    
	private void initLayout() {
		trigger = new Trigger();
		triggerHandler = new TriggerHandler();		
	}

	
}
