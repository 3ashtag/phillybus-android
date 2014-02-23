package com.hashtag.phillybusfinder.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hashtag.phillybusfinder.BusStopActivity;
import com.hashtag.phillybusfinder.DatabaseAdapter;
import com.hashtag.phillybusfinder.fragments.NearbyFragment.DataPullingInterface;
import com.hashtag.phillybusfinder.models.BusStop;

public class NearbyMapFragment extends SupportMapFragment implements OnMarkerClickListener {

    private static final String TAG = NearbyMapFragment.class.getSimpleName();
    private DataPullingInterface mHostInterface = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstance) {
        View layout = super.onCreateView(inflater, view, savedInstance);

        GoogleMap map = getMap();
        map.getUiSettings().setZoomControlsEnabled(false);
        map.setOnMarkerClickListener(this);

        if (mHostInterface != null) {
            update(mHostInterface);
        }

        // This is a hack to remove the black box artifact on the SlidingMenu
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((ViewGroup) layout).addView(frameLayout, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return layout;
    }

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
        GoogleMap map = getMap();

        for (BusStop busStop : hostInterface.getBusStops()) {
            Log.d(TAG, "Adding " + busStop.getName() + " to Google Maps.");
            map.addMarker(new MarkerOptions().title(busStop.getName()).snippet(busStop.getId().toString()).position(new LatLng(busStop.getLat(), busStop.getLon())));
        }

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(hostInterface.getLatitude(), hostInterface
                .getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        map.animateCamera(zoom);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        BusStop busStop = mHostInterface.getBusStops().get(Integer.parseInt(marker.getSnippet()));
        Intent i = new Intent(getActivity(), BusStopActivity.class);
        i.putExtra("id", busStop.getId().toString());
        i.putExtra("name", busStop.getName());
        startActivity(i);
        
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        db.insert(busStop);
        
        return true;
    }
}
