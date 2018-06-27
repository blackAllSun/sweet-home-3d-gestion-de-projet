package junit;

import java.awt.Component;
import java.text.Collator;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import junit.framework.TestCase;
import metier.*;
import swing.CatalogTree;
/**
 * Classe de Test en Junit<br>
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="CarteCRCCatalogTreeSet.png">
 */


public class CatalogTreeSet extends TestCase{
	/**
	 * instance de Catalog <br>
	 * @Property catalog Catalog
	 */
    Catalog catalog;
	/**
	 * 1er Nom de la categorie en EN<br>
	 * @Property firstCategoryEnglishName String
	 */
    String firstCategoryEnglishName;
	/**
	 *  1er Nom de la piece en EN <br>
	 * @Property firstPieceEnglishName String
	 */
    String firstPieceEnglishName;
	/**
	 * 1er Nom de la categorie en FR <br>
	 * @Property firstCategoryFrenchName String
	 */
    String firstCategoryFrenchName;
	/**
	 * 1er Nom de la piece en FR <br>
	 * @Property firstPieceFrenchName String
	 */
    String firstPieceFrenchName;
	/**
	 * instance de Category <br>
	 * @Property firstCategory Category
	 */    
    Category firstCategory;
	/**
	 * instance de CatalogPieceOfFurniture <br>
	 * @Property firstPiece CatalogPieceOfFurniture
	 */    
    CatalogPieceOfFurniture firstPiece;
	/**
	 * liste de Category <br>
	 * @Property categories List<Category>
	 */      
    List<Category> categories;
	/**
	 * liste de Category <br>
	 * @Property  categoryFurniture List<CatalogPieceOfFurniture>
	 */   
    List<CatalogPieceOfFurniture> categoryFurniture;
	/**
	 * constructeur par defaut <br>
	 */
	public CatalogTreeSet() {
		//testCatalogTreeCreation();
		}
	/**
	 * mehode de test junit creation <br>
	 */
	public void testCatalogTreeCreation() {
		Locale.setDefault(Locale.US);
		catalog=new DefaultCatalog();
                JTree tree=new CatalogTree(catalog);
		categories=catalog.getCategories();
		firstCategory=categories.get(0);
		categoryFurniture=firstCategory.getFurniture();
		firstPiece=categoryFurniture.get(0);
                firstCategoryEnglishName=firstCategory.getName();
		firstPieceEnglishName=firstPiece.getName();
		Locale.setDefault(Locale.FRANCE);
		catalog=new DefaultCatalog();
		firstCategory = catalog.getCategories().get(0);
		categoryFurniture=firstCategory.getFurniture();
		firstPiece =categoryFurniture.get(0);
		firstCategoryFrenchName=firstCategory.getName();
		firstPieceFrenchName=firstPiece.getName();
		System.out.println("English Category name : "+firstCategoryEnglishName+" English Piece Name "+firstPieceEnglishName);
		System.out.println("French Category name : "+firstCategoryFrenchName+" French Piece Name "+firstPieceFrenchName);
		assertFalse("Same name for first category",firstPieceEnglishName.equals(firstPieceFrenchName));

		assertFalse("Same name for first category",firstCategoryEnglishName.equals(firstCategoryFrenchName));
		assertTreeIsSorted(tree);
	}
	/**
	 * mehode de test junit assert si arbre trié <br>
	 * @param tree JTree
	 */
	public void assertTreeIsSorted(JTree tree) {
		//fail("assert TreeIsSorted not implemented");
		TreeModel model = tree.getModel();
		Object root = model.getRoot();
		Collator comparator = Collator.getInstance();
		for (int i = 0, n = model.getChildCount(root); i < n; i++) {
			Object rootChild = model.getChild(root, i);
			if (i < n - 1) {
				Object nextChild = model.getChild(root, i + 1);
				assertTrue("Categories not sorted", comparator.compare(
						getNodeText(tree, rootChild),
						getNodeText(tree, nextChild)) <= 0);}
			for (int j = 0, m = model.getChildCount(rootChild) - 1;j < m; j++) {
				Object child = model.getChild(rootChild, j);
				if (j < m - 1) {
				Object nextChild = model.getChild(rootChild, j + 1);
				assertTrue("Furniture not sorted", comparator.compare(
						getNodeText(tree, child),
						getNodeText(tree, nextChild)) <= 0);}
				assertTrue("Piece not a leaf", model.isLeaf(child));
			}
		}
	}
	/**
	 * methode main<br>
	 * @param args String[]
	 */
	public static void main(String[] args) {
		new CatalogTreeSet();
		CatalogTree tree=new CatalogTree(new DefaultCatalog());
		JFrame frame=new JFrame("Catalog Tree Test");
		frame.add(new JScrollPane(tree));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	/**
	 * Renvoie le texte affiché par le composant de rendu d’un nœud.<br>
	 * @param tree JTree
	 * @param node Object
	 * @return String
	 */
	private String getNodeText(JTree tree, Object node) {
		TreeCellRenderer renderer = tree.getCellRenderer();
                System.out.println(node.toString());
		Component childLabel = renderer.getTreeCellRendererComponent(tree, node,false, true, false, 0, false);
		return ((JLabel)childLabel).getText();}
}
