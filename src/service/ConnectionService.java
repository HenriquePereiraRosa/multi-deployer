/**
 * 
 */
package service;

import com.android.ddmlib.AndroidDebugBridge;

import controller.Layout1Controller;

/**
 * @author user
 *
 */
public class ConnectionService implements Runnable {

	private Layout1Controller controller;

	public ConnectionService(Layout1Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {

		try {
			controller.getProgressBar().setProgress(0.5);
			controller.getTxaLog().appendText("Trying connections...\n");
			controller.setAdb(AndroidDebugBridge.createBridge(controller.getHelper().getAdbPath(), true));

			if (controller.getAdb() == null) {
				System.err.println("Invalid ADB location.");
				controller.getTxaLog().appendText("Error in ADB location. \n");
				return;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Thread Sleep Exception");
				e.printStackTrace();
			}

			controller.setDevices(controller.getAdb().getDevices());

			if (controller.getDevices().length > 0) {
				for (int i = 0; i < controller.getDevices().length; i++) {
					controller.getCbDevices().getItems().add(controller.getDevices()[i].getName());
				}
				controller.getTxaLog().appendText("Devices connected. \n");
				controller.getProgressBar().setProgress(1.0);
				controller.enableButtons();
			}
		} catch (Exception e) {
			controller.getProgressBar().setProgress(0);
			controller.getTxaLog().clear();
			controller.getTxaLog().appendText("Error: Verify the ADB file path:\n");
			controller.getTxaLog().appendText("- Linux hint: /home/user/Android/Sdk/platform-tools/adb\n");
			controller.getTxaLog().appendText("- Windows hint: C:\\Users\\[user]\\AppData\\Local\\Android\\sdk\\platform-tools\n");
			controller.getTxaLog().appendText("- You can also reinstall ADB on an known location.\n");
			e.printStackTrace();
		}
	}

}
