package com.mikeaubie.finalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikeaubie.finalproject.Adapters.QuakeAdapter;
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
public class QuakeListFragment extends Fragment {

  private QuakeAdapter quakeAdapter = null;
  public QuakeListFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quake_list, container, false);

    setupRecyclerView(view);
    return view;
  }

  private void setupRecyclerView(View v) {
    RecyclerView recyclerView = (RecyclerView)
            v.findViewById(R.id.recyclerView);
    quakeAdapter = new QuakeAdapter((ArrayList) EarthQuakes.filteredQuakes(), getContext());
    recyclerView.setAdapter(quakeAdapter);
    LinearLayoutManager mLinearLayoutManagerVertical =
            new LinearLayoutManager(getContext());
    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
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


    ArrayList<EarthQuake> quakeList = EarthQuakes.filteredQuakes();

    Toast.makeText(getContext(), EarthQuakes.filteredQuakes().size() + "All quakes: " + EarthQuakes.mEarthQuakeList.size(), Toast.LENGTH_LONG).show();

    RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
    QuakeAdapter quakeAdapter = (QuakeAdapter)recyclerView.getAdapter();
    quakeAdapter.swap(quakeList);
    quakeAdapter.notifyDataSetChanged();

  }










}
