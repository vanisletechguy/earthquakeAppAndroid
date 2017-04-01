package com.mikeaubie.finalproject.Activities;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mikeaubie.finalproject.Eventbus.Events;
import com.mikeaubie.finalproject.Eventbus.GlobalBus;
import com.mikeaubie.finalproject.Fragments.NavigationDrawerFragment;
import com.mikeaubie.finalproject.Fragments.QuakeListFragment;
import com.mikeaubie.finalproject.Fragments.QuakeMapFragment;
import com.mikeaubie.finalproject.Fragments.WelcomeFragment;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.Models.FetchQuakeData;
import com.mikeaubie.finalproject.R;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {
  public NavigationDrawerFragment drawerFragment = null;
  private Toolbar toolbar;
  String alertResponse = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    JodaTimeAndroid.init(this);
    setContentView(R.layout.activity_main);
    //EventBus.getDefault().register(this);

    if (savedInstanceState == null) {
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

  public boolean filterByDate(final MenuItem item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Within how many days?");
  // Set up the input
    final EditText input = new EditText(this);
  // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
    builder.setView(input);

  // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        Toast.makeText(getApplicationContext(), alertResponse, Toast.LENGTH_LONG).show();

        try{
          int dayFilterInput = Integer.parseInt(alertResponse);
          EarthQuakes.dateFilterValue = dayFilterInput;
          EarthQuakes.dateFilter = true;
          item.setChecked(true);

          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);

        }catch (Exception ex) {
          Toast.makeText(getApplicationContext(), "Not a valid number of days - Try 15", Toast.LENGTH_LONG).show();
        }

      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
        EarthQuakes.dateFilter = false;
      }
    });

    builder.show();

    return true;
  }

  public boolean filterByMagnitude(final MenuItem item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Show only Magnitudes Above this value:");
  // Set up the input
    final EditText input = new EditText(this);
  // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    builder.setView(input);

  // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        Toast.makeText(getApplicationContext(), alertResponse, Toast.LENGTH_LONG).show();


        try{
          Double magFilterInput = Double.parseDouble(alertResponse);
          EarthQuakes.magFilterValue = magFilterInput;
          EarthQuakes.magnitudeFilter = true;
          item.setChecked(true);

          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);
        } catch (Exception ex) {
          Toast.makeText(getApplicationContext(), "Not a valid value - Try 4.0", Toast.LENGTH_LONG).show();
        }

      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
        EarthQuakes.magnitudeFilter = true;
        EarthQuakes.magFilterValue = 0;
      }
    });

    builder.show();

    return true;
  }

  public boolean filterByProximity(final MenuItem item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Within how many km?");
  // Set up the input
    final EditText input = new EditText(this);
  // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    builder.setView(input);

  // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        //Toast.makeText(getApplicationContext(), alertResponse, Toast.LENGTH_LONG).show();

        try{
          int proxFilterInput = Integer.parseInt(alertResponse);
          EarthQuakes.proximityFilterValue = proxFilterInput;
          EarthQuakes.magnitudeFilter = true;
          item.setChecked(true);

          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);
        } catch (Exception ex) {
          Toast.makeText(getApplicationContext(), "Not a valid value - Try 50", Toast.LENGTH_LONG).show();
        }
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
      }
    });

    builder.show();

    return true;
  }

  void setUpNavDrawer() {
    drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
            .findFragmentById(R.id.nav_drwr_fragment);
    DrawerLayout drawerLayout =
            (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerFragment.setUpDrawer(drawerLayout, toolbar);
  }

  public void fetchData7Day(View v) {
    if (EarthQuakes.mEarthQuakeList.size() < 1) {
      final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/7d.json";
      FetchQuakeData fetchedData = new FetchQuakeData(this, url);
    }
  }

  public void fetchData30Day(View v) {
    if (EarthQuakes.mEarthQuakeList.size() < 1) {
      final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/30d.json";
      FetchQuakeData fetchedData = new FetchQuakeData(this, url);
    }
  }

  public void fetchData1Year(View v) {
    if (EarthQuakes.mEarthQuakeList.size() < 1) {
      final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/365d.json";
      FetchQuakeData fetchedData = new FetchQuakeData(this, url);
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
