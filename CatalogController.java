package controller;

import java.util.List;

import javax.swing.JComponent;

import metier.Catalog;
import metier.CatalogPieceOfFurniture;
import swing.CatalogTree;

/**
 * @author spooky
 *
 */
public class CatalogController {
	private Catalog catalog;
	private JComponent catalogView;
	/**
	 * @param catalog
	 */
	public CatalogController(Catalog catalog) {
		this.catalog = catalog;
		this.catalogView = new CatalogTree(catalog, this);
                System.out.println("CatalogController : "+this);}
	/**
	 * @return JComponent
	 */
	public JComponent getView() {return this.catalogView;}
	/**
	 * @param selectedFurniture
	 */
	public void setSelectedFurniture(List<CatalogPieceOfFurniture> selectedFurniture) {
		this.catalog.setSelectedFurniture(selectedFurniture);}
}
