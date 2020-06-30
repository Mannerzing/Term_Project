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

public class Mannerlist extends AppCompatActivity {

    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public String myUid;

    ListView showlist;

    int index=0;
    int on=0;

    String convert_time;
    String container_day;
    int s_hour,s_min,e_hour,e_min;

    ArrayAdapter<String> adapter;
    ArrayList<String> schedule_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mannerlist);

        if(on==0){getList();}//다시 들어왔을 때 디비

        Button back = findViewById(R.id.btn_back_main);
        showlist = findViewById(R.id.show_list);

        schedule_list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,schedule_list);

        showlist.setAdapter(adapter);
        showlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addList = new Intent(Mannerlist.this,MainActivity.class);
                startActivityForResult(addList, 44);

                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        int on=0;
    }

    public void getList(){
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference getlocation=databaseReference.child(myUid).child("장소");

        Toast.makeText(getApplicationContext(), getlocation.toString(), Toast.LENGTH_SHORT).show();

        getlocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String location=postSnapshot.child("location").getValue().toString();
                    //String name=postSnapshot.child("latitude").getValue().toString();
//                    String SM=postSnapshot.child("startMinute").getValue().toString();
//                    String EH=postSnapshot.child("endHour").getValue().toString();
//                    String EM=postSnapshot.child("endMinute").getValue().toString();
//
//                    String dbday="";
//                    String mon,tue,wed,thur,fri,sat,sun;
//                    String Mon=postSnapshot.child("Mon").getValue().toString();
//                    String Tue=postSnapshot.child("Tue").getValue().toString();
//                    String Wed=postSnapshot.child("Wed").getValue().toString();
//                    String Thur=postSnapshot.child("Thur").getValue().toString();
//                    String Fri=postSnapshot.child("Fri").getValue().toString();
//                    String Sat=postSnapshot.child("Sat").getValue().toString();
//                    String Sun=postSnapshot.child("Sun").getValue().toString();
//
//
//                    if(Mon=="true"){
//                        mon="월";
//                        dbday+=mon;
//                    }if(Tue=="true"){
//                        tue="화";
//                        dbday+=tue;
//                    }if(Wed=="true"){
//                        wed="수";
//                        dbday+=wed;
//                    }if(Thur=="true"){
//                        thur="목";
//                        dbday+=thur;
//                    }else if(Fri=="true"){
//                        fri="금";
//                        dbday+=fri;
//                    }else if(Sat=="true") {
//                        sat = "토";
//                        dbday+=sat;
//                    }else if(Sun=="true"){
//                        sun="일";
//                        dbday+=sun;
//                    }


                    schedule_list.add(0,location);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}