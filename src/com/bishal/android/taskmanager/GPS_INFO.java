package com.bishal.android.taskmanager;

public class GPS_INFO  {

		int _id;
	  String _category;
	 String _title;
	 String _latitude;
	 String _longitue;
	 
	 public GPS_INFO(){
			
		}
	 
	 public GPS_INFO(String Category,String Title,String Latitude,String Longitude){
			
			
			this._category=Category;
			this._title=Title;
			this._latitude=Latitude;
			this._longitue=Longitude;
		}
	 
	 public String getCATEGORY(){
			return this._category;
		}
	 public String getTITLE(){
			return this._title;
		}
	 public String getLATITUDE(){
		 	return this._latitude;
	 }
	 public String getLONGITUDE(){
		 	return this._longitue;
	 }
}
