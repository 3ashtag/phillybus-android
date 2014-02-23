package com.hashtag.phillybusfinder;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hashtag.phillybusfinder.models.BusStop;

public class DatabaseAdapter extends SQLiteOpenHelper {

    public DatabaseAdapter(Context context) {
        super(context, "PhillyBusFinder.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE RecentStops ( id INT NOT NULL AUTO_INCREMENT, stop_id INT, stop_name TEXT, PRIMARY KEY  (id) )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS RecentStops";
        db.execSQL(query);
        onCreate(db);
    }

    public void insert(BusStop busStop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stop_id", busStop.getId());
        values.put("stop_name", busStop.getName());
        db.insert("RecentStops", null, values);
        db.close();
    }

    public List<BusStop> getAll() {
        List<BusStop> busStops = new ArrayList<BusStop>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM  RecentStops";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BusStop busStop = new BusStop();
                busStop.setId(cursor.getInt(0));
                busStop.setName(cursor.getString(1));
                busStops.add(busStop);
            } while (cursor.moveToNext());
        }
        return busStops;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE * FROM  RecentStops";
        db.execSQL(query);
    }

}
