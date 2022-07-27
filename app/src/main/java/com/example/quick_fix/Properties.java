package com.example.quick_fix;

import android.app.Application;

import org.json.JSONArray;

import java.util.ArrayList;

public class Properties {

    private static Properties mInstance = null;

    // public ArrayList searchResults = new ArrayList<>();
    // public ArrayList listings = new ArrayList<>();
    private String databaseUserId;
    private String sfuUserId;
    private String studentProfileName;

    public ListingObjectsAdapter searchAdapter = new ListingObjectsAdapter(new ArrayList<>());;
    public ListingObjectsAdapter listingAdapter = new ListingObjectsAdapter(new ArrayList<>());;


    protected Properties() {
        //prevent from instantiating
    }

    public static synchronized Properties getInstance() {
        if (mInstance == null) {
            mInstance = new Properties();

        }
        return mInstance;
    }

    public void settingName(String studentName){

        this.studentProfileName = studentName;

    }
    public void settingDatabaseUserId (String databaseUserId){
        this.databaseUserId = databaseUserId;
    }
    public void settingSfuUserName(String sfuUserId){
        this.sfuUserId = sfuUserId;
    }

    public String gettingDatabaseUserId (){
        return this.databaseUserId;
    }

    public String gettingSfuUserId (){
        return this.sfuUserId;
    }
    public String gettingStudentProfileName(){
        return this.studentProfileName;
    }


}
