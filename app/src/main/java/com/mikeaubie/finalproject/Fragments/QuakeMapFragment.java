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
import com.google.android.gms.maps.model.MarkerOptions;
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
//        for (int index = 0; index < MapMarkers.gMapPoints.size(); index++) {
//         // LatLng newMarker = MapMarkers.gMapPoints.get(index).getLocation();
////          gMap.addMarker(new MarkerOptions()
////                  .position(newMarker)
////                  .title(MapMarkers.gMapPoints.get(index).getTitle())
////                  .snippet(MapMarkers.gMapPoints.get(index)
////                          .getDescription()));
//        }



        LatLng northIslandCollege = new LatLng(49.708652, -124.971147);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(northIslandCollege).zoom(15).build();
        gMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
      }
    });
    return rootView;
  }
//
//  public static MyMapFragment newInstance(String param1, String param2) {
//    MyMapFragment fragment = new MyMapFragment();
//    Bundle args = new Bundle();
//    args.putString(ARG_PARAM1, param1);
//    args.putString(ARG_PARAM2, param2);
//    fragment.setArguments(args);
//    return fragment;
//  }

}
