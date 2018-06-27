package junit;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Action;
import javax.swing.JFrame;


import abbot.finder.*;
import abbot.tester.*;
import controller.HomeController;
import junit.extensions.abbot.*;
import metier.*;
import swing.*;

public class TransferHandlerTest extends ComponentTestFixture{
	public void testTransferHandler() throws ComponentSearchException {
		UserPreferences preferences = new DefaultUserPreferences();
		HomeModel home = new Home();
		HomeController controller =new HomeController(home, preferences);
		CatalogTree catalogTree = (CatalogTree)findComponent(controller.getView(), CatalogTree.class); 
		FurnitureTable furnitureTable = (FurnitureTable)findComponent(controller.getView(), FurnitureTable.class); 
		PlanComponent planComponent = (PlanComponent)findComponent(controller.getView(), PlanComponent.class);
		JFrame frame = new JFrame("Home TransferHandler Test");
		frame.add(controller.getView());
		frame.pack();
		showWindow(frame);
		assertTrue(catalogTree.isFocusOwner()); 
		assertActionsEnabled(controller, false, false, false, false);
		catalogTree.expandRow(0);
		catalogTree.addSelectionInterval(1, 1);
		assertActionsEnabled(controller, false, true, false, false);
		JComponentTester tester = new JComponentTester();
		Rectangle selectedRowBounds = catalogTree.getRowBounds(1);
		tester.actionDrag(catalogTree, new ComponentLocation(new Point(selectedRowBounds.x, selectedRowBounds.y))); 
		tester.actionDrop(planComponent, new ComponentLocation(new Point(120, 120))); 

		assertEquals(1, home.getFurniture().size());
		HomePieceOfFurniture piece = home.getFurniture().get(0);
		assertTrue(Math.abs(200 - piece.getX()+ piece.getWidth() / 2) < 1E-10);
		assertTrue(Math.abs(200 - piece.getY()+ piece.getDepth() / 2) < 1E-10);
		tester.actionKeyStroke(KeyEvent.VK_TAB);
		tester.actionKeyStroke(KeyEvent.VK_TAB);
		assertTrue(planComponent.isFocusOwner());
		assertActionsEnabled(controller, true, true, false, true);
		controller.setWallCreationMode();
		assertActionsEnabled(controller, false, false, false, false);
		tester.actionClick(planComponent, 20, 20);
		tester.actionClick(planComponent, 100, 20,InputEvent.BUTTON1_MASK, 2);
		controller.setSelectionMode();
		assertActionsEnabled(controller, true, true, false, true);
		tester.actionKeyPress(KeyEvent.VK_SHIFT);
		tester.actionClick(planComponent, 120, 120);
		tester.actionKeyRelease(KeyEvent.VK_SHIFT);
		runAction(controller, HomePane.ActionType.CUT);
		assertEquals(0, home.getFurniture().size());
		assertEquals(0, home.getWalls().size());
		assertActionsEnabled(controller, false, false, true, false);
		runAction(controller, HomePane.ActionType.PASTE);
		assertEquals(1, home.getFurniture().size());
		assertEquals(1, home.getWalls().size());
		tester.actionKeyStroke(KeyEvent.VK_TAB,InputEvent.SHIFT_MASK);
		assertTrue(furnitureTable.isFocusOwner());
		runAction(controller, HomePane.ActionType.DELETE);
		assertEquals(0, home.getFurniture().size());
		assertEquals(1, home.getWalls().size());
		assertActionsEnabled(controller, false, false, true, false);
		runAction(controller, HomePane.ActionType.PASTE);
		assertEquals(1, home.getFurniture().size());
		assertEquals(1, home.getWalls().size());
		assertActionsEnabled(controller, true, true, true, true);
	}
	private Component findComponent(Container container,final Class<?> componentClass) throws ComponentSearchException { 
		return new BasicFinder().find(container, new Matcher () {
			@Override
			public boolean matches(Component component) { 
				return componentClass.isInstance(component);}});}
	private void runAction(HomeController controller, HomePane.ActionType actionType) {
		getAction(controller, actionType).actionPerformed(null);}
	private Action getAction(HomeController controller,HomePane.ActionType actionType) {
		return controller.getView().getActionMap().get(actionType);}
	private void assertActionsEnabled(HomeController controller,boolean cutActionEnabled, boolean copyActionEnabled,boolean pasteActionEnabled, boolean deleteActionEnabled) {
		assertTrue(cutActionEnabled == getAction(controller,HomePane.ActionType.CUT).isEnabled());
		assertTrue(copyActionEnabled == getAction(controller,HomePane.ActionType.COPY).isEnabled());
		assertTrue(pasteActionEnabled == getAction(controller,HomePane.ActionType.PASTE).isEnabled());
		assertTrue(deleteActionEnabled == getAction(controller,HomePane.ActionType.DELETE).isEnabled());}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
