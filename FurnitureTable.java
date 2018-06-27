package swing;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import controller.FurnitureController;
import event.FurnitureEvent;
import event.SelectionEvent;
import io.Content;
import io.IconManager;
import listener.FurnitureListener;
import listener.SelectionListener;

import metier.HomeModel;
import metier.HomePieceOfFurniture;
import metier.UserPreferences;
import metier.UserPreferences.Unit;

/**
 * Classe du tableau en SWING
 * @author spooky
 * @since 2-Tableau des meubles du logement<br>
 * <img src="FurnitureTable.png">
 */
public class FurnitureTable extends JTable {
	private ListSelectionListener tableSelectionListener;
	/**
	 * @since 2-Tableau des meubles du logement<br>
	 * @param home
	 * @param preferences
	 */
	public FurnitureTable(HomeModel home, UserPreferences preferences) {this(home, preferences, null);}
	/**
	 * @param home
	 * @param preferences
	 * @param controller
	 */
	public FurnitureTable(HomeModel home, UserPreferences preferences,FurnitureController controller) {
		String [] columnNames = getColumnNames();
		setModel(new FurnitureTableModel(home, columnNames));
		setColumnRenderers(preferences);
		if (controller != null) {
			addSelectionListeners(home, controller);
            System.out.println("FurnitureTable FurnitureController : "+controller);}}
	/**
	 * @param home
	 * @param controller
	 */
	private void addSelectionListeners(final HomeModel home,final FurnitureController controller) {
             System.out.println("FurnitureTable FurnitureController addSelectionListeners : "+controller);
		final SelectionListener homeSelectionListener =new SelectionListener() { 
			@Override
			public void selectionChanged(SelectionEvent ev) {
				getSelectionModel().removeListSelectionListener(tableSelectionListener); 
				List<HomePieceOfFurniture> furniture =home.getFurniture();
				clearSelection();
				for (Object item : ev.getSelectedItems()) {
					if (item instanceof HomePieceOfFurniture) {
						int index = furniture.indexOf(item); 
						addRowSelectionInterval(index, index);
						makeRowVisible(index);}}
				getSelectionModel().addListSelectionListener(tableSelectionListener);}};
				this.tableSelectionListener = new ListSelectionListener () { 
					@Override
					public void valueChanged(ListSelectionEvent ev) {
						if (!ev.getValueIsAdjusting()) { 	
							home.removeSelectionListener(homeSelectionListener); 
							int [] selectedRows = getSelectedRows(); 
							List<HomePieceOfFurniture> selectedFurniture = new ArrayList<HomePieceOfFurniture>(selectedRows.length); 
							List<HomePieceOfFurniture> furniture =home.getFurniture();
							for (int index : selectedRows) {
								selectedFurniture.add(furniture.get(index)); }
							controller.setSelectedFurniture(selectedFurniture);
							home.addSelectionListener(homeSelectionListener); }}};
			getSelectionModel().addListSelectionListener(this.tableSelectionListener);
			home.addSelectionListener(homeSelectionListener);}
	/**
	 * @param row
	 */
	private void makeRowVisible(int row) {
		Rectangle includingRectangle = getCellRect(row, 0, true);
		if (getAutoResizeMode() == AUTO_RESIZE_OFF) { 
			int lastColumn = getColumnCount() - 1;
			includingRectangle = includingRectangle.union(getCellRect(row, lastColumn, true)); }
		scrollRectToVisible(includingRectangle);
		}
	/**
	 * @since 2-Tableau des meubles du logement<br>
	 * @param preferences
	 */
	private void setColumnRenderers(UserPreferences preferences) {
		TableCellRenderer sizeRenderer = getSizeRenderer(preferences);
		TableCellRenderer checkBoxRenderer =getDefaultRenderer(Boolean.class);
		TableCellRenderer [] columnRenderers = {
				getNameWithIconRenderer(),
				sizeRenderer,
				sizeRenderer,
				sizeRenderer,
				getColorRenderer(),
				checkBoxRenderer,
				checkBoxRenderer,
				checkBoxRenderer};
		for (int i = 0, n = getColumnCount(); i < n; i++) {
			getColumn(getColumnName(i)).
			setCellRenderer(columnRenderers [i]);}}
	/**
	 * couleur colonne
	 * @since 2-Tableau des meubles du logement<br>
	 * @return TableCellRenderer 
	 */
	private TableCellRenderer getColorRenderer() {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
				JLabel label = (JLabel)super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);
				if (value != null) {
					label.setText("\u25fc");
					label.setForeground(new Color((Integer)value));
				}else {
					label.setText("-");
					label.setForeground(table.getForeground());}
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}};
	}
	/**
	 * dimension d'un meuble
	 * @since 2-Tableau des meubles du logement<br>
	 * @param preferences
	 * @return TableCellRenderer
	 */
	private TableCellRenderer getSizeRenderer(UserPreferences preferences) {
		final TableCellRenderer floatRenderer =getDefaultRenderer(Float.class);
		return new TableCellRenderer () {
			@Override
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				if (preferences.getUnit() == Unit.INCH) {value = preferences.centimerToInch((Float)value);}
				return floatRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);}};}
	/**
	 * Nom avec Icon
	 * @since 2-Tableau des meubles du logement<br>
	 * @return  TableCellRenderer
	 */
	private TableCellRenderer getNameWithIconRenderer() {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				HomePieceOfFurniture piece = (HomePieceOfFurniture)value;
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, piece.getName(),isSelected, hasFocus, row, column);
				Content iconContent = piece.getIcon();
				label.setIcon(IconManager.getInstance().getIcon(iconContent, getRowHeight(), table));
				return label;}
			};}
	
	/**
	 * nom des colonnes du tableau
	 * @since 2-Tableau des meubles du logement<br>
	 * @return String []
	 */
	private String [] getColumnNames() {
		ResourceBundle resource = ResourceBundle.getBundle(FurnitureTable.class.getName());
		String [] columnNames = {
				resource.getString("nameColumn"),
				resource.getString("widthColumn"),
				resource.getString("heightColumn"),
				resource.getString("depthColumn"),
				resource.getString("colorColumn"),
				resource.getString("movableColumn"),
				resource.getString("doorOrWindowColumn"),
				resource.getString("visibleColumn")};
			return columnNames;}
	private void addUserPreferencesListener(UserPreferences preferences) {
		preferences.addPropertyChangeListener("unit",new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent ev) {
				repaint();}});}
	/**
	 * @author spooky
	 * @since 2-Tableau des meubles du logement<br>
	 */
	public class FurnitureTableModel extends AbstractTableModel{
		HomeModel home;
		String[] columnNames;
		/**
		 * constructeur du model du tableau Swing
		 * @param home
		 * @param columnNames
		 */
		public FurnitureTableModel(HomeModel home,String[] columnNames) {
			this.home=home;
			this.columnNames=columnNames;
			addHomeListener(home);}
		/**
		 * @param home
		 */
		private void addHomeListener(HomeModel home) {
			home.addFurnitureListener(new FurnitureListener() {
				@Override
				public void pieceOfFurnitureChanged(FurnitureEvent ev) {
				int pieceIndex = ev.getIndex(); 
				switch (ev.getType()) { 
					case ADD :fireTableRowsInserted(pieceIndex, pieceIndex); break;
					case DELETE :fireTableRowsDeleted(pieceIndex, pieceIndex); break;
				default:
					break;}}});}
		@Override
		public String getColumnName(int columnIndex) {return this.columnNames[columnIndex];}
		@Override
		public int getColumnCount() {return this.columnNames.length;}
		@Override
		public int getRowCount() {return this.home.getFurniture().size();}
		@Override	
		public Object getValueAt(int rowIndex, int columnIndex) {
			HomePieceOfFurniture piece =this.home.getFurniture().get(rowIndex);
			switch(columnIndex) {
			case 0:return piece;
			case 1: return piece.getWidth();
			case 2: return piece.getHeight();
			case 3: return piece.getDepth();
			case 4: return piece.getColor();
			case 5: return piece.isDoorOrWindow();
			case 6: return piece.isMovable();
			case 7: return piece.isVisible();
			default:throw new IllegalArgumentException("Unknown column " + columnIndex);
			}
		}
	}

}
