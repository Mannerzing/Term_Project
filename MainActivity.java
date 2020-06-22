package org.techtown.setgooglemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    //private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    private DrawerLayout drawerLayout;
    private View drawerView;
    Button b_add, b_sche,b_loginout;
    private String userId;
    private TextView hello,name,mannerzing;
    String nickname;
    Boolean isLogin=false;
    private DatabaseReference mDatabase;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = (DrawerLayout) findViewById(R.id.layout_main);
        drawerView = (View) findViewById(R.id.drawerView);
        //drawerLayout.setDrawerListener(listner);
        drawerLayout.openDrawer(drawerView);


        b_add = findViewById(R.id.manner_add);
        b_sche = findViewById(R.id.manner_sche);
        b_loginout = findViewById(R.id.manner_loginout);

        hello=findViewById(R.id.hello);
        name = findViewById(R.id.name);
        mannerzing=findViewById(R.id.mannerzing);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        //매너리스트 추가버튼
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogin){
                    Toast.makeText(getApplicationContext(),"로그인 후 이용가능합니다",Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
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




//        User user2=new User();
//        user2.name=nickname;
//
//        FirebaseDatabase.getInstance().getReference().child("mannerzing").removeValue();
//        FirebaseDatabase.getInstance().getReference().child("mannerzing").child(myUid).child("장소").setValue(user2);


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
//                Toast.makeText(this,"isLogin"+isLogin.toString(),Toast.LENGTH_SHORT).show();
            }
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
//            Toast.makeText(this,"isLogin is "+isLogin.toString(),Toast.LENGTH_SHORT).show();

        }

        //저장된 값 불러오기
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);


        //처음에는 로그인 x
        if(isLogin) { //로그인 돼있을 때
            String value1 = info.getString("hello", "");
            String value2 = info.getString("name", ""); //꺼내온다
            String value3 = info.getString("mannerzing", "");

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