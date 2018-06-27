package metier;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import io.RecorderException;

/**
 * @author spooky
 * d’une application. Sur un ordinateur équipé d’un système multi-utilisateur, ces préférences <br>
 * sont soit propres à chaque utilisateur, soit propres au système lui-même et donc partagées <br>
 * par tous les utilisateurs<br>
 * <img src="UserPreferences.png">
 */
public abstract class UserPreferences {
	/**
	 * @author spooky
	 *
	 */
	public enum Unit {CENTIMETER,INCH;

	/**
	 * @param length
	 * @return float
	 */
	public static float centimerToInch(float length) {return length / 2.54f;}
	/**
	 * @param length
	 * @return float
	 */
	public static float inchToCentimer(float length) {return length * 2.54f;}};
	/**
	 * catalog Catalog
	 */

	Catalog catalog;
	/**
	 * unit Unit
	 */
	Unit unit;
	boolean magnetismEnabled;
	float newWallThickness;
	float newHomeWallHeight;
	public UserPreferences() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}
	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * @return Catalog
	 */
	public Catalog getCatalog() {return catalog;}
	/**
	 * @param catalog Catalog
	 */
	public void setCatalog(Catalog catalog) {this.catalog = catalog;}
	/**
	 * @return Unit
	 */
	public Unit getUnit() {return unit;}
	/**
	 * @param unit Unit
	 */
	public void setUnit(Unit unit) {
		if(this.unit != unit) {
			Unit oldUnit=this.unit;
			this.unit=unit;
			this.propertyChangeSupport.firePropertyChange("unit", oldUnit, unit);}}
	/**
	 * @return boolean
	 */
	public boolean isMagnetismEnabled() {return magnetismEnabled;}
	/**
	 * @param magnetismEnabled boolean
	 */
	public void setMagnetismEnabled(boolean magnetismEnabled) {
		if(this.magnetismEnabled != magnetismEnabled) {
			boolean oldMagnetismEnabled=this.magnetismEnabled;
			this.magnetismEnabled = magnetismEnabled;
			this.propertyChangeSupport.firePropertyChange("magnetismEnabled", oldMagnetismEnabled, magnetismEnabled);}}

	/**
	 * @return float
	 */
	public float getNewHomeWallHeight() {return newHomeWallHeight;}
	/**
	 * @param newHomeWallHeight float
	 */
	public void setNewHomeWallHeight(float newHomeWallHeight) {
		if(this.newHomeWallHeight !=newHomeWallHeight) {
			float oldNewHomeWallHeight=this.newHomeWallHeight;
			this.newHomeWallHeight = newHomeWallHeight;
			this.propertyChangeSupport.firePropertyChange("newHomeWallHeight", oldNewHomeWallHeight, newHomeWallHeight);}
	}
	/**
	 * @return float
	 */
	public float getNewWallThickness() {return newWallThickness;}
	/**
	 * @param newWallThickness float
	 */
	public void setNewWallThickness(float newWallThickness) {
		if(this.newWallThickness != newWallThickness) {
			float oldNewWallThickness=this.newWallThickness;
			this.newWallThickness = newWallThickness;
			this.propertyChangeSupport.firePropertyChange("newWallThickness", oldNewWallThickness, newWallThickness);}
	}
	/**
	 * @param length float
	 * @return float
	 */
	public static float centimerToInch(float length) {return length/2.54f;}
	/**
	 * @param length float
	 * @return float
	 */
	public static float inchToCentimer(float length) {return length*2.54f;}
	/**
	 * @throws RecorderException
	 */
	public abstract void write() throws RecorderException ;
	/**
	 * @param property
	 * @param listener
	 */
	public void addPropertyChangeListener(String property,PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(property, listener);}
	/**
	 * @param property
	 * @param listener
	 */
	public void removeProrertyChangeistener(String property,PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(property, listener);}
}
