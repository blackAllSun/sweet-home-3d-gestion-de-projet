package metier;

import io.Content;

/**
 * @author spooky
 * @since 2-Tableau des meubles du logement<br>
 * <img src="HomePieceOfFurniture.png">
 */
public class HomePieceOfFurniture implements PieceOfFurniture{
	int x,y;
	String name;
	Content icon,model;
	float width,depth,height;
	boolean movable,doorOrWindow;
	Integer color;
	boolean visible;
	/**
	 * @param piece
	 */
	public HomePieceOfFurniture(PieceOfFurniture piece) {
		this.name=piece.getName();
		this.icon=piece.getIcon();
		this.model=piece.getModel();
		this.width=piece.getWidth();
		this.depth=piece.getDepth();
		this.height=piece.getHeight();
		this.movable=piece.isMovable();
		this.doorOrWindow=piece.isDoorOrWindow();
		this.x=piece.getX();
		this.y=piece.getY();
		this.visible=true;
		System.out.println(this+" Piece : "+piece);
		}
	@Override
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Content getIcon() {
		return icon;
	}
	/**
	 * @param icon
	 */
	@Override
	public void setIcon(Content icon) {
		this.icon = icon;
	}
	@Override
	public Content getModel() {
		return model;
	}
	/**
	 * @param model
	 */
	@Override
	public void setModel(Content model) {
		this.model = model;
	}
	@Override
	public float getWidth() {
		return width;
	}
	/**
	 * @param width
	 */
	@Override
	public void setWidth(float width) {
		this.width = width;
	}
	@Override
	public float getDepth() {
		return depth;
	}
	/**
	 * @param depth
	 */
	@Override
	public void setDepth(float depth) {
		this.depth = depth;
	}
	@Override
	public float getHeight() {
		return height;
	}
	/**
	 * @param height
	 */
	@Override
	public void setHeight(float height) {
		this.height = height;
	}
	@Override
	public boolean isMovable() {
		return movable;
	}
	/**
	 * @param movable
	 */
	@Override
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	@Override
	public boolean isDoorOrWindow() {
		return doorOrWindow;
	}
	/**
	 * @param doorOrWindow
	 */
	@Override
	public void setDoorOrWindow(boolean doorOrWindow) {
		this.doorOrWindow = doorOrWindow;
	}
	/**
	 * @return Integer
	 */
	public Integer getColor() {
		return color;
	}
	/**
	 * @param color
	 */
	public void setColor(Integer color) {
		this.color = color;
	}
	/**
	 * @return boolean
	 */
	public boolean isVisible() {
		return visible;
	}
	/**
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
}
