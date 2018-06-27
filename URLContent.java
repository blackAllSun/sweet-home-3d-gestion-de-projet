package io;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Classe gestion fichier avec URL
 * @author spooky
 * @since 1-Arbre du catalogue des meubles<br>
 * <img src="URLContent.png">
 */
public class URLContent implements Content {
	URL url;
	/**
	 * @param url
	 */
	public URLContent(URL url) {this.url=url;}
	/**
	 * @return URL
	 */
	public URL getUrl() {
		return url;
	}
	/**
	 * @param url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	@Override
	public InputStream  openStream() throws IOException {return this.url.openStream();}

}
