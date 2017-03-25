package com.mikeaubie.finalproject.Models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import java.io.*;
import java.util.Iterator;

import org.json.*;
/**
 * Created by Family on 3/24/2017.
 */

public class FetchQuakeData {
  public FetchQuakeData(final Context context) {

    final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/7d.json";

    JSONObject newObject = new JSONObject();
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, newObject, new Response.Listener<JSONObject>() {


              @Override
              public void onResponse(JSONObject response) {

                EarthQuakes.generateQuakes(); ///to test for response
                try {

                  Iterator<?> keys = response.keys(); ///returns in reverse order

                  while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    //Toast.makeText(context, key, Toast.LENGTH_LONG).show();
                    //itterates over the keys
                    if ( response.get(key) instanceof JSONObject ) {

                        JSONObject currentChild = (JSONObject)response.get(key);
                        if(key.equalsIgnoreCase("metadata")) {
                          Toast.makeText(context, key, Toast.LENGTH_LONG).show();
                        } else {
                          Toast.makeText(context, (currentChild.get("magnitude")).toString(), Toast.LENGTH_LONG).show();
                        }

                    }
                  }



                  //JSONObject quake = response.getJSONObject("metadata");
                  JSONObject quake = response.getJSONObject("20170324.1002002");
                  Double magnitude = (double)quake.get("magnitude");


//                  Toast.makeText(context, magnitude.toString(), Toast.LENGTH_LONG).show();
                }catch (JSONException ex) {
                  Toast.makeText(context, "ERROR: " + ex.toString(), Toast.LENGTH_LONG).show();
                }

              }
            }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                //System.out.print(error);

              }
            });
    Volley.newRequestQueue(context.getApplicationContext()).add(jsonObjectRequest);









  }
}
