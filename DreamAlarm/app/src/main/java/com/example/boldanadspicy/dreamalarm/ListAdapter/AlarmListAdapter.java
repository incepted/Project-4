package com.example.boldanadspicy.dreamalarm.ListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boldanadspicy.dreamalarm.Objects.AlarmObj;
import com.example.boldanadspicy.dreamalarm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private ArrayList<AlarmObj> mAlarms;
    private Context mContext;
    private int mXDimen, mYDimen;

    public AlarmListAdapter(ArrayList<AlarmObj> mAlarms, Context mContext, int mXDimen, int mYDimen) {
        this.mAlarms = mAlarms;
        this.mContext = mContext;
        this.mXDimen = mXDimen;
        this.mYDimen = mYDimen;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView charImageView;
        TextView timeTextView, ampmTextView,
                sundayTextView, mondayTextView, tuesdayTextView,wednesTextView,
                thursTextView,friTextView,saturTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            charImageView = (ImageView) itemView.findViewById(R.id.alarm_list_char_image_view);
            timeTextView = (TextView) itemView.findViewById(R.id.alarm_list_time_text_view);
            ampmTextView = (TextView) itemView.findViewById(R.id.alarm_list_ampm_text_view);

            sundayTextView = (TextView) itemView.findViewById(R.id.alarm_list_sun_text_view);
            mondayTextView = (TextView) itemView.findViewById(R.id.alarm_list_mon_text_view);
            tuesdayTextView = (TextView) itemView.findViewById(R.id.alarm_list_tue_text_view);
            wednesTextView = (TextView) itemView.findViewById(R.id.alarm_list_wed_text_view);
            thursTextView = (TextView) itemView.findViewById(R.id.alarm_list_thurs_text_view);
            friTextView = (TextView) itemView.findViewById(R.id.alarm_list_fri_text_view);
            saturTextView = (TextView) itemView.findViewById(R.id.alarm_list_sat_text_view);
        }
    }


    public AlarmListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmListAdapter.ViewHolder holder, int position) {

        AlarmObj alarm = mAlarms.get(position);

        Picasso.with(mContext)
                .load(R.drawable.kotori_icon)
                .into(holder.charImageView);

        holder.timeTextView.setText(alarm.getmTriggerTimeHour()+":"+alarm.getmTriggerTimeMinute());
        holder.ampmTextView.setText(alarm.getmTriggerTimeAmPm());


        if(mAlarms.get(position).isSunday()){holder.sundayTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isMonday()){holder.mondayTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isTuesday()){holder.tuesdayTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isWednesday()){holder.wednesTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isThursday()){holder.thursTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isFriday()){holder.friTextView.setTextColor(Color.parseColor("#4caf50"));}
        if(mAlarms.get(position).isSaturday()){holder.saturTextView.setTextColor(Color.parseColor("#4caf50"));}

    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }
}
