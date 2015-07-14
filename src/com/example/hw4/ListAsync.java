package com.example.hw4;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ListAsync extends AsyncTask < String, Void, ArrayList < ListItem >> {
    ProgressDialog pd;
    Activity activity;

    public ListAsync(Activity activity) {
        this.activity = activity;
    }

    @Override
    
    protected ArrayList < ListItem > doInBackground(String...params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                Log.d("async url", url.toString());

                return JSONParser.parseList(con.getInputStream());
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    
    protected void onPreExecute() {
        Log.d("pre execute", "pre execute");
        pd = new ProgressDialog(activity, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        pd.setMessage("Loading Stories");
        pd.show();
        pd.setCancelable(false);
        super.onPreExecute();

    }

    @Override
    
    protected void onPostExecute(ArrayList < ListItem > result) {

        pd.dismiss();
        pd.cancel();
        ((ListActivity) activity).setListItems(result);
        Log.d("post execute", "post execute");
        super.onPostExecute(result);

    }

}