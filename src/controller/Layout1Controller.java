package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Layout1Controller {

	final FileChooser fileChooser = new FileChooser();
	private IDevice device;
	private File file;

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
	private Button btnScan;

	@FXML
	private Button btnClear;

	@FXML
	void initialize() {
		assert txtFieldFileAddress != null : "fx:id=\"txtFieldFileAddress\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnDeploy != null : "fx:id=\"btnDeploy\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert txaLog != null : "fx:id=\"txaLog\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert cbxDevices != null : "fx:id=\"cbxDevices\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnScan != null : "fx:id=\"btnScan\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Layout1.fxml'.";

		try {
			System.out.println("Initializing the DEBUG bridge.");
			AndroidDebugBridge.init(false);
		} catch (Exception e) {
			txaLog.appendText("Exception in init()");
			e.printStackTrace();
		}
	}
	
	public static void closeWindowEvent() {
        try {
			System.out.println("Closing the DEBUG bridge.");
			AndroidDebugBridge.disconnectBridge();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@FXML
	void selectFile(Event event) {

		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();

		Parent parent = new Pane();
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);

		file = fileChooser.showOpenDialog(stage);

		txaLog.appendText("Mouse clicked in TxtFieldFileAddress. On " + localDate + "_" + localTime + ".\n");

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Applications files", "*.apk"));
		if (file != null) {
			txaLog.appendText(file.getPath() + "\n");
			txtFieldFileAddress.setText(file.getPath());
		} else {
			txaLog.appendText("No file selected. \n");
		}

	}

	@FXML
	void scanADBDevices(ActionEvent event) {

		progressBar.setProgress(0.2);
		AndroidDebugBridge adb = AndroidDebugBridge.createBridge("/home/user/Android/Sdk/platform-tools/adb", true);
		if (adb == null) {
			System.err.println("Invalid ADB location.");
			txaLog.appendText("Erro na localizaçao do ADB. \n");
			return;
		} else {
			txaLog.appendText("DEVICES: \n");
			for (IDevice device : adb.getDevices()) {
				txaLog.appendText(device.getName() + "|" + device.getSerialNumber() + "\n");
			}
		}

		AndroidDebugBridge.addDeviceChangeListener(new IDeviceChangeListener() {

			@Override
			public void deviceChanged(IDevice device, int arg1) {
				System.out.println(String.format("%s changed", device.getSerialNumber()));
				try {
					txaLog.appendText("Changed: " + device.getName() + " BAT LEVEL: "
							+ device.getBattery().get().toString() + "%\n");

					btnDeploy.setDisable(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void deviceConnected(IDevice device) {
				progressBar.setProgress(1.0);
				System.out.println(String.format("%s connected", device.getSerialNumber()));
				try {
					txaLog.appendText("Connected: " + device.getName() + " BAT LEVEL: "
							+ device.getBattery().get().toString() + "%\n");
					btnDeploy.setDisable(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void deviceDisconnected(IDevice device) {
				progressBar.setProgress(0.0);
				System.out.println(String.format("%s disconnected", device.getSerialNumber()));
				txaLog.appendText("Disconnected: " + device.toString() + "\n");
				btnDeploy.setDisable(true);
			}

		});

	}

	@FXML
    void deploy(ActionEvent event) {
    	try {
    		IShellOutputReceiver receiver = new IShellOutputReceiver() {
				
				@Override
				public boolean isCancelled() {
		    		txaLog.appendText("IshellOutputReceiver.isCancelled(); \n");
					return false;
				}
				
				@Override
				public void flush() {
		    		txaLog.appendText("IshellOutputReceiver.flush(); \n");
					
				}
				
				@Override
				public void addOutput(byte[] arg0, int arg1, int arg2) {
		    		txaLog.appendText("IshellOutputReceiver.addOutput(); "
		    				+ "ARG0 " + arg0 + "ARG1 " + arg1 + "ARG2 " + arg2 + "\n");
					
				}
			};
    		StringBuffer command  = new StringBuffer();
    		command.append("adb install -r -t ");
    		command.append(file.getPath()); // TODO: Exception when file is null.
    		txaLog.appendText("Command: " + command.toString() + "\n");
    		this.device.executeShellCommand(command.toString(), receiver);
    	} catch (TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException |
    			IOException e) {
    		e.printStackTrace();
    	} catch (NullPointerException npe) {
    		System.out.println("NullPointerException occured...\n");
    		txaLog.appendText("NullPointerException occured...\n");
    		if (file.getPath().isEmpty()) {
    		System.out.println("File Path: " + file.getPath() + "\n");
    		txaLog.appendText("File Path: " + file.getPath() + "\n");
    		} else {
    			npe.printStackTrace();
	    		System.out.println(npe.getCause());
	    		}
    	} catch (Exception e) {
		System.out.println("Exception occured...\n");
		txaLog.appendText("Exception occured..\n");
		e.printStackTrace();
	}

    }

	@FXML
	void ClearHistory(ActionEvent event) {
		txaLog.clear();
	}

}
