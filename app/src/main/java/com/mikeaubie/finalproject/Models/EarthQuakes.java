package com.mikeaubie.finalproject.Models;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Family on 3/24/2017.
 */

public class EarthQuakes {
  public static boolean magnitudeFilter = false;
  public static float magFilterValue = 0.0f;
  public static boolean dateFilter = false;
  public static int dateFilterValue = 0;
  public static boolean proximityFilter = false;
  public static int proximityFilterValue = 0;

  public static ArrayList<EarthQuake> mEarthQuakeList = new ArrayList<>();
  public static ArrayList<EarthQuake> filteredQuakes() {
    ArrayList<EarthQuake> filteredList = new ArrayList<EarthQuake>(mEarthQuakeList.size());
    for(EarthQuake quake : mEarthQuakeList) filteredList.add(new EarthQuake(quake));



    if(magnitudeFilter) {
      for(EarthQuake quake : mEarthQuakeList) {
        if(quake.getMagnitude() < magFilterValue) { filteredList.remove(quake);}
      }
    }

    if(dateFilter) {
      for(EarthQuake quake : mEarthQuakeList) {
        GregorianCalendar cal = new GregorianCalendar();
        DateTime today = new DateTime();
        DateTime quakeTime = new DateTime(quake.getDate());
        int daysBetween = Days.daysBetween(today, quakeTime).getDays();

        if (daysBetween > magFilterValue) {
          filteredList.remove(quake);
        }
      }
    }

    return filteredList;
  }


  public EarthQuakes() {
  }

}
