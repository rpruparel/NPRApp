package com.example.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;
import android.widget.Toast;

public class StoryActivity extends BaseActivity implements OnPreparedListener,
MediaPlayerControl {

    ImageView iv;
    MediaController mc;
    MediaPlayer mp;
    Story story;
    Intent returnIntent;
    int favClick = 0;
    String mp3URL;

    //tracks stopped state of media player, needed when coming back from website
    boolean isStopped = true;
    int playerPosition = 0;

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // intialize media player
        mp = new MediaPlayer();
        mc = new MediaController(this);
        mp.setOnPreparedListener(this);

        // setup return intent
        returnIntent = new Intent();

        // get story from intent
        story = (Story) getIntent().getExtras().getSerializable(
            StoriesActivity.STORY_ID);

        Log.d("story", story.getStoryID());

        setupUI();

        // back button
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            
            public void onClick(View v) {



                // TODO Auto-generated method stub
                Log.d("story", "set result to " + RESULT_OK);
                if (favClick == 0) {
                    setResult(RESULT_OK);
                } else {
                    setResult(RESULT_OK, returnIntent);
                }
                finish();
            }
        });

        // globe button
        iv = (ImageView) findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mp.pause();

                if (story.getTextURL() != null) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(story.getTextURL()));
                    startActivity(i);
                } else {
                    Toast.makeText(StoryActivity.this,
                            "No webpage for this story", Toast.LENGTH_LONG)
                        .show();
                }
            }
        });

        // speaker button
        iv = (ImageView) findViewById(R.id.imageView3);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            
            public void onClick(View v) {

                if (story.getAudioURL() != null) {
                    new MusicAsync().execute(story.getAudioURL());
                } else {
                    Toast.makeText(StoryActivity.this,
                        "No audio file for this story", Toast.LENGTH_LONG);
                }

            }
        });

        // star button
        iv = (ImageView) findViewById(R.id.imageView4);

        // change icon if favorite

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            
            public void onClick(View v) {

                boolean isFavorite = (Boolean) v.getTag();

                if (isFavorite) { // set to not favorite
                    ((ImageView) v).setImageDrawable(getResources()
                        .getDrawable(R.drawable.not_favorite));

                    Log.d("story", "remove from favorites");

                    FavoritesUtil.initFavoritesUtil(
                        (Context) StoryActivity.this,
                        StoriesActivity.PREF_FAVORITES, MODE_PRIVATE);
                    FavoritesUtil.removeID(story.getStoryID());
                    v.setTag(false);
                } else { // set to favorite and store in intent
                    ((ImageView) v).setImageDrawable(getResources()
                        .getDrawable(R.drawable.favorite));

                    Log.d("story", "add to favorites and set to true");

                    FavoritesUtil.initFavoritesUtil(
                        (Context) StoryActivity.this,
                        StoriesActivity.PREF_FAVORITES, MODE_PRIVATE);
                    FavoritesUtil.addID(story.getStoryID());
                    v.setTag(true);
                }

                returnIntent.putExtra(StoriesActivity.STORY_ID,
                    story.getStoryID());
                favClick = 1;
            }
        });
    }

    private void setupUI() {
        // title
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(story.getTitle());

        // reporter name
        tv = (TextView) findViewById(R.id.textView2);

        if (story.getReporterName() == null) {
            tv.setText("No Reporter Name");
        } else {
            tv.setText("by " + story.getReporterName());
        }

        // date and length
        tv = (TextView) findViewById(R.id.textView3);
        StringBuilder sb = new StringBuilder();

        if (story.getDateAired() != null) {
            sb.append(story.getDateAired());
        } else {
            sb.append("Unknown Air Date");
        }

        sb.append(" | ");

        if (story.getLengthOfBroadcast() != null) {
            sb.append(story.getLengthOfBroadcast());
        } else {
            sb.append("Unknown Broadcast Length");
        }

        tv.setText(sb.toString());

        // teaser
        tv = (TextView) findViewById(R.id.textView4);
        if (story.getTeaser() != null) {
            tv.setText(story.getTeaser());
        } else {
            tv.setText("No Information");
        }

        ImageView iv = (ImageView) findViewById(R.id.imageView4);
        SharedPreferences settings = getSharedPreferences(
            StoriesActivity.PREF_FAVORITES, MODE_PRIVATE);
        String ids = settings.getString(StoriesActivity.PREF_FAVORITES, null);

        if (ids != null && ids.contains(story.getStoryID())) {
            Log.d("story setup", "set tag to true");
            iv.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
            iv.setTag(true); 
        } else {
            Log.d("story setup", "set tag to false");
            iv.setTag(false);
        }

    }

    @Override
    
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("touch event", event.toString());

        Log.d("on touch", " on touch");

        if (isStopped && mp3URL != null) {
            try {
                mp.reset();
                mp.setDataSource(mp3URL);
                mp.prepare();
                mp.seekTo(playerPosition);


            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (mp != null) {
            mc.show();
        }


        return false;
    }

    @Override
    
    public void start() {
        // TODO Auto-generated method stub
        Log.d("start", "starting");
        mp.start();
    }


    @Override
    
    public void pause() {
        // TODO Auto-generated method stub
        Log.d("pause", "pausing");
        mp.pause();
    }

    @Override
    
    public int getDuration() {
        // TODO Auto-generated method stub
        return mp.getDuration();

    }

    @Override
    
    public int getCurrentPosition() {
        // TODO Auto-generated method stub
        return mp.getCurrentPosition();
    }

    @Override
    
    public void seekTo(int pos) {
        // TODO Auto-generated method stub
        Log.d("seekto", "seeking");
        mp.seekTo(pos);
    }

    @Override
    
    public boolean isPlaying() {
        // TODO Auto-generated method stub
        return mp.isPlaying();
    }

    @Override
    
    public int getBufferPercentage() {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    
    public boolean canPause() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    
    public boolean canSeekBackward() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    
    public boolean canSeekForward() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    
    public int getAudioSessionId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    
    public void onBackPressed() {
        // TODO Auto-generated method stub
        mp.stop();
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.performClick();
        super.onBackPressed();
    }

    @Override
    
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub

        mp.start();
        isStopped = false;
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.mediaController1));
        mc.setEnabled(true);
        mc.show();

    }



    @Override
    
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        isStopped = true;
        playerPosition = mp.getCurrentPosition();
        mc.hide();
        mp.stop();

        //do not release here, only release on back presses
        //mp.release();
        Log.d("onStop", "stopped, not released");
    }



    @Override
    
    protected void onDestroy() {
        // TODO Auto-generated method stub
        playerPosition = 0;
        isStopped = true;

        mp.stop();
        mp.release();
        super.onDestroy();
    }




    class MusicAsync extends AsyncTask < String, Integer, String > {

        @Override
        
        protected String doInBackground(String...params) {
            // TODO Auto-generated method stub
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();
                con.setRequestMethod("GET");
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = reader.readLine();
                    }
                    return sb.toString();

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
        
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            mp3URL = result;

            try {
                mp.setDataSource(mp3URL);
                mp.prepareAsync();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


}