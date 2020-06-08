package org.techtown.setgooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private View drawerView;
    Button b_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.layout_main);
        drawerView=(View)findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listner);
        drawerLayout.openDrawer(drawerView);

        b_add=drawerView.findViewById(R.id.manner_add);

        b_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent=new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
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
