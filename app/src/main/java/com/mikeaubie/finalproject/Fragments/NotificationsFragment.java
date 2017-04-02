package com.mikeaubie.finalproject.Fragments;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mikeaubie.finalproject.Activities.MainActivity;
import com.mikeaubie.finalproject.R;
import com.mikeaubie.finalproject.Services.QuakeService;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

  EditText magAlertSize;
  EditText proximityAlertSize;
  EditText refreshRate;
  Button makeAlertButton;

  public NotificationsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_notifications, container, false);


    magAlertSize = (EditText) view.findViewById(R.id.etAlertMagnitude);
    proximityAlertSize = (EditText) view.findViewById(R.id.etAlertProximity);
    refreshRate = (EditText) view.findViewById(R.id.etAlertMinutes);
    makeAlertButton = (Button) view.findViewById(R.id.alertBtn);
    makeAlertButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        double mag = Double.parseDouble(magAlertSize.getText().toString());
        int prox = Integer.parseInt(proximityAlertSize.getText().toString());
        int refresh = Integer.parseInt(refreshRate.getText().toString());
        startQuakeService(mag, prox, refresh);
      }
    });
    return view;
  }

  public void startQuakeService(double magnitude, int proximity, int refresh) {
    MainActivity mainActivity = (MainActivity) getActivity();
    mainActivity.startQuakeService(magnitude, proximity, refresh);
  }
}
