package com.mikeaubie.finalproject.Services;

import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mikeaubie.finalproject.Activities.MainActivity;
import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.Models.EarthQuakes;
import com.mikeaubie.finalproject.Models.FetchQuakeData;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Michael Aubie on 4/1/2017.
 */

public class QuakeService extends IntentService {
  int refreshRate = 300000; // 5mins
  DateTime lastQuake;
  public QuakeService() {
    super("HelloIntentService");
    lastQuake = new DateTime();
    if(!EarthQuakes.mEarthQuakeList.isEmpty()) {
      lastQuake = new DateTime(EarthQuakes.mEarthQuakeList.get(0).getDate());
    }
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    while (1 < 2) {
      serviceLoop();
    }
  }

  @Override
  public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

    return super.onStartCommand(intent, flags, startId);
  }


  public void serviceLoop() {
    try {
      Thread.sleep(refreshRate);
      final String url = "http://www.earthquakescanada.nrcan.gc.ca/api/v2/locations/latest/7d.json";
      new FetchQuakeData(this, url);

      DateTime newQuake = new DateTime(EarthQuakes.mEarthQuakeList.get(0).getDate()); ///////////change to get filtered alert quake

      if(lastQuake.compareTo(newQuake) != 0) {
        postNotification();
        lastQuake = newQuake;
      }
    } catch (InterruptedException e) {
      // Restore interrupt status.
      Thread.currentThread().interrupt();
    }
  }

  public void postNotification() {
    Context applicationContext = getApplicationContext();
    Intent notificationIntent = new Intent(applicationContext, MainActivity.class);

    PendingIntent pendingIntentg = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0);
    String title = EarthQuakes.mEarthQuakeList.get(0).getMagnitude().toString();
    String text = EarthQuakes.mEarthQuakeList.get(0).getLocationDescription();
    Notification notification = new Notification.Builder(applicationContext)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(android.R.drawable.star_on)
            .setContentIntent(pendingIntentg)
            .build();
    NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
    // hide the notification after its selected
    notification.flags |= Notification.FLAG_AUTO_CANCEL;

    notificationManager.notify(0, notification);
  }
}
