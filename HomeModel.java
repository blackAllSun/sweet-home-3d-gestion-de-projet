package metier;

import event.FurnitureEvent;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;

import listener.*;

/**
 * @author spooky
 *
 */
public interface HomeModel{
/**
 * @param listener
 */
void addFurnitureListener(FurnitureListener listener);
/**
 * @param listener
 */
void removeFurnitureListener(FurnitureListener listener);
/**
 * @param piece
 */
void addPieceOfFurniture(HomePieceOfFurniture piece);
/**
 * @param piece
 */
void deletePieceOfFurniture(HomePieceOfFurniture piece);
/**
 * @param piece
 * @param index
 * @param eventType
 */
void firePieceOfFurnitureChanged(HomePieceOfFurniture piece,int index, FurnitureEvent.Type eventType);
/**
 * @param listener
 */
void addSelectionListener(SelectionListener listener);
/**
 * @param listener
 */
void removeSelectionListener(SelectionListener listener);
/**
 * @return List<Object>
 */
List<Object> getSelectedItems();
/**
 * @param selectedItems
 */
void setSelectedItems(List<? extends Object> selectedItems) ;
/**
 * @param item
 */
void deselectItem(Object item) ;
/**
 * @return List<HomePieceOfFurniture>
 */
List<HomePieceOfFurniture> getFurniture() ;
/**
 * @param piece
 * @param index
 */
void addPieceOfFurniture(HomePieceOfFurniture piece,int index) ;
/**
 * @param listener
 */
void addWallListener(WallListener listener);
/**
 * @param listener
 */
void removeWallListener(WallListener listener);
/**
 * @param wall
 */
void addWall(Wall wall);
/**
 * @param wall
 */
void deleteWall(Wall wall);
/**
 * @return Collection<Wall>
 */
Collection<Wall> getWalls();
String getName();
boolean isModified();
float getWallHeight();
void addPropertyChangeListener(String string, PropertyChangeListener propertyChangeListener);
void setName(String homeName);
}
