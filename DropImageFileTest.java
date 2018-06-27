package junit;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import io.IconManager;
import io.URLContent;

public class DropImageFileTest {

	public static void main(String[] args) {
		JLabel label = new JLabel("Drop a file here", JLabel.CENTER);
		label.setTransferHandler(new LabelIconTransferHandler());
		JFrame frame = new JFrame("Image preview");
		frame.add(label);
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private static class LabelIconTransferHandler extends TransferHandler {
		@Override
		public boolean canImport(JComponent destinationComponent,DataFlavor [] flavors) { 
			return destinationComponent instanceof JLabel && Arrays.asList(flavors).contains(DataFlavor.javaFileListFlavor);}
		@Override
		public boolean importData(JComponent destinationComponent,Transferable transferedFiles) { 
				try { 
					List<File> files = (List<File>)transferedFiles.getTransferData(DataFlavor.javaFileListFlavor); 
				URLContent imageContent = new URLContent(files.get(0).toURL()); 	
				Icon icon = IconManager.getInstance().getIcon(imageContent, 128, destinationComponent); 
				JLabel label = (JLabel)destinationComponent;
				label.setIcon(icon); 
				label.setText("");
				return true;} 
				catch (UnsupportedFlavorException ex) { return false;} 
				catch (IOException ex) { return false;}}
	}
}
