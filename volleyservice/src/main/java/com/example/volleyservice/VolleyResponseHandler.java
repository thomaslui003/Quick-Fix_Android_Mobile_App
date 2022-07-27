package com.example.volleyservice;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface VolleyResponseHandler {
    public void onSuccess(String requestType, JSONObject response);
    public void onSuccess(String requestType, JSONArray response);
    public void onError(String requestType, VolleyError error);
}