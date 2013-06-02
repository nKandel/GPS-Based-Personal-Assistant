package com.bishal.android.taskmanager.tasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int VERSION = 2;
	public static final String DB_NAME  = "tasks_db.sqlite";
	public static final String TASKS_TABLE  = "tasks";
	public static final String TASK_ID = "id";
	public static final String TASK_NAME = "name";
	public static final String TASK_COMPLETE  = "complete";
	public static final String TASK_ADDRESS = "address";
	public static final String TASK_LATITUDE = "latitude";
	public static final String TASK_LONGITUDE = "longitude";
	public static final String TASK_AUTH = "whoseTask";
	
	public TasksSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropAndCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_ADDRESS + " text");
		db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_LATITUDE + " integer");
		db.execSQL("alter table " + TASKS_TABLE + " add column " + TASK_LONGITUDE + " integer");
	}

	protected void dropAndCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + TASKS_TABLE + ";");
		createTables(db);
	}
	
	protected void createTables(SQLiteDatabase db) {
		db.execSQL(
				"create table " + TASKS_TABLE +" (" +
				TASK_ID + " integer primary key autoincrement not null," +
				TASK_NAME + " text, " +
				TASK_COMPLETE + " text, " +
				TASK_ADDRESS + " text, " +
				TASK_LATITUDE + " integer, " +
				TASK_LONGITUDE + " integer " +
				TASK_AUTH + " integer " +
				");"
			);
	}
	
	protected void getTask(SQLiteDatabase db) {
		db.execSQL(
				"select table " + TASKS_TABLE +" (" +
				TASK_ID + " integer primary key autoincrement not null," +
				TASK_NAME + " text, " +
				TASK_COMPLETE + " text, " +
				TASK_ADDRESS + " text, " +
				TASK_LATITUDE + " integer, " +
				TASK_LONGITUDE + " integer " +
				");"
			);
	}
	
	public String checkTask(String Task,int is_latitude){
		Cursor c=getReadableDatabase().rawQuery("SELECT * FROM " + TASKS_TABLE + " WHERE "
                + TASK_NAME +Task+ "'" ,  null);
		// SQLiteDatabase db_adapter=(SQLiteDatabase) c;
		if(c.getCount()>0){ 
			//ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
			//SQLiteDatabase db_adapter=this.getReadableDatabase();
			//double Distance=distance(location.data_latitude,location.data_longitude,c.getDouble(3),c.getDouble(4));
			//double Distance=4;
			/* Cursor locationCursor = db_adapter.query
					("Gps_Info", new String[] {
		            "Title", "Category", "Latitude",
		            "Longitude" },Distance+"<="+"2.5 [0xc8]", null,
		            null, null, null); */

		   // locationCursor.moveToFirst();
		  //  do {	
//		        String title = locationCursor.getString(1);		        		
//		        String category = locationCursor.getString(2);		        		
//		        String latitude = (locationCursor.getString(3));
//		        String longitude = (locationCursor.getString(4));
//		        double x,y;
//		        x=Float.parseFloat(latitude);
//		        y=Float.parseFloat(longitude);
//		        items.add(new OverlayItem(new GeoPoint((int)(x*1E6),(int)(y*1E6)), title,category));
		    	
		   // } while (locationCursor.moveToNext());
			// if(locationCursor.getCount()>0) return true;
			
			String latitude = (c.getString(4));
			String longitude = (c.getString(5));
			if(is_latitude==0) return latitude;
			else if (is_latitude==1) return longitude;
			else return TASK_ADDRESS;
		}
		
		return "EMPTY";
	}
	
}
