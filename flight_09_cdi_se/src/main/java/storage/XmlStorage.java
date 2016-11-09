package storage;

import java.io.InputStream;
import java.io.OutputStream;

public interface XmlStorage {

	InputStream getInputStream(String name);

	void remove(String name);

	OutputStream getOutputStream(String name);

	boolean exists(String name);

}
