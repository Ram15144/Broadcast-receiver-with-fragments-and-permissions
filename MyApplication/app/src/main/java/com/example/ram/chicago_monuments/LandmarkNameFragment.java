package com.example.ram.chicago_monuments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ram_n on 10/29/2017.
 */

public class LandmarkNameFragment extends ListFragment {

    private static final String TAG="LandmarkNameFragment";
    private ListSelectionListener mListener=null;
    private int mCurrIdx = -1;

    //Callback interface used to notify the MainActivity when user selects an item
    public interface ListSelectionListener
    {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        if (mCurrIdx != pos) {
            mCurrIdx = pos;

            // Inform the MainActivity that the item in position pos has been selected
            mListener.onListSelection(pos);
        }
        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the MainActivity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);

        try {
            // Set the ListSelectionListener for communicating with the MainActivity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    // UB:  Notice that the superclass's method does an OK job of inflating the
    //      container layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.landmark_name_fragment, MainActivity.mTitleArray));


        // If an item has been selected, set its checked state
        if (-1 != mCurrIdx) {
            getListView().setItemChecked(mCurrIdx, true);

            // UB:  10-6-2017 Added this call to handle configuration changes
            // that broke in API 25
            mListener.onListSelection(mCurrIdx);
        }

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

}
