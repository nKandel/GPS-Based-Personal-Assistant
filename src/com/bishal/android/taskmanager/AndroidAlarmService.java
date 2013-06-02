package com.bishal.android.taskmanager;

import java.util.Calendar;
import android.app.Activity;     
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AndroidAlarmService extends Activity {

private PendingIntent pendingIntent;
private PendingIntent pendingIntent2;

private String feedthrough;
private boolean status;


/** Called when the activity is first created. */
@Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.maina);
 ImageButton buttonStart = (ImageButton)findViewById(R.id.startalarm);
 ImageButton buttonCancel = (ImageButton)findViewById(R.id.cancelalarm);

 buttonStart.setOnClickListener(new Button.OnClickListener(){


public void onClick(View arg0) {
   // TODO Auto-generated method stub
	 status=getIntent().getExtras().getBoolean("treatback");
	 String feedback=getIntent().getExtras().getString("feedback");
     int length=feedback.length();
     String taskOnly=feedback.substring(0,length-24);
     	char trueString[]= new char[24];
		feedback.getChars(length-24,length-1,trueString,0);

		if(trueString[0]==' ')
			feedthrough=String.valueOf(trueString,1,23);
		else
			feedthrough=String.valueOf(trueString,0,24);
		
		
		int nyear,nmonth,nday,nhour,nminute,nsecond;
		Calendar now = Calendar.getInstance();
		nyear=now.get(Calendar.YEAR);
		nmonth=now.get(Calendar.MONTH);
		
        //Toast.makeText(AndroidAlarmService.this, "Present Time="/*+nyear+"/"*/+nmonth/*+"/"+nday+"  "+nhour+":"+nminute+":"+nsecond*/, Toast.LENGTH_LONG).show();
        
		nday=now.get(Calendar.DAY_OF_MONTH);
		nhour=now.get(Calendar.HOUR_OF_DAY);
		nminute=now.get(Calendar.MINUTE);
		nsecond=now.get(Calendar.SECOND);
		now.set(nyear, nmonth, nday, nhour, nminute, nsecond);
		//String present=nyear+"/"+nmonth+"/"+nday+"    "+nhour+":"+nminute+":"+nsecond;
		
		int year,month,day,hour,minute,second;
		year=scan("year");
		month=scan("month");
		
		//Toast.makeText(AndroidAlarmService.this, "Future Time="/*+year+"/"*/+month/*+"/"+day+"  "+hour+":"+minute*/, Toast.LENGTH_LONG).show();
		
		day=scan("day");
		hour=scan("hour");
		minute=scan("minute");
		second=scan("second");
		Calendar cFuture=Calendar.getInstance();
		cFuture.set(year, month, day, hour, minute, second);
		//String future=year+"/"+month+"/"+day+"    "+hour+":"+minute+":"+second;
		
		double milliPresent,milliFuture;
		milliFuture=year*31536000.00+month*2592000.00+(day-1)*86400.00+(hour-1)*3600+(minute-1)*60;
		milliPresent=nyear*31536000.00+nmonth*2592000.00+(nday-1)*86400.00+(nhour-1)*3600+(nminute-1)*60+nsecond;
		int total_seconds=(int)(milliFuture-milliPresent);
if(total_seconds>0){
		Intent myIntent = new Intent(AndroidAlarmService.this, MyAlarmService.class);
		myIntent.putExtra("taskOnly", taskOnly);
		myIntent.putExtra("dateAlso", feedback);
		pendingIntent = PendingIntent.getService(AndroidAlarmService.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, total_seconds);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        
        Intent yourIntent = new Intent(AndroidAlarmService.this, YourAlarmService.class);
        yourIntent.putExtra("taskOnly", taskOnly);
        yourIntent.putExtra("dateAlso", feedback);
        pendingIntent2 = PendingIntent.getService(AndroidAlarmService.this, 0, yourIntent, 0);
        calendar.add(Calendar.SECOND, (int)((-0.25)*total_seconds));
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent2);                        
        Toast.makeText(AndroidAlarmService.this, "Alarm Set.", Toast.LENGTH_LONG).show();
        Toast.makeText(AndroidAlarmService.this, "You will be notified beforehand.", Toast.LENGTH_LONG).show();
        
  Intent i=new Intent(AndroidAlarmService.this,AddTaskActivity.class);
  i.putExtra("feedback",feedback);
  startActivity(i);
}
  else {
	  Intent i=new Intent(AndroidAlarmService.this,SpecifyTime.class);
	  i.putExtra("task",feedback);
	  startActivity(i);
  	}
}

private int scan(String tring) {
	// TODO Auto-generated method stub
	int fragT,offset=0;
	String inter;
	
	if(feedthrough.length()==24)
		offset=1;
	
	if(tring.contentEquals("year")){
		inter=feedthrough.substring(8, 12);
		fragT=(int) (Float.parseFloat(inter));
	}
		else if(tring.contentEquals("month")){
		
		inter=feedthrough.substring(0, 4).trim();
		if(inter.contentEquals("Jan"))
			fragT=0;
		else if(inter.contentEquals("Feb"))
			fragT=1;
		else if(inter.contentEquals("Mar"))
			fragT=2;
		else if(inter.contentEquals("Apr"))
			fragT=3;
		else if(inter.contentEquals("May"))
			fragT=4;
		else if(inter.contentEquals("Jun"))
			fragT=5;
		else if(inter.contentEquals("Jul"))
			fragT=6;
		else if(inter.contentEquals("Aug"))
			fragT=7;
		else if(inter.contentEquals("Sep"))
			fragT=8;
		else if(inter.contentEquals("Oct"))
			fragT=9;
		else if(inter.contentEquals("Nov"))
			fragT=10;
		else 
			fragT=11;
		
		//Toast.makeText(AndroidAlarmService.this, /*+year+"/"*/inter/*+"/"+day+"  "+hour+":"+minute*/, Toast.LENGTH_LONG).show();
		
	}
	
	else if(tring.contentEquals("day")){
		inter=feedthrough.substring(4, 6);
		fragT=(int) (Float.parseFloat(inter));
	} else if(tring.contentEquals("hour")){
		inter=feedthrough.substring(13,14+offset);
			if(status==true)
			fragT=(int) (Float.parseFloat(inter))+12;
			else
				fragT=(int) (Float.parseFloat(inter));
	} else if(tring.contentEquals("minute")){
		inter=feedthrough.substring(15+offset,17+offset);
		fragT=(int) (Float.parseFloat(inter));
	}else {
	//	inter=feedthrough.substring(18,20+offset);
		//fragT=(int) (Float.parseFloat(inter));
		// for conveinence
		fragT=0;
	}
	return fragT;
}

});

 buttonCancel.setOnClickListener(new Button.OnClickListener(){


   public void onClick(View arg0) {
   // TODO Auto-generated method stub
   AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
   alarmManager.cancel(pendingIntent);

        // Tell the user about what we did.
        Toast.makeText(AndroidAlarmService.this, "Cancel!", Toast.LENGTH_LONG).show();
        String feedback=getIntent().getExtras().getString("feedback");
        Intent i=new Intent(AndroidAlarmService.this,AddTaskActivity.class);
        int length=feedback.length();
        char trueString[]= new char[length-24];
		feedback.getChars(0,length-25,trueString,0);
		//detailTask=trueString.toString();
		feedback=String.valueOf(trueString, 0, length-25);
        i.putExtra("feedback",feedback);
        startActivity(i);
    }});

    }
 }
