package cdi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import storage.XmlStorage;

public class FlightService {
	@Inject
	private XmlStorage storage;

	public Flight getFlight(long id) {
		String name = getNameForId(id);
		if (!storage.exists(name))
			return null;
		
		try (InputStream is = storage.getInputStream(name)) {
			JAXBContext context = JAXBContext.newInstance(Flight.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (Flight) unmarshaller.unmarshal(is);
		} catch (IOException | JAXBException ex) {
			throw new IllegalStateException(ex);
		}

	}

	public void deleteFlight(long id) {
		storage.remove(getNameForId(id));
	}

	public void addFlight(Flight flight) {
		try (OutputStream os = storage.getOutputStream(getNameForId(flight.getId()))) {
			JAXBContext context = JAXBContext.newInstance(Flight.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(flight, os);
		} catch (IOException | JAXBException ex) {
			throw new IllegalStateException(ex);
		}
	}
	
	private String getNameForId(long id) {
		return Long.toString(id) + ".xml";
	}
}
