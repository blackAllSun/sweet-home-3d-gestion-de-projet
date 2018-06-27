package controller;

import javax.swing.JComponent;

import metier.HomeModel;
import metier.UserPreferences;


/**
 * @author spooky
 *
 */
public abstract class AbstractHomeController implements HomeFurnitureController{
	JComponent view;
	public AbstractHomeController() {}
	/**
	 * @param home
	 * @param preferences
	 */
	public AbstractHomeController(HomeModel home, UserPreferences preferences) {}
	@Override
	public abstract void addHomeFurniture() ;
	/**
	 * @return JComponent
	 */
	public abstract JComponent getView() ;
}
