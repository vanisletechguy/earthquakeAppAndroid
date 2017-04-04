package com.mikeaubie.finalproject.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Family on 4/3/2017.
 */
public class QuakeServiceReciever extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    context.startService(new Intent(context, QuakeService.class));
  }
}