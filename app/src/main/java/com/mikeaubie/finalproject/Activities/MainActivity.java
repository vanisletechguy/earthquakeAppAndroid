package com.mikeaubie.finalproject.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.mikeaubie.finalproject.Services.QuakeService;
import com.mikeaubie.finalproject.R;
import com.mikeaubie.finalproject.Services.QuakeServiceReciever;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
  public NavigationDrawerFragment drawerFragment = null;
  private Toolbar toolbar;
  String alertResponse = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    JodaTimeAndroid.init(this);
    setContentView(R.layout.activity_main);
    EarthQuakes.context = this;

    if (savedInstanceState == null) {
      WelcomeFragment welcomeFragment = new WelcomeFragment();
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.add(R.id.flContainer, welcomeFragment);
      ft.commit();
    }
    setUpToolbar();
    setUpNavDrawer();
  }

  private void setUpToolbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("Canadian Quake Monitor");
    toolbar.inflateMenu(R.menu.menu_list);
    toolbar.getMenu().findItem(R.id.filterByDate).setChecked(EarthQuakes.dateFilter);
    toolbar.getMenu().findItem(R.id.filterByMagnitude).setChecked(EarthQuakes.magnitudeFilter);
    toolbar.getMenu().findItem(R.id.filterByProximity).setChecked(EarthQuakes.dateFilter);
  }

  public boolean filterByDate(final MenuItem item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Within how many days?");
  // Set up the input
    final EditText input = new EditText(this);
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
    builder.setView(input);

  // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        try{
          int dayFilterInput = Integer.parseInt(alertResponse);
          EarthQuakes.dateFilterValue = dayFilterInput;
          EarthQuakes.dateFilter = true;
          item.setChecked(true);

          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);

        }catch (Exception ex) {
          Toast.makeText(getApplicationContext(),
                  "Not a valid number of days - Try 15",
                  Toast.LENGTH_LONG).show();
        }
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
        EarthQuakes.dateFilter = false;
        Events.NewMarkerMessage newMarkerMessage =
                new Events.NewMarkerMessage();
        GlobalBus.getBus().postSticky(newMarkerMessage);
      }
    });

    builder.show();

    return true;
  }

  public boolean filterByMagnitude(final MenuItem item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Show only Magnitudes Above this value:");
    final EditText input = new EditText(this);
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
    builder.setView(input);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        try{
          Double magFilterInput = Double.parseDouble(alertResponse);
          EarthQuakes.magFilterValue = magFilterInput;
          EarthQuakes.magnitudeFilter = true;
          item.setChecked(true);
          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);
        } catch (Exception ex) {
          Toast.makeText(getApplicationContext(),
                  "Not a valid value - Try 4.0", Toast.LENGTH_LONG).show();
        }
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
        EarthQuakes.magnitudeFilter = false;
        EarthQuakes.magFilterValue = 0;
        Events.NewMarkerMessage newMarkerMessage =
                new Events.NewMarkerMessage();
        GlobalBus.getBus().postSticky(newMarkerMessage);
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
    input.setInputType(
            InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    builder.setView(input);

  // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        alertResponse = input.getText().toString();
        try{
          int proxFilterInput = Integer.parseInt(alertResponse);
          EarthQuakes.proximityFilterValue = proxFilterInput;
          EarthQuakes.proximityFilter = true;
          item.setChecked(true);

          Events.NewMarkerMessage newMarkerMessage =
                  new Events.NewMarkerMessage();
          GlobalBus.getBus().postSticky(newMarkerMessage);
        } catch (Exception ex) {
          Toast.makeText(getApplicationContext(),
                  "Not a valid value - Try 50", Toast.LENGTH_LONG).show();
        }
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        item.setChecked(false);
        EarthQuakes.proximityFilter = false;
        EarthQuakes.proximityFilterValue = 0;
        Events.NewMarkerMessage newMarkerMessage =
                new Events.NewMarkerMessage();
        GlobalBus.getBus().postSticky(newMarkerMessage);
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

  public void startQuakeService(double magnitude, int proximity, int refresh) {
    QuakeService.magAlertFilter = magnitude;
    QuakeService.proximityAlertFilter = proximity;
    QuakeService.refreshAlertFilter = refresh;

    AlarmManager processTimer = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this, QuakeService.class);
    //
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intent, 0);


    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR, 3);
    calendar.set(Calendar.MINUTE, 29);
    calendar.set(Calendar.AM_PM, Calendar.PM);

    startService(intent);
   // processTimer.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
    intent = new Intent(this, QuakeServiceReciever.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intent, 0);
    processTimer.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pendingIntent);



    AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
    alarm.set(
            // This alarm will wake up the device when System.currentTimeMillis()
            // equals the second argument value
            alarm.RTC_WAKEUP,
            System.currentTimeMillis() + (1000 * 60 * 60), // One hour from now
            // PendingIntent.getService creates an Intent that will start a service
            // when it is called. The first argument is the Context that will be used
            // when delivering this intent. Using this has worked for me. The second
            // argument is a request code. You can use this code to cancel the
            // pending intent if you need to. Third is the intent you want to
            // trigger. In this case I want to create an intent that will start my
            // service. Lastly you can optionally pass flags.
            PendingIntent.getService(this, 0, new Intent(this, QuakeService.class), 0)
    );






  }
}































