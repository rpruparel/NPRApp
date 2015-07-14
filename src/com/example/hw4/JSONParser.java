package com.example.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
    public static ArrayList < ListItem > parseList(InputStream in ) {
        ArrayList < ListItem > list = new ArrayList < ListItem > ();

        BufferedReader reader = new BufferedReader(new InputStreamReader( in ));

        String line;

        try {
            line = reader.readLine();

            while (line != null) {
                try {

                    // parse all top object
                    JSONObject o = new JSONObject(line);
                    JSONObject titleObject = o.getJSONObject("title");

                    ListItem item = new ListItem(o.getString("id"),
                        titleObject.getString("$text"));
                    list.add(item);

                    // parse inner object
                    JSONArray jsons = o.getJSONArray("item");

                    for (int i = 0; i < jsons.length(); i++) {
                        JSONObject innerObject = jsons.getJSONObject(i);
                        item = new ListItem(innerObject.getString("id"),
                            innerObject.getJSONObject("title").getString(
                                "$text"));
                        list.add(item);
                        // Log.d("parser", item.toString());
                    }

                    line = reader.readLine();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList < Story > parseStory(InputStream in ) {
        ArrayList < Story > stories = new ArrayList < Story > ();

        BufferedReader reader = new BufferedReader(new InputStreamReader( in ));

        String line;

        try {
            line = reader.readLine();

            while (line != null) {
                try {

                    // parse all top object -- starts with version
                    JSONObject o = new JSONObject(line);
                    JSONObject list = o.getJSONObject("list");
                    JSONArray storyArray = list.getJSONArray("story");

                    for (int i = 0; i < storyArray.length(); i++) {
                        JSONObject storyObject = storyArray.getJSONObject(i);

                        Story newStory = new Story();

                        newStory.setStoryID(storyObject.getString("id"));
                        newStory.setPublicationDate(storyObject.getJSONObject(
                            "pubDate").getString("$text"));
                        newStory.setTitle(storyObject.getJSONObject("title")
                            .getString("$text"));
                        newStory.setTeaser(storyObject.getJSONObject("teaser")
                            .getString("$text"));

                        // setting text link
                        JSONArray linkArray = storyObject.getJSONArray("link");
                        JSONObject linkObject = new JSONObject();
                        for (int j = 0; j < linkArray.length(); j++) {
                            linkObject = linkArray.getJSONObject(j);
                            if (linkObject.getString("type").equals("html")) {
                                newStory.setTextURL(linkObject
                                    .getString("$text"));
                                break; // exit for loop
                            }
                        }

                        // setting audio
                        if (storyObject.has("audio")) {
                            JSONArray audioArray = storyObject
                                .getJSONArray("audio");
                            JSONObject audioObject = new JSONObject();

                            for (int k = 0; k < audioArray.length(); k++) {
                                audioObject = (JSONObject) audioArray.get(k);

                                //get mp3 address
                                if (audioObject.has("format")) {

                                    JSONObject audioFormatObject = audioObject
                                        .getJSONObject("format");

                                    JSONArray mp3Array = audioFormatObject
                                        .getJSONArray("mp3");
                                    JSONObject mp3Object = new JSONObject();

                                    for (int z = 0; z < mp3Array.length(); z++) {
                                        mp3Object = (JSONObject) mp3Array
                                            .get(z);
                                        if (mp3Object.has("$text")) {
                                            newStory.setAudioURL(mp3Object
                                                .getString("$text"));
                                        } else {
                                            newStory.setAudioURL(null);
                                        }

                                    }

                                }

                                //get duration
                                if (audioObject.has("duration")) {
                                    JSONObject durationObject = audioObject
                                        .getJSONObject("duration");
                                    if (durationObject.has("$text")) {
                                        newStory.setLengthOfBroadcast(Story
                                            .convertDurationFromSeconds(durationObject
                                                .getString("$text")));
                                    } else {
                                        newStory.setLengthOfBroadcast(null);
                                    }

                                }
                            }
                        } else {
                            newStory.setAudioURL(null);
                        }
                        //

                        // setting reporter if byline
                        if (storyObject.has("byline")) {
                            JSONArray bylineArray = storyObject.getJSONArray("byline");
                            JSONObject bylineObject = new JSONObject();

                            for (int y = 0; y < bylineArray.length(); y++) {
                                bylineObject = (JSONObject) bylineArray.get(y);

                                if (bylineObject.has("name")) {
                                    JSONObject nameObject = bylineObject.getJSONObject("name");

                                    if (nameObject.has("$text")) {
                                        newStory.setReporterName(nameObject.getString("$text"));
                                    } else {
                                        newStory.setReporterName(null);
                                    }

                                }

                            }
                        }


                        // setting thumbnail
                        if (storyObject.has("thumbnail")) {
                            JSONObject thumbnailObject = storyObject
                                .getJSONObject("thumbnail");

                            if (thumbnailObject.has("large")) {
                                newStory.setImageURL(thumbnailObject
                                    .getJSONObject("large").getString(
                                        "$text"));
                            } else if (thumbnailObject.has("medium")) {
                                newStory.setImageURL(thumbnailObject
                                    .getJSONObject("medium").getString(
                                        "$text"));
                            } else {
                                newStory.setImageURL(null);
                            }
                        }

                        // setting Date Aired
                        if (storyObject.has("audioRunByDate")) {
                            JSONObject audioRunByObject = storyObject
                                .getJSONObject("audioRunByDate");
                            if (audioRunByObject.has("$text")) {
                                newStory.setDateAired(storyObject
                                    .getJSONObject("audioRunByDate")
                                    .getString("$text"));
                            }
                        }

                        stories.add(newStory);

                    }

                    line = reader.readLine();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return stories;
    }

}