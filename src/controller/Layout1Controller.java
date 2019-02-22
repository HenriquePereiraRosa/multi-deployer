package controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

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


public class Layout1Controller {
	
	final FileChooser fileChooser = new FileChooser();

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
    void initialize() {
        assert txtFieldFileAddress != null : "fx:id=\"txtFieldFileAddress\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert btnDeploy != null : "fx:id=\"btnDeploy\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert txaLog != null : "fx:id=\"txaLog\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert cbxDevices != null : "fx:id=\"cbxDevices\" was not injected: check your FXML file 'Layout1.fxml'.";
        assert btnScan != null : "fx:id=\"btnScan\" was not injected: check your FXML file 'Layout1.fxml'.";

    }
    
    
    @FXML
    void selectFile(Event event) {
    	
    	LocalDate localDate = LocalDate.now();
    	LocalTime localTime = LocalTime.now();
    	
    	Parent parent = new Pane();
    	Scene scene = new Scene(parent);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	
		File file = fileChooser.showOpenDialog(stage);
    	
    	txaLog.appendText("Mouse clicked in TxtFieldFileAddress. On " + localDate + "_" + localTime + ".\n");
    	
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Applications files", "*.apk"));
		if (file != null) {
			txaLog.appendText(file.getPath() + "\n");
		} else {
			txaLog.appendText("No file selected. \n");
		}
    	
    }
    

    @FXML
    void scanADBDevices(ActionEvent event) {
		
    	// Create the libusb context
        Context context = new Context();

        // Initialize the libusb context
        int result = LibUsb.init(context);
        if (result < 0)
        {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

        // Read the USB device list
        DeviceList list = new DeviceList();
        result = LibUsb.getDeviceList(context, list);
        if (result < 0)
        {
            throw new LibUsbException("Unable to get device list", result);
        }

        try
        {
            // Iterate over all devices and list them
            for (Device device: list)
            {
                int address = LibUsb.getDeviceAddress(device);
                int busNumber = LibUsb.getBusNumber(device);
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result < 0)
                {
                    throw new LibUsbException(
                        "Unable to read device descriptor", result);
                }
                System.out.format(
                    "Bus %03d, Device %03d: Vendor %04x, Product %04x%n",
                    busNumber, address, descriptor.idVendor(),
                    descriptor.idProduct());
                
                txaLog.appendText(device + "\n");
            }
        }
        finally
        {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }

        // Deinitialize the libusb context
        LibUsb.exit(context); 

    }
    
}
