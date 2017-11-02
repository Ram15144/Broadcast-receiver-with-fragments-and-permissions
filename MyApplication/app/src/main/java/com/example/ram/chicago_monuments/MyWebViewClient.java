package com.example.ram.chicago_monuments;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ram_n on 10/31/2017.
 */

public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;
        }
}
