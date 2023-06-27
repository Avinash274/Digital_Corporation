package com.example.petscorner;

public class fragment3_Modal {

    private String uName,uemailId,uImageView,uDescription,uLocation,uContact,uId,eventId;

    public fragment3_Modal(String uName, String uemailId, String uImageView, String uDescription, String uLocation, String uContact, String uId, String eventId) {
        this.uName = uName;
        this.uemailId = uemailId;
        this.uImageView = uImageView;
        this.uDescription = uDescription;
        this.uLocation = uLocation;
        this.uContact = uContact;
        this.uId = uId;
        this.eventId = eventId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUemailId() {
        return uemailId;
    }

    public void setUemailId(String uemailId) {
        this.uemailId = uemailId;
    }

    public String getuImageView() {
        return uImageView;
    }

    public void setuImageView(String uImageView) {
        this.uImageView = uImageView;
    }

    public String getuDescription() {
        return uDescription;
    }

    public void setuDescription(String uDescription) {
        this.uDescription = uDescription;
    }

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }

    public String getuContact() {
        return uContact;
    }

    public void setuContact(String uContact) {
        this.uContact = uContact;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
