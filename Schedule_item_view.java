package org.techtown.setgooglemaps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Schedule_item_view extends LinearLayout {

    TextView timeview;
    TextView repeatview;
    Switch active_check;

    public Schedule_item_view(Context context){
        super(context);
        init(context);
    }
    public Schedule_item_view(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_item,this,true);

        timeview = findViewById(R.id.show_time);
        repeatview = findViewById(R.id.show_repeat);
        active_check = findViewById(R.id.sth_active);

        active_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    public void setTimeview(String time){
        timeview.setText(time);
    }

    public void setRepeatview(String repeat){
        repeatview.setText(repeat);
    }
}