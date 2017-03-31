package com.mikeaubie.finalproject.Models;

import java.util.ArrayList;

/**
 * Created by Family on 3/24/2017.
 */

public class EarthQuakes {
  public static boolean magnitudeFilter = false;
  public static double magFilterValue = 0.0;
  public static boolean dateFilter = false;
  public static int dateFilterValue = 0;
  public static boolean proximityFilter = false;
  public static int proximityFilterValue = 0;

  public static ArrayList<EarthQuake> mEarthQuakeList = new ArrayList<>();

  public static ArrayList<EarthQuake> filteredQuakes() {
    ArrayList<EarthQuake> filteredList = new ArrayList<EarthQuake>(mEarthQuakeList.size());
//    for (EarthQuake quake : mEarthQuakeList)
//      filteredList.add(new EarthQuake(quake));


    if (magnitudeFilter) {
      for (EarthQuake quake : mEarthQuakeList) {
        if (quake.getMagnitude() - magFilterValue >= 0) {
          filteredList.add(quake);
        }
      }
    }


//    if(magnitudeFilter) {
//      for(EarthQuake quake : mEarthQuakeList) {
//        if(quake.getMagnitude() - magFilterValue >= 0) {
//          if(dateFilter) {
//            if(proximityFilter) {
//
//            }
//          }
//          filteredList.add(quake);
//        }
//      }
//    }


//    if(dateFilter) {
//      for(int x=0; x < filteredList.size(); x++) {
//        GregorianCalendar cal = new GregorianCalendar();
//        DateTime today = new DateTime();
//        DateTime quakeTime = new DateTime(filteredList.get(x).getDate());
//        int daysBetween = Days.daysBetween(today, quakeTime).getDays();
//
//        if (daysBetween > dateFilterValue) {
//          filteredList.remove(x);
//        }
//      }
//    }

    return filteredList;
  }


  public EarthQuakes() {
  }

}
