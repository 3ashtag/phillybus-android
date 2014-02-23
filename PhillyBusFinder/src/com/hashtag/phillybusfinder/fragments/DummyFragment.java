package com.hashtag.phillybusfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hashtag.phillybusfinder.R;

public class DummyFragment extends Fragment {

    private int mColorRes;

    public DummyFragment() {
        mColorRes = R.color.red;
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mColorRes = savedInstanceState.getInt("mColorRes");
        }

        int color = getResources().getColor(mColorRes);
        RelativeLayout view = new RelativeLayout(getActivity());
        view.setBackgroundColor(color);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mColorRes", mColorRes);
    }

}
