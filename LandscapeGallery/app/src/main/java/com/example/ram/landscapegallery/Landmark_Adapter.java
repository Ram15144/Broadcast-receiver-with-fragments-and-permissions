package com.example.ram.landscapegallery;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ram_n on 10/31/2017.
 */

public class Landmark_Adapter extends BaseAdapter {

    private static final int PADDING = 8;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private Context mContext;          // Passed to ImageView
    private ArrayList<Chicago_Landmarks> landmarks;       // Adapter must store AdapterView's items

    // Save the list of image IDs and the context
    public Landmark_Adapter(Context c, ArrayList<Chicago_Landmarks> landmarks) {
        mContext = c;
        this.landmarks = landmarks;
    }

    // Now the methods inherited from abstract superclass BaseAdapter

    // Return the number of items in the Adapter
    @Override
    public int getCount() {
        return landmarks.size();
    }

    // Return the data item at position
    @Override
    public Object getItem(int position) {
        return landmarks.get(position);
    }

    // Will get called to provide the ID that
    // is passed to OnItemClickListener.onItemClick()
    @Override
    public long getItemId(int position) {
        return landmarks.get(position).getImg();
    }

    // Return an ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View grid;

        LayoutInflater inflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.landscape_model,parent,false);
        }

        //The views of each image
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView);
        TextView tv= (TextView) convertView.findViewById(R.id.textView);

        Typeface mycustomfont=Typeface.createFromAsset(mContext.getAssets(), "fonts/libel.ttf");
        tv.setTypeface(mycustomfont);


        //Setting the Data of each image in grid view
        img.setImageBitmap(landmarks.get(position).getBitmap());
        tv.setText(landmarks.get(position).getName());

        return convertView;
    }
}
