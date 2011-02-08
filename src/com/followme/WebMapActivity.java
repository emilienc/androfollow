package com.followme;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebMapActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapweb);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        String code = getIntent().getStringExtra("CODE"); 
       
        Server m_server = new Server();
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(m_server.getHost()+"/lieus/check?code="+code);
    }
}
