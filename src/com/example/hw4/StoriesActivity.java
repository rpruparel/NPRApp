package com.example.hw4;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StoriesActivity extends BaseActivity {
	static final String STORY_ID = "story_id";

	static final String PREF_FAVORITES = "photo";
	static final String NO_PREF_FOUND = "no pref";

	static final int FAVORITE_REQ_CODE = 1001;
	static final int PROGRAM_TOPIC_REQ_CODE = 1002;

	ArrayList < Story > storyList;
	ListView lv;
	Intent returnIntent;
	int requestCode = 0;
	StoryAdapter adapter;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);

		SharedPreferences settings = getSharedPreferences(PREF_FAVORITES, MODE_PRIVATE);

		lv = (ListView) findViewById(R.id.listViewStory);

		// check if passed a story id
		boolean hasStoryID = (getIntent().getStringExtra(MainActivity.STORY_ID) != null);

		if (hasStoryID) {
			Log.d("stories", "hasStoryID = " + hasStoryID);
			String id = getIntent().getStringExtra(MainActivity.STORY_ID);

			// async task to get list of ids
			new GetStoriesAsync(StoriesActivity.this).execute(getIntent()
				.getStringExtra(MainActivity.STORY_ID));

			// set reqeustcode to program so no list changes needed
			requestCode = this.PROGRAM_TOPIC_REQ_CODE;

		} else {
			Log.d("stories", "hasStoryID = " + hasStoryID);

			String stringIDs = settings.getString(PREF_FAVORITES, MainActivity.EMPTY_STRING);
			Log.d("stories -fav",
			settings.getString(PREF_FAVORITES, MainActivity.EMPTY_STRING));
			new GetStoriesAsync(StoriesActivity.this).execute(stringIDs);

			requestCode = this.FAVORITE_REQ_CODE;
		}

	}



	@Override

	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}



	public void setStories(ArrayList < Story > stories) {
		storyList = stories;

		adapter = new StoryAdapter(StoriesActivity.this,
		R.layout.item_row_layout, storyList);
		adapter.setNotifyOnChange(true);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override

			public void onItemClick(AdapterView <? > arg0, View arg1, int arg2,
			long arg3) {
				// TODO Auto-generated method stub
				Log.d("stories activity", "click");
				Intent intent = new Intent(StoriesActivity.this,
				StoryActivity.class);
				Story story = storyList.get(arg2);
				intent.putExtra(StoriesActivity.STORY_ID, story);

				startActivityForResult(intent, requestCode);
			}

		});

	}

	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("stories", "activity result");
		Log.d("stories", "result code = " + resultCode);
		Log.d("stories", "request code = " + requestCode);
		Log.d("stories", "result code = ok?" + (resultCode == RESULT_OK));
		Log.d("stories", "result code = cancelled?" + (resultCode == RESULT_CANCELED));

		if (resultCode == RESULT_OK) {
			Log.d("stories", "RESULT_OK");
			if (requestCode == FAVORITE_REQ_CODE) {
				Log.d("stories", "favorite request code");

				if (data != null) {
					Log.d("data", data.getStringExtra(StoriesActivity.STORY_ID));
					//add new item to list

					SharedPreferences settings = getSharedPreferences(PREF_FAVORITES, MODE_PRIVATE);

					if (settings.getString(PREF_FAVORITES, MainActivity.EMPTY_STRING).equals(MainActivity.EMPTY_STRING)) {
						lv = (ListView) findViewById(R.id.listViewStory);
						adapter.remove(storyList.get(0));
						Toast.makeText(StoriesActivity.this,
							"You have not yet selected any favorites",
						Toast.LENGTH_LONG).show();
						finish();
					} else {
						String ids = settings.getString(PREF_FAVORITES, MainActivity.EMPTY_STRING);
						new GetStoriesAsync(StoriesActivity.this).execute(ids);
					}
				} else {
					Log.d("stories", "not getting into loop");
				}
			} else if (requestCode == PROGRAM_TOPIC_REQ_CODE) {
				//do nothing
				Log.d("stories", "program or topic request");
			}
		}
	}

	private int getStoryPositionFromID(String id) {
		boolean isFound = false;
		int pos = -1;

		for (int i = 0; i < storyList.size() && !isFound; i++) {
			if (storyList.get(i).getStoryID().equals(id)) {
				pos = i;
				isFound = true;
			}
		}
		return pos;
	}

}