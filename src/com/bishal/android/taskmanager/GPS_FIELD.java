package com.bishal.android.taskmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GPS_FIELD extends Activity {

	private EditText et_category;
	private EditText et_title;
	private EditText et_latitude;
	private EditText et_longitude;
	private DB_ADAPTER dbadapter;
	private Button hit_enter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps);
		dbadapter = new DB_ADAPTER(this);
		et_category=(EditText)findViewById(R.id.txt_category);
		et_title=(EditText)findViewById(R.id.edit_description);
		et_latitude=(EditText)findViewById(R.id.edit_latitude);
		et_longitude=(EditText)findViewById(R.id.edit_longitude);
		hit_enter=(Button)findViewById(R.id.enter);
		hit_enter.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String CategoryValue=et_category.getText().toString();
				String TitleValue=et_title.getText().toString();
				String LatValue=et_latitude.getText().toString();
				String LongValue=et_longitude.getText().toString();
				
				
				
				GPS_INFO gps=new GPS_INFO(CategoryValue,TitleValue,LatValue,LongValue);
				
				
				dbadapter.addGPS(gps);
			}
		});
	}
	
	
}
