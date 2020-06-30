package org.techtown.setgooglemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public String myUid;

    ListView showlist;

    int index=0;
    //int on=1;

    String convert_time;
    String container_day;
    int s_hour,s_min,e_hour,e_min;

    ArrayAdapter<String> adapter;
    ArrayList<String> schedule_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSchedule();
        //if(on==0){getSchedule();}//다시 들어왔을 때 디비

        Button add = findViewById(R.id.btn_addList);
        Button back = findViewById(R.id.btn_back_main);
        Button delete = findViewById(R.id.btn_delList);
        showlist = findViewById(R.id.show_list);

        schedule_list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,schedule_list);

        showlist.setAdapter(adapter);
        showlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);



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

                myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference getinfo=databaseReference.child(myUid).child("시간");
                //getinfo.child("");
                //Toast.makeText(getApplicationContext(), getinfo.toString(), Toast.LENGTH_SHORT).show();

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


        User user1=new User();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(requestCode == 44){
            if(resultCode == 54){
                if(cartridge!= null) {
                    index++;

                    container_day = cartridge.getString("day_repeat");
                    s_hour = cartridge.getInt("start_hour");
                    s_min = cartridge.getInt("start_min");
                    e_hour = cartridge.getInt("end_hour");
                    e_min = cartridge.getInt("end_min");
                    convert_time = s_hour + ":" + s_min + " ~ " + e_hour + ":" + e_min + " 반복: " + container_day;


                    user1.index=index;
                    user1.startHour=s_hour;
                    user1.startMinute=s_min;
                    user1.endHour=e_hour;
                    user1.endMinute=e_min;


                    if(container_day.contains("월")){
                        user1.Mon=true;
                    }else if(container_day.contains("화")){
                        user1.Tue=true;
                    }else if(container_day.contains("수")){
                        user1.Wed=true;
                    }else if(container_day.contains("목")){
                        user1.Thur=true;
                    }else if(container_day.contains("금")){
                        user1.Fri=true;
                    }else if(container_day.contains("토")){
                        user1.Sat=true;
                    }else if(container_day.contains("일")){
                        user1.Sun=true;
                    }else if(container_day.length()==0){
                        Toast.makeText(getApplicationContext(),"값이 없습니다",Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getApplicationContext(),index,Toast.LENGTH_SHORT).show();
                    databaseReference.child(myUid).child("시간").child(String.valueOf(index)).setValue(user1);


                    schedule_list.add(0,convert_time);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        int on=0;
    }

    public void getSchedule(){
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference getinfo=databaseReference.child(myUid).child("시간");

        Toast.makeText(getApplicationContext(), getinfo.toString(), Toast.LENGTH_SHORT).show();

        getinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String SH=postSnapshot.child("startHour").getValue().toString();
                    String SM=postSnapshot.child("startMinute").getValue().toString();
                    String EH=postSnapshot.child("endHour").getValue().toString();
                    String EM=postSnapshot.child("endMinute").getValue().toString();

                    String dbday="";
                    String mon,tue,wed,thur,fri,sat,sun;
                    String Mon=postSnapshot.child("Mon").getValue().toString();
                    String Tue=postSnapshot.child("Tue").getValue().toString();
                    String Wed=postSnapshot.child("Wed").getValue().toString();
                    String Thur=postSnapshot.child("Thur").getValue().toString();
                    String Fri=postSnapshot.child("Fri").getValue().toString();
                    String Sat=postSnapshot.child("Sat").getValue().toString();
                    String Sun=postSnapshot.child("Sun").getValue().toString();


                    if(Mon=="true"){
                        mon="월";
                        dbday+=mon;
                    }if(Tue=="true"){
                        tue="화";
                        dbday+=tue;
                    }if(Wed=="true"){
                        wed="수";
                        dbday+=wed;
                    }if(Thur=="true"){
                        thur="목";
                        dbday+=thur;
                    }else if(Fri=="true"){
                        fri="금";
                        dbday+=fri;
                    }else if(Sat=="true") {
                        sat = "토";
                        dbday+=sat;
                    }else if(Sun=="true"){
                        sun="일";
                        dbday+=sun;
                    }


                    schedule_list.add(0,SH+":"+SM+" ~ "+EH+":"+EM+" , 반복: "+dbday);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}