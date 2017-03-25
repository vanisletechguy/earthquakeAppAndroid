package com.mikeaubie.finalproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikeaubie.finalproject.R;
import com.mikeaubie.finalproject.Adapters.NavigationDrawerAdapter;
import com.mikeaubie.finalproject.Models.NavigationDrawerItem;

/**
 * Created by Michael Aubie on 3/18/2017.
 */
public class NavigationDrawerFragment extends Fragment {

  private ActionBarDrawerToggle mDrawerToggle;
  public DrawerLayout mDrawerLayout;

  public NavigationDrawerFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_navigation_drawer,
            container, false);
    setUpRecyclerView(v);
    return v;
  }




  private void setUpRecyclerView(View view) {
    RecyclerView recyclerView =
            (RecyclerView) view.findViewById(R.id.drawerList);
    NavigationDrawerAdapter adapter =
            new NavigationDrawerAdapter(getActivity(),
                    NavigationDrawerItem.getData());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar) {
    mDrawerLayout = drawerLayout;

    mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
            toolbar, R.string.drawer_open, R.string.drawer_close) {
      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
      }
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
      }
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
      }
    };

    mDrawerLayout.addDrawerListener(mDrawerToggle);
    mDrawerLayout.post(new Runnable() {
      @Override
      public void run() {
        mDrawerToggle.syncState();
      }
    });
  }
}
