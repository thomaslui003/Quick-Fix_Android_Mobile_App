package com.example.volleyservice;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyService {

    VolleyResponseHandler volleyResponseHandler = null;
    Context context;

    public VolleyService(VolleyResponseHandler responseHandler, Context context) {
        volleyResponseHandler = responseHandler;
        this.context = context;
    }

    public void postDataVolley(final String requestType, String url, JSONObject sendObj) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url, sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onSuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onError(requestType, error);
                }
            });

            queue.add(jsonObj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putDataVolley(final String requestType, String url, JSONObject sendObj) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url + sendObj.getString("id"), sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (volleyResponseHandler != null) {
                        volleyResponseHandler.onSuccess(requestType, response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onError(requestType, error);
                }
            });

            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteDataVolley(final String requestType, String url, String id) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (volleyResponseHandler != null) {
                        volleyResponseHandler.onSuccess(requestType, response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onError(requestType, error);
                }
            });

            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataVolley(final String requestType, String url) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onSuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResponseHandler != null)
                        volleyResponseHandler.onError(requestType, error);
                }
            });

            queue.add(jsonArrayRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}