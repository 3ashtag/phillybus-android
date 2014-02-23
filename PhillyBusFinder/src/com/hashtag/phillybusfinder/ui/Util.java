package com.hashtag.phillybusfinder.ui;

import android.content.Context;
import android.graphics.Typeface;
import java.util.Hashtable;

public class Util {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface getTypeface(Context context, String font) {
        Typeface typeface = fontCache.get(font);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), font);
            fontCache.put(font, typeface);
        }
        return typeface;
    }
}
