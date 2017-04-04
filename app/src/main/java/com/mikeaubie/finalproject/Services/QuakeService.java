package com.mikeaubie.finalproject.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.mikeaubie.finalproject.Activities.MainActivity;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Michael Aubie on 4/1/2017.
 */

public class QuakeService extends IntentService {
  DateTime lastQuake;
  public static double magAlertFilter = 0.0;
  public static int proximityAlertFilter = 0;
  public static int refreshAlertFilter = 0;
  private Context context;
  ArrayList<EarthQuake> mEarthQuakeList = new ArrayList<>();
  final String url =
    "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/7d.json";

  public QuakeService() {
    super("HelloIntentService");
    lastQuake = new DateTime(EarthQuakes.mEarthQuakeList.get(0).getDate());
    this.context = EarthQuakes.context;
    FetchQuakeData(context, url);
    if (!mEarthQuakeList.isEmpty()) {
      lastQuake = new DateTime(mEarthQuakeList.get(0).getDate());
    }
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    serviceLoop();
  }

  @Override
  public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  public void serviceLoop() {
    try {
      if (mEarthQuakeList.size() > 0) {
        DateTime newQuake = new DateTime(mEarthQuakeList.get(0).getDate());
        if (lastQuake.compareTo(newQuake) != 0) { ///change to 1 to test
          if (mEarthQuakeList.get(0).getMagnitude() >= magAlertFilter) {
            if (EarthQuakes.getDistanceBetween(
                    mEarthQuakeList.get(0).getLocation(),
                    EarthQuakes.lastKnownLocation) <= proximityAlertFilter) {
              postNotification();
              lastQuake = new DateTime(mEarthQuakeList.get(0).getDate());
            }
          }
        }
      }
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void postNotification() {
    Context applicationContext = getApplicationContext();
    Intent notificationIntent =
            new Intent(applicationContext, MainActivity.class);

    PendingIntent pendingIntentg =
            PendingIntent.getActivity(applicationContext, 0,
                    notificationIntent, 0);
    String title = mEarthQuakeList.get(0).getMagnitude().toString();
    String text = mEarthQuakeList.get(0).getLocationDescription();
    Notification notification = new Notification.Builder(applicationContext)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(android.R.drawable.star_on)
            .setContentIntent(pendingIntentg)
            .build();
    NotificationManager notificationManager =
            (NotificationManager) applicationContext.getSystemService(
                    Context.NOTIFICATION_SERVICE);
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    notificationManager.notify(0, notification);
  }

  private void FetchQuakeData(final Context context, final String url) {
    mEarthQuakeList.clear();
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
                                Double.parseDouble(coordArray.get(1)
                                        .toString()));
                        Date date = new Date(); ///
                        String newDate =
                                (String) currentChild.get("origin_time");
                        DateFormat dateformat =
                                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                          long newTime = dateformat.parse(newDate).getTime();
                          date.setTime(dateformat.parse(newDate).getTime());
                        } catch (ParseException ex) {
                          Toast.makeText(context,
                                  "FAILED on DATE", Toast.LENGTH_LONG).show();
                        }
                        int localTimeUTCOffset = Integer.parseInt(
                                currentChild.get("local_time_utc_offset")
                                        .toString());
                        int localTimeDSTOffset = Integer.parseInt(
                                currentChild.get("local_time_dst_offset")
                                        .toString());
                        double depthInKM =
                                Double.parseDouble(currentChild
                                        .get("depth").toString());
                        Double magnitude =
                                Double.parseDouble(currentChild
                                        .get("magnitude").toString());
                        JSONObject locationObject =
                                (JSONObject) currentChild.get("location");
                        String locationDescription =
                                locationObject.get("en").toString();
                        EarthQuake newQuake = new EarthQuake(
                                newCoord, date, localTimeUTCOffset,
                                localTimeDSTOffset, depthInKM, magnitude,
                                locationDescription);
                        mEarthQuakeList.add(newQuake);
                      }
                    }
                  }
                  serviceLoop();
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
    Volley.newRequestQueue(context).add(jsonObjectRequest);
  }
}

