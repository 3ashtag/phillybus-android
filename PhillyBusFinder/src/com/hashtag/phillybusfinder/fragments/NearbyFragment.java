package com.hashtag.phillybusfinder.fragments;

import java.util.ArrayList;

import com.hashtag.phillybusfinder.R;
import com.hashtag.phillybusfinder.models.BusStop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NearbyFragment extends Fragment {
    private FragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("Map"), NearbyMapFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("List"), NearbyListFragment.class, null);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    public interface DataPullingInterface {
        public double getLatitude();

        public double getLongitude();

        public ArrayList<BusStop> getBusStops();
    }
}
