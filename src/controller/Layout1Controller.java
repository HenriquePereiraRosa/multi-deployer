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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;

import controller.util.AppHelper;
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
import net.dongliu.apk.parser.ApkFile;
import service.ConnectionService;
import service.InstallationService;
import service.UninstallationService;

public class Layout1Controller {

	final FileChooser fileChooser = new FileChooser();
	private AndroidDebugBridge adb;
	private AppHelper helper;

	private IDevice devices[];
	private ProcessBuilder processBuilder = new ProcessBuilder();
	private Properties prop;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtFieldAppPath, txtFieldAdbPath;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Button btnInstall, btnClear, btnUninstall, btnLaunch;

	@FXML
	private ComboBox<String> cbDevices = new ComboBox<String>();

	@FXML
	private TextArea txaLog;

	
	
	public AndroidDebugBridge getAdb() {
		return adb;
	}

	public void setAdb(AndroidDebugBridge adb) {
		this.adb = adb;
	}

	public AppHelper getHelper() {
		return helper;
	}

	public void setHelper(AppHelper helper) {
		this.helper = helper;
	}

	public IDevice[] getDevices() {
		return devices;
	}

	public void setDevices(IDevice[] devices) {
		this.devices = devices;
	}

	public ProcessBuilder getProcessBuilder() {
		return processBuilder;
	}

	public void setProcessBuilder(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public ResourceBundle getResources() {
		return resources;
	}

	public void setResources(ResourceBundle resources) {
		this.resources = resources;
	}

	public URL getLocation() {
		return location;
	}

	public void setLocation(URL location) {
		this.location = location;
	}

	public TextField getTxtFieldAppPath() {
		return txtFieldAppPath;
	}

	public void setTxtFieldAppPath(TextField txtFieldAppPath) {
		this.txtFieldAppPath = txtFieldAppPath;
	}

	public TextField getTxtFieldAdbPath() {
		return txtFieldAdbPath;
	}

	public void setTxtFieldAdbPath(TextField txtFieldAdbPath) {
		this.txtFieldAdbPath = txtFieldAdbPath;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public Button getBtnInstall() {
		return btnInstall;
	}

	public void setBtnInstall(Button btnInstall) {
		this.btnInstall = btnInstall;
	}

	public Button getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(Button btnClear) {
		this.btnClear = btnClear;
	}

	public Button getBtnUninstall() {
		return btnUninstall;
	}

	public void setBtnUninstall(Button btnUninstall) {
		this.btnUninstall = btnUninstall;
	}

	public Button getBtnLaunch() {
		return btnLaunch;
	}

	public void setBtnLaunch(Button btnLaunch) {
		this.btnLaunch = btnLaunch;
	}

	public ComboBox<String> getCbDevices() {
		return cbDevices;
	}

	public void setCbDevices(ComboBox<String> cbDevices) {
		this.cbDevices = cbDevices;
	}

	public TextArea getTxaLog() {
		return txaLog;
	}

	public void setTxaLog(TextArea txaLog) {
		this.txaLog = txaLog;
	}

	public FileChooser getFileChooser() {
		return fileChooser;
	}

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
		
		helper = new AppHelper();
		
		txtFieldAppPath.setFocusTraversable(false);
		txaLog.appendText("Detected OS: " + System.getProperty("os.name") + ".\n");
				
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
			helper.setAppPath(prop.getProperty("appPath"));
			System.out.println(helper.getAppPath());
			
			if (helper.getAppPath() != null) {
				txtFieldAppPath.setText(helper.getAppPath());
				txtFieldAppPath.setAlignment(Pos.CENTER_RIGHT);
			}
			helper.setAdbPath(prop.getProperty("adbPath"));
			System.out.println(helper.getAdbPath());
			if (helper.getAdbPath() != null) {
				txtFieldAdbPath.setText(helper.getAdbPath());
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
			helper.setAppPath(file.getPath());
			txaLog.appendText(helper.getAppPath() + "\n");
			txtFieldAppPath.setText(helper.getAppPath());
			txtFieldAppPath.setAlignment(Pos.CENTER_RIGHT);
			
			OutputStream output = null;

			try {
				output = new FileOutputStream("file.path");
				prop.setProperty("appPath", helper.getAppPath());
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
			txaLog.appendText("No app file selected. \n");
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
			helper.setAdbPath(file.getPath());
			txaLog.appendText(helper.getAdbPath() + "\n");
			txtFieldAdbPath.setText(helper.getAdbPath());
			txtFieldAdbPath.setAlignment(Pos.CENTER_RIGHT);
			
			OutputStream output = null;

			try {
				output = new FileOutputStream("file.path");
				prop.setProperty("adbPath", helper.getAdbPath());
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
			txaLog.appendText("No adb file selected. \n");
		}

	}
	
	@FXML
	void selectActivityFile(Event event) {
		txaLog.appendText("Lack of implementation.. rsrs\n");
	}

	private void connectDevices() {
		
		ConnectionService service = new ConnectionService(this);
		Thread thread =  new Thread(service);
		thread.start();
		
		ApkFile apk;
		try {
			apk = new ApkFile(helper.getAppPath());
			String manifest = apk.getManifestXml();
			helper.setActivityName(helper.extractActivity(manifest));
			txaLog.appendText("apk Activity: " + helper.getActivityName() + "\n");
			helper.setPackageName(helper.extractPackage(manifest));
			txaLog.appendText("apk Package: " + helper.getPackageName() + "\n");
		} catch (IOException e1) {
			e1.printStackTrace();
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
					enableButton();

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
				enableButton();
				
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
				devices = adb.getDevices();
				cbDevices.getItems().remove(device.getName());
				btnLaunch.setDisable(true);
			}
		});
	}

	@FXML
	void install(ActionEvent event) {
		
		InstallationService service = new InstallationService(this);
		Thread thread =  new Thread(service);
		thread.start();
	}

	@FXML
	void launch(ActionEvent event) {

		StringBuffer cmd = new StringBuffer();
		boolean tryAgain = true;

		try {

			if (devices != null) {
				for (int i = 0; i < devices.length; i++) {

					cmd.append("adb -s " + devices[i].getSerialNumber() + " shell am start -n " 
							+ helper.getPackageName() + "/" + helper.getActivityName());

					if (System.getProperty("os.name").equalsIgnoreCase("windows")) {
						processBuilder.command("cmd.exe", "/c", cmd.toString());
					} else if (System.getProperty("os.name").equalsIgnoreCase("linux")
							|| System.getProperty("os.name").indexOf("mac") > 0) {
						processBuilder.command("bash", "-c", cmd.toString());
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
		
		UninstallationService service = new UninstallationService(this);
		Thread thread =  new Thread(service);
		thread.start();
	}

	@FXML
	void ClearHistory(ActionEvent event) {
		txaLog.clear();
	}
	
	private void enableButton() {
		cbDevices.setDisable(false);
		btnInstall.setDisable(false);
		btnLaunch.setDisable(false);
		btnUninstall.setDisable(false);		
	}

}
