package com.mikeaubie.finalproject.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Michael Aubie on 3/24/2017.
 */

public class EarthQuake {
  LatLng location;
  Date date;
  int localTimeUTCOffset;
  int localTimeDSTOffset;
  double depthInKM;
  Double magnitude;
  String locationDescription;

  public EarthQuake(LatLng location, Date date, int localTimeUTCOffset,
                    int localTimeDSTOffset, double depthInKM, Double magnitude,
                    String locationDescription) {
    this.location = location;
    this.date = date;
    this.localTimeUTCOffset = localTimeUTCOffset;
    this.localTimeDSTOffset = localTimeDSTOffset;
    this.depthInKM = depthInKM;
    this.magnitude = magnitude;
    this.locationDescription = locationDescription;
  }

  public EarthQuake(EarthQuake earthQuake) {
    this.location = earthQuake.location;
    this.date = earthQuake.date;
    this.localTimeUTCOffset = earthQuake.localTimeUTCOffset;
    this.localTimeDSTOffset = earthQuake.localTimeDSTOffset;
    this.depthInKM = earthQuake.depthInKM;
    this.magnitude = earthQuake.magnitude;
    this.locationDescription = earthQuake.locationDescription;

  }
  public LatLng getLocation() {
    return location;
  }

  public void setLocation(LatLng location) {
    this.location = location;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getLocalTimeUTCOffset() {
    return localTimeUTCOffset;
  }

  public void setLocalTimeUTCOffset(int localTimeUTCOffset) {
    this.localTimeUTCOffset = localTimeUTCOffset;
  }

  public int getLocalTimeDSTOffset() {
    return localTimeDSTOffset;
  }

  public void setLocalTimeDSTOffset(int localTimeDSTOffset) {
    this.localTimeDSTOffset = localTimeDSTOffset;
  }

  public double getDepthInKM() {
    return depthInKM;
  }

  public void setDepthInKM(double depthInKM) {
    this.depthInKM = depthInKM;
  }

  public Double getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(Double magnitude) {
    this.magnitude = magnitude;
  }

  public String getLocationDescription() {
    return locationDescription;
  }

  public void setLocationDescription(String locationDescription) {
    this.locationDescription = locationDescription;
  }
}
