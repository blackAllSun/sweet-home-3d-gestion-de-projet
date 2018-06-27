package swing;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.swing.Icon;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultTreeCellRenderer;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import controller.CatalogController;
import event.SelectionEvent;
import io.Content;
import io.IconManager;
import listener.SelectionListener;
import metier.Catalog;
import metier.CatalogPieceOfFurniture;
import metier.Category;

/**
 * sous-classe de javax.swing.JTree , pour le composant graphique de l’arbre qui affichera le catalogue de meubles<br>
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="CatalogTree.png">
 */
public class CatalogTree extends JTree {
	/**
	 * treeSelectionListener TreeSelectionListener
	 */
	private TreeSelectionListener treeSelectionListener;
	/**
	 * Constructor <br>
	 * @since 1-Arbre du catalogue des meubles
	 * @param catalog Catalog
	 */
	public CatalogTree(Catalog catalog) {
		this(catalog, null);}
	/**
	 * Constructor <br>
	 * @param catalog Catalog 
	 * @param controller CatalogController
	 */
	public CatalogTree(Catalog catalog, CatalogController controller) {
		setModel(new CatalogTreeModel (catalog));
		setRootVisible(false);
		setShowsRootHandles(true);
		setCellRenderer(new CatalogCellRenderer());
		if (controller != null) {
			addSelectionListeners(catalog, controller);}}
	/**
	 * ajouter listeners Selection
	 * @param catalog final Catalog
	 * @param controller final CatalogController
	 */
	private void addSelectionListeners(final Catalog catalog,final CatalogController controller) {
		final SelectionListener catalogSelectionListener =new SelectionListener() {
			@Override
			public void selectionChanged(SelectionEvent ev) {
				getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
				clearSelection();
				for (Object item : ev.getSelectedItems()) {
					selectPieceOfFurniture(catalog,(CatalogPieceOfFurniture)item);}
				getSelectionModel().addTreeSelectionListener(treeSelectionListener); }};
		this.treeSelectionListener = new TreeSelectionListener () {
			@Override
			public void valueChanged(TreeSelectionEvent ev) {
				catalog.removeSelectionListener(catalogSelectionListener);
				controller.setSelectedFurniture(getSelectedFurniture());
				catalog.addSelectionListener(catalogSelectionListener);}};
		getSelectionModel().addTreeSelectionListener(this.treeSelectionListener);
		catalog.addSelectionListener(catalogSelectionListener);}
	/**
	 * @param catalog Catalog
	 * @param selectedPiece CatalogPieceOfFurniture
	 */
	private void selectPieceOfFurniture(Catalog catalog,CatalogPieceOfFurniture selectedPiece) {
		for (Category category : catalog.getCategories()) {
			for (CatalogPieceOfFurniture piece : category.getFurniture()) {
				if (piece == selectedPiece) {
					TreePath path = new TreePath(new Object [] {catalog, category, piece});
					addSelectionPath(path);
					scrollRowToVisible(getRowForPath(path));
					break;}}}}
	/**
	 * @return List<CatalogPieceOfFurniture>
	 */
	private List<CatalogPieceOfFurniture> getSelectedFurniture() {
		List<CatalogPieceOfFurniture> selectedFurniture = new ArrayList<CatalogPieceOfFurniture>();
		TreePath [] selectionPaths = getSelectionPaths();
		if (selectionPaths != null) {
			for (TreePath path : selectionPaths) {
				if (path.getPathCount() == 3) {
					selectedFurniture.add((CatalogPieceOfFurniture)path.getLastPathComponent());}}}
		return selectedFurniture;}
	/**
	 * @author spooky
	 * class interne Renderer DefaultTreeCellRenderer pour JTree<br>
	 * @since 1-Arbre du catalogue des meubles
	 */
	class CatalogCellRenderer extends DefaultTreeCellRenderer{
		/**
		 * Hauteur d'un icon ayant pour valeur 32px<br>
		 * @author spooky
		 * @since 1-Arbre du catalogue des meubles
		 */
		static final int DEFAULT_ICON_HEIGHT=32;
		/**
		 * Hauteur d'un icon ayant pour valeur 32px
		 * @author spooky
		 * @since 1-Arbre du catalogue des meubles
		 * @param tree JTree
		 * @param value Object
		 * @param selected boolean
		 * @param expanded boolean
		 * @param leaf boolean
		 * @param row int
		 * @param hasFocus boolean
		 */
		@Override
		public Component getTreeCellRendererComponent(JTree tree,Object value,boolean selected,boolean expanded,boolean leaf,int row,boolean hasFocus) {
			JLabel label = (JLabel)super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			if (value instanceof Category) {
				label.setText(((Category)value).getName());}
				else if (value instanceof CatalogPieceOfFurniture) {
				CatalogPieceOfFurniture piece = (CatalogPieceOfFurniture)value;
				label.setText(piece.getName());
				label.setIcon(getLabelIcon(piece.getIcon()));
				}
			return label;}
		/**
         * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param content Content
		 * @return Icon
		 */
		private Icon getLabelIcon(Content content) {
			int rowHeight = isFixedRowHeight()
					? getRowHeight()
					: DEFAULT_ICON_HEIGHT;
					return IconManager.getInstance().getIcon(
					content, rowHeight, CatalogTree.this);}}
		/**
		 * class interne Model TreeModel de JTree
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
	 */
	private static class CatalogTreeModel implements TreeModel{
		/**
         * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * catalog Catalog
		 */
		private Catalog catalog;
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param catalog Catalog
		 */
		public CatalogTreeModel(Catalog catalog) {this.catalog = catalog;}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param parent Object
		 * @param index int
		 * @return Object
		 */
		@Override
		public Object getChild(Object parent, int index) {
			if (parent instanceof Catalog) {return ((Catalog)parent).getCategories().get(index);
			} else {return ((Category)parent).getFurniture().get(index);}}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param parent Object
		 * @return int
		 */
		@Override
		public int getChildCount(Object parent) {
			if (parent instanceof Catalog) {
				return ((Catalog)parent).getCategories().size();
			} else {
				return ((Category)parent).getFurniture().size();}}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param parent Object
		 * @param child Object
		 * @return int
		 */
		@Override
		public int getIndexOfChild(Object parent, Object child) {
			if(parent instanceof Catalog) {return Collections.binarySearch(((Catalog)parent).getCategories(), (Category)child);}
			else {return Collections.binarySearch(((Category)parent).getFurniture(),(CatalogPieceOfFurniture)child);}}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @return  Object
		 */
		@Override
		public Object getRoot() {return this.catalog;}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param node Object
		 * @return  boolean
		 */
		@Override
		public boolean isLeaf(Object node) {
			// TODO Auto-generated method stub
			return node instanceof CatalogPieceOfFurniture;
		}
		/**
		 * listener add<br>
		 * @author spooky
		 * @since 1-Arbre du catalogue des meubles
		 * @param listener TreeModelListener
		 */
		@Override
		public void addTreeModelListener(TreeModelListener listener) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * listener remove<br>
		 * @author spooky
		 * @since 1-Arbre du catalogue des meubles
		 * @param listener TreeModelListener
		 */
		@Override
		public void removeTreeModelListener(TreeModelListener listener) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * @author spooky
 		 * @since 1-Arbre du catalogue des meubles
		 * @param treePath TreePath
		 * @param object Object
		 */
		@Override
		public void valueForPathChanged(TreePath treePath, Object object) {
			// TODO Auto-generated method stub
			
		}
	}
}
