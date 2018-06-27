package main;

import controller.HomeController;
import metier.*;
/**
 * @author spooky
 * @since 4-Modification du tableau des meubles avec MVC
 */
public class SweetHome3D {

	public static void main(String[] args) {
		UserPreferences preferences=new DefaultUserPreferences();
		HomeModel home=new Home();
		new HomeController(home,preferences);
	}

}
