package com.example.testcanvasv1;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.support.v7.app.ActionBarActivity;
import android.text.style.TtsSpan;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = "Main";
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private DrawSurfaceView mDrawView;
	LocationClient locMgr;
	TextView textt;
	
	private final SensorEventListener mSensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			//Log.e(TAG, "sensorChanged (" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");
			
			if (mDrawView != null) {
				mDrawView.setOffset(event.values[0]);
				//mDrawView.setMyLocation(13.7292394, 100.7772206);
				mDrawView.invalidate();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        setContentView(R.layout.activity_main);
        textt = (TextView) findViewById(R.id.textView1);
        
        boolean result = isServicesAvailable();        
        if(result) {
            // �����ͧ�� Google Play Services
        	locMgr = new LocationClient(this, mCallback, mListener);
        } else {
            // �����ͧ����� Google Play Services �Դ�������
        	finish();
        }

    }
    
    @Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		mSensorManager.registerListener(mSensorListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
	}
    
    protected void onStart(){
    	super.onStart();
    	locMgr.connect();
    }
    
    protected void onStop(){
    	super.onStop();
    	Log.e(TAG, "onStop");
    	mSensorManager.unregisterListener(mSensorListener);
    	locMgr.disconnect();
    }
    
    private ConnectionCallbacks mCallback = new ConnectionCallbacks() {
        public void onConnected(Bundle bundle) {
            // �������͡Ѻ Google Play Services ��
        	Toast.makeText(MainActivity.this, "Services connected", Toast.LENGTH_SHORT).show();

            LocationRequest mRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(0).setFastestInterval(0);

            locMgr.requestLocationUpdates(mRequest, new LocationListener() {
                public void onLocationChanged(Location location) {
                	Log.e(TAG, "Location Changed : " + location.getLatitude() + "," + location.getLongitude());
                	try{
                		//mDrawView.setMyLocation(location.getLatitude(), location.getLongitude());
                		mDrawView.me.latitude = location.getLatitude();
                		mDrawView.me.longitude = location.getLongitude();
                		mDrawView.invalidate();
                	}catch(NullPointerException e){
                		e.printStackTrace();
                		Log.e(TAG, "Null");
                		Log.e(TAG, "Description : ", e);
                		textt.setText("Error");
                	}
                }
            });
        }

        public void onDisconnected() {
            // ��ش�������͡Ѻ Google Play Services
        	Toast.makeText(MainActivity.this, "Services disconnected", Toast.LENGTH_SHORT).show();
        }
    };
    
    private OnConnectionFailedListener mListener = new OnConnectionFailedListener() {
        public void onConnectionFailed(ConnectionResult result) {
            // ������Դ�ѭ���������͡Ѻ Google Play Services �����
        	Toast.makeText(MainActivity.this, "Services connection failed", Toast.LENGTH_SHORT).show();
        }
    };
    
    private boolean isServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        return (resultCode == ConnectionResult.SUCCESS);
    }

    
    /*LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        	Log.e(TAG, "Location Changed : " + location.getLatitude() + "," + location.getLongitude());
        	try{
        		mDrawView.setMyLocation(location.getLatitude(), location.getLongitude());
        		mDrawView.invalidate();
        	}catch(NullPointerException e){
        		e.printStackTrace();
        		Log.e(TAG, "Null");
        		Log.e(TAG, "Description : ", e);
        		textt.setText("Error");
        	}
        }
    };*/
    
}
