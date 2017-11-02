package com.example.ram.chicago_monuments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ram.chicago_monuments.R.id.webView;

/**
 * Created by ram_n on 10/29/2017.
 */

public class LandmarkWebviewFragment extends Fragment{

    private static final String TAG = "LandmarkWebviewFragment";
    public static final String SAVED_OBJECT_TAG="SavedObjectTag";

    private int mCurrIdx = -1;
    private int mLandmarkUrlArrLen;
    WebView myWebView;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the webpage  in the LandmarsUrl string array at position newIndex
    void showWebPageAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mLandmarkUrlArrLen)
            return;
        mCurrIdx = newIndex;

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(MainActivity.mLandmarkUrlArray[mCurrIdx]);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in landmark_webpage_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.landmark_webpage_fragment,
                container, false);
    }

    // Set up some information about the myWebView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        myWebView=(WebView) getActivity().findViewById(webView);
        mLandmarkUrlArrLen = MainActivity.mLandmarkUrlArray.length;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putInt(SAVED_OBJECT_TAG,mCurrIdx);
    }
}