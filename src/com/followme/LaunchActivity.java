package com.followme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ToggleButton;


public class LaunchActivity extends Activity {
	
	
	private TextView codeField,posField;
	private ToggleButton mainButton;
	private Server m_server;
	private String m_code;
	private int m_positions=0;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection    
		switch (item.getItemId()) {
		
		case R.id.menu_exit:
			final AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
	        alt_bld.setMessage(R.string.exit);
	        alt_bld.setCancelable(false);  
	    	alt_bld.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {  
	    		public void onClick(DialogInterface dialog, int id) {  
	           	 // Action for 'Yes' Button  
	           		   finish();
	               	   return;
	           	 }  

	           	 });
	      	 alt_bld.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {  
	        	 public void onClick(DialogInterface dialog, int id) {  
	        	 //  Action for 'NO' Button  
	        	 dialog.cancel();  
	        	 }  
	        	 });  

			AlertDialog alert = alt_bld.create();  
       	 	alert.setIcon(R.drawable.icon);  
       	 	alert.show();  
			return true;
		
		case R.id.menu_code: 
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			emailIntent .setType("text/html");
			emailIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.subject);	 
			emailIntent .putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(m_server.getMail()));		 
			LaunchActivity.this.startActivity(Intent.createChooser(emailIntent,""));

			return true;  
		case R.id.menu_map:    
			Intent myMapIntent = new Intent(LaunchActivity.this, WebMapActivity.class);
			myMapIntent.putExtra("CODE", m_code);
			LaunchActivity.this.startActivity(myMapIntent);

			return true;  
		default:    
			return super.onOptionsItemSelected(item);  
			}
	}
	
	
		
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        codeField = (TextView) findViewById(R.id.TVCode);
        posField = (TextView) findViewById(R.id.TVPosition);
        mainButton = (ToggleButton) findViewById(R.id.TBExit);  
        m_server = new Server();
        m_code = m_server.getCode();
        codeField.setText(getString(R.string.codeField)+" "+m_code);
        
		posField.setText(getString(R.string.posFielddeb)+" "+m_positions+" "+getString(R.string.posFieldend));
        
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, ll);
        
        
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
			if ( mainButton.isChecked())
			{
				m_server.updateLocation(location);
				m_positions +=1;
				posField.setText(getString(R.string.posFielddeb)+" "+m_positions+" "+getString(R.string.posFieldend));
			}
			
		}
        }

}