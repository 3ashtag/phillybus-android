package com.hashtag.phillybusfinder;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hashtag.phillybusfinder.client.RestClient;
import com.hashtag.phillybusfinder.client.RestClient.RequestMethod;
import com.hashtag.phillybusfinder.client.RestTask;
import com.hashtag.phillybusfinder.models.BusSchedule;

public class BusStopActivity extends SherlockActivity implements RestTask.RestCallback {

    private static final String TAG = LandingActivity.class.getSimpleName();
    private ArrayList<BusSchedule> mBusSchedules = new ArrayList<BusSchedule>();
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mList = (ListView) findViewById(android.R.id.list);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = "http://phillybusfinder.com/api/stops/schedules?stopId=" + id;
        RestClient client = new RestClient(url, RequestMethod.GET);
        RestTask task = new RestTask(this, this);
        task.execute(client);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(JSONArray response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeDeserializer()).create();

        ArrayList<String> busSchedules = new ArrayList<String>();
        for (int i = 0; i < response.length(); i++) {
            try {
                String jsonString = response.getJSONObject(i).toString();
                BusSchedule busSchedule = gson.fromJson(jsonString, BusSchedule.class);
                mBusSchedules.add(busSchedule);
                busSchedules.add(busSchedule.getRoute());
                Log.d(TAG, "Adding " + busSchedule.getRoute() + " to mBusStops.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mList.setAdapter(new ArrayAdapter<BusSchedule>(this, android.R.layout.simple_list_item_1, mBusSchedules));
    }
}
