package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.NullOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

public class Layout1Controller {

	final FileChooser fileChooser = new FileChooser();
	private AndroidDebugBridge adb;
	private String appPath, adbPath;

	private IDevice devices[];
	private ProcessBuilder processBuilder = new ProcessBuilder();
	private Properties prop;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtFieldAppPath;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private TextField txtFieldAdbPath;

	@FXML
	private Button btnInstall, btnClear, btnUninstall, btnLaunch;

	@FXML
	private ComboBox<String> cbDevices = new ComboBox<String>();

	@FXML
	private TextArea txaLog;

	@FXML
	void initialize() {
		assert txtFieldAppPath != null : "fx:id=\"txtFieldFileAddress\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert txtFieldAdbPath != null : "fx:id=\"txtFieldADBPath\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert cbDevices != null : "fx:id=\"cbxDevices\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnInstall != null : "fx:id=\"btnInstall\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnLaunch != null : "fx:id=\"btnLaunch\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert txaLog != null : "fx:id=\"txaLog\" was not injected: check your FXML file 'Layout1.fxml'.";
		assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Layout1.fxml'.";

		txtFieldAppPath.setFocusTraversable(false);
		txaLog.appendText("Detected OS: " + System.getProperty("os.name") + ".\n"); // TODO: to remove.
				
		try {
			System.out.println("Initializing the DEBUG bridge.");
			AndroidDebugBridge.init(false);
		} catch (Exception e) {
			txaLog.appendText("Exception in init()");
			e.printStackTrace();
		}

		prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("file.path");
			
			prop.load(input);
			appPath = prop.getProperty("appPath");
			System.out.println(appPath);
			
			if (appPath != null) {
				txtFieldAppPath.setText(appPath);
				txtFieldAppPath.setAlignment(Pos.CENTER_RIGHT);
			}
			adbPath = prop.getProperty("adbPath");
			System.out.println(adbPath);
			if (adbPath != null) {
				txtFieldAdbPath.setText(adbPath);
				txtFieldAdbPath.setAlignment(Pos.CENTER_RIGHT);
				this.connectDevices();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void closeWindowEvent() {
		try {
			System.out.println("Closing the DEBUG bridge.");
			AndroidDebugBridge.terminate();
			System.exit(0); // TODO: check this addition
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectAppFile(Event event) {

		Parent parent = new Pane();
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);

		File file = fileChooser.showOpenDialog(stage);

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Applications files", "*.apk"));

		if (file != null) {
			appPath = file.getPath();
			txaLog.appendText(appPath + "\n");
			txtFieldAppPath.setText(appPath);
			txtFieldAppPath.setAlignment(Pos.CENTER_RIGHT);
			
			OutputStream output = null;

			try {
				output = new FileOutputStream("file.path");
				prop.setProperty("appPath", appPath);
				prop.store(output, null);

			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (NullPointerException npe) {
				System.out.println("Output: " + output);
				npe.printStackTrace();

			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		} else {
			txaLog.appendText("No file selected. \n");
		}

	}

	@FXML
	void selectAdbFile(Event event) {

		Parent parent = new Pane();
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);

		File file = fileChooser.showOpenDialog(stage);

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ADB executor", "*adb*.*"));
		if (file != null) {
			adbPath = file.getPath();
			txaLog.appendText(adbPath + "\n");
			txtFieldAdbPath.setText(adbPath);
			txtFieldAdbPath.setAlignment(Pos.CENTER_RIGHT);
			
			OutputStream output = null;

			try {
				output = new FileOutputStream("file.path");
				prop.setProperty("adbPath", adbPath);
				prop.store(output, null);
				this.connectDevices();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (NullPointerException npe) {
				System.out.println("Output: " + output);
				npe.printStackTrace();

			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		} else {
			txaLog.appendText("No file selected. \n");
		}

	}

	private void connectDevices() {
		progressBar.setProgress(0.5);
		txaLog.appendText("Trying connections...\n");
		this.adb = AndroidDebugBridge.createBridge(adbPath, true);

		if (this.adb == null) {
			System.err.println("Invalid ADB location.");
			txaLog.appendText("Erro na localizaçao do ADB. \n");
			return;
		}

		AndroidDebugBridge.addDeviceChangeListener(new IDeviceChangeListener() {

			@Override
			public void deviceChanged(IDevice device, int arg1) {
				System.out.println(String.format("%s changed", device.getSerialNumber()));
				try {
					txaLog.appendText("Changed: " + device.getName() + " BAT LEVEL: "
							+ device.getBattery().get().toString() + "%\n");

					devices = adb.getDevices();
					if (!cbDevices.getItems().contains(device.getName())) {
						cbDevices.getItems().add(device.getName());
					}
					cbDevices.setDisable(false);
					btnInstall.setDisable(false);
					btnLaunch.setDisable(false);
					btnUninstall.setDisable(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void deviceConnected(IDevice device) {
				progressBar.setProgress(1.0);
				System.out.println(String.format("%s connected", device.getSerialNumber()));

				if (!cbDevices.getItems().contains(device.getName())) {
					cbDevices.getItems().add(device.getName());
				}
				cbDevices.setDisable(false);
				btnInstall.setDisable(false);
				btnLaunch.setDisable(false);
				btnUninstall.setDisable(false);
				
				try {
					txaLog.appendText("Connected: " + device.getName() + " - Battery: "
							+ device.getBattery().get().toString() + "%\n");
					devices = adb.getDevices();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void deviceDisconnected(IDevice device) {
				progressBar.setProgress(0.0);
				System.out.println(String.format("%s disconnected", device.getSerialNumber()));
				txaLog.appendText("Disconnected: " + device.toString() + "\n");
				cbDevices.getItems().remove(device.getName());
				btnLaunch.setDisable(true);
			}

		});

	}

	@FXML
	void install(ActionEvent event) {

		txaLog.appendText("Installing app...\n");
		try {
			for (int i = 0; i < devices.length; i++) {
				devices[i].installPackage(appPath, true, "-r");
				txaLog.appendText("Installed .apk in device " + i + ".\n");
			}
		} catch (NullPointerException npe) {
			System.out.println("NullPointerException occured...\n");
			txaLog.appendText("NullPointerException occured...\n");
			npe.printStackTrace();
			System.out.println(npe.getCause());
		} catch (InstallException ie) {
			System.out.println("ERROR: Not installed due an OLDER SDK.\n");
			txaLog.appendText("ERROR: Not installed due an OLDER SDK.\n");
			ie.printStackTrace();
			System.out.println(ie.getCause());
		} catch (Exception e) {
			System.out.println("Exception occured...\n");
			txaLog.appendText("Exception occured..\n");
			e.printStackTrace();
		}
	}

	@FXML
	void launch(ActionEvent event) {

		boolean tryAgain = true;

		try {

			if (devices != null) {
				for (int i = 0; i < devices.length; i++) {

					if (System.getProperty("os.name").equalsIgnoreCase("windows")) {
						processBuilder.command("cmd.exe", "/c", "adb -s " + devices[i].getSerialNumber()
								+ " shell am start -n com.example.myapplication/com.example.myapplication.MapsActivity");
					} else if (System.getProperty("os.name").equalsIgnoreCase("linux")
							|| System.getProperty("os.name").indexOf("mac") > 0) {
						processBuilder.command("bash", "-c", "adb -s " + devices[i].getSerialNumber()
								+ "  shell am start -n com.example.myapplication/com.example.myapplication.MapsActivity");
					} else {
						txaLog.appendText("Aborting command due a not supported OS.");
						return;
					}

					Process process = processBuilder.start();
					txaLog.appendText("Command sent to Device " + i + " \n");

					StringBuilder output = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

					String line;
					while ((line = reader.readLine()) != null) {
						txaLog.appendText("reader.readLine(): " + line + "\n");
						output.append(line + "\n");
					}
					if ((i == (devices.length - 1)) && tryAgain) {
						i = 0;
						tryAgain = false;
					}
				}
			} else {
				txaLog.appendText("No device is connected.");
			}
		} catch (IOException e) {
			txaLog.appendText("IOException occured..\n");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println(npe.getCause());
		} catch (Exception e) {
			System.out.println("Exception occured...\n");
			txaLog.appendText("Exception occured..\n");
			e.printStackTrace();
		}
	}

	@FXML
	void uninstall(ActionEvent event) {

		txaLog.appendText("Uninstalling app...\n");
		try {
			for (int i = 0; i < devices.length; i++) {
				devices[i].uninstallPackage("com.example.myapplication");
				txaLog.appendText("Installed .apk in device " + i + ".\n");
			}
		} catch (NullPointerException npe) {
			System.out.println("NullPointerException occured...\n");
			txaLog.appendText("NullPointerException occured...\n");
			npe.printStackTrace();
			System.out.println(npe.getCause());
		} catch (InstallException ie) {
			System.out.println("ERROR: Not installed due an OLDER SDK.\n");
			txaLog.appendText("ERROR: Not installed due an OLDER SDK.\n");
			ie.printStackTrace();
			System.out.println(ie.getCause());
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
