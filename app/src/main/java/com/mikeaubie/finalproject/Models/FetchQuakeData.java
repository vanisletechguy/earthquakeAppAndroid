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
import org.json.*;
/**
 * Created by Family on 3/24/2017.
 */

public class FetchQuakeData {
  public FetchQuakeData(final Context context) {

    final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/7d.json";

    JSONObject newObject = new JSONObject();
    JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
            (Request.Method.GET, url, newObject, new Response.Listener<JSONObject>() {


              @Override
              public void onResponse(JSONObject response) {
                //mTxtDisplay.setText("Response: " + response.toString());
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                EarthQuakes.generateQuakes(); ///to test for response
                try {
                  JSONObject quake = response.getJSONObject("metadata");
                  //JSONArray newarray = quake.getJSONArray("request");
                 // String dateCreated = quake.getString("dateCreated");
                  //JSONArray names = quake.getJSONArray("request");


                  JSONObject request = (JSONObject)quake.get("request");
                  String strRequest = (String) request.get("dateCreated");
                  //String dateCreated = strRequest.toString();
                  Toast.makeText(context, strRequest, Toast.LENGTH_LONG).show();
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
    Volley.newRequestQueue(context.getApplicationContext()).add(jsonArrayRequest);









  }
}
