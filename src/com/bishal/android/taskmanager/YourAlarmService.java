package com.bishal.android.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class YourAlarmService extends Service {

@Override
public void onCreate() {
 // TODO Auto-generated method stub
 Toast.makeText(this, "Reminder for Task!", Toast.LENGTH_LONG).show();
}

@Override
public IBinder onBind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "YourAlarmService.onBind()", Toast.LENGTH_LONG).show();
 return null;
}

@Override
public void onDestroy() {
// TODO Auto-generated method stub
super.onDestroy();
Toast.makeText(this, "YourAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
}

@Override
public void onStart(Intent intent, int startId) {
// TODO Auto-generated method stub
super.onStart(intent, startId);
String taskOnly=intent.getExtras().getString("taskOnly");
MediaPlayer mp=MediaPlayer.create(YourAlarmService.this,R.raw.notify);
mp.start();
Toast.makeText(this,taskOnly, Toast.LENGTH_LONG).show();
}

@Override
public boolean onUnbind(Intent intent) {
 // TODO Auto-generated method stub
  Toast.makeText(this, "YourAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
  return super.onUnbind(intent);
}

}