package com.mikeaubie.finalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikeaubie.finalproject.Adapters.QuakeAdapter;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.R;

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
    quakeAdapter = new QuakeAdapter((ArrayList) EarthQuakes.mEarthQuakeList, getContext());
    recyclerView.setAdapter(quakeAdapter);
    LinearLayoutManager mLinearLayoutManagerVertical =
            new LinearLayoutManager(getContext());
    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
  }
}
