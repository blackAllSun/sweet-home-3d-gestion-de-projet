package metier;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import io.Content;
import io.URLContent;

/**
 * capable de lire le catalogue des meubles par défaut<br>
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="DefaultCatalog.png">
 */
public class DefaultCatalog extends Catalog {
	/**
	 * constructeur charge la BDD contenu de meuble du fichier *.properties<br>
     * @author spooky
     * @since 1-Arbre du catalogue des meubles
	 */
	public DefaultCatalog() {
		ResourceBundle resource=ResourceBundle.getBundle(DefaultCatalog.class.getName());
		for(int i=1;i<=50;i++) {
			String name=null;
			try {
				name=resource.getString("name#"+i);}
			catch(MissingResourceException ex) {ex.printStackTrace();break;}
			String category=resource.getString("category#"+i);
			Content icon=getContent(resource,"icon#"+i);
			Content model=getContent(resource,"model#"+i);
			float width=Float.parseFloat(resource.getString("width#"+i));
			float depth=Float.parseFloat(resource.getString("depth#"+i));
			float height=Float.parseFloat(resource.getString("height#"+i));
			boolean movable=Boolean.parseBoolean(resource.getString("movable#"+i));
			boolean doorOrWindow=Boolean.parseBoolean(resource.getString("doorOrWindow#"+i));
			add(new Category(category),new CatalogPieceOfFurniture(name,icon,model,width,depth,height,movable,doorOrWindow));
			
		}
	}
	/**
	 * charge fichier *.obj et *.png<br>
	 * @author spooky
	 * @param resource ResourceBundle
	 * @param key String 
	 * @return Content
	 * @since 1-Arbre du catalogue des meubles
	 * */
	private Content getContent(ResourceBundle resource, String key) {
	    String file = resource.getString(key);
	    URL url = DefaultCatalog.class.getResource(file); 
	    if(url==null) {throw new IllegalArgumentException("Unknown resource "+file);}
	    else return new URLContent(url);
	  
	}
}
