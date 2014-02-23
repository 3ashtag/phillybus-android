package com.hashtag.phillybusfinder.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hashtag.phillybusfinder.R;
import com.hashtag.phillybusfinder.models.BusSchedule;

public class BusScheduleAdapter extends ArrayAdapter<String> {

    private ArrayList<BusSchedule> mBusSchedules;
    private Typeface mFontFace;
    private LayoutInflater mInflater;

    public class CustomListItem {
        TextView descText;
    }

    public BusScheduleAdapter(Context context, ArrayList<BusSchedule> commandsList) {
        super(context, R.layout.list_item);
        Context applicationContext = context.getApplicationContext();
        mBusSchedules = new ArrayList<BusSchedule>();
        mBusSchedules.addAll(commandsList);
        mFontFace = Util.getTypeface(applicationContext, "fonts/SourceSansPro-ExtraLight.ttf");
        mInflater = LayoutInflater.from(applicationContext);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListItem myListItem;
        String myText = getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            myListItem = new CustomListItem();
            myListItem.descText = (TextView) convertView.findViewById(R.id.commandText);
            myListItem.descText.setTypeface(mFontFace);
            convertView.setTag(myListItem);
        } else {
            myListItem = (CustomListItem) convertView.getTag();
        }

        myListItem.descText.setText(myText);
        return convertView;
    }
}
