package listener;

import java.util.EventListener;
import event.WallEvent;




/**
 * @author spooky
 *
 */
public interface WallListener extends EventListener {
	/**
	 * @param ev
	 */
	void wallChanged(WallEvent ev);
}
