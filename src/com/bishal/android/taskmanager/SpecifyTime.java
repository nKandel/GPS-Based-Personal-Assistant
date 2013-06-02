package com.bishal.android.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;


public class SpecifyTime extends Activity {
	DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
	TextView lblDateAndTime;
	String dateTime;
	Calendar myCalendar = Calendar.getInstance();
	boolean is_pm=false;
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
	myCalendar.set(Calendar.YEAR, year);
	myCalendar.set(Calendar.MONTH, monthOfYear);
	myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	updateLabel(0);
	}
};

	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		if(hourOfDay>=12)
			is_pm=true;
		myCalendar.set(Calendar.MINUTE, minute);
		updateLabel(1);
		}
	};

	private void updateLabel(int x) {
		dateTime  =fmtDateAndTime.format(myCalendar.getTime());
		lblDateAndTime.setText(dateTime);
		if(x==1){
			String detailTask=getIntent().getExtras().getString("task");
			int length=detailTask.length();
			char tempString[]= new char[length];
			detailTask.getChars(0,length-1,tempString,0);
			boolean time=false;
			if(length>=8){
			if(tempString[length-8]=='0' || tempString[length-8]=='1'|| tempString[length-8]=='2'|| tempString[length-8]=='3'|| tempString[length-8]=='4'|| tempString[length-8]=='5')
				time=true;
			}
			if(length>=2){
			if((tempString[length-2]=='A'||tempString[length-2]=='P') && time){
				char trueString[]= new char[length-24];
				detailTask.getChars(0,length-25,trueString,0);
				//detailTask=trueString.toString();
				detailTask=String.valueOf(trueString, 0, length-24);
			}
			}
			detailTask=detailTask+"  "+dateTime;
			Intent i=new Intent(SpecifyTime.this,AndroidAlarmService.class);
			i.putExtra("treatback", is_pm);
			i.putExtra("feedback",detailTask);
			startActivity(i);
		}
	}
	

	@Override
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.add_time_for_task);
	lblDateAndTime = (TextView) findViewById(R.id.lblDateAndTime);
	ImageButton btnDate = (ImageButton) findViewById(R.id.btnDate);
	btnDate.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			new DatePickerDialog(SpecifyTime.this, d, myCalendar
					.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
					myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		}
	});

	ImageButton btnTime = (ImageButton) findViewById(R.id.btnTime);
	btnTime.setOnClickListener(new View.OnClickListener() {
		public  void onClick(View v) {
			new TimePickerDialog(SpecifyTime.this, t, myCalendar
					.get(Calendar.HOUR_OF_DAY), myCalendar
					.get(Calendar.MINUTE), true).show();
		}
	});
	
	 updateLabel(0);
	}// onCreate
} // class