package com.example.petscorner;

public class Search_Pets_First_Page_Modal {

    private String uImageView,emailId,petId;

    public Search_Pets_First_Page_Modal(String uImageView, String emailId, String petId) {
        this.uImageView = uImageView;
        this.emailId = emailId;
        this.petId = petId;
    }

    public String getuImageView() {
        return uImageView;
    }

    public void setuImageView(String uImageView) {
        this.uImageView = uImageView;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
}
