package com.bishal.android.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GPA_HOME extends Activity {
   

	ImageButton location,create_account,login;
	Intent myintent;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        location=(ImageButton)findViewById(R.id.location_button);
        	location.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myintent=new Intent(GPA_HOME.this,CURRENT_LOCATION.class);
					startActivity(myintent);
				}
			});
       
        	create_account=(ImageButton)findViewById(R.id.signup_button);
        	create_account.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(GPA_HOME.this,SIGNUP.class);
				startActivity(intent);
			}
		});
        login=(ImageButton)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(GPA_HOME.this,LOGIN.class);
				startActivity(intent);
			}
		});
        
//        Button gps_login = (Button)findViewById(R.id.bhutan);
//        gps_login.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(GPA_HOME.this,GPS_FIELD.class);
//				startActivity(intent);
//			}
//		});
    }
}