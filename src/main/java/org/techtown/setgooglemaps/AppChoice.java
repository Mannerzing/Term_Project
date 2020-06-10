package org.techtown.setgooglemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appchoice);

        // 돌아가기 버튼
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppChoice.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);
                finish();
            }
        });
    }
}
