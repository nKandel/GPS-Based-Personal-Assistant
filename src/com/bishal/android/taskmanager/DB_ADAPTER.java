package com.bishal.android.taskmanager;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DB_ADAPTER extends SQLiteOpenHelper {

	
	private static final String TAG=DB_ADAPTER.class.getSimpleName();
	public static final String DB_NAME="Store GPS.db";
	public static final int DB_VERSION=1;
	public static final String TABLE="GPS_Info";
	public static final String COLUMN_ID="Id";   //special for ID
	public static final String COLUMN_CATEGORY="Category";
	public static final String COLUMN_TITLE="Title";
	public static final String COLUMN_LATITUDE="Latitude";
	public static final String COLUMN_LONGITUDE="Longitude";
	String places;
	public double total_distance;

	
	public DB_ADAPTER(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String STORING_GPS_INFORMATION = "CREATE TABLE " + TABLE + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TITLE + " String," + COLUMN_CATEGORY + " String,"
				 + COLUMN_LATITUDE + " String," + COLUMN_LONGITUDE + " String" + ")";
		
		Log.d(TAG, "onCreate sql: "+STORING_GPS_INFORMATION );
		db.execSQL(STORING_GPS_INFORMATION);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("Drop Table If Exists " + TABLE);
		Log.d(TAG,"onUpdate dropped table "+ TABLE);
		onCreate(db);
	}
	
	void addGPS(GPS_INFO Gps){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(COLUMN_CATEGORY,Gps.getCATEGORY());
		values.put(COLUMN_TITLE,Gps.getTITLE());
		values.put(COLUMN_LATITUDE, Gps.getLATITUDE());
		values.put(COLUMN_LONGITUDE, Gps.getLONGITUDE());
		db.insert(TABLE, null, values);
		db.close();
	}
	
	public double distance(double latA,double longA,double latB,double longB){
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
	
	public boolean checkHospital(/*CURRENT_LOCATION location*/){
		return checkCategory("Hospital'");
	}
	
	private boolean checkCategory(String Category){
		Cursor c=getReadableDatabase().rawQuery("SELECT * FROM " + TABLE + " WHERE "
                + COLUMN_CATEGORY +Category+ "'" ,  null);
		SQLiteDatabase db_adapter=(SQLiteDatabase) c;
		if(c.getCount()>0){ 
			ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
			//SQLiteDatabase db_adapter=this.getReadableDatabase();
			//double Distance=distance(location.data_latitude,location.data_longitude,c.getDouble(3),c.getDouble(4));
			double Distance;		        		
	        String latitude = (c.getString(3));
	        String longitude = (c.getString(4));
	        double x,y;
	        x=Float.parseFloat(latitude);
	        y=Float.parseFloat(longitude);
	        
	        
	        double a=CURRENT_LOCATION.data_latitude;
	        double b=CURRENT_LOCATION.data_longitude;
	        
	        Distance=distance(x,y,a,b);
	        total_distance=Distance;
	        
			Cursor locationCursor = db_adapter.query
					("Gps_Info", new String[] {
		            "Title", "Category", "Latitude",
		            "Longitude" },Distance+"<="+"2.5 [0xc8]", null,
		            null, null, null);

		    locationCursor.moveToFirst();
		    do {	
		        String title = locationCursor.getString(1);		        		
		        String category = locationCursor.getString(2);		        		
		        latitude = (locationCursor.getString(3));
		        longitude = (locationCursor.getString(4));
		        x=Float.parseFloat(latitude);
		        y=Float.parseFloat(longitude);
		        items.add(new OverlayItem(new GeoPoint((int)(x*1E6),(int)(y*1E6)), title,category));
		    } while (locationCursor.moveToNext());
			if(locationCursor.getCount()>0) return true;
		}
		return false;
	}
	
	

	public boolean checkTourism(/*CURRENT_LOCATION location*/){
		return checkCategory("Tourism'");
	}
	
	public boolean checkCollege(/*CURRENT_LOCATION location*/){
		return checkCategory("College'");
	}
}
