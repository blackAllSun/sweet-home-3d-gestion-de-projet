package junit;

import java.lang.reflect.Field;
import java.util.Locale;

import javax.swing.*;

import io.RecorderException;
import junit.framework.TestCase;
import metier.*;
import swing.UserPreferencesPanel;

public class UserPreferencesPanelTest extends TestCase{
	public void testUserPreferencesPanel() throws RecorderException,NoSuchFieldException, IllegalAccessException {
		Locale.setDefault(Locale.FRANCE);
		UserPreferences defaultPreferences =new DefaultUserPreferences();
		UserPreferences preferences = new FileUserPreferences();
		preferences.setUnit(defaultPreferences.getUnit());
		preferences.setMagnetismEnabled(defaultPreferences.isMagnetismEnabled());
		preferences.setNewWallThickness(defaultPreferences.getNewWallThickness());
		preferences.setNewHomeWallHeight(defaultPreferences.getNewHomeWallHeight());
		UserPreferencesPanel panel = new UserPreferencesPanel();
		panel.setPreferences(preferences);
		JRadioButton centimeterRadioButton =(JRadioButton)getField(panel, "centimeterRadioButton");
		JRadioButton inchRadioButton =(JRadioButton)getField(panel, "inchRadioButton");
		JCheckBox magnetismCheckBox =(JCheckBox)getField(panel, "magnetismCheckBox");
		JSpinner newWallThicknessSpinner =(JSpinner)getField(panel, "newWallThicknessSpinner");
		JSpinner newHomeWallHeightSpinner =(JSpinner)getField(panel, "newHomeWallHeightSpinner");
		assertTrue(centimeterRadioButton.isSelected()); 
		assertFalse(inchRadioButton.isSelected());
		assertTrue(magnetismCheckBox.isSelected());
		assertEquals(newWallThicknessSpinner.getValue(),defaultPreferences.getNewWallThickness());
		assertEquals(newHomeWallHeightSpinner.getValue(),defaultPreferences.getNewHomeWallHeight());
		inchRadioButton.setSelected(true); 
		magnetismCheckBox.setSelected(false);
		newWallThicknessSpinner.setValue(1);
		newHomeWallHeightSpinner.setValue(100);
		preferences.setUnit(panel.getUnit());
		preferences.setMagnetismEnabled(panel.isMagnetismEnabled());
		preferences.setNewWallThickness(panel.getNewWallThickness());
		preferences.setNewHomeWallHeight(panel.getNewHomeWallHeight());
		assertPreferencesEqual(UserPreferences.Unit.INCH, false,UserPreferences.Unit.inchToCentimer(1),UserPreferences.Unit.inchToCentimer(100), preferences);
		preferences.write();
		UserPreferences readPreferences = new FileUserPreferences();
		assertPreferencesEqual(preferences.getUnit(),preferences.isMagnetismEnabled(),preferences.getNewWallThickness(),preferences.getNewHomeWallHeight(), readPreferences);}
	private void assertPreferencesEqual(UserPreferences.Unit unit,boolean magnetism,float newWallThickness,float newHomeWallHeight,UserPreferences preferences) {
		assertEquals(unit, preferences.getUnit());
		assertEquals(magnetism, preferences.isMagnetismEnabled());
		assertEquals(newWallThickness,preferences.getNewWallThickness());
		assertEquals(newHomeWallHeight,preferences.getNewHomeWallHeight());}
	private Object getField(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException { 
		Field field =instance.getClass().getDeclaredField(fieldName); 
		field.setAccessible(true); 
		return field.get(instance);}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
