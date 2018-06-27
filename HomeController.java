package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import event.SelectionEvent;
import io.RecorderException;
import listener.SelectionListener;
import metier.CatalogPieceOfFurniture;
import metier.Home;
import metier.HomeModel;
import metier.HomePieceOfFurniture;
import metier.UserPreferences;
import swing.HomeApplication;
import swing.HomePane;
import swing.UserPreferencesPanel;


/**
 * Sous Classe concrete de AbstractHomeController
 * @author spooky
 * @since 4-Modification du tableau des meubles avec MVC<br>
 * <img src="HomeController.png">
 */
public class HomeController extends AbstractHomeController{
	private ResourceBundle resource = null;
	private HomeModel home;
	private UserPreferences preferences;
	private JComponent homeView;
	private CatalogController catalogController;
	private FurnitureController furnitureController;
	private PlanController planController;
	private UndoableEditSupport undoSupport;
	private UndoManager undoManager;
	private HomeApplication application;
	public HomeController() {super();}
	/**
	 * Constructor
	 * @param home
	 * @param preferences
	 */
	
	public HomeController(HomeModel home, UserPreferences preferences) {
        super(home,preferences);
        this.home=home;
		this.preferences=preferences;
		
		this.undoSupport = new UndoableEditSupport();
		this.undoManager = new UndoManager();
		this.undoSupport.addUndoableEditListener(this.undoManager);
		
		this.catalogController=new CatalogController(preferences.getCatalog());
		this.furnitureController=new FurnitureController(home,preferences, this.undoSupport);
		this.planController=new PlanController(home, preferences, this.undoSupport);
		this.homeView=new HomePane(home,preferences,this);
		
		addListeners();
		enableDefaultActions((HomePane) this.homeView);
        System.out.println("HomeController : "+this);
        System.out.println("FurnitureController HomeController : "+furnitureController); }
	/**
	 * @param homeView HomePane
	 */
	private void enableDefaultActions(HomePane homeView) {
		homeView.setEnabled(HomePane.ActionType.NEW_HOME, true);
		homeView.setEnabled(HomePane.ActionType.OPEN, true);
		homeView.setEnabled(HomePane.ActionType.CLOSE, true);
		homeView.setEnabled(HomePane.ActionType.SAVE, true);
		homeView.setEnabled(HomePane.ActionType.SAVE_AS, true);
		homeView.setEnabled(HomePane.ActionType.EXIT, true);

		homeView.setEnabled(HomePane.ActionType.UNDO, true);
		homeView.setEnabled(HomePane.ActionType.REDO, true);
		homeView.setEnabled(HomePane.ActionType.ADD_HOME_FURNITURE, true);
		homeView.setEnabled(HomePane.ActionType.DELETE_HOME_FURNITURE, true);
		homeView.setEnabled(HomePane.ActionType.PREFERENCES, true);
		homeView.setEnabled(HomePane.ActionType.ABOUT, true);

		homeView.setEnabled(HomePane.ActionType.CUT, true);
		homeView.setEnabled(HomePane.ActionType.PASTE, true);
		homeView.setEnabled(HomePane.ActionType.COPY, true);
		homeView.setEnabled(HomePane.ActionType.DELETE, true);
		// TODO Création des autres actions inchangée
	}
	
	/**
	 * 
	 */
	private void addListeners() {
		// TODO Add listeners on home, catalog and undo support
		addCatalogSelectionListener();
		addHomeSelectionListener();
		addUndoSupportListener();
		}
	
	/**
	 * 
	 */
	public void undo() {
		// TODO Undo last undoable operation
		this.undoManager.undo();
		HomePane view = ((HomePane)getView());
		boolean moreUndo = this.undoManager.canUndo();
		view.setEnabled(HomePane.ActionType.UNDO, moreUndo);
		view.setEnabled(HomePane.ActionType.REDO, true);
		if (moreUndo) {
			view.setUndoRedoName(this.undoManager.getUndoPresentationName(),this.undoManager.getRedoPresentationName());
		} else {
			view.setUndoRedoName(null, this.undoManager.getRedoPresentationName());}}
	
	/**
	 * 
	 */
	public void redo() {
		// TODO Redo last undone operation
		this.undoManager.redo();
		HomePane view = ((HomePane)getView());
		boolean moreRedo = this.undoManager.canRedo();
		view.setEnabled(HomePane.ActionType.UNDO, true);
		view.setEnabled(HomePane.ActionType.REDO, moreRedo);
		if (moreRedo) {view.setUndoRedoName(this.undoManager.getUndoPresentationName(),this.undoManager.getRedoPresentationName());
		} else {view.setUndoRedoName(this.undoManager.getUndoPresentationName(), null);}}
	
	/**
	 * 
	 */
	private void addCatalogSelectionListener() {
		this.preferences.getCatalog().addSelectionListener(new SelectionListener() {
			@Override
			public void selectionChanged(SelectionEvent ev) {
				((HomePane)getView()).setEnabled(HomePane.ActionType.ADD_HOME_FURNITURE,!ev.getSelectedItems().isEmpty());}});}
	
	/**
	 * 
	 */
	private void addHomeSelectionListener() {
		this.home.addSelectionListener(new SelectionListener() {
			@Override
			public void selectionChanged(SelectionEvent ev) { 
				boolean selectionContainsFurniture = false;
				for (Object item : ev.getSelectedItems()) {
					if (item instanceof HomePieceOfFurniture) {
						selectionContainsFurniture = true;
						break;}} 
		((HomePane)getView()).setEnabled(HomePane.ActionType.DELETE_HOME_FURNITURE,selectionContainsFurniture); 

		/*((HomePane) homeView).setEnabled(HomePane.ActionType.UNDO, true);
		((HomePane) homeView).setEnabled(HomePane.ActionType.REDO, false);
		((HomePane) homeView).setUndoRedoName(ev.getEdit().getUndoPresentationName(), null);*/}});}
	
	/**
	 * 
	 */
	private void addUndoSupportListener() {
		this.undoSupport.addUndoableEditListener(new UndoableEditListener () {
			@Override
			public void undoableEditHappened(UndoableEditEvent ev) {
				HomePane view = ((HomePane)getView());
				view.setEnabled(HomePane.ActionType.UNDO, true);
				view.setEnabled(HomePane.ActionType.REDO, false);
				view.setUndoRedoName(ev.getEdit().getUndoPresentationName(), null);}});}

    @Override 
    public String toString(){return home+" "+catalogController+" "+furnitureController+" "+homeView;}
	@Override
    public JComponent getView() {return this.homeView;}
	/**
	 * @return CatalogController
	 */
	public CatalogController getCatalogController() {return catalogController;}

	/**
	 * @return FurnitureController
	 */
	public FurnitureController getFurnitureController() {
            System.out.println("FurnitureController HomeController getter");
            return furnitureController;}
        @Override
	public void addHomeFurniture() {
           // super.addHomeFurniture();
		List<CatalogPieceOfFurniture> selectedFurniture =this.preferences.getCatalog().getSelectedFurniture();
		if (!selectedFurniture.isEmpty()) {
			List<HomePieceOfFurniture> newFurniture =new ArrayList<HomePieceOfFurniture>();
			for (CatalogPieceOfFurniture piece : selectedFurniture) {
				newFurniture.add(new HomePieceOfFurniture(piece));}
			getFurnitureController().addFurniture(newFurniture);}}
    /**
     * 
     */
    public void editPreferences() {
    	UserPreferencesPanel preferencesPanel =new UserPreferencesPanel();
    	preferencesPanel.setPreferences(preferences);
    	if (preferencesPanel.showDialog(getView())) { 
    		this.preferences.setUnit(preferencesPanel.getUnit());
    		this.preferences.setMagnetismEnabled(preferencesPanel.isMagnetismEnabled());
    	this.preferences.setNewWallThickness(preferencesPanel.getNewWallThickness());
    	this.preferences.setNewHomeWallHeight(preferencesPanel.getNewHomeWallHeight());
    	try {this.preferences.write();
        }catch (RecorderException ex) {((HomePane)getView()).showError(this.resource.getString("savePreferencesError"));}}}
    /**
     * 
     */
    public void about() {((HomePane)getView()).showAboutDialog();}
	/**
	 * @return PlanController
	 */
	public PlanController getPlanController() {return planController;}
	public void focusedViewChanged(JComponent focusedComponent) {
		// TODO Auto-generated method stub
	}
	/**
	 * 
	 */
	public void newHome() {
		this.application.addHome(new Home(this.preferences.getNewHomeWallHeight()));}
	/**
	 * 
	 */
	public void open() {
		String homeName = ((HomePane)getView()).showOpenDialog();
		if (homeName != null) {
			try {
				HomeModel openedHome =this.application.getHomeRecorder().readHome(homeName);
				openedHome.setName(homeName);
				this.application.addHome(openedHome);
			}catch (RecorderException ex) {
				String message = String.format(this.resource.getString("openError"), homeName);
						((HomePane)getView()).showError(message);}
		}
	}
	public void setWallCreationMode() {
		// TODO Auto-generated method stub
		
	}
	public void setSelectionMode() {
		// TODO Auto-generated method stub
		
	}
	public void close() {}
	public void save() {}
	public void saveAs() {}
	public void exit() {}
	public void cut() {}
	public void copy() {}
	public void paste() {}
	public void delete() {}
}
