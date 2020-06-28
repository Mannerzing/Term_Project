package org.techtown.setgooglemaps;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public String myUid;

    ListView showlist;

    int index=0;

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
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,schedule_list);

        showlist.setAdapter(adapter);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addList = new Intent(Schedule.this,ScheduleAdd.class);
                startActivityForResult(addList, 44);

                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addList = new Intent(Schedule.this,MainActivity.class);
                startActivityForResult(addList, 44);

                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
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

        index++;

        if(requestCode == 44){
            if(resultCode == 54){
                if(cartridge!= null){
                    container_day = cartridge.getString("day_repeat");
                    s_hour = cartridge.getInt("start_hour");
                    s_min = cartridge.getInt("start_min");
                    e_hour = cartridge.getInt("end_hour");
                    e_min = cartridge.getInt("end_min");
                    convert_time = s_hour + ":" + s_min + " ~ " + e_hour + ":" + e_min + " 반복:" +container_day ;


                    User user1=new User();
                    user1.startHour=s_hour;
                    user1.startMinute=s_min;
                    user1.endHour=e_hour;
                    user1.endMinute=e_min;


                    if(container_day.contains("월")){
                        user1.Mon=true;
                    }if(container_day.contains("화")){
                        user1.Tue=true;
                    }if(container_day.contains("수")){
                        user1.Wed=true;
                    }if(container_day.contains("목")){
                        user1.Thur=true;
                    }if(container_day.contains("금")){
                        user1.Fri=true;
                    }if(container_day.contains("토")){
                        user1.Sat=true;
                    }if(container_day.contains("일")){
                        user1.Sun=true;
                    }



                    myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference.child(myUid).child("시간").child(String.valueOf(index)+"번째 스케쥴").setValue(user1);

                    schedule_list.add(0,convert_time);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

//    class ScheduleAdapter extends BaseAdapter {
//        ArrayList<Schedule_item> items = new ArrayList<Schedule_item>();
//
//        public int getCount(){
//            return items.size();
//        }
//
//        public void addItem(Schedule_item item){
//            items.add(item);
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items.get(position);
//        }
//
//        public long getItemId(int position){
//            return position;
//        }
//
//        public View getView(int position, View view, ViewGroup viewGroup){
//            Schedule_item_view itemView = new Schedule_item_view(getApplicationContext());
//
//            Schedule_item item = items.get(position);
//            itemView.setTimeview(item.getTime());
//            itemView.setRepeatview(item.getDays());
//
//            return itemView;
//        }
//    }
}
