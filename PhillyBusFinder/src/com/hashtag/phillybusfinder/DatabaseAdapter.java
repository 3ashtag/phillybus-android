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
        query = "CREATE TABLE RecentStops ( _id INTEGER PRIMARY KEY NOT NULL, stop_id INTEGER NOT NULL, stop_name TEXT NOT NULL )";
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
        String query = "SELECT * FROM  RecentStops WHERE stop_id IS " + busStop.getId();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() < 1) {
            ContentValues values = new ContentValues();
            values.put("stop_id", busStop.getId());
            values.put("stop_name", busStop.getName());
            db.insert("RecentStops", null, values);
        }
        cursor.close();
        db.close();
    }

    public List<BusStop> getAll() {
        List<BusStop> busStops = new ArrayList<BusStop>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM  RecentStops ORDER BY _id DESC LIMIT 20";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BusStop busStop = new BusStop();
                busStop.setId(cursor.getInt(1));
                busStop.setName(cursor.getString(2));
                busStops.add(busStop);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return busStops;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE * FROM  RecentStops";
        db.execSQL(query);
    }

}
