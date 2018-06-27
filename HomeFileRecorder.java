package metier;


import java.io.*;

import java.net.URL;
import java.util.*;
import java.util.zip.*;

import io.*;



public class HomeFileRecorder implements HomeRecorder {

	@Override
	public void writeHome(HomeModel home,String name) throws RecorderException{
		HomeOutputStream out = null;
		try {
			out = new HomeOutputStream(new FileOutputStream(name));
			out.writeHome(home);
		}catch (IOException ex) {
			throw new RecorderException("Can't save home " + name, ex);
		} finally {
			try {
				if (out != null)
				out.close(); 
			} catch (IOException ex) {
					throw new RecorderException("Can't close file " + name, ex);}}}

	@Override
	public HomeModel readHome(String name) throws RecorderException{
		HomeInputStream in = null;
		try {
			in = new HomeInputStream(new FileInputStream(name));
			Home home = in.readHome();
			return home;
		}catch (IOException ex) {throw new RecorderException("Can't read home from " + name, ex);
		} catch (ClassNotFoundException ex) {throw new RecorderException("Missing classes to read home " + name, ex);}
		finally {
		try {
			if (in != null)
				in.close();
		} catch (IOException ex) {throw new RecorderException("Can't close file " + name, ex);}}}

	@Override
	public boolean exists(String name) {return new File(name).exists();}

	@Override
	public void writeHome(Home home, String name) {
		// TODO Auto-generated method stub
		
	}
	private static class HomeOutputStream extends FilterOutputStream { 
		private List<Content> contents = new ArrayList<Content>();
	public HomeOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	public void writeHome(HomeModel home) throws IOException {
		ZipOutputStream zipOut = new ZipOutputStream(this.out);
		zipOut.putNextEntry(new ZipEntry("Home"));
		ObjectOutputStream objectOut =new HomeObjectOutputStream(zipOut);
		objectOut.writeObject(home); 
		objectOut.flush();
		zipOut.closeEntry();
		byte [] buffer = new byte [8096];

		for (int i = 0, n = contents.size(); i < n; i++) { 
			InputStream contentIn = null;
			try {
				zipOut.putNextEntry(new ZipEntry(String.valueOf(i)));
				contentIn = contents.get(i).openStream();
				int size;
				while ((size = contentIn.read(buffer)) != -1) {
					zipOut.write(buffer, 0, size); }
		zipOut.closeEntry(); 
		} finally {
			if (contentIn != null) {
				contentIn.close();}}}
				zipOut.finish();}
	private class HomeObjectOutputStream extends ObjectOutputStream {
		public HomeObjectOutputStream(OutputStream out) throws IOException {
			super(out);	
			enableReplaceObject(true); 
		}
		@Override
		protected Object replaceObject(Object obj) throws IOException {
			if (obj instanceof Content) {
				contents.add((Content)obj); 	
				return new URLContent(new URL("jar:file:temp!/" + (contents.size() - 1)));
				} else {
				return obj;
				}
		}
	}
}


	private static class HomeInputStream extends FilterInputStream { 
		private File tempFile;
	public HomeInputStream(InputStream in) throws IOException {
		super(in);
	}
	public Home readHome() throws IOException,ClassNotFoundException {
		this.tempFile = File.createTempFile("open", ".sh3d");
		this.tempFile.deleteOnExit(); 
		OutputStream tempOut = null;
		try {
		tempOut = new FileOutputStream(this.tempFile);
		byte [] buffer = new byte [8096];
		int size;
		while ((size = this.in.read(buffer)) != -1) { 
		tempOut.write(buffer, 0, size);
		}
		} finally {
		if (tempOut != null) {
		tempOut.close(); 
		}
		}

		ZipInputStream zipIn = null;
		try {
		zipIn = new ZipInputStream(new FileInputStream(this.tempFile));

		zipIn.getNextEntry();
		ObjectInputStream objectStream = new HomeObjectInputStream(zipIn);
		return (Home)objectStream.readObject();
		} finally {
		if (zipIn != null) {
		zipIn.close();
		}
		}
	}
	private class HomeObjectInputStream extends ObjectInputStream {
		public HomeObjectInputStream(InputStream in) throws IOException {
			super(in);
			enableResolveObject(true);}
		@Override
		protected Object resolveObject(Object obj) throws IOException {
			if (obj instanceof URLContent) {
				URL tmpURL = ((URLContent)obj).getUrl();
				URL fileURL = new URL(tmpURL.toString(). replace("temp", tempFile.toString()));
			return new URLContent(fileURL); 
		} else {return obj;}}
	}
	}


}
