package controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


import javax.swing.JTable;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import metier.Home;
import metier.HomeModel;
import metier.HomePieceOfFurniture;
import metier.UserPreferences;
import swing.FurnitureTable;

/**
 * @author spooky
 *
 */
public class FurnitureController extends AbstractHomeController{
	private HomeModel home;
	private JTable furnitureView;
	ResourceBundle resource;
	UndoableEditSupport undoSupport;
   @Override 
   public String toString(){return home+" "+furnitureView;}
   /**
 * @param home
 * @param preferences
 */
public FurnitureController(Home home,UserPreferences preferences) {
        this(home, preferences, null);}
	/**
	 * @param home
	 * @param preferences
	 * @param undoSupport
	 */
	public FurnitureController(HomeModel home,UserPreferences preferences,UndoableEditSupport undoSupport) {
            super(home,preferences);
		this.home = home;
		this.undoSupport = undoSupport;
		this.resource=ResourceBundle.getBundle(FurnitureController.class.getName());
		this.furnitureView = new FurnitureTable(home, preferences, this);
        System.out.println("FurnitureController : "+this);}
	@Override
	public JTable getView() {
                       System.out.println("FurnitureController getter");
            return this.furnitureView;}

	/**
	 * @param furniture
	 */
	public void addFurniture(List<HomePieceOfFurniture> furniture) {
		final List<Object> oldSelection =this.home.getSelectedItems();
		final HomePieceOfFurniture[] newFurniture = furniture.toArray(new HomePieceOfFurniture [furniture.size()]);
		final int [] furnitureIndex = new int [furniture.size()];
		int endIndex = home.getFurniture().size();
		for (int i = 0; i < furnitureIndex.length; i++) {
			furnitureIndex [i] = endIndex++; } 
		doAddFurniture(newFurniture, furnitureIndex);
		if (this.undoSupport != null) {
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
			@Override
			public void undo() throws CannotUndoException {
				super.undo();
				doDeleteFurniture(newFurniture); 
				home.setSelectedItems(oldSelection);}
			@Override
			public void redo() throws CannotRedoException {
				super.redo();
				doAddFurniture(newFurniture, furnitureIndex);}
			@Override
			public String getPresentationName() {return resource.getString("undoAddFurnitureName");}};
		this.undoSupport.postEdit(undoableEdit);
		/*for (HomePieceOfFurniture piece : furniture) {
			this.home.addPieceOfFurniture(piece);}
		this.home.setSelectedItems(furniture);*/
		}
		
	}
	/*	public void deleteSelection() {
		for (Object item : this.home.getSelectedItems()) {
			if (item instanceof HomePieceOfFurniture) {
				this.home.deletePieceOfFurniture((HomePieceOfFurniture)item);}}}*/
	/**
	 * 
	 */
	public void deleteSelection() {
		List<HomePieceOfFurniture> homeFurniture =this.home.getFurniture(); 
		Map<Integer, HomePieceOfFurniture> sortedMap =new TreeMap<Integer, HomePieceOfFurniture>(); 
		for (Object item : this.home.getSelectedItems()) {
			if (item instanceof HomePieceOfFurniture) {
				HomePieceOfFurniture piece = (HomePieceOfFurniture)item;
				sortedMap.put(homeFurniture.indexOf(piece), piece); }}
		final HomePieceOfFurniture [] furniture = sortedMap.values().toArray(new HomePieceOfFurniture [sortedMap.size()]); 
		final int [] furnitureIndex = new int [furniture.length];
		int i = 0;
		for (int index : sortedMap.keySet()) {
			furnitureIndex [i++] = index; }
		doDeleteFurniture(furniture); 
		if (this.undoSupport != null) {
			UndoableEdit undoableEdit = new AbstractUndoableEdit() {
				@Override
				public void undo() throws CannotUndoException {
					super.undo();
					doAddFurniture(furniture, furnitureIndex);}
				@Override
				public void redo() throws CannotRedoException {
					super.redo();
					home.setSelectedItems(Arrays.asList(furniture));
					doDeleteFurniture(furniture);}
				@Override
				public String getPresentationName() {return resource.getString("undoDeleteSelectionName");}};
			this.undoSupport.postEdit(undoableEdit);}}
	/**
	 * @param furniture
	 */
	private void doDeleteFurniture(HomePieceOfFurniture [] furniture) {
		for (HomePieceOfFurniture piece : furniture) {
			this.home.deletePieceOfFurniture(piece);}}
	/**
	 * @param furniture
	 * @param furnitureIndex
	 */
	private void doAddFurniture(HomePieceOfFurniture [] furniture,int [] furnitureIndex) { 
		for (int i = 0; i < furnitureIndex.length; i++) {
			this.home.addPieceOfFurniture (furniture [i],furnitureIndex [i]);}
		this.home.setSelectedItems(Arrays.asList(furniture));}
	/**
	 * @param selectedFurniture
	 */
	public void setSelectedFurniture(List<HomePieceOfFurniture> selectedFurniture) {
		this.home.setSelectedItems(selectedFurniture);}
	@Override
	public void addHomeFurniture() {
		// TODO Auto-generated method stub
		
	}
}
