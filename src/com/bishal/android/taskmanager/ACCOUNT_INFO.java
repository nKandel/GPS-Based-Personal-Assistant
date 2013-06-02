package com.bishal.android.taskmanager;
public class ACCOUNT_INFO {

	 int _id;
	 String _name;
	 String _email;
	// String _username;
	 String _password;
	
	public ACCOUNT_INFO(){
		
	}
	
	public ACCOUNT_INFO(String Name,String Email,String Password){
		
		//this._id=Id;
		this._name=Name;
		this._email=Email;
		//this._username=Username;
		this._password=Password;
	}
	
	
	
	public String getNAME(){
		return this._name;
	}
	
//	public void setFNAME(String Name){
//		this._name=Name;
//	}
	
	public String getEMAIL(){
		return this._email;
	}
	
//	public void setEMAIL(String Email){
//		this._email=Email;
//	}
	
//	public String getUSERNAME(){
//		return this._username;
//	}
	
//	public void setUSERNAME(String Username){
//		this._username=Username;
//	}
	
	public String getPASSWORD(){
		return this._password;
	}
	
//	public void setPASSWORD(String Password){
//		this._password=Password;
//	}
}
