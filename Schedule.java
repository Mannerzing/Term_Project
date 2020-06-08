package com.example.schedule_1;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    ListView showlist;

    String convert_time;
    String container_day;
    int s_hour,s_min,e_hour,e_min;

    ArrayAdapter<String> adapter;
    ArrayList<String> schedule_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button add = findViewById(R.id.btn_addList);
        Button back = findViewById(R.id.btn_back_main);
        Button delete = findViewById(R.id.btn_delList);
        showlist = findViewById(R.id.show_list);


        schedule_list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,schedule_list);

        showlist.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addList = new Intent(Schedule.this,ScheduleAdd.class);
                startActivityForResult(addList, 44);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = showlist.getCheckedItemPositions();
                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        schedule_list.remove(i) ;
                    }
                }

                // 모든 선택 상태 초기화.
                showlist.clearChoices() ;

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle cartridge = data.getExtras();

        if(requestCode == 44){
            if(resultCode == 54){
                if(cartridge!= null){
                    container_day = cartridge.getString("day_repeat");
                    if(container_day==null)
                        container_day="매일";
                    s_hour = cartridge.getInt("start_hour");
                    s_min = cartridge.getInt("start_min");
                    e_hour = cartridge.getInt("end_hour");
                    e_min = cartridge.getInt("end_min");
                    convert_time = s_hour + ":" + s_min + " ~ " + e_hour + ":" + e_min + " 반복:" +container_day ;

                    schedule_list.add(0,convert_time);
                    adapter.notifyDataSetChanged();
                }

            }
        }

    }


}