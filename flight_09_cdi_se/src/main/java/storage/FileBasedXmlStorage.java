package storage;

import javax.enterprise.inject.Default;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileBasedXmlStorage implements XmlStorage {

    @Override
    public InputStream getInputStream(String name) {
        try {
            return Files.newInputStream(Paths.get(name), StandardOpenOption.READ);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void remove(String id) {
        try {
            Files.delete(Paths.get(id));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

    }

    @Override
    public OutputStream getOutputStream(String name) {
        try {
            return Files.newOutputStream(Paths.get(name), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public boolean exists(String name) {
        return Files.exists(Paths.get(name));
    }

}
