package org.techtown.setgooglemaps;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

public class MuteActivity extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        // intent로부터 전달받은 string
        String get_yout_string = intent.getExtras().getString("state");

        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, MuteService.class);

        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("state", get_yout_string);
        // start the ringtone service

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(service_intent);
        }else{
            this.context.startService(service_intent);
        }
    }
}