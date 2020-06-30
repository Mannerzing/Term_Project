package org.techtown.setgooglemaps;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public String myUid;
    public String myUid_bundle;

    private DrawerLayout drawerLayout;
    private View drawerView;
    Button b_add, b_sche,b_loginout,b_list;
    private TextView hello,name,mannerzing;
    String nickname;
    Boolean isLogin=false;
    private DatabaseReference mDatabase;
    int i=0;

    private GoogleMap mMap;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001;


    AlarmManager alarm_manager;
    Context context;
    PendingIntent pendingIntent;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        // 알람매니저 설정
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Calendar 객체 생성
        final Calendar calendar = Calendar.getInstance();

        // 알람리시버 intent 생성
        Intent my_intent = new Intent(this.context, MuteActivity.class);
        // calendar에 시간 셋팅
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE,20);
        calendar.set(Calendar.DAY_OF_WEEK,3);

        my_intent.putExtra("state","alarm on");

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 알람셋팅
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

        drawerLayout = (DrawerLayout) findViewById(R.id.layout_main);
        drawerView = (View) findViewById(R.id.drawerView);
        //drawerLayout.setDrawerListener(listner);
        drawerLayout.openDrawer(drawerView);

        b_add = findViewById(R.id.manner_add);
        b_sche = findViewById(R.id.manner_sche);
        b_loginout = findViewById(R.id.manner_loginout);
        b_list=findViewById(R.id.mannerlist);

        hello=findViewById(R.id.hello);
        name = findViewById(R.id.name);
        mannerzing=findViewById(R.id.mannerzing);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        permissionCheck();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //매너리스트 추가버튼
        b_add.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if(!isLogin){
                    Toast.makeText(getApplicationContext(),"로그인 후 이용가능합니다",Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra("UID",myUid_bundle);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                }
            }
        });


        // 스케쥴 버튼
        b_sche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogin){
                    Toast.makeText(getApplicationContext(),"로그인 후 이용가능합니다",Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(MainActivity.this, Schedule.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                }
            }
        });


        b_loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_loginout.getText().toString().equals("로그아웃")){//사용자가 로그인해서 텍스트가 로그아웃으로 바뀔 때
                    Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                    startActivityForResult(intent,1);

                    overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                }
                else if(b_loginout.getText().toString().equals("로그인")){ //로그인
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent,1);
                    isLogin=true;
                    overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                }
            }
        });

        b_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogin){
                    Toast.makeText(getApplicationContext(),"로그인 후 이용가능합니다",Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(MainActivity.this, Mannerlist.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                }
            }
        });

        //mDatabase.child("mannerzing").removeValue();
//        mDatabase.child("mannerzing").child(myUid).child("장소").setValue(user2);

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

    public void permissionCheck() {
        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "위치 정보 사용을 위해 ACCESS_FINE_LOCATION 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                Toast.makeText(this, "위치 정보 사용을 위해 ACCESS_FINE_LOCATION 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onMapSearch(View view) {
        String addressString=null;
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = "가천대";
        List<Address> addressList = null;


        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this, Locale.KOREAN);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                if(addressList.size()>0){
                    addressString=addressList.get(0).toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(addressString);
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Seoul and move the camera
        LatLng seoul = new LatLng(37.494870,126.960763);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Korea, Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    { switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "승인이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "승인을 받아주세요.", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode==2){ //로그인 갔다가 돌아오는
                isLogin=data.getBooleanExtra("isLogin",true);
                if(isLogin){ //로그인 됐을 때
                    i=0;
                    Bundle bundle2 = data.getExtras();
                    nickname = bundle2.getString("nickname");

                    //Intent intent=getIntent();
                    //String nickname=intent.getStringExtra("nickname"); //닉네임 전달받음

                    hello.setText("반갑습니다");
                    hello.setTextSize(18);
                    name.setText(nickname + "님");
                    mannerzing.setText("오늘도 매너Zing과 함께, 매너 챙겨요 ;)");
                    b_loginout.setText("로그아웃");

                    myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Toast.makeText(getApplicationContext(), myUid, Toast.LENGTH_SHORT).show();

                    SharedPreferences uid=getSharedPreferences("uid", MODE_PRIVATE);
                    SharedPreferences.Editor editor_uid = uid.edit();
                    editor_uid.putString("uid",myUid);
                    editor_uid.commit();

                    //저장
                    String pre_hello=hello.getText().toString(); //'반갑습니다'
                    String pre_name=name.getText().toString(); //닉네임
                    String pre_mannerzing=mannerzing.getText().toString(); //'오늘도~'

                    SharedPreferences info=getSharedPreferences("info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = info.edit();

//                    Toast.makeText(getApplicationContext(), pre_hello+"1", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), pre_name+"2", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), pre_mannerzing+"3", Toast.LENGTH_SHORT).show();

                    editor.putString("hello",pre_hello);
                    editor.putString("name",pre_name);
                    editor.putString("mannerzing",pre_mannerzing);
                    editor.commit(); //save완료
                }
            }else if(resultCode==3){//로그아웃 됐을 때
                //isLogin=false;
                i=1;
                Toast.makeText(this,"isLogin"+isLogin.toString(),Toast.LENGTH_SHORT).show();
            }
        }

        //데이터 받아오는곳 (bundle로)
        if(isLogin){
            mDatabase.child(myUid).child("장소").child("가천대").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if(dataSnapshot.getValue(User.class) != null){
                        User post = dataSnapshot.getValue(User.class);
                        Log.w("FireBaseData", "getData" + post.toString());
                    } else {
                        Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences login=getSharedPreferences("login", MODE_PRIVATE);
        //저장된 로그인 값
        Boolean loginfo=login.getBoolean("logintype",false);


//        Toast.makeText(this,"loginfo is = "+loginfo.toString(),Toast.LENGTH_SHORT).show();

        if(i==0){//로그아웃 안됐을 때
            isLogin=loginfo; //저장된 로그인 값 가져오기 (초기에는 false)
        }else if(i==1){//로그아웃하고 왔을때 i=1
            loginfo=false;
            isLogin=loginfo;
            Toast.makeText(this,"isLogin is "+isLogin.toString(),Toast.LENGTH_SHORT).show();

        }

        //저장된 값 불러오기
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences uid = getSharedPreferences("uid", MODE_PRIVATE);


        //처음에는 로그인 x
        if(isLogin) { //로그인 돼있을 때
            String value1 = info.getString("hello", "");
            String value2 = info.getString("name", ""); //꺼내온다
            String value3 = info.getString("mannerzing", "");
            myUid_bundle=uid.getString("uid","");
            Toast.makeText(getApplicationContext(),myUid_bundle,Toast.LENGTH_SHORT).show();

            //이용을 위해 로그인을 해주세요
//            Toast.makeText(this,value1.toString(),Toast.LENGTH_SHORT).show();
            hello.setText(value1);
            hello.setTextSize(18);
            name.setText(value2); //name에 다시 넣어줌
            mannerzing.setText(value3);
            b_loginout.setText("로그아웃");

        }else{ //로그인 안돼있을 때
            hello.setText("이용을 위해 \n로그인을 해주세요");
            hello.setTextSize(30);
            name.setText("");
            mannerzing.setText("");
            b_loginout.setText("로그인");
//            Toast.makeText(getApplicationContext(),name.getText(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() { //액티비티 벗어났을 때 (저장)
        super.onStop();

        if(isLogin){//로그인 정보 저장
            Boolean Login=isLogin.booleanValue();

            SharedPreferences login=getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = login.edit();

//            Toast.makeText(getApplicationContext(), Login.toString(), Toast.LENGTH_SHORT).show();

            editor1.putBoolean("logintype",Login);
            editor1.commit(); //save완료
        }
    }
}