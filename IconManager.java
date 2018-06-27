package io;

import java.awt.Component;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Classe Chargement Icone
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="IconManager.png">
 */
public class IconManager {
	static IconManager instance;
	Content errorIconContent,waitIconContent;
	Executor iconLoader;
	Map<ContentHeightKey,Icon> icons=new HashMap<ContentHeightKey,Icon>();
	/**
	 * @author spooky
	 * @since 1-Arbre du catalogue des meubles<br>
	 */
	private IconManager() {
		this.errorIconContent=new URLContent(this.getClass().getResource("resources/error.png"));
		this.waitIconContent=new URLContent(this.getClass().getResource("resources/wait.png"));
		this.iconLoader=Executors.newFixedThreadPool(5);}
	
	/**
	 * @author spooky
	 * @since 1-Arbre du catalogue des meubles<br>
	 * @return IconManager
	 */
	public static synchronized IconManager getInstance() {
		if(instance==null) {instance=new IconManager();}
		return instance;}
	/**
	 * Retourne l’instance unique de la classe instanciée si elle n’existe pas encore.
	 * @author spooky
	 * @since 1-Arbre du catalogue des meubles<br>
	 * @param content
	 * @param height
	 * @param waitingComponent
	 * @return Icon
	 */
	public Icon getIcon(Content content,int height,Component waitingComponent) {
		ContentHeightKey contentKey=new ContentHeightKey(content,height);
		Icon icon=this.icons.get(contentKey);
		if(icon==null) {
			if(content==this.errorIconContent || content==this.waitIconContent) {
				icon=createIcon(content,height,waitingComponent,null);}
			else {
				icon=new IconProxy(content,height,waitingComponent,
						getIcon(this.errorIconContent,height,null),
						getIcon(this.waitIconContent, height, null));}
			this.icons.put(contentKey, icon);
		}
		return icon;}
	/**
	 * Charge et redimensionne l’icône contenue dans l’objet content .
	 * @author spooky
	 * @since 1-Arbre du catalogue des meubles<br>
	 * @param content
	 * @param height
	 * @param waitingComponent
	 * @param errorIcon
	 * @return Icon
	 */
	private Icon createIcon(Content content, int height,Component waitingComponent,Icon errorIcon) {
		try {
			InputStream contentStream = content.openStream();
			BufferedImage image = ImageIO.read(contentStream);
			contentStream.close();
			if (image != null) {
			int width = image.getWidth() * height / image.getHeight();
			Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon (scaledImage);}
		}catch(IOException ex) {ex.printStackTrace();}
		return errorIcon;}
	/**
	 * Classe des clés de l’ensemble icons .
	 * @author spooky
	 * @since 1-Arbre du catalogue des meubles<br>
	 */
	private static final class ContentHeightKey {
		final Content content;
		int height;
		/**
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
		 * @param content
		 * @param height
		 */
		public ContentHeightKey(Content content,int height) {
			this.content=content;
			this.height=height;}
		/**
		 * Redéfinition de la méthode equals pour établir une relation d’égalité entre des clés contenant le même objet de type Content et la même hauteur.
		 * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
	     * @param obj Object
		 * */
		@Override
		public boolean equals(Object obj) {
			ContentHeightKey key=(ContentHeightKey) obj;
			return this.content==key.content && this.height==key.height;}
		/**
		 * Redéfinition de la méthode hashCode pour que deux clés égales aient le même code de hachage (code utilisé par la classe HashMap pour optimiser la recherche d’une clé).
		 * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
		 * */
		@Override
		public int hashCode() {return this.content.hashCode()+this.height;}}
		/**
		 * Proxy d’icône affichant l’icône d’attente waitIcon tant que le chargement de l’icône contenue dans le paramètre content du constructeur n’est pas chargée.
		 * @author spooky
		 * @since 1-Arbre du catalogue des meubles<br>
		 */
	private class IconProxy implements Icon {
		Icon icon;
		/**
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
		 * @param content
		 * @param height
		 * @param waitingComponent
		 * @param errorIcon
		 * @param waitIcon
		 */
		public IconProxy(final Content content,final int height,final Component waitingComponent,final Icon errorIcon,Icon waitIcon) {
			this.icon=waitIcon;
			iconLoader.execute(new Runnable() {
				@Override
				public void run() {
					icon=createIcon(content,height,waitingComponent,errorIcon);
					waitingComponent.repaint();}});}
		/**
		 * Retourne la longueur de l'icone
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
	     * @return int
		 * */
		@Override
		public int getIconHeight() {return this.icon.getIconHeight();}
		/**
		 * Retourne la largeur de l'icone
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
	     * @return int
		 * */
		@Override
		public int getIconWidth() {return this.icon.getIconWidth();}
		/**
		 * Draw the icon at the specified location. Icon implementations may use the Component argument to get properties useful for painting, e.g. the foreground or background color.
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
	     * @author spooky
	     * @since 1-Arbre du catalogue des meubles<br>
	     * @param c Component
	     * @param g Graphics
	     * @param x int
	     * @param y int
		 * */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {this.icon.paintIcon(c, g, x, y);}
	}
}
