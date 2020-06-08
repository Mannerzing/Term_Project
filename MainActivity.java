package org.techtown.setgooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends AppCompatActivity
{

    private DrawerLayout drawerLayout;
    private View drawerView;
    Button b_add, b_del, b_sche, b_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.layout_main);
        drawerView=(View)findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listner);
        drawerLayout.openDrawer(drawerView);



        //매너리스트 추가버튼
        b_add = findViewById(R.id.manner_add);
        b_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });

        // 매너리스트 삭제버튼
        b_del=findViewById(R.id.manner_del);
        b_del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, ListDel.class);
                startActivity(intent);

            }
        });

        // 스케쥴 버튼
        b_sche=findViewById(R.id.manner_sche);
        b_sche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);

            }
        });


        // 진동모드 앱 선택
        b_app=findViewById(R.id.manner_choice);
        b_app.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, AppChoice.class);
                startActivity(intent);

            }
        });



    }

    DrawerLayout.DrawerListener listner = new DrawerLayout.DrawerListener() {


        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

}