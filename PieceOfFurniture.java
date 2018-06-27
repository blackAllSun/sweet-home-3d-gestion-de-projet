package metier;

import io.Content;

/**
 * @author spooky
 *
 */
public interface PieceOfFurniture {

	/**
	 * @return String
	 */
	String getName();
	/**
	 * @return Content
	 */
	Content getIcon();
	/**
	 * @return Content
	 */
	Content getModel();
	/**
	 * @return float
	 */
	float getWidth();
	/**
	 * @return float
	 */
	float getDepth();
	/**
	 * @return float
	 */
	float getHeight();
	/**
	 * @return boolean
	 */
	boolean isMovable();
	/**
	 * @return boolean
	 */
	boolean isDoorOrWindow();
	int getX();
	int getY();
	void setWidth(float width);
	void setModel(Content model);
	void setIcon(Content icon);
	void setName(String name);
	void setDepth(float depth);
	void setHeight(float height);
	void setMovable(boolean movable);
	void setDoorOrWindow(boolean doorOrWindow);

}
