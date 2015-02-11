package com.example.testcanvasv1;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class LocationUtils {
	
	private static final String TAG = "LocationUtils";
	
	public static void init(Context ctx, LocationClient locMgr){
		LocationListener locationListener = new LocationListener() {
	        public void onLocationChanged(Location location) {
	        	Log.e(TAG, "LocationChange");
	        }
		};

	}
	
}
