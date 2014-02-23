package com.hashtag.phillybusfinder.client;

import org.json.JSONArray;

public interface RestCallback {
    void onComplete(JSONArray response);
}
