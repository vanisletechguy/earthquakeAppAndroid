package com.mikeaubie.finalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikeaubie.finalproject.Adapters.QuakeAdapter;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuakeListFragment extends Fragment {

  private QuakeAdapter quakeAdapter = null;
  private Toolbar toolbar;
 // public NavigationDrawerFragment drawerFragment = null;
  public QuakeListFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quake_list, container, false);

    setUpToolbar(view);

    setupRecyclerView(view);
    //setUpNavDrawer();
    return view;
  }

//  void setUpNavDrawer() {
//    drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
//            .findFragmentById(R.id.nav_drwr_fragment);
//    DrawerLayout drawerLayout =
//            (DrawerLayout) findViewById(R.id.drawer_layout);
//    drawerFragment.setUpDrawer(drawerLayout, toolbar);
//  }

  private void setUpToolbar(View view) {
    toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    toolbar.setTitle("Assignment5");
    toolbar.inflateMenu(R.menu.menu_list);
  }

  private void setupRecyclerView(View v) {
    RecyclerView recyclerView = (RecyclerView)
            v.findViewById(R.id.recyclerView);
    //MainActivity.mData = User.sortByBirthday(MainActivity.mData);
    quakeAdapter = new QuakeAdapter((ArrayList) EarthQuakes.mEarthQuakeList, getContext());
    recyclerView.setAdapter(quakeAdapter);
    LinearLayoutManager mLinearLayoutManagerVertical =
            new LinearLayoutManager(getContext());
    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
  }
}
