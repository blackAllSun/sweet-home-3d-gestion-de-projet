package listener;
import java.util.EventListener;

import event.FurnitureEvent;
/**
 * @author spooky
 *
 */
public interface FurnitureListener extends EventListener{
	/**
	 * @param ev
	 */
	void pieceOfFurnitureChanged(FurnitureEvent ev);
}
