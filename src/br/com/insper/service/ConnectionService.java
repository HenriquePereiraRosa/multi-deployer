/**
 * 
 */
package br.com.insper.service;

import br.com.insper.service.util.SleepUtil;
import com.android.ddmlib.AndroidDebugBridge;

import br.com.insper.controller.Layout1Controller;

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

		// TODO: remove after test
		System.out.println("Before 1st sleep");
		SleepUtil.sleep(3000L);
		System.out.println("After 1st sleep");

		try {
			controller.getProgressBar().setProgress(0.5);
			controller.getTxaLog().appendText("Trying connections...\n");
			controller.setAdb(AndroidDebugBridge.createBridge(controller.getHelper().getAdbPath(), true));

			if (controller.getAdb() == null) {
				System.err.println("Invalid ADB location.\n");
				controller.getTxaLog().appendText("Error in ADB location.\n");
				return;
			}

			// TODO: remove after test
			System.out.println("Before 2st sleep");
			SleepUtil.sleep(1000L);
			System.out.println("After 2st sleep");

//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				System.out.println("Thread Sleep Exception\n");
//				e.printStackTrace();
//			}

			controller.setDevices(controller.getAdb().getDevices());

			if (controller.getDevices().length > 0) {
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
			controller.getTxaLog().appendText("- Mac hint: /Users/agile/Library/Android/sdk/platform-tools/adb\n");
			controller.getTxaLog().appendText("** You can also reinstall ADB on an known location. **\n");
			e.printStackTrace();
		}
	}

}
