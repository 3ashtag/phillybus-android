package com.hashtag.phillybusfinder.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hashtag.phillybusfinder.R;
import com.hashtag.phillybusfinder.ui.Util;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(getActivity().getResources().getString(R.string.about));
        Typeface mFontFace = Util.getTypeface(getActivity(), "fonts/Lato.ttf");
        textView.setTypeface(mFontFace);
        return view;
    }

}
