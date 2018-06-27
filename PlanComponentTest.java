package junit;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.undo.*;

import abbot.tester.ComponentLocation;
import abbot.tester.JComponentTester;
import controller.PlanController;
import event.WallEvent;
import junit.extensions.abbot.ComponentTestFixture;
import listener.WallListener;
import metier.*;
import swing.PlanComponent;
/**
 * @author spooky
 *
 */
public class PlanComponentTest extends ComponentTestFixture{

	/**
	 * 
	 */
	public void testPlanComponent() { 
		// TODO Test plan component features on a test frame
		PlanTestFrame frame = new PlanTestFrame();
		showWindow(frame);
		PlanComponent planComponent = (PlanComponent)frame.planController.getView();
		assertEquals(540, planComponent.getWidth());
		final ArrayList<Wall> orderedWalls = new ArrayList<Wall>();
		frame.home.addWallListener(new WallListener () {
			@Override
			public void wallChanged(WallEvent ev) {
				if (ev.getType() == WallEvent.Type.ADD) {
					orderedWalls.add(ev.getWall());}}});
		frame.modeButton.doClick();
		assertEquals(PlanController.Mode.WALL_CREATION,frame.planController.getMode());
		JComponentTester tester = new JComponentTester();
		tester.actionClick(planComponent, 30, 30); 
		tester.actionClick(planComponent, 270, 31);
		tester.actionClick(planComponent, 269, 170);
		tester.actionClick(planComponent, 30, 171,InputEvent.BUTTON1_MASK, 2);
		Wall wall1 = orderedWalls.get(0); 
		assertCoordinatesEqualWallPoints(20, 20, 500, 20, wall1);
		Wall wall2 = orderedWalls.get(1);
		assertCoordinatesEqualWallPoints(500, 20, 500, 300, wall2);
		Wall wall3 = orderedWalls.get(2);
		assertCoordinatesEqualWallPoints(500, 300, 20, 300, wall3);
		assertWallsAreJoined(null, wall1, wall2);
		assertWallsAreJoined(wall1, wall2, wall3);
		assertWallsAreJoined(wall2, wall3, null);
		assertSelectionContains(frame.home, wall1, wall2, wall3);
		tester.actionClick(planComponent, 30, 170);
		tester.actionKeyPress(KeyEvent.VK_SHIFT); 	
		tester.actionClick(planComponent, 50, 50,InputEvent.BUTTON1_MASK, 2);
		tester.actionKeyRelease(KeyEvent.VK_SHIFT); 
		Wall wall4 = orderedWalls.get(orderedWalls.size() - 1); 
		assertCoordinatesEqualWallPoints(20, 300, 60, 60, wall4);
		assertSelectionContains(frame.home, wall4);
		assertWallsAreJoined(wall3, wall4, null);
		frame.modeButton.doClick();
		assertEquals(PlanController.Mode.SELECTION,frame.planController.getMode());
		tester.actionFocus(planComponent);
		tester.actionKeyStroke(KeyEvent.VK_DELETE);
		assertHomeContains(frame.home, wall1, wall2, wall3);
		frame.modeButton.doClick();
		tester.actionClick(planComponent, 31, 29);
		tester.actionClick(planComponent, 30, 170,InputEvent.BUTTON1_MASK, 2);
		wall4 = orderedWalls.get(orderedWalls.size() - 1);
		assertCoordinatesEqualWallPoints(20, 20, 20, 300, wall4);
		assertWallsAreJoined(wall1, wall4, wall3);
		frame.modeButton.doClick();
		tester.actionMousePress(planComponent,new ComponentLocation(new Point(200, 100)));
		tester.actionMouseMove(planComponent,new ComponentLocation(new Point(300, 180)));
		tester.actionMouseRelease();
		assertSelectionContains(frame.home, wall2, wall3);
		tester.actionKeyStroke(KeyEvent.VK_RIGHT);
		tester.actionKeyStroke(KeyEvent.VK_RIGHT);
		assertCoordinatesEqualWallPoints(20, 20, 504, 20, wall1);
		assertCoordinatesEqualWallPoints(504, 20, 504, 300, wall2);
		assertCoordinatesEqualWallPoints(504, 300, 24, 300, wall3);
		assertCoordinatesEqualWallPoints(20, 20, 24, 300, wall4);
		tester.actionKeyPress(KeyEvent.VK_SHIFT);
		tester.actionClick(planComponent, 272, 40);
		tester.actionKeyRelease(KeyEvent.VK_SHIFT);
		assertSelectionContains(frame.home, wall3);
		tester.actionMousePress(planComponent,new ComponentLocation(new Point(50, 30)));
		tester.actionMouseMove(planComponent,new ComponentLocation(new Point(50, 50)));
		assertSelectionContains(frame.home, wall1);
		assertCoordinatesEqualWallPoints(20, 60, 504, 60, wall1);
		tester.actionKeyStroke(KeyEvent.VK_TAB);
		assertCoordinatesEqualWallPoints(20, 20, 504, 20, wall1);
		for (int i = 0; i < 6; i++) {frame.undoButton.doClick();}
		assertHomeContains(frame.home);
		for (int i = 0; i < 6; i++) {frame.redoButton.doClick();}
		assertHomeContains(frame.home, wall1, wall2, wall3, wall4);
		assertWallsAreJoined(wall4, wall1, wall2);
		assertWallsAreJoined(wall1, wall2, wall3);
		assertWallsAreJoined(wall2, wall3, wall4);
		assertWallsAreJoined(wall1, wall4, wall3);
		assertSelectionContains(frame.home, wall2, wall3);
	}
	private void assertCoordinatesEqualWallPoints(float xStart,float yStart, float xEnd, float yEnd, Wall wall) { 
			// TODO Assert wall points match coordinates in parameter
		assertTrue(Math.abs(xStart - wall.getXStart()) < 1E-10);
		assertTrue(Math.abs(yStart - wall.getYStart()) < 1E-10);
		assertTrue(Math.abs(xEnd - wall.getXEnd()) < 1E-10);
		assertTrue(Math.abs(yEnd - wall.getYEnd()) < 1E-10);
	}
	private void assertWallsAreJoined(Wall wallAtStart, Wall wall, Wall wallAtEnd) { 
			// TODO Assert wall is joined to wallAtStart and wallAtEnd
		assertSame(wallAtStart, wall.getWallAtStart());
		assertSame(wallAtEnd, wall.getWallAtEnd());
	}
	private void assertHomeContains(HomeModel home, Wall ... walls) {
		// TODO Assert home contains walls
		Collection <Wall> planWalls = home.getWalls();
		assertEquals(walls.length, planWalls.size());
		for (Wall wall : walls) {
			assertTrue(planWalls.contains(wall));}}
	private void assertSelectionContains(HomeModel home, Wall ... walls) { 
		List<Object> selectedItems = home.getSelectedItems();
		assertEquals(walls.length, selectedItems.size());
		for (Wall wall : walls) { 
			assertTrue(selectedItems.contains(wall));}}

	public static void main(String[] args) {
		JFrame frame = new PlanTestFrame(); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	private static class PlanTestFrame extends JFrame { 

		private final HomeModel home;
		private final PlanController planController;
		private final JToggleButton modeButton;
		private final JButton undoButton;
		private final JButton redoButton;
		public PlanTestFrame() {
			super("Plan Component Test");
			this.home = new Home(); 
			UserPreferences preferences = new DefaultUserPreferences();
			UndoableEditSupport undoSupport = new UndoableEditSupport();
			final UndoManager undoManager = new UndoManager();
			undoSupport.addUndoableEditListener(undoManager);
			this.planController =new PlanController(this.home, preferences, undoSupport);
			add(new JScrollPane(this.planController.getView()));
			this.modeButton = new JToggleButton(new ImageIcon(getClass().getResource("/io/resources/furniture-add.png")));
			this.modeButton.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent ev) {
					if (modeButton.isSelected()) {
						planController.setMode(PlanController.Mode.WALL_CREATION);
					} else {planController.setMode(PlanController.Mode.SELECTION);}}});
			this.undoButton = new JButton(new ImageIcon(getClass().getResource("/io/resources/edit-undo.png"))); 
			this.undoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					undoManager.undo();}});
			this.redoButton = new JButton(new ImageIcon(getClass().getResource("/io/resources/edit-redo.png"))); 	
			this.redoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					undoManager.redo();}});
			JToolBar toolBar = new JToolBar();
			toolBar.add(this.modeButton);
			toolBar.add(this.undoButton);
			toolBar.add(this.redoButton);
			add(toolBar, BorderLayout.NORTH);
			pack(); 
			}

		// TODO Create a frame with a plan component and buttons
		}
}
