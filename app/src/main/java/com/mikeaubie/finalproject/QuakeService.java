package com.mikeaubie.finalproject;

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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Family on 4/1/2017.
 */

public class QuakeService extends IntentService {

  public QuakeService() {
    super("HelloIntentService");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    while(1<2) {


      try {
        Thread.sleep(5000);
        //Toast.makeText(getApplicationContext(),"SERVICED!!!!", Toast.LENGTH_LONG).show();


        Context applicationContext = getApplicationContext();
        Intent notificationIntent = new Intent(applicationContext, MainActivity.class);

        PendingIntent pendingIntentg = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0);
        String title = "TITLE";
        String text = "TEXT";
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


      } catch (InterruptedException e) {
        // Restore interrupt status.
        Thread.currentThread().interrupt();
      }
    }
  }

  @Override
  public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();



    return super.onStartCommand(intent, flags, startId);
  }

}
