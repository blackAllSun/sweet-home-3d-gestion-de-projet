package metier;
import java.text.Collator;
import java.util.*;


/** Categorie<br>
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="Category.png">
 */
public class Category implements Comparable<Category>{
	/**
	 * Nom de la catégorie<br/>
	 * @since 1-Arbre du catalogue des meubles <br/>
	 * {@link Category#setName(String)}<br/>
	 * {@link Category#getName()} <br/>
	 * @Property name String
	 */
	String name;
	/**
	 * Liste des meubles de la catégorie<br/>
	 * @since 1-Arbre du catalogue des meubles <br/>
	 * {@link Category#setFurniture(List)}<br/>
	 * {@link Category#getFurniture()} <br/>
	 * @Property furniture List<CatalogPieceOfFurniture> <br/>
	 */
	List<CatalogPieceOfFurniture> furniture=new ArrayList<CatalogPieceOfFurniture>();
	/**
	 * COMPARATOR Collator
	 * @since 1-Arbre du catalogue des meubles
	 */
	private static Collator COMPARATOR=Collator.getInstance();
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * sorted boolean
	 */
	boolean sorted;
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return String
	 */
	public String getName() {return name;}
	@Override
	public String toString() {return "Category [name=" + name + ", furniture=" + furniture + ", sorted=" + sorted + "]";}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param name
	 */
	public void setName(String name) {this.name = name;}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @return List<CatalogPieceOfFurniture>
	 */
	public List<CatalogPieceOfFurniture> getFurniture() {return furniture;}
	/**
	 * @param furniture
	 */
	public void setFurniture(List<CatalogPieceOfFurniture> furniture) {this.furniture = furniture;}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param name
	 */
	public Category(String name) {
		this.name=name;
		//System.out.println(this);
	}
	@Override
	public boolean equals(Object obj) {return obj instanceof Category && COMPARATOR.equals(this.name, ((Category)obj).name);}
	@Override
	public int hashCode() {return this.name.hashCode();}
	@Override
	public int compareTo(Category category) {return COMPARATOR.compare(this.name, category.name);}
	/**
	 * @since 1-Arbre du catalogue des meubles
	 * @param piece
	 */
	public void add(CatalogPieceOfFurniture piece) {
		if(this.furniture.contains(piece)) {
			throw new IllegalArgumentException(piece.getName()+" Already exists in Catalog "+this.name);}
		this.furniture.add(piece);
		this.sorted = false;
		}
}
