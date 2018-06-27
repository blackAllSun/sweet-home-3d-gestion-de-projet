package controller;

import metier.HomeModel;
import metier.UserPreferences;
import swing.HomeApplication;
import swing.HomeFramePane;

public class HomeFrameController  extends HomeController{
	HomeModel home;
	HomeApplication application;
	/**
	 * @param home
	 * @param application
	 */
	public HomeFrameController(HomeModel home,HomeApplication application) {
		this.home=home;
		this.application=application;
			new HomeFramePane(home, application, this).displayView();}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
