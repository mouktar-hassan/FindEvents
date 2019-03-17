package beans;

import java.util.Date;

public class Events {

    private int id;
    private int creator;
    private String title;
    private String descritption;
    private String date_creation;
    private String date_event;
    private String date_updated;
    private String  location;
    private int latitude;
    private int longitude;


    public Events(){ super();}

    public Events(int id,int creator, String title, String descritption, String date_creation, String date_event, String location ){
        super();

        this.id=id;
        this.creator=creator;
        this.title=title;
        this.descritption=descritption;
        this.date_creation=date_creation;
        this.date_event=date_event;
        this.location=location;
    }

    public int getId() {
        return id;
    }


    public int getCreator() {
        return creator;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
