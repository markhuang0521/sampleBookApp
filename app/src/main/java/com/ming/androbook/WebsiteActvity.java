package com.ming.androbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsiteActvity extends AppCompatActivity {
    private WebView webView;
    public final static String WEBSITE_URL_KEY = "website_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_actvity);
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra(WEBSITE_URL_KEY);
            webView = findViewById(R.id.web_view);
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient());
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
