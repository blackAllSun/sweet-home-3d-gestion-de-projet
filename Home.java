package metier;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import event.FurnitureEvent;
import event.SelectionEvent;
import event.WallEvent;
import listener.FurnitureListener;
import listener.SelectionListener;
import listener.WallListener;

/**
 * @author spooky
 * @since 2-Tableau des meubles du logement<br>
 * <img src="Home.png">
 */
public class Home implements HomeModel{
	List<HomePieceOfFurniture> furniture;
	List<Object> selectedItems;
	List<FurnitureListener> furnitureListeners;
	List<SelectionListener> selectionListeners;
	Collection<Wall> walls;
	@Override
	public Collection<Wall> getWalls(){return walls;}
	List<WallListener> wallListeners;
	float wallHeight;
	/**
	 * @param furniture
	 */
	public Home(List<HomePieceOfFurniture> furniture) {
		this.furniture=new ArrayList<HomePieceOfFurniture>(furniture);
		this.selectedItems = new ArrayList<Object>();
		this.furnitureListeners=new ArrayList<FurnitureListener>();
		this.selectionListeners=new ArrayList<SelectionListener>();
		this.walls = new ArrayList<Wall>();
		this.wallListeners = new ArrayList<WallListener>();}
	/**
	 * 
	 */
	public Home() {
		this(new ArrayList<HomePieceOfFurniture>());}
	public Home(float wallHeight) {
		this(new ArrayList<HomePieceOfFurniture>(), wallHeight);
	}

	private Home(List<HomePieceOfFurniture> furniture,float wallHeight) {
			this.furniture =new ArrayList<HomePieceOfFurniture>(furniture);
			this.furnitureListeners = new ArrayList<FurnitureListener>();
			this.selectedItems = new ArrayList<Object>();
			this.selectionListeners = new ArrayList<SelectionListener>();
			this.walls = new ArrayList<Wall>();
			this.wallListeners = new ArrayList<WallListener>();
			this.wallHeight = wallHeight;
			}
	@Override
	public void addFurnitureListener(FurnitureListener listener) {
		this.furnitureListeners.add(listener);}
	@Override	
	public void removeFurnitureListener(FurnitureListener listener) {
		this.furnitureListeners.remove(listener);}
/*	public void addPieceOfFurniture(HomePieceOfFurniture piece) {
		this.furniture =new ArrayList<HomePieceOfFurniture>(this.furniture);
		this.furniture.add(piece);
		firePieceOfFurnitureChanged(piece, this.furniture.size() - 1,FurnitureEvent.Type.ADD);}*/
	@Override
	public void deletePieceOfFurniture(HomePieceOfFurniture piece) {
		deselectItem(piece);
		int index = this.furniture.indexOf(piece);
		if (index != -1) {
		this.furniture =new ArrayList<HomePieceOfFurniture>(this.furniture);
		this.furniture.remove(index);
		firePieceOfFurnitureChanged(piece, index,FurnitureEvent.Type.DELETE);}}
	@Override
	public void firePieceOfFurnitureChanged(HomePieceOfFurniture piece,int index, FurnitureEvent.Type eventType) {
		if (!this.furnitureListeners.isEmpty()) {
			FurnitureEvent furnitureEvent =new FurnitureEvent(this, piece, index, eventType);
			FurnitureListener [] listeners =this.furnitureListeners.toArray(new FurnitureListener [this.furnitureListeners.size()]); 
			for (FurnitureListener listener : listeners) {
				listener.pieceOfFurnitureChanged(furnitureEvent);}}}
	@Override
	public void addSelectionListener(SelectionListener listener) {this.selectionListeners.add(listener);}
	@Override
	public void removeSelectionListener(SelectionListener listener) {this.selectionListeners.remove(listener);}
	@Override
	public List<Object> getSelectedItems() {return Collections.unmodifiableList(this.selectedItems);}
	@Override
	public void setSelectedItems(List<? extends Object> selectedItems) {
		this.selectedItems = new ArrayList<Object>(selectedItems);
		if (!this.selectionListeners.isEmpty()) {
			SelectionEvent selectionEvent =new SelectionEvent(this, getSelectedItems());
			SelectionListener [] listeners =this.selectionListeners.toArray(new SelectionListener [this.selectionListeners.size()]);
			for (SelectionListener listener : listeners) {
				listener.selectionChanged(selectionEvent);}}}
	@Override
	public void deselectItem(Object item) {
		int pieceSelectionIndex = this.selectedItems.indexOf(item);
		if (pieceSelectionIndex != -1) {
			List<Object> selectedItems =new ArrayList<Object>(getSelectedItems());
			selectedItems.remove(pieceSelectionIndex);
			setSelectedItems(selectedItems);}}
	@Override
	public List<HomePieceOfFurniture> getFurniture() {return Collections.unmodifiableList(this.furniture);}
	@Override
	public void addPieceOfFurniture(HomePieceOfFurniture piece) {
		addPieceOfFurniture(piece, this.furniture.size() -1);}
	@Override
	public void addPieceOfFurniture(HomePieceOfFurniture piece,int index) {
		this.furniture =new ArrayList<HomePieceOfFurniture>(this.furniture);
		this.furniture.add(index, piece);
		firePieceOfFurnitureChanged(piece, index,FurnitureEvent.Type.ADD);}
	@Override
	public void addWallListener(WallListener listener) {this.wallListeners.add(listener);}
	@Override
	public void removeWallListener(WallListener listener) {this.wallListeners.remove(listener);}
	@Override
	public void addWall(Wall wall) {
		this.walls = new ArrayList<Wall>(this.walls);
		this.walls.add(wall);
		fireWallEvent(wall, WallEvent.Type.ADD);}
	@Override
	public void deleteWall(Wall wall) {
		deselectItem(wall);
		for (Wall otherWall : getWalls()) {
			if (wall.equals(otherWall.getWallAtStart())) {
				setWallAtStart(otherWall, null);
			} else if (wall.equals(otherWall.getWallAtEnd())) {
			setWallAtEnd(otherWall, null);}}
		this.walls = new ArrayList<Wall>(this.walls);
		this.walls.remove(wall);
		fireWallEvent(wall, WallEvent.Type.DELETE);}
	/**
	 * @param wall
	 * @param x
	 * @param y
	 */
	public void moveWallStartPointTo(Wall wall, float x, float y) {
		if (x != wall.getXStart() || y != wall.getYStart()) {
		wall.setXStart(x);
		wall.setYStart(y);
		fireWallEvent(wall, WallEvent.Type.UPDATE);}}
	/**
	 * @param wall
	 * @param x
	 * @param y
	 */
	public void moveWallEndPointTo(Wall wall, float x, float y) {
		if (x != wall.getXEnd() || y != wall.getYEnd()) {
		wall.setXEnd(x);
		wall.setYEnd(y);
		fireWallEvent(wall, WallEvent.Type.UPDATE);}}
	/**
	 * @param wall
	 * @param wallAtStart
	 */
	public void setWallAtStart(Wall wall, Wall wallAtStart) {
		detachJoinedWall(wall, wall.getWallAtStart());
		wall.setWallAtStart(wallAtStart);
		fireWallEvent(wall, WallEvent.Type.UPDATE);}
	/**
	 * @param wall
	 * @param wallAtEnd
	 */
	public void setWallAtEnd(Wall wall, Wall wallAtEnd) {
		detachJoinedWall(wall, wall.getWallAtEnd());
		wall.setWallAtEnd(wallAtEnd);
		fireWallEvent(wall, WallEvent.Type.UPDATE);}
	/**
	 * @param wall
	 * @param joinedWall
	 */
	private void detachJoinedWall(Wall wall, Wall joinedWall) {
		if (joinedWall != null) {
		if (wall.equals(joinedWall.getWallAtStart())) {
			joinedWall.setWallAtStart(null);
			fireWallEvent(joinedWall, WallEvent.Type.UPDATE);
		} else if (wall.equals(joinedWall.getWallAtEnd())) {
			joinedWall.setWallAtEnd(null);
			fireWallEvent(joinedWall, WallEvent.Type.UPDATE);}}}
	/**
	 * @param wall
	 * @param eventType
	 */
	private void fireWallEvent(Wall wall, WallEvent.Type eventType) {
		if (!this.wallListeners.isEmpty()) {
			WallEvent wallEvent = new WallEvent(this, wall, eventType);
			WallListener [] listeners = this.wallListeners.
			toArray(new WallListener [this.wallListeners.size()]);
			for (WallListener listener : listeners) {
				listener.wallChanged(wallEvent);}}}
	@Override
	public void setName(String homeName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public float getWallHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void addPropertyChangeListener(String string, PropertyChangeListener propertyChangeListener) {
		// TODO Auto-generated method stub
		
	}
}
