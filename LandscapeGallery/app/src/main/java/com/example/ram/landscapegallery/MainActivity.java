package com.example.ram.landscapegallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //String holding the names of the landmarks
    private String[] name={"Chicago Cultural Center 1","Chicago Cultural Center 2","Lincon Park Zoo"
            ,"Millennium Monument","Museum of Science and Industry","Willis tower","Wrigley Field"};

    //image array holding the integer ID of the images in the drawable folder
    int[] images={R.drawable.chicago_cultural_center1, R.drawable.chicago_cultural_center2,
            R.drawable.lincon_park_zoo, R.drawable.millennium_monument, R.drawable.museum_of_science_and_industry,
            R.drawable.willis_tower,R.drawable.wrigley_field};

    //ArrayList that holds the object cars of the type class Car
    ArrayList<Chicago_Landmarks> landmarks;

    //ArrayList that holds the bitmaps of the images
    private ArrayList<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up custom toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        //Function that converts images to bitmaps and stores it in bitmaps
        bitmaps=getBitmaps();

        //ArrayList landmarks holds the String name and integer ID of every landmark
        landmarks =getLandmarks();

        //Getting gridView ID
        GridView gridview = (GridView) findViewById(R.id.gridview);

        // Create a new ImageAdapter and set it as the Adapter for this GridView
        gridview.setAdapter(new Landmark_Adapter(this, landmarks));
    }

    //Creating the ArrayList of type Chicago_Landmarks
    private ArrayList<Chicago_Landmarks> getLandmarks()
    {
        ArrayList<Chicago_Landmarks> landmarks=new ArrayList<Chicago_Landmarks>();
        for(int i=0;i<images.length;i++)
        {
            landmarks.add(new Chicago_Landmarks(name[i], images[i], bitmaps.get(i)));
        }
        return landmarks;
    }

    //Creating the Bitmap ArrayList of each image
    public ArrayList<Bitmap> getBitmaps()
    {
        ArrayList<Bitmap> bitmaps=new ArrayList<Bitmap>();
        Bitmap bitmap;
        for(int i=0;i<images.length;i++)
        {
            bitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(),images[i]),325  ,370, Boolean.TRUE);
            bitmaps.add(bitmap);
        }
        return bitmaps;
    }
}
