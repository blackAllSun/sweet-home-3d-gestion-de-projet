package metier;
import java.text.Collator;

import io.Content;

/**
 * Ensemble de meubles
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="CatalogPieceOfFurniture.png">
 */
public class CatalogPieceOfFurniture implements PieceOfFurniture,Comparable<CatalogPieceOfFurniture>{
	/**
	 * Nom du meuble<br>
	 * @since 1-Arbre du catalogue des meubles <br>
	 * {@link CatalogPieceOfFurniture#setName(String)}<br>
	 * {@link CatalogPieceOfFurniture#getName()} <br>
	 * @Property name String
	 */
	String name;
	/**
	 * Image représentant le meuble<br>
	 * @since 1-Arbre du catalogue des meubles <br>
	 * {@link CatalogPieceOfFurniture#setIcon(Content)}<br>
	 * {@link CatalogPieceOfFurniture#getIcon()} <br>
	 * @Property icon Content
	 */
	Content icon;
	/**
	 * Modèle 3D du meuble<br>
	 * @since 1-Arbre du catalogue des meubles <br>
	 * {@link CatalogPieceOfFurniture#setModel(Content)}<br>
	 * {@link CatalogPieceOfFurniture#getModel()} <br>
	 * @Property model Content
	 */
	Content model;
	/**
	 * Largeur du meuble exprimée en cm<br>
	 * @since 1-Arbre du catalogue des meubles<br>
	 * {@link CatalogPieceOfFurniture#setWidth(float)}<br>
	 * {@link CatalogPieceOfFurniture#getWidth()} <br>
	 * @Property name float
	 */
	float width;
	/**
	 * Profondeur du meuble exprimée en cm<br/>
	 * @since 1-Arbre du catalogue des meubles
	 * {@link CatalogPieceOfFurniture#setDepth(float)}<br>
	 * {@link CatalogPieceOfFurniture#getDepth()} <br>
	 * @Property depth float
	 */
	float depth;
	/**
	 * Hauteur du meuble exprimée en cm<br>
	 * @since 1-Arbre du catalogue des meubles
	 * {@link CatalogPieceOfFurniture#setHeight(float)}<br>
	 * {@link CatalogPieceOfFurniture#getHeight()} <br>
	 * @Property height float
	 */
	float height;
	/**
	 * Si faux, meuble non déménageable comme une baignoire ou des W.C.<br>
	 * @since 1-Arbre du catalogue des meubles
	 * {@link CatalogPieceOfFurniture#setMovable(boolean)}<br>
	 * {@link CatalogPieceOfFurniture#isMovable()} <br>
	 * @Property movable float
	 */
	boolean movable;
	/**
	 * Si vrai, meuble de type porte ou fenêtre incrustable dans un mur<br>
	 * @since 1-Arbre du catalogue des meubles<br>
	 * {@link CatalogPieceOfFurniture#setDoorOrWindow(boolean)}<br>
	 * {@link CatalogPieceOfFurniture#isDoorOrWindow()} <br>
	 * @Property doorOrWindow float
	 */
	boolean doorOrWindow;
	/**
	 * 
	 * COMPARATOR Collator
	 */
	private static Collator COMPARATOR=Collator.getInstance();
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param name - Nom du meuble
	 * @param icon - Image représentant le meuble
	 * @param model - Modèle 3D du meuble
	 * @param width - Largeur du meuble exprimée en cm
	 * @param depth - Profondeur du meuble exprimée en cm
	 * @param height - Hauteur du meuble exprimée en cm
	 * @param movable - Si faux, meuble non déménageable comme une baignoire ou des W.C.
	 * @param doorOrWindow - Si vrai, meuble de type porte ou fenêtre incrustable dans un mur
	 */
	public CatalogPieceOfFurniture(String name, Content icon, Content model, float width, float depth, float height,
			boolean movable, boolean doorOrWindow) {
		super();
		this.name = name;
		this.icon = icon;
		this.model = model;
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.movable = movable;
		this.doorOrWindow = doorOrWindow;
		//System.out.println(this);
	}
	@Override
	public String toString() {
		return "CatalogPieceOfFurniture [name=" + name + ", icon=" + icon + ", model=" + model + ", width=" + width
				+ ", depth=" + depth + ", height=" + height + ", movable=" + movable + ", doorOrWindow=" + doorOrWindow
				+ "]";}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return String
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param name
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return Content
	 */
	@Override
	public Content getIcon() {
		return icon;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param icon
	 */
	@Override
	public void setIcon(Content icon) {
		this.icon = icon;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return Content
	 */
	@Override
	public Content getModel() {
		return model;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param model
	 */
	@Override
	public void setModel(Content model) {
		this.model = model;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return float
	 */
	@Override
	public float getWidth() {
		return width;
	}
	/**
	 * @param width
	 */
	@Override
	public void setWidth(float width) {
		this.width = width;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return float
	 */
	@Override
	public float getDepth() {
		return depth;
	}
	/**
	 * @param depth
	 */
	@Override
	public void setDepth(float depth) {
		this.depth = depth;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return float
	 */
	@Override
	public float getHeight() {
		return height;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param height
	 */
	@Override
	public void setHeight(float height) {
		this.height = height;
	}
	@Override
	public boolean isMovable() {
		return movable;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param movable
	 */
	@Override
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	@Override
	public boolean isDoorOrWindow() {
		return doorOrWindow;
	}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param doorOrWindow
	 */
	@Override
	public void setDoorOrWindow(boolean doorOrWindow) {
		this.doorOrWindow = doorOrWindow;
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int compareTo(CatalogPieceOfFurniture piece) {return COMPARATOR.compare(this.name, piece.name);}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(depth);
		result = prime * result + (doorOrWindow ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + (movable ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(width);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CatalogPieceOfFurniture && COMPARATOR.equals(this.name,((CatalogPieceOfFurniture) obj).name);
	}

	
}
