package org.techtown.setgooglemaps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleAdd extends AppCompatActivity {

    int S_hour, S_min, E_hour, E_min;
    int checker, day_check, end_check;
    int mon,tue,wen,thu,fri,sat,sun;
    TextView days_view;
    TimePicker S_Time;
    TimePicker E_Time;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);

        Button repeat = findViewById(R.id.R_day_button);
        Button add_list_button = findViewById(R.id.add_list_but);
        Button back_button = findViewById(R.id.cancel_but);

        S_Time = findViewById(R.id.Start_timepick);
        E_Time = findViewById(R.id.End_timepick);
        days_view = findViewById(R.id.S_days);

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Repeatday = new Intent(ScheduleAdd.this, Dayrepeat.class);
                startActivityForResult(Repeatday,999);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addList = new Intent(ScheduleAdd.this,Schedule.class);
                startActivity(addList);

                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
            }
        });

        add_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent();
                Bundle container = new Bundle();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    S_hour = S_Time.getHour();
                    S_min = S_Time.getMinute();
                    E_hour = E_Time.getHour();
                    E_min = E_Time.getMinute();
                }
                container.putInt("start_hour",S_hour);
                container.putInt("start_min",S_min);
                container.putInt("end_hour",E_hour);
                container.putInt("end_min",E_min);
                container.putString("day_repeat",day);

                add.putExtras(container);
                setResult(54,add);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999){
            if(resultCode == 111){
                Bundle R_bundle = data.getExtras();
                day = "";
                checker = 0;
                day_check = 0;
                end_check = 0;

                if(R_bundle != null){

                    if((checker = R_bundle.getInt("day_of_1")) == 1){
                        day += "월 ";
                        day_check++;
                        mon = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_2")) == 1){
                        day += "화 ";
                        day_check++;
                        tue = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_3")) == 1){
                        day += "수 ";
                        day_check++;
                        wen = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_4")) == 1){
                        day += "목 ";
                        day_check++;
                        thu = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_5")) == 1){
                        day += "금 ";
                        day_check++;
                        fri = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_6")) == 1){
                        day += "토 ";
                        end_check++;
                        sat = 1;
                    }
                    if((checker = R_bundle.getInt("day_of_7")) == 1){
                        day += "일 ";
                        end_check++;
                        sun = 1;
                    }

                    if(day_check == 5 && end_check == 2){
                        day = "매일";
                    }

                    else if(day_check == 5 && end_check==0){
                        day = "주중";
                    }

                    else if(end_check == 2 && day_check==0){
                        day = "주말";
                    }

                    days_view.setText(day);
                }
            }
        }
    }
}