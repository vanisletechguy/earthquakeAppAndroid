package com.mikeaubie.finalproject.Fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikeaubie.finalproject.Eventbus.Events;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuakeMapFragment extends Fragment implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  MapView gMapView;
  GoogleMap gMap = null;
  GoogleApiClient mGoogleApiClient = null;
  public QuakeMapFragment() {
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

        ArrayList<EarthQuake> quakeList = EarthQuakes.filteredQuakes();
        for (int index = 0; index < quakeList.size(); index++) {
          LatLng newMarker = quakeList.get(index).getLocation();
          gMap.addMarker(new MarkerOptions()
                  .position(newMarker)
                  .title(quakeList.get(index).getLocationDescription())
                  .snippet(quakeList.get(index).getMagnitude().toString()));
        }

        LatLng northIslandCollege = new LatLng(49.708652, -124.971147);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(northIslandCollege).zoom(3).build();
        gMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
      }
    });

    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .build();
    }
    mGoogleApiClient.connect();
    return rootView;
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void NewMarkerMessage(Events.NewMarkerMessage newMarkerMessage) {
    gMap.clear();

    ArrayList<EarthQuake> quakeList = EarthQuakes.filteredQuakes();

    for (int index = 0; index < quakeList.size(); index++) {
      LatLng latlngNewMarker = quakeList.get(index).getLocation();

      MarkerOptions options = new MarkerOptions()
              .position(latlngNewMarker)
              .title(quakeList.get(index).getLocationDescription())
              .snippet(quakeList.get(index).getMagnitude().toString());

      gMap.addMarker(new MarkerOptions()
              .position(latlngNewMarker)
              .title(quakeList.get(index).getLocationDescription())
              .snippet(quakeList.get(index).getMagnitude().toString()))
              .showInfoWindow();
    }

    LatLng northIslandCollege = new LatLng(49.708652, -124.971147);
    CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(northIslandCollege).zoom(3).build();
    gMap.animateCamera(CameraUpdateFactory
            .newCameraPosition(cameraPosition));
  }

  @Override
  public void onLocationChanged(Location location) {
    EarthQuakes.lastKnownLocation = new LatLng(location.getLatitude(),
            location.getLongitude());
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    //Toast.makeText(this.getContext(), "CONNECTED", Toast.LENGTH_LONG).show();
    Location mLastLocation = null;
    final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    if (ContextCompat.checkSelfPermission(this.getContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this.getActivity(),
              new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
              MY_PERMISSION_ACCESS_COARSE_LOCATION);
      mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
              mGoogleApiClient);
    }

    if (mLastLocation != null) {
      EarthQuakes.lastKnownLocation =
              new LatLng(mLastLocation.getLatitude(),
                      mLastLocation.getLongitude());
      Toast.makeText(this.getContext(), "NEW LOCATION",
              Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }
}
