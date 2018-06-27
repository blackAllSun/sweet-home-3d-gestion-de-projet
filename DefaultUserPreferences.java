package metier;

import java.util.ResourceBundle;

import io.RecorderException;

/**
 * @author spooky
 * @since 2-Tableau des meubles du logement<br>
 * classe relatives à la gestion de l’enregistrement et la lecture des préférences <br>
 * d’une application. Sur un ordinateur équipé d’un système multi-utilisateur, ces préférences <br>
 * sont soit propres à chaque utilisateur, soit propres au système lui-même et donc partagées <br>
 * par tous les utilisateurs<br>
 * <img src="DefaultUserPreferences.png">
 */
public class DefaultUserPreferences extends UserPreferences{

	public DefaultUserPreferences() {
		setCatalog(new DefaultCatalog());
		ResourceBundle resource = ResourceBundle.getBundle(DefaultUserPreferences.class.getName());
		Unit defaultUnit = Unit.valueOf(resource.getString("unit").toUpperCase());
		setUnit(defaultUnit);
		setNewHomeWallHeight(Float.parseFloat(resource.getString("newHomeWallHeight")));
	}

	@Override
	public void write() throws RecorderException {throw new RecorderException("Default user preferences can't be written");}
	
}
