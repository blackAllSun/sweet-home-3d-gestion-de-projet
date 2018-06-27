package io;
import java.io.IOException;
import java.io.InputStream;

/**
 * interface acces fichier
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="Content.png">
 */
public interface Content {
	/**
	 * @return InputStream
	 * @throws IOException
	 */
	InputStream openStream() throws IOException;
}
