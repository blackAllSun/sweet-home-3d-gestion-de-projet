package metier;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import io.RecorderException;

public class FileUserPreferences extends UserPreferences {
	public FileUserPreferences() {
		DefaultUserPreferences defaultPreferences =new DefaultUserPreferences();
		setCatalog(defaultPreferences.getCatalog());
		Preferences preferences = getPreferences();
		Unit unit = Unit.valueOf(preferences.get("unit",defaultPreferences.getUnit().toString()));
		setUnit(unit);
		setMagnetismEnabled(preferences.getBoolean("magnetismEnabled", true));
		setNewWallThickness(preferences.getFloat("newWallThickness",defaultPreferences.getNewWallThickness()));
		setNewHomeWallHeight(preferences.getFloat("newHomeWallHeight",defaultPreferences.getNewHomeWallHeight()));}
	@Override
	public void write() throws RecorderException {
		Preferences preferences = getPreferences();
		preferences.put("unit", getUnit().toString());
		preferences.putBoolean("magnetismEnabled", isMagnetismEnabled());
		preferences.putFloat("newWallThickness", getNewWallThickness());
		preferences.putFloat("newHomeWallHeight", getNewHomeWallHeight());
		try {preferences.sync(); 
		} catch (BackingStoreException ex) {throw new RecorderException("Couldn't write preferences", ex);}}
	private Preferences getPreferences() {return Preferences.userNodeForPackage(FileUserPreferences.class);}
	
}
