package com.hashtag.phillybusfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class RecentFragment extends ListFragment {

//    private final DatabaseAdapter mDatabaseAdapter = new DatabaseAdapter(getActivity());
//    private List<BusStop> mBusStops = null;

    private final String[] mFakeData = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight",
            "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mBusStops = mDatabaseAdapter.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,
                mFakeData);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
