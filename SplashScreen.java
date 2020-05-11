package org.techtown.setgooglemaps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends Activity {
    public void onAttacherToWindow(){
        super.onAttachedToWindow();
        Window window=getWindow();
        window.setFormat((PixelFormat.RGBA_8888));
    }
    /**CALLED WHEN THE ACTIVITY IS FIRST CREATED*/
    Thread splashThread;
    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_splashscreen);
        startAnimations();
    }
    private void startAnimations(){
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim=AnimationUtils.loadAnimation(this,R.anim.translate);
        anim.reset();
        ImageView iv=(ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashThread=new Thread(){
            @Override
            public void run(){
                try{

                    int waited=0;
                    //splash screen pause time
                    while(waited<3500){
                        sleep(100);
                        waited+=100;
                    }
                    Intent intent=new Intent(SplashScreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();

                } catch (InterruptedException e){

                    //do nothing


                }finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashThread.start();

    }


}
