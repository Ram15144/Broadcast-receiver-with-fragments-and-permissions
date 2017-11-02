package com.example.ram.chicago_monuments;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ram.chicago_monuments.LandmarkNameFragment.ListSelectionListener;

import static com.example.ram.chicago_monuments.R.id.webView;

public class MainActivity extends AppCompatActivity implements ListSelectionListener {

    public static String[] mTitleArray;
    public static String[] mLandmarkUrlArray;

    public final LandmarkWebviewFragment mLwebviewFragment = new LandmarkWebviewFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mLnameLayout, mLWebviewLayout;

    // Variables to hold the permission results
    public static final int MY_PERMISSIONS_INTERNET=0;
    public static final int MY_PERMISSIONS_INTERNET_ACCESS=1;
    public static final int MY_PERMISSIONS_SHOW_GALLERY=2;

    public static final String SHOW_LANDMARK_GALLERY="com.example.ram.landscapegallery.showGallery";

    //Information needed for the Broadcast Receiver
    private BroadcastReceiver mReceiver ;
    private IntentFilter mFilter ;
    private static final String SHOW_GALLERY_ACTION = "ShowLandmarkGallery";


    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up custom toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        // Get the string arrays with the titles and qutoes
        mTitleArray = getResources().getStringArray(R.array.Titles);
        mLandmarkUrlArray = getResources().getStringArray(R.array.LandmarsUrl);

        // Get references to the LandmarkNameFragment and to the LandmarkWebviewFragment
        mLnameLayout = (FrameLayout) findViewById(R.id.Lname_fragment_container);
        mLWebviewLayout = (FrameLayout) findViewById(R.id.Lwebview_fragment_container);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the TitleFragment to the layout
        // Changed add() to replace() to avoid overlapping fragments
        if(savedInstanceState==null)
                fragmentTransaction.replace(R.id.Lname_fragment_container,
                new LandmarkNameFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {

        // Determine whether the LandmarkWebviewFragment has been added
        if (!mLwebviewFragment.isAdded()) {

            // Make the TitleFragment occupy the entire layout
            mLnameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mLWebviewLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            // Getting the orientation of the device
            Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int rotation = display.getRotation();

            // Checking if the device is vertical
            if(rotation== Surface.ROTATION_0 || rotation==Surface.ROTATION_180)
            {
                // Make the LandmarkNameFragment layout take 1/3 of the layout's width
                mLnameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));

                // Make the LandmarkWebviewFragment take 2/3's of the layout's width
                mLWebviewLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

            // Checking if the device is horizontal
            else if(rotation==Surface.ROTATION_90 || rotation==Surface.ROTATION_270)
            {
                // Make the LandmarkNameFragment take 1/3 of the layout's width
                mLnameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 4f));

                // Make the LandmarkWebviewFragment take 2/3's of the layout's width
                mLWebviewLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 8f));
            }
        }
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        // If the LandmarkWebviewFragment has not been added, add it now
        if (!mLwebviewFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the LandmarkWebviewFragment to the layout
            fragmentTransaction.replace(R.id.Lwebview_fragment_container,
                    mLwebviewFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mLwebviewFragment.getShownIndex() != index) {

            // Checking Permissions
            int permissionCheck_Internet = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.INTERNET);

            int permissionCheck_InternetAccess = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_NETWORK_STATE);

            if (PackageManager.PERMISSION_GRANTED == permissionCheck_Internet
                    && PackageManager.PERMISSION_GRANTED == permissionCheck_InternetAccess)
            {
                if ( isNetworkAvailable() )     //check if internet available or not
                {
                    //Toast.makeText( MainActivity.this, "Internet Connected", Toast.LENGTH_SHORT).show();

                    // Telling the LandmarkWebViewFragment to display webpage of the selected Landmark
                    mLwebviewFragment.showWebPageAtIndex(index);
                }
                else    //Not connected
                {
                    Toast.makeText(MainActivity.this, "Internet Disconnected", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if (PackageManager.PERMISSION_DENIED == permissionCheck_Internet) {
                    ActivityCompat.requestPermissions( MainActivity.this,
                            new String[]{ Manifest.permission.INTERNET },
                            MY_PERMISSIONS_INTERNET);
                }
                if (PackageManager.PERMISSION_DENIED == permissionCheck_InternetAccess) {
                    ActivityCompat.requestPermissions( MainActivity.this,
                            new String[]{ Manifest.permission.ACCESS_NETWORK_STATE},
                            MY_PERMISSIONS_INTERNET_ACCESS);
                }
                /* Prompt user for permission now */
                Toast.makeText(MainActivity.this,"Please Grant necessary permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Checking if internet is available
    //<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    //<uses-permission android:name="android.permission.INTERNET"/>
    public boolean isNetworkAvailable()
    {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_INTERNET: {
                                            if (grantResults.length > 0
                                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                            {
                                                // permission was granted
                                                Toast.makeText(MainActivity.this,"Permission granted!", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                // permission denied
                                                Toast.makeText(MainActivity.this,"Sorry the operation could not be performed", Toast.LENGTH_SHORT).show();
                                            }
                                          }
            case MY_PERMISSIONS_INTERNET_ACCESS:
                                            {
                                            if (grantResults.length > 0
                                                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                            {
                                                // permission was granted
                                                Toast.makeText(MainActivity.this,"Permission granted!", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                // permission denied
                                                Toast.makeText(MainActivity.this,"Sorry the operation could not be performed", Toast.LENGTH_SHORT).show();
                                            }
                                            }
            case MY_PERMISSIONS_SHOW_GALLERY: {
                                            if (grantResults.length > 0
                                                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                            {
                                                // permission was granted
                                                Toast.makeText(MainActivity.this,"Permission granted!", Toast.LENGTH_SHORT).show();
                                                Intent aIntent = new Intent(SHOW_GALLERY_ACTION) ;
                                                sendBroadcast(aIntent) ;
                                            }
                                            else
                                            {
                                                // permission denied
                                                Toast.makeText(MainActivity.this,"Sorry the application " +
                                                        "could not be opened becuse required permissions do not exist", Toast.LENGTH_SHORT).show();
                                            }
                                        }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //if the user clicks the exit button this intent exits the app and takes them to the homepage
            case R.id.action_settings_1: Intent intent = new Intent(Intent.ACTION_MAIN);
                                         intent.addCategory(Intent.CATEGORY_HOME);
                                         startActivity(intent);
                                         break;
            case R.id.action_settings_2: //final LocalBroadcastManager lbs = BroadcastManager.getInstance(getApplicationContext()) ;
                                         check();
                                         break;
        }
        return true;
    }

    public void check()
    {
        int permissionCheck_showGallery = ContextCompat.checkSelfPermission(MainActivity.this, SHOW_LANDMARK_GALLERY);
        Log.i("Permission",String.valueOf(permissionCheck_showGallery));

        //permissionCheck_showGallery=PackageManager.PERMISSION_GRANTED;
        if (PackageManager.PERMISSION_GRANTED == permissionCheck_showGallery)
        {
            Log.i("Permission","granted");
            Intent aIntent = new Intent(SHOW_GALLERY_ACTION) ;
            sendBroadcast(aIntent) ;
        }
        else
        {
            Log.i("Request","Permission Needed");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{SHOW_LANDMARK_GALLERY},
                    MY_PERMISSIONS_SHOW_GALLERY);
        }
    }
}