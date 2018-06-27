package junit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.Content;
import io.RecorderException;
import junit.framework.TestCase;
import metier.*;

public class HomeFileRecorderTest extends TestCase{
	public void testWriteReadHome() throws RecorderException{
		HomeModel home1=new Home();
		Wall wall=new Wall(0,10,100,80,10);
		home1.addWall(wall);
		Catalog catalog=new DefaultCatalog();
		HomePieceOfFurniture piece=new HomePieceOfFurniture(catalog.getCategories().get(0).getFurniture().get(0));
		home1.addPieceOfFurniture(piece);
		HomeRecorder recorder=new HomeFileRecorder();
		String testFile=new File("test.sh3d").getAbsolutePath();
		recorder.writeHome(home1,testFile);
		assertTrue(recorder.exists(testFile));
		HomeModel home2=recorder.readHome(testFile);
		assertNotSame(home1,home2);
		assertEquals(home1.getWallHeight(),home2.getWallHeight());
		assertEquals(home1.getWalls().size(),home2.getWalls().size());
		assertEquals(wall, home2.getWalls().iterator().next());
		assertEquals(home1.getFurniture().size(),home2.getFurniture().size());
		assertEquals(piece, home2.getFurniture().get(0));
		if (!new File(testFile).delete()) { 
			fail("Couldn't delete file " + testFile);}
	}
	private void assertEquals(Wall wall1, Wall wall2) {
		assertNotSame("Wall not loaded", wall1, wall2);
	    assertEquals("Different X start", wall1.getXStart(), wall2.getXStart());     
	    assertEquals("Different Y start", wall1.getYStart(), wall2.getYStart());     
	    assertEquals("Different X end", wall1.getXEnd(), wall2.getXEnd());     
	    assertEquals("Different Y end", wall1.getYEnd(), wall2.getYEnd());     
	    assertEquals("Different thickness", wall1.getThickness(), wall2.getThickness());
	    if (wall1.getWallAtStart() == null) {
	      assertEquals("Different wall at start", wall2.getWallAtStart(), null);
	    } else {
	      assertFalse("Different wall at start", wall2.getWallAtStart() == null);
	      assertNotSame("Wall at start not loaded", wall1.getWallAtStart(), wall2.getWallAtEnd());
	    }
	    if (wall1.getWallAtEnd() == null) {
	      assertEquals("Different wall at end", wall2.getWallAtEnd(), null);
	    } else {
	      assertFalse("Different wall at end", wall2.getWallAtEnd() == null);
	      assertNotSame("Wall at end not loaded", wall1.getWallAtStart(), wall2.getWallAtEnd());
	}
		}
	private void assertEquals(HomePieceOfFurniture piece1,HomePieceOfFurniture piece2) {
	    assertNotSame("Piece not loaded", piece1, piece2);
	    assertEquals("Different X", piece1.getX(), piece2.getX());     
	    assertEquals("Different Y", piece1.getY(), piece2.getY());     
	    assertEquals("Different color", piece1.getColor(), piece2.getColor());     
	    assertEquals("Different width", piece1.getWidth(), piece2.getWidth());     
	    assertEquals("Different height", piece1.getHeight(), piece2.getHeight());     
	    assertEquals("Different depth", piece1.getDepth(), piece2.getDepth());     
	    assertEquals("Different name", piece1.getName(), piece2.getName());     
	    assertNotSame("Piece icon not loaded", piece1.getIcon(), piece2.getIcon());
	    assertContentEquals("Different icon content", piece1.getIcon(), piece2.getIcon());     
	    assertNotSame("Piece model not loaded", piece1.getModel(), piece2.getModel());
	assertContentEquals("Different model content", piece1.getModel(), piece2.getModel()); 
		}
	private void assertContentEquals(String message,Content content1, Content content2) {
		InputStream stream1 = null;
	    InputStream stream2 = null;
	    try {
	      stream1 = new BufferedInputStream(content1.openStream());
	      stream2 = new BufferedInputStream(content2.openStream());
	      for (int b; (b = stream1.read()) != -1; ) {
	        assertEquals(message, b, stream2.read());}
	      assertEquals(message, -1, stream2.read());
	    } catch (IOException ex) { fail("Can't access to content");
	    } finally {
	      try {
	        stream1.close();
	        stream2.close();
	      } catch (IOException ex) { fail("Can't close content stream");}
	}
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
