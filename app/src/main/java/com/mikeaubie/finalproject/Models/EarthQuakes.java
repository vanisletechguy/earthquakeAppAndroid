package com.mikeaubie.finalproject.Models;

import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;

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

    ArrayList<EarthQuake> magfilteredList = new ArrayList<>();

    for(EarthQuake quake : mEarthQuakeList) {
      EarthQuake newQuake = new EarthQuake(quake);
      magfilteredList.add(newQuake);
    }



    if(magnitudeFilter) {

      //magfilteredList.removeIf(item -> item.magnitude == 0);

      for (int x = 0; x < magfilteredList.size(); x++) {
        if (magfilteredList.get(x).getMagnitude() - magFilterValue < 0) {
          magfilteredList.remove(x--);
        }
      }
    }









//
//
//    ArrayList<EarthQuake> magfilteredList = new ArrayList<>();
//   // ArrayList<EarthQuake> datefilteredList = new ArrayList<>();
//
//    if (magnitudeFilter) {
//      for (EarthQuake quake : mEarthQuakeList) {
//        if (quake.getMagnitude() - magFilterValue >= 0) {
//          magfilteredList.add(quake);
//        }
//      }
//    }






//    if (dateFilter) {
//      for (EarthQuake quake : magfilteredList ) {
//        DateTime current = new DateTime();
//        DateTime quakeTime = new DateTime(quake.getDate());
//        Days daysBetween = Days.daysBetween(current.toInstant(), quakeTime.toInstant());
//        int howMany = daysBetween.getDays();
//        if (howMany < dateFilterValue) {
//          datefilteredList.add(quake);
//        }
//      }
//    } else {
//      for (EarthQuake quake : magfilteredList) {
//        datefilteredList.add(quake);
//      }
//    }

    return magfilteredList;
  }


  public EarthQuakes() {
  }

}



































