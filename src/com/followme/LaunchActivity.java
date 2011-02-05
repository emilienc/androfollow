package com.followme;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;


public class LaunchActivity extends Activity {
	
	
	private TextView codeField;
	private ToggleButton activateButton;
	private Server m_server;
	
		
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        codeField = (TextView) findViewById(R.id.TVCode);
        activateButton = (ToggleButton) findViewById(R.id.TBActivate);
     
        m_server = new Server();
        codeField.setText(m_server.getCode());
        
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    }
    
    private class mylocationlistener implements LocationListener {
              
        
        public void onProviderDisabled(String provider) {
        }
        
        public void onProviderEnabled(String provider) {
        }
        
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
		
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (activateButton.isChecked()){
				m_server.updateLocation(codeField.getText().toString(),location);
			}
		}
        }

}