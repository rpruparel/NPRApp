package com.example.hw4;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends BaseActivity {
    ArrayList < ListItem > list;

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        int resourceID = getIntent().getExtras().getInt(MainActivity.URL_ID);

        new ListAsync(this).execute(getResources().getString(resourceID));
    }

    public void setListItems(ArrayList < ListItem > result) {
        Log.d("set results", "");
        this.list = result;

        ListView lv = (ListView) findViewById(R.id.listViewStory);

        ArrayAdapter < ListItem > adapter = new ArrayAdapter < ListItem > (
            ListActivity.this, R.layout.program_list_layout, list);


        adapter.setNotifyOnChange(true);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            
            public void onItemClick(AdapterView <? > arg0, View arg1, int arg2,
                long arg3) {

                Intent intent = new Intent(ListActivity.this,
                    StoriesActivity.class);
                intent.putExtra(MainActivity.STORY_ID, list.get(arg2).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
    }

}