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
		controller.getProgressBar().setProgress(0.5);
		controller.getTxaLog().appendText("Trying connections...\n");
		controller.setAdb(AndroidDebugBridge.createBridge(controller.getHelper().getAdbPath(), true));

		if (controller.getAdb() == null) {
			System.err.println("Invalid ADB location.");
			controller.getTxaLog().appendText("Erro na localizaçao do ADB. \n");
			return;
		}
		controller.setDevices(controller.getAdb().getDevices());
		
		
		if (controller.getDevices().length > 0) {
			for (int i = 0; i < controller.getDevices().length; i++) {
				controller.getCbDevices().getItems().add(controller.getDevices()[i].getName());
			}
			controller.getCbDevices().setDisable(false);
			controller.getBtnInstall().setDisable(false);
			controller.getBtnLaunch().setDisable(false);
			controller.getBtnUninstall().setDisable(false);
		}
	}

}
