package com.hashtag.phillybusfinder.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hashtag.phillybusfinder.BusStopActivity;
import com.hashtag.phillybusfinder.R;
import com.hashtag.phillybusfinder.fragments.NearbyFragment.DataPullingInterface;
import com.hashtag.phillybusfinder.models.BusStop;

public class NearbyListFragment extends ListFragment {

    private DataPullingInterface mHostInterface = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, null);

        if (mHostInterface != null) {
            update(mHostInterface);
        }

        return v;
    }

    // @Override
    // public void onActivityCreated(Bundle savedInstanceState) {
    // super.onActivityCreated(savedInstanceState);
    // String[] items = getResources().getStringArray(R.array.menu_items);
    // ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(),
    // android.R.layout.simple_list_item_1,
    // android.R.id.text1, items);
    // setListAdapter(itemAdapter);
    // }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mHostInterface = (DataPullingInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DataPullingInterface");
        }
    }

    public void update(DataPullingInterface hostInterface) {
        List<String> stops = new ArrayList<String>();
        for (BusStop busStop : hostInterface.getBusStops()) {
            stops.add(busStop.getName());
        }
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, stops);
        setListAdapter(itemAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BusStop busStop = mHostInterface.getBusStops().get(position);
        Intent i = new Intent(getActivity(), BusStopActivity.class);
        i.putExtra("id", busStop.getId().toString());
        i.putExtra("name", busStop.getName());
        startActivity(i);
    }
}
