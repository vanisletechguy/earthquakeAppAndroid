package com.mikeaubie.finalproject.Models;

import com.mikeaubie.finalproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Aubie on 3/18/2017.
 */

public class NavigationDrawerItem {

  private String title;
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public static List<NavigationDrawerItem> getData() {
    List<NavigationDrawerItem> dataList = new ArrayList<>();
    String[] titles = getTitles();

    for (int i = 0; i < titles.length; i++) {
      NavigationDrawerItem navItem = new NavigationDrawerItem();
      navItem.setTitle(titles[i]);
      dataList.add(navItem);
    }
    return dataList;
  }

  private static String[] getTitles() {
    return new String[]{
            "Welcome Screen",
            "EarthQuake List View",
            "EarthQuake Map View",
            "Notifications Settings",
            "About Application",
    };
  }
}
