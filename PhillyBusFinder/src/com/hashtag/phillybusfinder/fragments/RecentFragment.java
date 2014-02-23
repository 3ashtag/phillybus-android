package com.hashtag.phillybusfinder.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hashtag.phillybusfinder.BusStopActivity;
import com.hashtag.phillybusfinder.DatabaseAdapter;
import com.hashtag.phillybusfinder.models.BusStop;

public class RecentFragment extends ListFragment {
    List<BusStop> mBusStops = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        mBusStops = db.getAll();

        List<String> stops = new ArrayList<String>();
        for (BusStop busStop : mBusStops) {
            stops.add(busStop.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,
                stops);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BusStop busStop = mBusStops.get(position);
        Intent i = new Intent(getActivity(), BusStopActivity.class);
        i.putExtra("id", busStop.getId().toString());
        i.putExtra("name", busStop.getName());
        startActivity(i);

        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        db.insert(busStop);
    }
}
