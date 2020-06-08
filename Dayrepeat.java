package com.example.schedule_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Dayrepeat extends AppCompatActivity {

    CheckBox C_mon;
    CheckBox C_tue;
    CheckBox C_wen;
    CheckBox C_thu;
    CheckBox C_fri;
    CheckBox C_sat;
    CheckBox C_sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayrepeat);

        Button back = findViewById(R.id.B_button);

        C_mon = findViewById(R.id.C_mon);
        C_tue = findViewById(R.id.C_tue);
        C_wen = findViewById(R.id.C_wen);
        C_thu = findViewById(R.id.C_thu);
        C_fri = findViewById(R.id.C_fri);
        C_sat = findViewById(R.id.C_sat);
        C_sun = findViewById(R.id.C_sun);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_back = new Intent();
                Bundle day_data = new Bundle();

                if (C_mon.isChecked()){
                    int day1 = 1;
                    day_data.putInt("day_of_1",day1);
                }
                if (C_tue.isChecked()){
                    int day2 = 1;
                    day_data.putInt("day_of_2",day2);
                }
                if (C_wen.isChecked()){
                    int day3 = 1;
                    day_data.putInt("day_of_3",day3);
                }
                if (C_thu.isChecked()){
                    int day4 = 1;
                    day_data.putInt("day_of_4",day4);
                }
                if (C_fri.isChecked()){
                    int day5 = 1;
                    day_data.putInt("day_of_5",day5);
                }
                if (C_sat.isChecked()){
                    int day6 = 1;
                    day_data.putInt("day_of_6",day6);
                }
                if (C_sun.isChecked()){
                    int day7 = 1;
                    day_data.putInt("day_of_7",day7);
                }

                go_back.putExtras(day_data);
                setResult(111, go_back);
                finish();
            }
        });
    }
}