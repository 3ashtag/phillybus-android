package com.hashtag.phillybusfinder.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class RestClient {

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    private String mURL;
    private RequestMethod mRequestMethod;
    private ArrayList<NameValuePair> mHeaders;
    private int mResponseCode;
    private String mResponse;
    private String mMessage;
    private JSONObject mJson;

    public RestClient(String url, RequestMethod requestMethod) {
        this.mURL = url;
        this.mRequestMethod = requestMethod;
        mHeaders = new ArrayList<NameValuePair>();
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setJSON(JSONObject json) {
        this.mJson = json;
    }

    public void addHeader(String name, String value) {
        mHeaders.add(new BasicNameValuePair(name, value));
    }

    public void Execute() throws UnsupportedEncodingException {
        switch (mRequestMethod) {
            case GET: {
                HttpGet request = new HttpGet(mURL);

                for (NameValuePair header : mHeaders) {
                    request.addHeader(header.getName(), header.getValue());
                }

                executeRequest(request);
                break;
            }
            case POST: {
                HttpPost request = new HttpPost(mURL);

                for (NameValuePair header : mHeaders) {
                    request.addHeader(header.getName(), header.getValue());
                }

                StringEntity stringEntity = new StringEntity(mJson.toString());
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                request.setEntity(stringEntity);

                executeRequest(request);
                break;
            }
            case PUT: {
                HttpPut request = new HttpPut(mURL);

                for (NameValuePair header : mHeaders) {
                    request.addHeader(header.getName(), header.getValue());
                }

                StringEntity stringEntity = new StringEntity(mJson.toString());
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                request.setEntity(stringEntity);

                executeRequest(request);
                break;
            }
            case DELETE: {
                HttpDelete request = new HttpDelete(mURL);

                for (NameValuePair header : mHeaders) {
                    request.addHeader(header.getName(), header.getValue());
                }

                executeRequest(request);
                break;
            }
        }
    }

    private void executeRequest(HttpUriRequest request) {
        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            mResponseCode = httpResponse.getStatusLine().getStatusCode();
            mMessage = httpResponse.getStatusLine().getReasonPhrase();

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                mResponse = convertStreamToString(inputStream);
                inputStream.close();
            }
        } catch (Exception e) {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

}
