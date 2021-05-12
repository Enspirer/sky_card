package com.example.myapplication.models;

import android.util.Log;
import android.widget.ImageView;

public class Model {

    private String name;
    private String title;
    private String phoneNumber1;
    private String phoneNumber2;
    private String email;
    private String website;
    public String viewCardURL;
    private String address;
    private String coverImage;
    private String ProfilePicture;

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }


    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String setViewCardURL(String viewCardURL) {
        this.viewCardURL = viewCardURL;
        Log.d("uuuuu", "uuuuu " + viewCardURL);
        return viewCardURL;
    }

    public Model() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.d("aaaaaa", "aaaaa " + name);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

//    public JSONArray getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(JSONArray contactNumber) {
//        this.contactNumber = contactNumber;
//    }

    //    public int getTheme() {
//        return theme;
//    }
//
//    public void setTheme(Integer theme) {
//        this.theme = theme;
//    }
//
//    public int getProfilePic() {
//        return Integer.parseInt(profilePic);
//    }

//    public void setProfilePic(Integer profilePic) {
//        this.profilePic =profilePic;
//    }
}
