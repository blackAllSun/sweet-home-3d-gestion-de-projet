package metier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import event.SelectionEvent;
import listener.SelectionListener;

/**
 * catalogue des meubles<br>
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="Catalog.png">
 */
public abstract class Catalog {
	/**
	 * @since 1-Arbre du catalogue des meubles<br>
	 * {@link Catalog#getCategories()} <br>
	 * @Property $categories
	 */
	List<Category> categories=new ArrayList<Category>();
	/**
	 * Marqueur qui permet de savoir si liste a ete modifie <br>
	 * @since 1-Arbre du catalogue des meubles
	 * @Property $sorted
	 */
	boolean sorted;
	private List<CatalogPieceOfFurniture> selectedFurniture =Collections.emptyList();
	private List<SelectionListener> selectionListeners =new ArrayList<SelectionListener>();

	/**
	 * Accesseurs <br>
	 * @since 1-Arbre du catalogue des meubles
	 * @return List<Category>
	 */
	public List<Category> getCategories() {
		if(!sorted) {
			Collections.sort(this.categories);
			this.sorted=true;}
		return Collections.unmodifiableList(this.categories);}
	/**
	 * methode utilitaire : ajoute une categorie à ce catalogue si 1 categorie <br>
	 * de m nom que celle en parametre existe déjà ds cette categorie une exception <br>
	 * @since 1-Arbre du catalogue des meubles
	 * @param category
	 */
	public void add(Category category) {
		if(this.categories.contains(category)) {
			throw new IllegalArgumentException(category.getName()+" Already exists in Catalog");}
		this.categories.add(category);
		this.sorted=false;}
	/**
	 * methode utilitaire : ajoute un meuble pour les sous classes<br>
	 * @since 1-Arbre du catalogue des meubles
	 * @param category
	 * @param piece
	 */
	protected void add(Category category,CatalogPieceOfFurniture piece) {
		int index=this.categories.indexOf(category);
		if(index==-1) {add(category);}
		else {category=this.categories.get(index);}
                category.add(piece);
	}
	/**
	 * @return List<CatalogPieceOfFurniture>
	 */
	public List<CatalogPieceOfFurniture> getSelectedFurniture() {return Collections.unmodifiableList(this.selectedFurniture);}
	/**
	 * @param selectedFurniture
	 */
	public void setSelectedFurniture(List<CatalogPieceOfFurniture> selectedFurniture) {
		this.selectedFurniture = new ArrayList<CatalogPieceOfFurniture>(selectedFurniture);
		if (!this.selectionListeners.isEmpty()) {
			SelectionEvent selectionEvent =new SelectionEvent(this, getSelectedFurniture());
			SelectionListener [] listeners =this.selectionListeners.toArray(new SelectionListener [this.selectionListeners.size()]);
			for (SelectionListener listener : listeners) { 
				listener.selectionChanged(selectionEvent); }}}
	/**
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener) {this.selectionListeners.add(listener);}
	/**
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener) {this.selectionListeners.remove(listener);}
	@Override
	public String toString() {
		return "Catalog [categories=" + categories + ", sorted=" + sorted + ", selectedFurniture=" + selectedFurniture
				+ ", selectionListeners=" + selectionListeners + "]";
	}

}
