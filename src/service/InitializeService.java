/**
 * 
 */
package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.android.ddmlib.AndroidDebugBridge;

import javafx.geometry.Pos;

/**
 * @author user
 *
 */
public class InitializeService implements Runnable {

	
	public InitializeService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
//		txtFieldAppPath.setFocusTraversable(false);
//		txaLog.appendText("Detected OS: " + systemProp.getProperty("os.name") + ".\n"); // TODO: to remove.
//
//		try {
//			System.out.println("Initializing the DEBUG bridge.");
//			AndroidDebugBridge.init(false);
//		} catch (Exception e) {
//			txaLog.appendText("Exception in init()");
//			e.printStackTrace();
//		}
//
//		prop = new Properties();
//		InputStream input = null;
//
//		try {
//			input = new FileInputStream("file.path");
//			
//			prop.load(input);
//			appPath = prop.getProperty("appPath");
//			System.out.println(appPath);
//			
//			if (appPath != null) {
//				txtFieldAppPath.setText(appPath);
//				txtFieldAppPath.setAlignment(Pos.CENTER_RIGHT);
//			}
//			adbPath = prop.getProperty("adbPath");
//			System.out.println(adbPath);
//			if (adbPath != null) {
//				txtFieldAdbPath.setText(adbPath);
//				txtFieldAdbPath.setAlignment(Pos.CENTER_RIGHT);
//				this.connectDevices();
//			}
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
	}

}
