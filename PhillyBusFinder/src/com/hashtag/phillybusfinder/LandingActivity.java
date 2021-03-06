package com.hashtag.phillybusfinder;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.hashtag.phillybusfinder.client.RestClient;
import com.hashtag.phillybusfinder.client.RestClient.RequestMethod;
import com.hashtag.phillybusfinder.client.RestTask;
import com.hashtag.phillybusfinder.fragments.MenuFragment;
import com.hashtag.phillybusfinder.fragments.NearbyFragment;
import com.hashtag.phillybusfinder.fragments.NearbyFragment.DataPullingInterface;
import com.hashtag.phillybusfinder.models.BusStop;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

public class LandingActivity extends SlidingSherlockFragmentActivity implements RestTask.RestCallback, DataPullingInterface,
        LocationListener {

    private static final String TAG = LandingActivity.class.getSimpleName();
    private Fragment mContent;
    private LocationManager mLocationManager;
    private String mProvider;
    private double mLatitude;
    private double mLongitude;
    private ArrayList<BusStop> mBusStops = new ArrayList<BusStop>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            mBusStops = savedInstanceState.getParcelableArrayList("busStops");
            Log.d(TAG, "Loading mBusStops from the bundle.");
        } else {
            mContent = new NearbyFragment();

            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            mProvider = mLocationManager.getBestProvider(criteria, false);
            Location location = mLocationManager.getLastKnownLocation(mProvider);

            if (location != null) {
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
            } else {
                mLatitude = 39.956272999999996;
                mLongitude = -75.19286799999998;
            }

            String url = "http://phillybusfinder.com/api/stops/nearby?lat=" + mLatitude + "&long=" + mLongitude;
            RestClient client = new RestClient(url, RequestMethod.GET);
            RestTask task = new RestTask(this, this);
            task.execute(client);
        }

        setContentView(R.layout.content_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindCanvasTransformer(new CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSlidingActionBarEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
        if (mBusStops != null && !mBusStops.isEmpty()) {
            outState.putParcelableArrayList("busStops", mBusStops);
        }
    }

    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getSupportMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public void onComplete(JSONArray response) {
        Gson gson = new Gson();
        for (int i = 0; i < response.length(); i++) {
            try {
                String jsonString = response.getJSONObject(i).toString();
                BusStop busStop = gson.fromJson(jsonString, BusStop.class);
                mBusStops.add(busStop);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public ArrayList<BusStop> getBusStops() {
        return mBusStops;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String string) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String string) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
