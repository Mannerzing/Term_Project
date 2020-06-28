package org.techtown.setgooglemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {

    Button logout;
    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logout=findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();

                        Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LogoutActivity.this, MainActivity.class);
                        setResult(3, intent);
                        //startActivityForResult(intent,-1);

                        overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                        //finishAffinity();
                        finish();
                        break;
                }
            }
        });
    }
}