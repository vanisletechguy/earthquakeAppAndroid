package com.mikeaubie.finalproject.Adapters;

import android.app.Application;
import android.content.Context;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikeaubie.finalproject.Fragments.QuakeListFragment;
import com.mikeaubie.finalproject.Fragments.QuakeMapFragment;
import com.mikeaubie.finalproject.Fragments.WelcomeFragment;
import com.mikeaubie.finalproject.QuakeService;
import com.mikeaubie.finalproject.R;
import com.mikeaubie.finalproject.Activities.MainActivity;
import com.mikeaubie.finalproject.Fragments.NavigationDrawerFragment;
import com.mikeaubie.finalproject.Models.NavigationDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Michael Aubie on 3/17/2017.
 */

public class NavigationDrawerAdapter extends
        RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
  private List<NavigationDrawerItem> mDataList = Collections.emptyList();
  private LayoutInflater inflater;
  private Context context;

  public NavigationDrawerAdapter(Context context,
                                 List<NavigationDrawerItem> data) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.mDataList = data;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
    MyViewHolder holder = new MyViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, int position) {
    NavigationDrawerItem current = mDataList.get(position);
    holder.title.setText(current.getTitle());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        Toast.makeText(context, holder.title.getText().toString(),
//                Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity) context;
        FragmentManager fragmentManager =
                mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (holder.title.getText().toString() ==
                "Welcome Screen") {
          WelcomeFragment welcomeFragment = new WelcomeFragment();
          ft = fragmentManager.beginTransaction();
          ft.replace(R.id.flContainer, welcomeFragment);
        } else if (holder.title.getText().toString() ==
                "EarthQuake List View") {
          QuakeListFragment quakeListFragment = new QuakeListFragment();
          ft = fragmentManager.beginTransaction();
          ft.replace(R.id.flContainer, quakeListFragment);
        } else if (holder.title.getText().toString() ==
                "EarthQuake Map View") {
          QuakeMapFragment quakeMapFragment = new QuakeMapFragment();
          ft.replace(R.id.flContainer, quakeMapFragment);
        } else if (holder.title.getText().toString() ==
                "Notifications Settings") {
          ((MainActivity) context).startQuakeService();
        }




        ft.commit();
        NavigationDrawerFragment drawerFragment =
                ((MainActivity) context).drawerFragment;
        drawerFragment.mDrawerLayout.closeDrawers();
      }
    });
  }

  @Override
  public int getItemCount() {
    return mDataList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    ImageView imgIcon;

    public MyViewHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView.findViewById(R.id.title);
    }
  }
}
