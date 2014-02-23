package com.hashtag.phillybusfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hashtag.phillybusfinder.LandingActivity;
import com.hashtag.phillybusfinder.R;

public class MenuFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] items = getResources().getStringArray(R.array.menu_items);
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, items);
        setListAdapter(itemAdapter);
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        Fragment newContent = null;

        switch (position) {
            case 0:
                newContent = new DummyFragment();
                break;
            case 1:
                newContent = new DummyFragment();
                break;
            case 2:
                newContent = new DummyFragment();
                break;
            case 3:
                newContent = new DummyFragment();
                break;
        }

        if (newContent != null) {
            switchFragment(newContent);
        }
    }

    private void switchFragment(Fragment fragment) {
        if (getActivity() == null) {
            return;
        }

        LandingActivity la = (LandingActivity) getActivity();
        la.switchContent(fragment);
    }

}
