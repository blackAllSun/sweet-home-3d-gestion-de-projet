package listener;

import java.util.EventListener;

import event.SelectionEvent;

/**
 * @author spooky
 *
 */
public interface SelectionListener extends EventListener{
	/**
	 * @param selectionEvent
	 */
	void selectionChanged(SelectionEvent selectionEvent);
}
