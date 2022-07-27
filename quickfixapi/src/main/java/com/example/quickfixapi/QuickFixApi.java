package com.example.quickfixapi;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.volleyservice.VolleyResponseHandler;
import com.example.volleyservice.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public abstract class QuickFixApi {

    public abstract void onSuccess(String requestType, JSONObject response);

    public abstract void onSuccess(String requestType, JSONArray response);

    public abstract void onError(String requestType, VolleyError error);

    VolleyResponseHandler volleyResponseHandler;
    VolleyService volleyService;

    public QuickFixApi(Context context) {
        volleyResponseHandler = new VolleyResponseHandler() {
            @Override
            public void onSuccess(String requestType, JSONObject response) {
                QuickFixApi.this.onSuccess(requestType, response);
            }

            @Override
            public void onSuccess(String requestType, JSONArray response) {
                QuickFixApi.this.onSuccess(requestType, response);
            }

            @Override
            public void onError(String requestType, VolleyError error) {
                QuickFixApi.this.onError(requestType, error);
            }
        };
        volleyService = new VolleyService(volleyResponseHandler, context);
    }

    public void searchListings(String query) {
        volleyService.getDataVolley("GET", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/search/" + query);
    }

    public void getListings() {
        volleyService.getDataVolley("GET", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/");
    }

    public void getListingsByUserId(String userId) {
        volleyService.getDataVolley("GET", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/user/" + userId);
    }

    public void getUser() {
        volleyService.getDataVolley("GET", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/user/");
    }

    public void getUser(String id) {
        volleyService.getDataVolley("GET", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/user/" + id);
    }

    public void postName(String userId, String name) {
        JSONObject newName = new JSONObject();
        try {
            newName.put("userId", userId);
            newName.put("title", name);

            volleyService.postDataVolley("POST", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/user/", newName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void postListing(String providerId, String title, String description, String postalCode, String fee) {
        JSONObject newListing = new JSONObject();
        try {
            newListing.put("providerId", providerId);
            newListing.put("title", title);
            newListing.put("description", description);
            newListing.put("postalCode", postalCode);
            newListing.put("fee", fee);
            volleyService.postDataVolley("POST", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/", newListing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void putListing(String id, String title, String description, String postalCode, String fee) {
        JSONObject newListing = new JSONObject();
        try {
            newListing.put("id", id);
            newListing.put("title", title);
            newListing.put("description", description);
            newListing.put("postalCode", postalCode);
            newListing.put("fee", fee);
            volleyService.putDataVolley("PUT", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/", newListing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteListing(String id) {
        volleyService.deleteDataVolley("DELETE", "https://us-central1-quick-fix-api-873cc.cloudfunctions.net/listing/", id);
    }


}
