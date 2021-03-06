package com.mikeaubie.finalproject.Models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.mikeaubie.finalproject.Eventbus.Events;
import com.mikeaubie.finalproject.Eventbus.GlobalBus;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.*;

/**
 * Created by Michael Aubie on 3/24/2017.
 */

public class FetchQuakeData {
  public FetchQuakeData(final Context context, final String url) {
    EarthQuakes.mEarthQuakeList.clear();

    JSONObject newObject = new JSONObject();
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, newObject, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                try {
                  Iterator<?> keys = response.keys();
                  while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (response.get(key) instanceof JSONObject) {
                      JSONObject currentChild = (JSONObject) response.get(key);
                      if (key.equalsIgnoreCase("metadata")) {
                      } else {
                        JSONObject coordObject =
                                (JSONObject) currentChild.get("geoJSON");
                        JSONArray coordArray =
                                (JSONArray) coordObject.get("coordinates");
                        LatLng newCoord = new LatLng(Double.parseDouble(
                                coordArray.get(0).toString()),
                                Double.parseDouble(
                                        coordArray.get(1).toString()));
                        Date date = new Date(); ///  origin_time
                        String newDate =
                                (String) currentChild.get("origin_time");
                        DateFormat dateformat =
                                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                          long newTime = dateformat.parse(newDate).getTime();
                          date.setTime(dateformat.parse(newDate).getTime());
                        } catch (ParseException ex) {
                          Toast.makeText(context, "FAILED on DATE",
                                  Toast.LENGTH_LONG).show();
                        }
                        int localTimeUTCOffset = Integer.parseInt(
                                currentChild.get(
                                        "local_time_utc_offset").toString());
                        int localTimeDSTOffset = Integer.parseInt(
                                currentChild.get(
                                        "local_time_dst_offset").toString());
                        double depthInKM = Double.parseDouble(
                                currentChild.get("depth").toString());
                        Double magnitude = Double.parseDouble(
                                currentChild.get("magnitude").toString());
                        JSONObject locationObject =
                                (JSONObject) currentChild.get("location");
                        String locationDescription =
                                locationObject.get("en").toString();
                        EarthQuake newQuake = new EarthQuake(newCoord, date,
                                localTimeUTCOffset, localTimeDSTOffset,
                                depthInKM, magnitude, locationDescription);
                        EarthQuakes.mEarthQuakeList.add(newQuake);
                      }
                    }
                  }
                } catch (JSONException ex) {
                  Toast.makeText(context, "ERROR: " + ex.toString(),
                          Toast.LENGTH_LONG).show();
                }
              }
            }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(context, error.toString(),
                        Toast.LENGTH_LONG).show();
              }
            });

    Volley.newRequestQueue(context.getApplicationContext())
            .add(jsonObjectRequest);
  }
}
