package com.bishal.android.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbHelper extends SQLiteOpenHelper{

	private static final String TAG=DbHelper.class.getSimpleName();
	public static final String DB_NAME="Store Account.db";
	public static final int DB_VERSION=1;
	public static final String TABLE="Account_Info";
	public static final String COL_ID="Id";   //special for ID
	public static final String COL_NAME="Name";
	public static final String COL_EMAIL="Email";
	//public static final String COL_UNAME="Username";
	public static final String COL_PASSWORD="Password";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
				String CREATE_ACCOUNT_INFORMATION = "CREATE TABLE " + TABLE + "("
				+ COL_ID + " INTEGER PRIMARY KEY," + COL_NAME + " String,"
				 + COL_EMAIL + " String," + COL_PASSWORD + " String" + ")";
		
		Log.d(TAG, "onCreate sql: "+CREATE_ACCOUNT_INFORMATION);
		db.execSQL(CREATE_ACCOUNT_INFORMATION);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("Drop Table If Exists " + TABLE);
		Log.d(TAG,"onUpdate dropped table "+ TABLE);
		onCreate(db);
	}
	
	
	void addAccount(ACCOUNT_INFO account){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(COL_NAME,account.getNAME());
		values.put(COL_EMAIL,account.getEMAIL());
		//values.put(COL_UNAME, account.getUSERNAME());
		values.put(COL_PASSWORD, account.getPASSWORD());
		db.insert(TABLE, null, values);
		db.close();
	}
	
	
	
	public boolean validateuser(String email,String password){
		Cursor c=getReadableDatabase().rawQuery("SELECT * FROM " + TABLE + " WHERE "
                + COL_EMAIL + "='" + email +"'AND "+COL_PASSWORD+"='" + password+"'" ,  null);
		if(c.getCount()>0)
			return true;
		else
			return false;
	}
	
	public boolean validateregister(String email){
		Cursor c=getReadableDatabase().rawQuery("SELECT * FROM " + TABLE + " WHERE "
                + COL_EMAIL + "='" + email+"'" ,  null);
		if(c.getCount()>0)
			return false;
		else
			return true;
	}

	public boolean eMailValidation(String emailValue) {
		final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern=Pattern.compile(EMAIL_PATTERN);;
		Matcher matcher;
		matcher = pattern.matcher(emailValue);
		return matcher.matches();
	}

//	public int NoOfUser() {
//		Cursor c=getReadableDatabase().rawQuery("SELECT count(*) FROM " + TABLE, null);
//		int count=Integer.parseInt(c.getString(0));
//		Log.d("Msg", c.getString(0));
//		return count;
//	}
}
