package io;

import java.awt.event.ActionEvent;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * @author spooky
 *
 */
public class ResourceAction extends AbstractAction {
	/**
	 * @param resource
	 * @param actionPrefix
	 */
	public ResourceAction(ResourceBundle resource,String actionPrefix) {
		String propertyPrefix = actionPrefix + ".";
		String name = resource.getString(propertyPrefix + NAME);
		putValue(NAME, name);
		putValue(DEFAULT, name);
		String shortDescription = getOptionalString(resource, propertyPrefix + SHORT_DESCRIPTION);
		if (shortDescription != null) {
		putValue(SHORT_DESCRIPTION, shortDescription);}
		String longDescription = getOptionalString(resource, propertyPrefix + LONG_DESCRIPTION);
		if (longDescription != null) {
		putValue(LONG_DESCRIPTION, longDescription);}
		String smallIcon = getOptionalString(resource, propertyPrefix + SMALL_ICON);
		if (smallIcon != null) {
		putValue(SMALL_ICON,new ImageIcon(getClass().getResource(smallIcon)));
		}
		String propertyKey = propertyPrefix + ACCELERATOR_KEY;
		String acceleratorKey = getOptionalString(resource,propertyKey + "." + System.getProperty("os.name"));


		if (acceleratorKey == null) {acceleratorKey = getOptionalString(resource, propertyKey);}
		if (acceleratorKey != null) {putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(acceleratorKey));}
		String mnemonicKey = getOptionalString(resource, propertyPrefix + MNEMONIC_KEY);
		if (mnemonicKey != null) {putValue(MNEMONIC_KEY,Integer.valueOf(mnemonicKey.charAt(0)));}

		setEnabled(false);}
	private String getOptionalString(ResourceBundle resource,
			String propertyKey) {
		try {return resource.getString(propertyKey);
		} catch (MissingResourceException ex) {
			return null;}}
	@Override
	public void actionPerformed(ActionEvent ev) {throw new UnsupportedOperationException();}
	}
