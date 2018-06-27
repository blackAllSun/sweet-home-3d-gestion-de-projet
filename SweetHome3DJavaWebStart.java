package main;

import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.jnlp.*;
import controller.HomeController;
import controller.HomeFrameController;
import event.HomeEvent;
import io.RecorderException;
import listener.HomeListener;
import metier.*;
import swing.HomeApplication;

/**
 * @author spooky
 *
 */
public class SweetHome3DJavaWebStart extends HomeApplication{
	private HomeRecorder homeRecorder;
	private UserPreferences userPreferences;
	private static HomeApplication application;
	public SweetHome3DJavaWebStart() {
		this.homeRecorder = new HomeFileRecorder();
		this.userPreferences = new FileUserPreferences();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (application == null) { 
			initLookAndFeel();
			application = createApplication();}

		if (args.length == 2 && args [0].equals("-open")) { 
			try {
			HomeModel home =application.getHomeRecorder().readHome(args [1]);
			application.addHome(home);
			} catch (RecorderException ex) {
			ResourceBundle resource = ResourceBundle.getBundle(HomeController.class.getName());
			String message = String.format(
			resource.getString("openError"), args [1]);
			JOptionPane.showMessageDialog(null, message,"Sweet Home 3D", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();}
		} else {
			Home home = new Home(
			application.getUserPreferences().getNewHomeWallHeight());
			application.addHome(home);}
	}
	private static HomeApplication createApplication() {
		javax.jnlp.SingleInstanceService service = null;
		final SingleInstanceListener singleInstanceListener =new SingleInstanceListener() {
			@Override
			public void newActivation(String [] args) {main(args);}};
			try {
				service = (SingleInstanceService)ServiceManager.lookup(
				"javax.jnlp.SingleInstanceService"); 	
				service.addSingleInstanceListener(singleInstanceListener);} 
			catch (UnavailableServiceException ex) {}
			final SingleInstanceService singleInstanceService = service;
			final HomeApplication application = new SweetHome3DJavaWebStart();
			application.addHomeListener(new HomeListener() {
				@Override
				public void homeChanged(HomeEvent ev) {
				switch (ev.getType()) {
				case ADD :HomeModel home = ev.getHome();new HomeFrameController(home, application);break;
				case DELETE :
				if (application.getHomes().isEmpty()) {
					if (singleInstanceService != null) {
						singleInstanceService.removeSingleInstanceListener(singleInstanceListener);}
					System.exit(0);}break;
				default:
					break;}};});
				return application;}
	private static void initLookAndFeel() {
		System.setProperty("sun.swing.enableImprovedDragGesture", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name","Sweet Home 3D");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		 try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {e.printStackTrace();}
		}
	@Override
	public HomeRecorder getHomeRecorder() {
		// TODO Auto-generated method stub
		return homeRecorder;
	}
	@Override
	public UserPreferences getUserPreferences() {
		// TODO Auto-generated method stub
		return userPreferences;
	}
}
