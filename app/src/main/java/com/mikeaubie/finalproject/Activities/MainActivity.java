package com.mikeaubie.finalproject.Activities;

import android.content.Context;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.mikeaubie.finalproject.Fragments.QuakeListFragment;
import com.mikeaubie.finalproject.Fragments.QuakeMapFragment;
import com.mikeaubie.finalproject.Fragments.WelcomeFragment;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.R;

public class MainActivity extends AppCompatActivity {



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    WelcomeFragment welcomeFragment = new WelcomeFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.add(R.id.flContainer, welcomeFragment);
    ft.commit();



  }

  public void fetchData(View v) {
    EarthQuakes.generateQuakes();
    int numberOfQuakes = EarthQuakes.mEarthQuakeList.size();
    Toast.makeText(this.getApplicationContext(), "generated " + numberOfQuakes + " Quakes", Toast.LENGTH_SHORT).show();
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
    ft.replace(R.id.flContainer, quakeMapFragment );
    ft.commit();
  }
}