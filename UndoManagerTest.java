package junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import junit.framework.TestCase;

/**
 * @author spooky
 *
 */
public class UndoManagerTest extends TestCase{
	/**
	 * 
	 */
	public void testUndoManager() {
		UndoManager manager=new UndoManager();
		List<String> list=new ArrayList<String>();
		manager.addEdit(new AddWordToListEdit(list,"a"));
		manager.addEdit(new AddWordToListEdit(list,"b"));
		assertEquals(Arrays.asList(new String[] {"a","b"}),list);
		manager.undo(); 
		assertEquals(Arrays.asList(new String [] {"a"}), list);
		manager.addEdit(new AddWordToListEdit(list, "c")); 
		assertEquals(Arrays.asList(new String [] {"a", "c"}), list);
		manager.undo(); 
		assertEquals(Arrays.asList(new String [] {"a"}), list);
		manager.redo(); 
		assertEquals(Arrays.asList(new String [] {"a", "c"}), list);
		assertTrue(manager.canUndo());
		assertFalse(manager.canRedo());}
	
	/**
	 * 
	 */
	public void testUndoableEditSupport() {
		UndoableEditSupport editSupport = new UndoableEditSupport();
		UndoManager manager = new UndoManager(); 
		editSupport.addUndoableEditListener(manager);
		List<String> list = new ArrayList<String>();
		UndoableEdit edit = new AddWordToListEdit(list, "a");
		editSupport.addUndoableEditListener(new UndoableEditListener() {
		@Override
		public void undoableEditHappened(UndoableEditEvent ev) { 
			assertEquals(edit, ev.getEdit());}});
			assertFalse(manager.canUndo());
		editSupport.postEdit(edit);
		assertTrue(manager.canUndo());}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private static class AddWordToListEdit extends AbstractUndoableEdit {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<String> list;
		private String word;
		
		public AddWordToListEdit(List<String> list, String word) {
			this.list = list;
			this.word = word;
			doEdit();}
		private void doEdit() {this.list.add(this.word);}
		@Override
		public void undo() throws CannotUndoException {
			super.undo();
			int endIndex = list.size() - 1;
			this.list.remove(endIndex); }
		@Override
		public void redo() throws CannotRedoException {
			super.redo();
			doEdit(); }}
}
