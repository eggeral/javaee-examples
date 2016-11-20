package cdi;

import storage.XmlStorage;

import javax.enterprise.inject.Alternative;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Alternative
class DummyStorage implements XmlStorage {
    Map<String, ByteArrayOutputStream> disk = new HashMap<>();

    @Override
    public InputStream getInputStream(String name) {
        return new ByteArrayInputStream(disk.get(name).toByteArray());
    }

    @Override
    public void remove(String name) {
        disk.remove(name);
    }

    @Override
    public OutputStream getOutputStream(String name) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        disk.put(name, buffer);
        return buffer;
    }

    @Override
    public boolean exists(String name) {
        return disk.containsKey(name);
    }

}
