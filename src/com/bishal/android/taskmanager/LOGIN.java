package com.bishal.android.taskmanager;

import com.bishal.android.taskmanager.ViewTasksActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LOGIN extends Activity {

	 EditText et_email;
	 EditText et_password;
	 AlertDialogManager alert = new AlertDialogManager();
	private Button hit_login;
	private DbHelper Dbinformer;
	//Toast toast;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Dbinformer =new DbHelper(this);
		et_email=(EditText)findViewById(R.id.edit_login_email);
		et_password=(EditText)findViewById(R.id.edit_login_password);
		hit_login=(Button)findViewById(R.id.login);
		hit_login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String emailValue=et_email.getText().toString();
				String passwordValue=et_password.getText().toString();
//				public boolean isEmailValid(String emailValue)
//				{
//			         String regExpn =
//			             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
//			                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//			                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//			                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//			                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
//			                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
//
//			     CharSequence inputStr = emailValue;
//			     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
//			      Matcher matcher = pattern.matcher(inputStr);
//
//			     if(matcher.matches())
//			        return true;
//			     else
//			    	 alert.showAlertDialog(LOGIN.this, "Login failed..", "Invalid Email Address", false);
//			        return false;
//			}
				
				if(emailValue.trim().length() > 0 && passwordValue.trim().length() > 0){
					if(Dbinformer.validateuser(emailValue, passwordValue)){
					Intent intent=new Intent(LOGIN.this,ViewTasksActivity.class);
					startActivity(intent);
				}
			else{
//				Toast.makeText(getApplicationContext(),"Wrong username/password",Toast.LENGTH_SHORT).show();
//				Intent intent=getIntent();
//				finish();
//				startActivity(intent);
				alert.showAlertDialog(LOGIN.this, "Login failed..", "Email/Password is incorrect", false);
			}
				}
				else{
                    alert.showAlertDialog(LOGIN.this, "Login failed..", "Please enter empty fields", false);
			
			}
			
			}
		});
		
		
	
	}

}
