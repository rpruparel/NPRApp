package com.example.hw4;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.hw4.*;

public class GetStoriesAsync extends AsyncTask < String, Void, ArrayList < Story >> {
    ProgressDialog dialog;
    Activity activity;

    public GetStoriesAsync(Activity activity) {
        this.activity = activity;
    }

    @Override
    
    protected ArrayList < Story > doInBackground(String...params) {
        ArrayList < Story > stories = new ArrayList < Story > ();

        Log.d("get async", "storyid string = " + params[0]);

        String[] paramIDs;

        if (params[0].contains(" ")) {
            paramIDs = params[0].split(" ");
        } else {
            paramIDs = new String[1];
            paramIDs[0] = params[0];
        }

        for (String id: paramIDs) {
            Log.d("get async", "param = " + id);
            StringBuilder sb = new StringBuilder();
            sb.append("http://api.npr.org/query?id=");
            sb.append(id);
            sb.append("&fields=title,thumbnail,teaser,storyDate,byline,audio,image,dates&dateType=story&output=JSON&numResults=25&apiKey=<provide your api key here>");

            String urlString = sb.toString();

            Log.d("story async", urlString);

            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    stories.addAll(JSONParser.parseStory(con.getInputStream()));

                }

                con.disconnect();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return stories;
    }

    @Override
    
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        dialog = new ProgressDialog(activity, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        dialog.setMessage("Loading Stories");
        dialog.show();

        super.onPreExecute();
    }

    @Override
    
    protected void onPostExecute(ArrayList < Story > result) {
        // TODO Auto-generated method stub

        dialog.dismiss();
        ((StoriesActivity) activity).setStories(result);
        super.onPostExecute(result);
    }

}