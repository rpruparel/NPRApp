package com.example.hw4;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    static final String URL_ID = "url_id";
    static final String STORY_ID = "story_id";
    static public final String EMPTY_STRING = "";

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // program button listener
        findViewById(R.id.buttonPrograms).setOnClickListener(
            new View.OnClickListener() {

                @Override
                
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,
                        ListActivity.class);
                    intent.putExtra(URL_ID, R.string.url_programs);
                    startActivity(intent);
                }
            });

        // topics button listener
        findViewById(R.id.buttonTopics).setOnClickListener(
            new View.OnClickListener() {

                @Override
                
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this,
                        ListActivity.class);
                    intent.putExtra(URL_ID, R.string.url_topics);
                    startActivity(intent);
                }
            });

        // favorites button listener
        findViewById(R.id.buttonFavorites).setOnClickListener(
            new View.OnClickListener() {

                @Override
                
                public void onClick(View v) {

                    SharedPreferences settings = getSharedPreferences(
                        StoriesActivity.PREF_FAVORITES, MODE_PRIVATE);

                    if (settings.getString(StoriesActivity.PREF_FAVORITES, EMPTY_STRING).equals(EMPTY_STRING)) {
                        Toast.makeText(MainActivity.this,
                            "You have not yet selected any favorites",
                            Toast.LENGTH_LONG).show();
                    } else {

                        // TODO Auto-generated method stub
                        Intent intent = new Intent(MainActivity.this,
                            StoriesActivity.class);
                        startActivity(intent);
                    }
                }
            });

        // exit button listener
        findViewById(R.id.buttonExit).setOnClickListener(
            new View.OnClickListener() {

                @Override
                
                public void onClick(View v) {
                    finish();

                }
            });
    }

}