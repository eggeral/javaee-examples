package entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Flight {
    private long id;
    private String flightNumber;
    private String from;
    private String to;

    public Flight() {
    }
    
    public Flight(String flightNumber, String from, String to) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }



}
