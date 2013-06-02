package com.bishal.android.taskmanager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class CURRENT_LOCATION extends MapActivity {
	
	private MapView Mapview;
	  public MapController mapController;
	  private LocationManager locationManager;
	  private LocationListener locationListener;
	  private GeoPoint myGeoPoint;
	  private DB_ADAPTER db_adapter;
	  public static double data_latitude,data_longitude;
	 
	  
	   class MapOverlay extends com.google.android.maps.Overlay {
	          @Override
	          public boolean draw(Canvas canvas, MapView Mapview, 
	          boolean shadow, long when) 
	          {
	              super.draw(canvas, Mapview, shadow);                   
	   
	              //---translate the GeoPoint to screen pixels---
	              Point screenPts = new Point();
	              Mapview.getProjection().toPixels(myGeoPoint, screenPts);
	   
	              //---add the marker---
	              Bitmap bmp = BitmapFactory.decodeResource(
	                  getResources(), R.drawable.pushpin);            
	              canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
	              return true;
	          }
	      } 
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        locationListener = new GPSLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Mapview=(MapView)findViewById(R.id.mapview);
        Mapview.setStreetView(true);
        Mapview.setBuiltInZoomControls(true);
        mapController = Mapview.getController();
        mapController.setZoom(16); 
		
	}
	
	// Initiating Menu XML file (menu.xml)
		 @Override
		 public boolean onCreateOptionsMenu(Menu menu)
		 {
		 MenuInflater menuInflater = getMenuInflater();
		 menuInflater.inflate(R.menu.menu, menu);
		 return true;
		 }
		 
	      @Override
		 public boolean onOptionsItemSelected(MenuItem item){
		
	    switch (item.getItemId())
		 {
		 case R.id.display_hospital:
			// locationListener.equals(myGeoPoint);
			 if(myGeoPoint!=null){
				 if(db_adapter.checkHospital(/*CURRENT_LOCATION.this*/))
				 Toast.makeText(CURRENT_LOCATION.this, "Nearby Hospital", Toast.LENGTH_LONG).show();
				 else
					 Toast.makeText(CURRENT_LOCATION.this, "No Hospital Nearby", Toast.LENGTH_LONG).show();
			}	
			 
			 return true;

		 case R.id.display_tourism:
				// locationListener.equals(myGeoPoint);
				 if(myGeoPoint!=null){
					 if(db_adapter.checkTourism(/*CURRENT_LOCATION.this*/))
					 Toast.makeText(CURRENT_LOCATION.this, "Nearby Tourist Areas", Toast.LENGTH_LONG).show();
					 else
						 Toast.makeText(CURRENT_LOCATION.this, "No Tourist Areas Nearby", Toast.LENGTH_LONG).show();
				}	
				 
				 return true;
		 	
		 case R.id.display_college:
			// locationListener.equals(myGeoPoint);
			 if(myGeoPoint!=null){
				 if(db_adapter.checkCollege(/*CURRENT_LOCATION.this*/))
				 Toast.makeText(CURRENT_LOCATION.this, "Nearby Colleges", Toast.LENGTH_LONG).show();
				 else
					 Toast.makeText(CURRENT_LOCATION.this, "No Colleges Nearby", Toast.LENGTH_LONG).show();
			}	
			 
			 return true;

		 default:
			 return super.onOptionsItemSelected(item);
		 }
	}
	
	
	     class GPSLocationListener implements LocationListener{

	       public void onLocationChanged(Location location) {
	        // TODO Auto-generated method stub
	    	   	/*	data_latitude=location.getLatitude();
	    	   		data_longitude=location.getLongitude();
	    	   		String coordinates[] = {""+location.getLatitude(), ""+location.getLongitude()};
	    	   		double lat = Double.parseDouble(coordinates[0]);
	    	   		double lng = Double.parseDouble(coordinates[1]);
	    	   		GeoPoint myGeoPoint = new GeoPoint((int) (lat * 1E6),(int) (lng * 1E6));
	    	   		mapController.animateTo(myGeoPoint);
	    	   		mapController.setZoom(7);
	    	   		MapOverlay mapOverlay = new MapOverlay();
	                List<Overlay> listOfOverlays = Mapview.getOverlays();
	                listOfOverlays.clear();
	                listOfOverlays.add(mapOverlay);
	                updateWithNewLocation(location);
	                Mapview.invalidate();  */
					
					
					if(location !=null)
	   	          myGeoPoint = new GeoPoint(
	   	                      (int)(location.getLatitude()*1000000),
	   	                      (int)(location.getLongitude()*1000000));
	   	                  mapController.animateTo(myGeoPoint);
	   	                  MapOverlay mapOverlay = new MapOverlay();
	   	                List<Overlay> listOfOverlays = Mapview.getOverlays();
	   	                listOfOverlays.clear();
	   	                listOfOverlays.add(mapOverlay);        
	                String address = updateWithNewLocation(myGeoPoint);
	                Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();
	                Mapview.invalidate();   
					
	      }

	      public void onProviderDisabled(String provider) {
	        // TODO Auto-generated method stub
	    	  Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
	      }

	      public void onProviderEnabled(String provider) {
	        // TODO Auto-generated method stub
	    	  Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
	      }

	      public void onStatusChanged(String provider, int status,
	          Bundle extras) {
	        // TODO Auto-generated method stub
	        
	      }
	      
	  }
	  
	     /* private void updateWithNewLocation(Location location){
			 
	 	  	double lat = location.getLatitude(); 
	 	  	double lon = location.getLongitude();       
	 		TextView myAddress = (TextView)findViewById(R.id.myaddress);
	 		 Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

	 	       try {
	 	    	   	List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
	 	 
	 	    	   	if(addresses!= null) 
	 	    	   	{
	 	    	   		Address returnedAddress = addresses.get(0);
	 	    	   		StringBuilder strReturnedAddress = new StringBuilder("Current Address:\n");
	 	    	   		for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) 
	 	    	   		{
	 	    	   			strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
	 	    	   		}
	 	    	   		myAddress.setText(strReturnedAddress.toString());
	 	    	   	}
	 	    	
	 	    	   	else{
	 	    	   			myAddress.setText("No Address returned!");
	 	    	   		}
	 	       		} 
	 	       	catch (IOException e) {
	 	       		Log.i("MyLocTAG => ", "this is the exception part");
	 	       		 e.printStackTrace();

	 	     }
	 	 }*/
		 
		 public String updateWithNewLocation(GeoPoint myGeoPoint){
	    	 String address = "";
	 		 Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

	 	       try {
	 	    	   	List<Address> addresses = geocoder.getFromLocation(myGeoPoint.getLatitudeE6()  / 1E6, 
	 	    	   		myGeoPoint.getLongitudeE6() / 1E6, 1);
	 	 
	 	    	   if (addresses.size() > 0) {
	 		            for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
	 		            	address += addresses.get(0).getAddressLine(index) + " ";
	 		        }
	 	    	
//	 	    	   	else{
//	 	    	   	myAddress.setText("No Address returned!");
//	 	    	   		}
	 	       		} 
	 	       	catch (IOException e) {
	 	       		Log.i("MyLocTAG => ", "this is the exception part");
	 	       		 e.printStackTrace();

	 	     }
	 	       return address;
	     }
	     
	     
	     
	@Override
	protected boolean isRouteDisplayed(){
		return false;
	}
}