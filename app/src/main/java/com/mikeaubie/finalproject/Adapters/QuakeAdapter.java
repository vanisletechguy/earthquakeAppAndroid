package com.mikeaubie.finalproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikeaubie.finalproject.Models.EarthQuake;
import com.mikeaubie.finalproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Family on 3/24/2017.
 */

public class QuakeAdapter extends
        RecyclerView.Adapter<QuakeAdapter.MyViewHolder> {
  private List<EarthQuake> mData;
  private LayoutInflater mInflater;
  private Context context;

  public QuakeAdapter(List<EarthQuake> mData, Context context) {
    this.mData = mData;
    this.context = context;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.quake_item, parent, false);
    MyViewHolder holder = new MyViewHolder(view);
    return holder;
  }


  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {
    final EarthQuake currentQuake = mData.get(position);
    holder.setData(currentQuake, position);
  }

  public void swap(ArrayList<EarthQuake> newData){
    mData.clear();
    mData.addAll(newData);
    //notifyDataSetChanged();
  }

  class MyViewHolder extends RecyclerView.ViewHolder
          implements View.OnClickListener {

    TextView description, location, magnitude, date;
    int position;
    EarthQuake current;
    private View container;

    public MyViewHolder(View itemView) {
      super(itemView);
      description = (TextView) itemView.findViewById(R.id.tvDescription);
      location = (TextView) itemView.findViewById(R.id.tvCoords);
      magnitude = (TextView) itemView.findViewById(R.id.tvMagnitude);
      date = (TextView) itemView.findViewById(R.id.tvDate);
    }

    public void setData(EarthQuake currentObj, int position) {

      this.description.setText("Date: " + currentObj.getDate().toString());
      this.location.setText(currentObj.getLocation().toString());
      this.magnitude.setText("Date: " + currentObj.getDate().toString());
      this.date.setText(currentObj.getDate().toString());
      this.current = currentObj;
    }




    public void setListeners() {
    }

    @Override
    public void onClick(View v) {
    }
  }


}