package junit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JToolBar;

import controller.CatalogController;
import controller.FurnitureController;
import controller.HomeController;
import junit.framework.TestCase;
import metier.DefaultUserPreferences;
import metier.Home;
import metier.HomeModel;
import metier.HomePieceOfFurniture;
import metier.UserPreferences;
import swing.CatalogTree;
import swing.FurnitureTable;
import swing.HomePane;

/**
 * @author spooky
 *
 */
public class HomeControllerTest extends TestCase{
	HomeModel home;
	HomeController homeController;
	CatalogTree catalogTree;
	FurnitureTable furnitureTable;
	FurnitureController furnitureController;
	/**
	 * 
	 */
	public HomeControllerTest() {
		testHomeFurniture();
	}

	/**
	 * 
	 */
	public void testHomeFurniture() {
		UserPreferences preferences = new DefaultUserPreferences();
		this.home = new Home();
		this.homeController =new HomeController(home, preferences);
		CatalogController catalogController = this.homeController.getCatalogController();
		this.catalogTree =(CatalogTree)catalogController.getView();
		this.furnitureController =this.homeController.getFurnitureController();
		this.furnitureTable =(FurnitureTable) this.furnitureController.getView();
		catalogTree.expandRow(0);
		catalogTree.addSelectionInterval(1, 2);
		homeController.addHomeFurniture();
		assertEquals(2, furnitureTable.getRowCount());
		assertEquals(2, furnitureTable.getSelectedRowCount());
		furnitureTable.setRowSelectionInterval(0, 0);
		furnitureController.deleteSelection();
		assertEquals(1, furnitureTable.getRowCount());
		assertEquals(0, furnitureTable.getSelectedRowCount());
	}
	@Override
	protected void setUp() {
		UserPreferences preferences = new DefaultUserPreferences();
		this.home = new Home();
		this.homeController = new HomeController(home, preferences);
		CatalogController catalogController = this.homeController.getCatalogController();
		this.catalogTree = (CatalogTree)catalogController.getView();
		this.furnitureController =this.homeController.getFurnitureController();
		this.furnitureTable =(FurnitureTable) this.furnitureController.getView();
	}
	/**
	 * 
	 */
	public void testHomeFurnitureUndoableActions() {
		assertActionsEnabled(false, false, false, false);
		catalogTree.expandRow(0);
		catalogTree.addSelectionInterval(1, 2);
		assertActionsEnabled(true, false, false, false);
		runAction(HomePane.ActionType.ADD_HOME_FURNITURE);
		List<HomePieceOfFurniture> furniture =home.getFurniture();
		assertActionsEnabled(true, true, true, false);
		runAction(HomePane.ActionType.UNDO); 
		HomePieceOfFurniture firstPiece = furniture.get(0);
		assertEquals(firstPiece, home.getFurniture().get(0));
		assertEquals(firstPiece, home.getSelectedItems().get(0));
		assertActionsEnabled(true, true, true, true); 
		runAction(HomePane.ActionType.UNDO); 
		assertTrue(home.getFurniture().isEmpty());
		assertActionsEnabled(true, false, false, true);
		runAction(HomePane.ActionType.REDO);
		assertEquals(furniture, home.getFurniture());
		assertEquals(furniture, home.getSelectedItems());
		assertActionsEnabled(true, true, true, true); 
		runAction(HomePane.ActionType.REDO); 
		assertEquals(furniture.get(1), home.getFurniture().get(0));
		assertTrue(home.getSelectedItems().isEmpty());
		assertActionsEnabled(true, false, true, false);
	}
	/**
	 * @param actionType
	 */
	private void runAction(HomePane.ActionType actionType) {getAction(actionType).actionPerformed(null);}
	/**
	 * @param actionType
	 * @return Action
	 */
	private Action getAction(HomePane.ActionType actionType) {
		JComponent homeView = this.homeController.getView();
		return homeView.getActionMap().get(actionType); }
	/**
	 * @param addActionEnabled
	 * @param deleteActionEnabled
	 * @param undoActionEnabled
	 * @param redoActionEnabled
	 */
	private void assertActionsEnabled(boolean addActionEnabled,boolean deleteActionEnabled,boolean undoActionEnabled,boolean redoActionEnabled) {
		assertTrue(getAction(HomePane.ActionType.ADD_HOME_FURNITURE).isEnabled() == addActionEnabled);
		assertTrue(getAction(HomePane.ActionType.DELETE_HOME_FURNITURE).isEnabled() == deleteActionEnabled);
		assertTrue(getAction(HomePane.ActionType.UNDO).isEnabled() == undoActionEnabled);
		assertTrue(getAction(HomePane.ActionType.REDO).isEnabled() == redoActionEnabled);}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserPreferences preferences = new DefaultUserPreferences();
		Home home = new Home();
		new ControllerTest(home, preferences);}
	
	private static class ControllerTest extends HomeController {
		public ControllerTest(HomeModel home,UserPreferences preferences) {
			super(home, preferences);
			new ViewTest(this).displayView();
                        System.out.println("ControllerTest : "+this);
                         System.out.println("ControllerTest HomeModel : "+home);
                         System.out.println("ControllerTest UserPreferences : "+preferences);
			// TODO Display home controller view in a frame with buttons
                       
		}
	/**
	 * @author spooky
	 *
	 */
	private static class ViewTest extends JRootPane {
		/**
		 * @param controller
		 */
		public ViewTest(final HomeController controller) {
			JButton addButton = new JButton(new ImageIcon(getClass().getResource("/io/resources/add.png")));
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
				controller.addHomeFurniture();}});
			JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/io/resources/delete.png")));
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					controller.getFurnitureController().deleteSelection();}});
			JToolBar toolBar = new JToolBar();
			toolBar.add(addButton);
			toolBar.add(deleteButton);
			getContentPane().add(toolBar, BorderLayout.NORTH);
			getContentPane().add(controller.getView(),BorderLayout.CENTER);}
		/**
		 * 
		 */
		public void displayView() {
			JFrame frame = new JFrame("Home Controller Test") {{setRootPane(ViewTest.this);}};
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);}}
	
}}
