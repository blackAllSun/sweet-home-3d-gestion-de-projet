package controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.undo.UndoableEditSupport;


import metier.*;
import swing.PlanComponent;

/**
 * @author spooky
 *
 */
public class PlanController {
	public enum Mode {WALL_CREATION, SELECTION};
	JComponent planView;
	HomeModel home;
	UserPreferences preferences;
	UndoableEditSupport undoSupport;
	ResourceBundle resource;
	ControllerState state;
	/**
	 * @return ControllerState
	 */
	public ControllerState getSelectionState() {return state;}
	ControllerState selectionState;
	ControllerState selectionMoveState;
	/**
	 * @return ControllerState
	 */
	ControllerState getSelectionMoveState(){return selectionMoveState;}
	ControllerState rectangleSelectionState;
	/**
	 * @return ControllerState
	 */
	ControllerState getRectangleSelectionState() {return rectangleSelectionState;}
	ControllerState wallCreationState;
	/**
	 * @return ControllerState
	 */
	ControllerState getWallCreationState() {return wallCreationState;}
	ControllerState newWallState;
	/**
	 * @return ControllerState
	 */
	ControllerState getNewWallState() {return newWallState;}
	float xLastMousePress;
	/**
	 * @return float
	 */
	public float getXLastMousePress() {return xLastMousePress;}
	float yLastMousePress;
	/**
	 * @return float
	 */
	public float getYLastMousePress() {return yLastMousePress;}
	boolean shiftDownLastMousePress;
	/**
	 * @return float
	 */
	public boolean wasShiftDownLastMousePress() {return shiftDownLastMousePress;}
	/**
	 * @param dx float
	 * @param dy float
	 */
	public void moveAndShowSelectedItems(float dx, float dy){
		//TODO
	}

	/**
	 * @param movedItems  
	 * @param dx float 
	 * @param dy float 
	 */
	private void postItemsMove(List<?> movedItems,float dx, float dy) {
		//TODO
	}

	/**
	 * @param x float 
	 * @param y float 
	 * @return Object
	 */
	private Object getItemAt(float x, float y) {return null;}
	/**
	 * @param items List<?>
	 */
	private void selectItems(List<?> items) {}
	/**
	 * @param item 
	 * 
	 */
	private void selectItem(Object item){}
	/**
	 * 
	 */
	private void deselectAll(){}
	/**
	 * @param home
	 * @param preferences
	 * @param undoSupport
	 */
	public PlanController(HomeModel home, UserPreferences preferences, UndoableEditSupport undoSupport) {
			this.home = home;
			this.preferences = preferences;
			this.undoSupport = undoSupport;
			this.resource = ResourceBundle.getBundle(PlanController.class.getName());
			this.planView = new PlanComponent(home, preferences, this);
			this.selectionState = new SelectionState();
			this.selectionMoveState = new SelectionMoveState();
			this.rectangleSelectionState = new RectangleSelectionState();
			this.wallCreationState = new WallCreationState();
			this.newWallState = new NewWallState();
			setState(this.selectionState);
			System.out.println(this);}
	
	/**
	 * @param state
	 */
	protected void setState(ControllerState state) {
		if (this.state != null) {this.state.exit();}
			this.state = state;
			this.state.enter();}
	/**
	 * @return Mode
	 */
	public Mode getMode() {return this.state.getMode();}
	/**
	 * @param mode
	 */
	public void setMode(Mode mode) {this.state.setMode(mode);}
	/**
	 * 
	 */
	public void deleteSelection() {this.state.deleteSelection();}
	/**
	 * 
	 */
	public void escape() {this.state.escape();}
	/**
	 * @param dx
	 * @param dy
	 */
	public void moveSelection(float dx, float dy) {this.state.moveSelection(dx, dy);}
	/**
	 * @param magnetismToggled
	 */
	public void toggleMagnetism(boolean magnetismToggled) {this.state.toggleMagnetism(magnetismToggled);}
	/**
	 * @param x
	 * @param y
	 * @param clickCount
	 * @param shiftDown
	 */
	public void pressMouse(float x, float y,int clickCount, boolean shiftDown) {
		this.xLastMousePress = x;
		this.yLastMousePress = y;
		this.shiftDownLastMousePress = shiftDown;
		this.state.pressMouse(x, y, clickCount, shiftDown);} 
	/**
	 * @param x
	 * @param y
	 */
	public void releaseMouse(float x, float y) {this.state.releaseMouse(x, y);} 
	/**
	 * @param x
	 * @param y
	 */
	public void moveMouse(float x, float y) {this.state.moveMouse(x, y);}
	/**
	 * @param selectedItems
	 * @param f
	 * @param g
	 */
	public void moveItems(List<Object> selectedItems, float f, float g) {}
	/**
	 * @param items
	 */
	public void deleteItems(java.util.List<?> items) {}
	/**
	 * @return JComponent
	 */
	public JComponent getView() {return this.planView;}
	/***
	 * Renvoie une nouvelle instance de Wall dont les extrémités sont en ( xStart , yStart ) et ( xEnd , yEnd ). 
	 * L’extrémité de départ du mur renvoyé est jointe avec, soit l’extrémité de départ de wallStartAtStart , soit l’extrémité de fin de wallEndAtStart .*/
	private Wall createNewWall(
			float xStart, float yStart,
			float xEnd, float yEnd,
			Wall wallStartAtStart,
			Wall wallEndAtStart) {
		Wall wall=new Wall(yEnd, yEnd, yEnd, yEnd, yEnd);
				return wall;
			//TODO	
	}
	
	
	private abstract class ControllerState {
		public abstract void enter();
		public abstract void exit();
		public abstract Mode getMode();
		public abstract void setMode(Mode mode);
		public abstract void deleteSelection();
		public abstract void escape();
		public abstract void moveSelection(float dx, float dy);
		public abstract void toggleMagnetism(boolean magnetismToggled);
		public abstract void pressMouse(float x, float y,int clickCount, boolean shiftDown);
		public abstract void releaseMouse(float x, float y);
		public abstract void moveMouse(float x, float y);
	}
	
	private class SelectionState extends ControllerState {
		public SelectionState() {System.out.println(this);}
		@Override
		public void enter() {((PlanComponent)getView()).setCursor(getMode());}
		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Mode getMode() {return Mode.SELECTION;}
		@Override
		public void setMode(Mode mode) {if (mode == Mode.WALL_CREATION) {setState(getWallCreationState());}}
		@Override
		public void deleteSelection() {deleteItems(home.getSelectedItems());}
		@Override
		public void escape() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void moveSelection(float dx, float dy) {moveAndShowSelectedItems(dx, dy);}
		@Override
		public void toggleMagnetism(boolean magnetismToggled) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pressMouse(float x, float y, int clickCount, boolean shiftDown) {
			if (!shiftDown && getItemAt(x, y) != null) {
				setState(getSelectionMoveState()); 
			} else {setState(getRectangleSelectionState());}}

		@Override
		public void releaseMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void moveMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}}
	private class WallCreationState extends ControllerState {
		public WallCreationState() {System.out.println(this);}
		@Override
		public void enter() {((PlanComponent)getView()).setCursor(getMode());}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Mode getMode() {return Mode.WALL_CREATION;}

		@Override
		public void setMode(Mode mode) {
			if (mode == Mode.SELECTION) {
				setState(getSelectionState());}
		}

		@Override
		public void deleteSelection() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void escape() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void moveSelection(float dx, float dy) {setState(getNewWallState());}

		@Override
		public void toggleMagnetism(boolean magnetismToggled) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pressMouse(float x, float y, int clickCount, boolean shiftDown) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void releaseMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void moveMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}}
	private class SelectionMoveState extends ControllerState {
		float xLastMouseMove,yLastMouseMove;
		boolean mouseMoved;
		public SelectionMoveState() {System.out.println(this);}
		@Override
		public void enter() {
			this.xLastMouseMove = getXLastMousePress();
			this.yLastMouseMove = getYLastMousePress();
			this.mouseMoved = false;
			Object itemUnderCursor = getItemAt(getXLastMousePress(),getYLastMousePress());
			List<Object> selection = home.getSelectedItems();
			if (!selection.contains(itemUnderCursor)) {selectItem(itemUnderCursor); }}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Mode getMode() {return Mode.SELECTION;}

		@Override
		public void setMode(Mode mode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteSelection() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void escape() {
			if (this.mouseMoved) {
				moveItems(home.getSelectedItems(),getXLastMousePress() - this.xLastMouseMove,getYLastMousePress() - this.yLastMouseMove);}
				setState(getSelectionState());}

		@Override
		public void moveSelection(float x, float y) {
}

		@Override
		public void toggleMagnetism(boolean magnetismToggled) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pressMouse(float x, float y, int clickCount, boolean shiftDown) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void releaseMouse(float x, float y) {
			if (this.mouseMoved) {
				postItemsMove(home.getSelectedItems(),this.xLastMouseMove - getXLastMousePress(),this.yLastMouseMove - getYLastMousePress());
			} else {
				Object itemUnderCursor = getItemAt(x, y);
				selectItem(itemUnderCursor);}
				setState(getSelectionState());}

		@Override
		public void moveMouse(float x, float y) {
			moveItems(home.getSelectedItems(),x - this.xLastMouseMove, y - this.yLastMouseMove);
			((PlanComponent)getView()).makePointVisible(x, y);
			this.xLastMouseMove = x;
			this.yLastMouseMove = y;
			this.mouseMoved = true;
			
		}
		
	}
	private class RectangleSelectionState extends ControllerState {
		public RectangleSelectionState() {System.out.println(this);}
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Mode getMode() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setMode(Mode mode) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void deleteSelection() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void escape() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void moveSelection(float dx, float dy) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void toggleMagnetism(boolean magnetismToggled) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void pressMouse(float x, float y, int clickCount, boolean shiftDown) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void releaseMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void moveMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}}
	private class NewWallState extends ControllerState {
		public NewWallState() {System.out.println(this);}
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Mode getMode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMode(Mode mode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteSelection() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void escape() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void moveSelection(float dx, float dy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void toggleMagnetism(boolean magnetismToggled) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pressMouse(float x, float y, int clickCount, boolean shiftDown) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void releaseMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void moveMouse(float x, float y) {
			// TODO Auto-generated method stub
			
		}}
	private static class PointWithMagnetism {
		private static final int STEP_COUNT = 24;
		private float xEnd;
		private float yEnd;
		public PointWithMagnetism(float xStart, float yStart,float x, float y) {
			this.xEnd = x;
			this.yEnd = y;
			if (xStart != x && yStart != y) {
				double angleStep = 2 * Math.PI / STEP_COUNT;
				double angle = Math.atan2(yStart - y, x - xStart);
				double previousStepAngle =Math.floor(angle / angleStep) * angleStep;
				double tanAngle1;
				double tanAngle2;
				if (Math.tan(angle) > 0) {
					tanAngle1 = Math.tan(previousStepAngle); 
					tanAngle2 = Math.tan(previousStepAngle + angleStep);
				} else {
					tanAngle1 = Math.tan(previousStepAngle + angleStep);
					tanAngle2 = Math.tan(previousStepAngle);}
				double firstQuarterTanAngle1 = Math.abs(tanAngle1);
				double firstQuarterTanAngle2 = Math.abs(tanAngle2);
				float xEnd1 = Math.abs(xStart - x);
				float yEnd2 = Math.abs(yStart - y);
				float xEnd2 = 0;
				if (firstQuarterTanAngle2 > 1E-10) { xEnd2 = (float)(yEnd2 / firstQuarterTanAngle2);}
				float yEnd1 = 0;
				if (firstQuarterTanAngle1 < 1E10) { yEnd1 = (float)(xEnd1 * firstQuarterTanAngle1);}
				if (Math.abs(xEnd2 - xEnd1) < Math.abs(yEnd1 - yEnd2)) {this.xEnd = xStart + (float)((yStart - y) / tanAngle2); 	
				} else {this.yEnd = yStart - (float)((x - xStart) * tanAngle1);}}
		}
	}
}

