package com.hashtag.phillybusfinder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class NearbyMapFragment extends SupportMapFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstance) {
        View layout = super.onCreateView(inflater, view, savedInstance);

        GoogleMap map = getMap();
        map.getUiSettings().setZoomControlsEnabled(false);

        // This is a hack to remove the black box artifact on the SlidingMenu
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((ViewGroup) layout).addView(frameLayout, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return layout;
    }
}
