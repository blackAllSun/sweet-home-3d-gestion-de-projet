package junit;

import java.awt.Component;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import junit.framework.TestCase;
import metier.*;
import swing.FurnitureTable;


/**
 * Classe de Test en Junit<br>
 * @author spooky
 * @since 2-Tableau des meubles du logement<br>
 * <img src="FurnitureTableTest.png">
 */
public class FurnitureTableTest extends TestCase {
	/**
	 * mehode de test junit creation <br>
	 * @since 2-Tableau des meubles du logement<br>
	 */
	public void testFurnitureTableCreation() {
		Locale.setDefault(Locale.US);
		UserPreferences preferences=new DefaultUserPreferences();
		UserPreferences.Unit currentUnit=preferences.getUnit();
		System.out.println(currentUnit);
		assertFalse("Unit is in centimeter",currentUnit==UserPreferences.Unit.CENTIMETER);
		Catalog catalog=preferences.getCatalog();
		System.out.println(catalog);
		List<HomePieceOfFurniture> homeFurniture=createHomeFurnitureFromCatalog(catalog);
		Home home=new Home(homeFurniture);
		assertEquals("Different furniture count in list and home",homeFurniture.size(),home.getFurniture().size());
		JTable table=new FurnitureTable(home,preferences);
		assertEquals("Different furniture count in home and table",home.getFurniture().size(),table.getRowCount());
		String widthInInch=getRenderedDepth(table,0);
		preferences.setUnit(UserPreferences.Unit.CENTIMETER);
		String widthInMeter=getRenderedDepth(table,0);
		assertFalse(widthInInch.equals(widthInMeter));
	}
	/**
	 * methode creation HomePieceOfFurniture de Catalog
	 * @since 2-Tableau des meubles du logement<br>
	 * @param catalog Catalog
	 * @return List<HomePieceOfFurniture>
	 */
	private static List<HomePieceOfFurniture> createHomeFurnitureFromCatalog(Catalog catalog) {
		List<HomePieceOfFurniture> homeFurniture =new ArrayList<HomePieceOfFurniture>();
		for (Category category : catalog.getCategories()) {
			for (CatalogPieceOfFurniture piece : category.getFurniture()) {
				homeFurniture.add(new HomePieceOfFurniture((PieceOfFurniture) piece));}}
			return homeFurniture;}
	/**
	 * verification des tests affichés ds le tableau
	 * @since 2-Tableau des meubles du logement<br>
	 * @param table JTable
	 * @param row int
	 * @return String
	 */
	private String getRenderedDepth(JTable table, int row) {
		ResourceBundle resource =ResourceBundle.getBundle(table.getClass().getName());
		String columnName = resource.getString("depthColumn");
		int modelColumnIndex =table.getColumn(columnName).getModelIndex();

		TableModel model=table.getModel();
		System.out.println(columnName+" "+modelColumnIndex+" "+model.getRowCount()+" "+model.getColumnCount() );
		Object cellValue=model.getValueAt(row, modelColumnIndex);

		int tableColumnIndex=table.convertColumnIndexToView(modelColumnIndex);
		TableCellRenderer renderer =table.getCellRenderer(row, tableColumnIndex);
		Component cellLabel = renderer.getTableCellRendererComponent(
				table, cellValue, false, false, row, tableColumnIndex);
		return ((JLabel)cellLabel).getText();
	}
	/**
	 * methode main()
	 * @since 2-Tableau des meubles du logement<br>
	 * @param args String[]
	 */
	public static void main(String[] args) {
		UserPreferences preferences = new DefaultUserPreferences();
		List<HomePieceOfFurniture> homeFurniture =createHomeFurnitureFromCatalog(preferences.getCatalog());
		Home home = new Home(homeFurniture);
		JTable table = new FurnitureTable(home, preferences);
		JFrame frame = new JFrame("Furniture table Test");
		frame.add(new JScrollPane(table));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
