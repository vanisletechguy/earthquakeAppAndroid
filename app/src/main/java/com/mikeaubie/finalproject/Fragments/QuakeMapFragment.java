package com.mikeaubie.finalproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuakeMapFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  MapView gMapView;
  GoogleMap gMap = null;

  public QuakeMapFragment() {
    // Required empty public constructor
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_map, container, false);
    gMapView = (MapView) rootView.findViewById(R.id.map);
    gMapView.onCreate(savedInstanceState);
    gMapView.onResume();
    try {
      MapsInitializer.initialize(getActivity().getApplicationContext());
    } catch (Exception e) {
      e.printStackTrace();
    }
    gMapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap mMap) {
        gMap = mMap;

        gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
          @Override
          public View getInfoWindow(Marker marker) {
            return null;
          }
          @Override
          public View getInfoContents(Marker marker) {
            View view = getActivity().getLayoutInflater().inflate(
                    R.layout.quake_info_marker, null);
            return view;
          }
        });

        for (int index = 0; index < EarthQuakes.mEarthQuakeList.size(); index++) {
          LatLng newMarker = EarthQuakes.mEarthQuakeList.get(index).getLocation();
          gMap.addMarker(new MarkerOptions()
                  .position(newMarker)
                  .title(EarthQuakes.mEarthQuakeList.get(index).getLocationDescription())
                  .snippet(EarthQuakes.mEarthQuakeList.get(index).getMagnitude().toString()));
        }

        LatLng northIslandCollege = new LatLng(49.708652, -124.971147);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(northIslandCollege).zoom(3).build();
        gMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
      }
    });
    return rootView;
  }

}
