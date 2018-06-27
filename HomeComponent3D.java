package swing;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;

import event.FurnitureEvent;
import event.WallEvent;
import listener.FurnitureListener;
import listener.WallListener;
import metier.HomeModel;
import metier.HomePieceOfFurniture;
import metier.Wall;

public class HomeComponent3D extends JComponent{
	private Map<Object, ObjectBranch> homeObjects = new HashMap<Object, ObjectBranch>();
	public HomeComponent3D(HomeModel home) {
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse universe = new SimpleUniverse(canvas3D);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(getSceneTree(home));
		setLayout(new GridLayout(1, 1));
		add(canvas3D);}
	private BranchGroup getSceneTree(HomeModel home) {
		// TODO Return scene root
		BranchGroup root = new BranchGroup(); 
		Group mainGroup = getMainGroup();
		Group behaviorGroup = getBehaviorGroup();
		behaviorGroup.addChild(getHomeTree(home));
		mainGroup.addChild(behaviorGroup);
		root.addChild(mainGroup);
		root.addChild(getBackgroundNode());
		for (Light light : getLights()) {
			root.addChild(light);}
		return root;}
	private Group getBehaviorGroup() {
		TransformGroup transformGroup = new TransformGroup();
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MouseRotate mouseBehavior = new MouseRotate(transformGroup);
		mouseBehavior.setFactor(mouseBehavior.getXFactor(), 0);
		Bounds schedulingBounds = new BoundingBox();
		mouseBehavior.setSchedulingBounds(schedulingBounds);
		transformGroup.addChild(mouseBehavior);
		return transformGroup;}
	private Node getBackgroundNode() {
		Background background = new Background(0.9f, 0.9f, 0.9f);
		background.setApplicationBounds(new BoundingBox());
		return background;}
	private Group getMainGroup() {
		Transform3D rotationX = new Transform3D();
		rotationX.rotX(Math.PI / 4);
		return new TransformGroup(rotationX);}
	private Light [] getLights() {
		Light [] lights = {new DirectionalLight(new Color3f(1, 1, 1),new Vector3f(1, -1, -1)), 
							new DirectionalLight(new Color3f(1, 1, 1),new Vector3f(-1, -1, -1)), 
							new AmbientLight(new Color3f(0.6f, 0.6f, 0.6f))}; 
		for (Light light : lights) {
			light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000000));}
		return lights;}
	private Node getHomeTree(HomeModel home) {
		// TODO Return home subtree root
		Group homeRoot = getHomeRoot();
		for (Wall wall : home.getWalls())
			addWall(homeRoot, wall, home);
		for (HomePieceOfFurniture piece : home.getFurniture())
			addPieceOfFurniture(homeRoot, piece);
		addHomeListeners(home, homeRoot);
		return homeRoot;}
	private Group getHomeRoot() {
		Transform3D translation = new Transform3D(); 
		translation.setTranslation(new Vector3f(-500, 0, -500));
		Transform3D homeTransform = new Transform3D(); 
		homeTransform.setScale(0.001);
		homeTransform.mul(translation); 
		TransformGroup homeTransformGroup =new TransformGroup(homeTransform); 
		homeTransformGroup.setCapability(Group.ALLOW_CHILDREN_WRITE); 
		homeTransformGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		return homeTransformGroup;}
	private void addHomeListeners(final HomeModel home,final Group homeRoot) {
		// TODO Add listeners to update 3D home view
		home.addWallListener(new WallListener() {
			@Override
			public void wallChanged(WallEvent ev) {
			Wall wall = ev.getWall();
			switch (ev.getType()) {
				case ADD : addWall(homeRoot, wall, home);break;
				case UPDATE : updateWall(wall);break;
				case DELETE : deleteObject(wall);break;}}});
		home.addWallListener(new WallListener() {
			@Override
			public void wallChanged(WallEvent ev) {
			Wall wall = ev.getWall(); 
			switch (ev.getType()) {
				case ADD : addWall(homeRoot, wall, home);break;
				case UPDATE : updateWall(wall);break;
				case DELETE : deleteObject(wall);break;}}});
		home.addFurnitureListener(new FurnitureListener() {
			@Override
			public void pieceOfFurnitureChanged(FurnitureEvent ev) {
			HomePieceOfFurniture piece =(HomePieceOfFurniture)ev.getPieceOfFurniture(); 
			switch (ev.getType()) {
				case ADD : addPieceOfFurniture(homeRoot, piece);break;
				case UPDATE : updatePieceOfFurniture(piece);break;
				case DELETE : deleteObject(piece);break;}}});
	}
	private void addWall(Group homeRoot, Wall wall, HomeModel home) {
		Wall3D wall3D = new Wall3D(wall, home);
		this.homeObjects.put(wall, wall3D); 
		homeRoot.addChild(wall3D);}
	private void updateWall(Wall wall) {
		this.homeObjects.get(wall).update(); 
		if (wall.getWallAtStart() != null) {
			this.homeObjects.get(wall.getWallAtStart()).update();}
		if (wall.getWallAtEnd() != null) {
			this.homeObjects.get(wall.getWallAtEnd()).update();}}
	private void deleteObject(Object homeObject) {
		this.homeObjects.get(homeObject).detach();
		this.homeObjects.remove(homeObject);}
	private void addPieceOfFurniture(Group homeRoot,HomePieceOfFurniture piece) {
		HomePieceOfFurniture3D piece3D =new HomePieceOfFurniture3D(piece);
		this.homeObjects.put(piece, piece3D);
		homeRoot.addChild(piece3D);}
	private void updatePieceOfFurniture(HomePieceOfFurniture piece) {this.homeObjects.get(piece).update(); }
	private static abstract class ObjectBranch extends BranchGroup {
		public abstract void update();}
	private static class Wall3D extends ObjectBranch {
		HomeModel home;
		public Wall3D(Wall wall, HomeModel home) {
			setUserData(wall); 
			this.home = home; 

			setCapability(BranchGroup.ALLOW_DETACH); 
			setCapability(BranchGroup.ALLOW_CHILDREN_READ);
			addChild(getWallShape());
			updateWallGeometry(); 
			updateWallAppearance();}
		private void updateWallAppearance() {
			Appearance wallAppearance=new Appearance();
			Material material=new Material();
			wallAppearance.setMaterial(material);
			((Shape3D) getChild(0)).setAppearance(wallAppearance);
		}
		private Node getWallShape() {
			Shape3D wallShape = new Shape3D();
			wallShape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
			return wallShape;}
		@Override
		public void update() {
			updateWallGeometry();
			}
		private void updateWallGeometry() {
			float [][] wallPoints = ((Wall)getUserData()).getPoints();
			Point3f [] bottom = new Point3f [4];
			Point3f [] top= new Point3f [4];
			for (int i = 0; i < bottom.length; i++) {
				bottom [i] = new Point3f(
				wallPoints[i][0], 0, wallPoints[i][1]);
				top [i] = new Point3f(wallPoints[i][0],
				this.home.getWallHeight(), wallPoints[i][1]);
				}
		}
	}
	private static class HomePieceOfFurniture3D extends ObjectBranch {
		private Map<Object, ObjectBranch> homeObjects =new HashMap<Object, ObjectBranch>();
		public HomePieceOfFurniture3D(HomePieceOfFurniture piece) {
			// TODO Create piece branch from its model
			}
		@Override
		public void update() {
			// TODO Update piece size, angle and location
			}
	}
}
