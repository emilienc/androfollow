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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class LaunchActivity extends Activity {
	
	
	private TextView codeField;
	private ImageButton exitButton;
	private Server m_server;
	private String m_code;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection    
		switch (item.getItemId()) {
		case R.id.menu_code: 
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			 
			emailIntent .setType("plain/text");
			 
			//emailIntent .putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"webmaster@website.com"});
			 
			emailIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.subject);
			 
			emailIntent .putExtra(android.content.Intent.EXTRA_TEXT, R.string.body + m_code);
			 
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

        
        codeField = (TextView) findViewById(R.id.TVCode);
        exitButton = (ImageButton) findViewById(R.id.IBExit);
        
        exitButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
            	 AlertDialog alert = alt_bld.create();  

            	 // Title for AlertDialog  

            	 //alert.setTitle("Title");  

            	 // Icon for AlertDialog  

            	 alert.setIcon(R.drawable.icon);  

            	 alert.show();  

           }});
            	
                
     
        m_server = new Server();
        m_code = m_server.getCode();
        codeField.setText("Votre Code de suivi est : "+ m_code);
        
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
			
				m_server.updateLocation(codeField.getText().toString(),location);
			
		}
        }

}