package com.mikeaubie.finalproject.Models;

import android.content.Context;
import com.google.android.gms.maps.model.LatLng;
import org.joda.time.DateTime;
import org.joda.time.Days;
import java.util.ArrayList;

/**
 * Created by Michael Aubie on 3/24/2017.
 */

public class EarthQuakes {
  public static boolean magnitudeFilter = false;
  public static double magFilterValue = 0.0;
  public static boolean dateFilter = false;
  public static int dateFilterValue = 0;
  public static boolean proximityFilter = false;
  public static int proximityFilterValue = 0;

  public static ArrayList<EarthQuake> mEarthQuakeList = new ArrayList<>();
  public static LatLng lastKnownLocation = new LatLng(49.708652, -124.971147);
  public static Context context;
  public static ArrayList<EarthQuake> filteredQuakes() {

    ArrayList<EarthQuake> magfilteredList = new ArrayList<>();

    for(EarthQuake quake : mEarthQuakeList) {
      EarthQuake newQuake = new EarthQuake(quake);
      magfilteredList.add(newQuake);
    }

    if(magnitudeFilter) {
      for (int x = 0; x < magfilteredList.size(); x++) {
        if (magfilteredList.get(x).getMagnitude() - magFilterValue < 0) {
          magfilteredList.remove(x--);
        }
      }
    }
    if(dateFilter) {
      DateTime today = new DateTime();
      for(int x = 0; x < magfilteredList.size(); x++) {
        DateTime quakeTime = new DateTime(magfilteredList.get(x).getDate());
        int daysBetween = Days.daysBetween(quakeTime, today).getDays();
        if(daysBetween > dateFilterValue) {
          magfilteredList.remove(x--);
        }
      }
    }
    if(proximityFilter) {
      for(int x = 0; x < magfilteredList.size(); x++) {
        int distanceBetween = getDistanceBetween(lastKnownLocation,
                magfilteredList.get(x).getLocation());
        if(distanceBetween > proximityFilterValue) {
         magfilteredList.remove(x--);
        }
      }
    }
    return magfilteredList;
  }

  public EarthQuakes() {
  }

  public static int getDistanceBetween(LatLng currentLocation,
                                       LatLng quakeLocation) {
    double distance = haversine(currentLocation.latitude,
            currentLocation.longitude, quakeLocation.latitude,
            quakeLocation.longitude);
    return ((Double)distance).intValue();
  }

  public static double haversine(
          double lat1, double lng1, double lat2, double lng2) {
    int r = 6371; // average radius of the earth in km
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lng2 - lng1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double d = r * c;
    return d;
  }
}



































