package com.mikeaubie.finalproject.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Family on 3/24/2017.
 */

public class EarthQuakes {
  public static ArrayList<EarthQuake> mEarthQuakeList;


  public EarthQuakes() {
  }

  public static void generateQuakes() {
    mEarthQuakeList = new ArrayList<>();
    LatLng newCoord = new LatLng(30, 30);
    Date date = new Date();
    int localTimeUTCOffset = -10800;
    int localTimeDSTOffset = 3600;
    double depthInKM = 20170323.1608001;
    Float magnitude = (float)3.4;
    String locationDescription = "Some place in Canada";

    EarthQuake newQuake = new EarthQuake(newCoord, date, localTimeUTCOffset,
            localTimeDSTOffset, depthInKM, magnitude, locationDescription);

    mEarthQuakeList.add(newQuake);
  }
}
