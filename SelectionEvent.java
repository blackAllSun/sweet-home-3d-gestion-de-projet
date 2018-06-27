package event;

import java.util.EventObject;
import java.util.List;



/**
 * @author spooky
 *
 */
public class SelectionEvent extends EventObject{
	private List<?> selectedItems;
	/**
	 * @param source
	 * @param selectedItems
	 */
	public SelectionEvent(Object source, List<?> selectedItems) {
		super(source);
		this.selectedItems = selectedItems;}
	/**
	 * @return List
	 */
	public List<?> getSelectedItems() {return this.selectedItems;}


}
