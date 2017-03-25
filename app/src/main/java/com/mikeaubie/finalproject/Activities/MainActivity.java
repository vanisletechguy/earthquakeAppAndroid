package com.mikeaubie.finalproject.Activities;

import android.content.Context;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapFragment;
import com.mikeaubie.finalproject.Fragments.NavigationDrawerFragment;
import com.mikeaubie.finalproject.Fragments.QuakeListFragment;
import com.mikeaubie.finalproject.Fragments.QuakeMapFragment;
import com.mikeaubie.finalproject.Fragments.WelcomeFragment;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.Models.FetchQuakeData;
import com.mikeaubie.finalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  public NavigationDrawerFragment drawerFragment = null;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if(savedInstanceState == null) {
      WelcomeFragment welcomeFragment = new WelcomeFragment();
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.add(R.id.flContainer, welcomeFragment);
      ft.commit();
      setUpToolbar();
      setUpNavDrawer();
    }
  }

  private void setUpToolbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("Assignment5");
    toolbar.inflateMenu(R.menu.menu_list);
  }

  void setUpNavDrawer() {
    drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
            .findFragmentById(R.id.nav_drwr_fragment);
    DrawerLayout drawerLayout =
            (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerFragment.setUpDrawer(drawerLayout, toolbar);
  }

  public void fetchData(View v) {
    if(EarthQuakes.mEarthQuakeList.size()<1) {
      FetchQuakeData fetchedData = new FetchQuakeData(this);
    }
  }

  public void showData(View v) {

    QuakeListFragment quakeListFragment = new QuakeListFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.flContainer, quakeListFragment);
    ft.commit();
  }

  public void showMap(View v) {
    QuakeMapFragment quakeMapFragment = new QuakeMapFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.flContainer, quakeMapFragment);
    ft.commit();
  }
}
