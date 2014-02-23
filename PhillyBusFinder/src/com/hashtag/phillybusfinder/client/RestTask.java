package com.hashtag.phillybusfinder.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.hashtag.phillybusfinder.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class RestTask extends AsyncTask<RestClient, Void, List<JSONArray>> {
    private final Context mContext;
    private final ProgressDialog mDialog;
    private final RestCallback mCallback;

    public RestTask(Context context, RestCallback callback) {
        mContext = context;
        mDialog = new ProgressDialog(context);
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        mDialog.setMessage(mContext.getResources().getString(R.string.loading));
        mDialog.show();
    }

    @Override
    protected List<JSONArray> doInBackground(RestClient... clients) {
        List<JSONArray> jsonList = new ArrayList<JSONArray>();

        for (RestClient client : clients) {
            try {
                client.Execute();
                String response = client.getResponse();
                jsonList.add(new JSONArray(response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonList;
    }

    @Override
    protected void onPostExecute(List<JSONArray> responses) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }

        for (JSONArray response : responses) {
            mCallback.onComplete(response);
        }
    }
    
    public interface RestCallback {
        void onComplete(JSONArray response);
    }
}
