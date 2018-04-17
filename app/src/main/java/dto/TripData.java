package dto;

/**
 * Created by eslam java on 2/4/2018.
 */

public class TripData {

    private int id;
    private String tripName;
    private String startPointAddress;
    private String DestinationAddress;
    private String Notes;
    private String roundTripOrOneWay;
    private String finishedOrUpcoming;
    private double StartPointLatitude;
    private double startPointLongtude;
    private double destinationLatitude;
    private double destinationLongtude;
    private int hour;
    private int minuets;
    private int year;
    private int monthe;
    private int day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartPointAddress() {
        return startPointAddress;
    }

    public void setStartPointAddress(String startPointAddress) {
        this.startPointAddress = startPointAddress;
    }

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        DestinationAddress = destinationAddress;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getRoundTripOrOneWay() {
        return roundTripOrOneWay;
    }

    public void setRoundTripOrOneWay(String roundTripOrOneWay) {
        this.roundTripOrOneWay = roundTripOrOneWay;
    }

    public String getFinishedOrUpcoming() {
        return finishedOrUpcoming;
    }

    public void setFinishedOrUpcoming(String finishedOrUpcoming) {
        this.finishedOrUpcoming = finishedOrUpcoming;
    }

    public double getStartPointLatitude() {
        return StartPointLatitude;
    }

    public void setStartPointLatitude(double startPointLatitude) {
        StartPointLatitude = startPointLatitude;
    }

    public double getStartPointLongtude() {
        return startPointLongtude;
    }

    public void setStartPointLongtude(double startPointLongtude) {
        this.startPointLongtude = startPointLongtude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongtude() {
        return destinationLongtude;
    }

    public void setDestinationLongtude(double destinationLongtude) {
        this.destinationLongtude = destinationLongtude;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinuets() {
        return minuets;
    }

    public void setMinuets(int minuets) {
        this.minuets = minuets;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthe() {
        return monthe;
    }

    public void setMonthe(int monthe) {
        this.monthe = monthe;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
