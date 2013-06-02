package com.bishal.android.taskmanager;

//import com.bishal.android.taskmanager.tasks.TasksSQLiteOpenHelper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyAlarmService extends Service {
	
//	private TasksSQLiteOpenHelper db_adapter;
	
	boolean too_near=false;
	double lat_current,long_current,lat_destination,long_destination;
	boolean create=true;

@Override
public void onCreate() {
 // TODO Auto-generated method stub
 if(create)
 Toast.makeText(this, "Deadline Crossed for Task!!!", Toast.LENGTH_LONG).show();
}

@Override
public IBinder onBind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
 return null;
}

@Override
public void onDestroy() {
// TODO Auto-generated method stub
super.onDestroy();
Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
}

@Override
public void onStart(Intent intent, int startId) {
// TODO Auto-generated method stub
	super.onStart(intent, startId);
	String taskOnly=intent.getExtras().getString("taskOnly");
	/* String taskOnly=intent.getExtras().getString("taskOnly");
	lat_current=CURRENT_LOCATION.data_latitude;
	long_current=CURRENT_LOCATION.data_longitude;
	String dateAlso=intent.getExtras().getString("dateAlso");
	if(!db_adapter.checkTask(dateAlso,0).contentEquals("EMPTY")){
	lat_destination=(double)(Float.parseFloat(db_adapter.checkTask(dateAlso,0)));
	long_destination=(double)(Float.parseFloat(db_adapter.checkTask(dateAlso,1)));
	double distance_uncovered=distance(lat_current, long_current, lat_destination, long_destination);
	// DB_ADAPTER db;
	if(distance_uncovered>0.5){
	MediaPlayer mp=MediaPlayer.create(MyAlarmService.this,R.raw.alarm);
	mp.start();
	Toast.makeText(this,taskOnly,Toast.LENGTH_LONG).show();
	Toast.makeText(this,"Are you near"+db_adapter.checkTask(dateAlso, -1),Toast.LENGTH_LONG).show();
	}
  }else
  {
	  MediaPlayer mp=MediaPlayer.create(MyAlarmService.this,R.raw.alarm);
		mp.start();
		Toast.makeText(this,taskOnly,Toast.LENGTH_LONG).show();
		Toast.makeText(this,"Are you near"+db_adapter.checkTask(dateAlso, -1),Toast.LENGTH_LONG).show();
  }
	*/
	MediaPlayer mp=MediaPlayer.create(MyAlarmService.this,R.raw.alarm);
	mp.start();
	Toast.makeText(this,taskOnly,Toast.LENGTH_LONG).show();
	//Toast.makeText(this,"Are you near"+db_adapter.checkTask(dateAlso, -1),Toast.LENGTH_LONG).show(); 
	
}

/* private double distance(double latA, double longA,
		double latB, double longB) {
	// TODO Auto-generated method stub
	double d2r = Math.PI / 180;



    double dlong = (longA - longB) * d2r;
    double dlat = (latA - latB) * d2r;
    double a = Math.pow(Math.sin(dlat / 2.0), 2)
            + Math.cos(latB * d2r) * Math.cos(latA * d2r)
            * Math.pow(Math.sin(dlong / 2.0), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double d = 6367 * c;

    return d;
}
*/

@Override
public boolean onUnbind(Intent intent) {
 // TODO Auto-generated method stub
  Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
  return super.onUnbind(intent);
}

}