package com.example.hw4;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryAdapter extends ArrayAdapter < Story > {
    Context context;
    ArrayList < Story > stories;

    public StoryAdapter(Context context, int resource, List < Story > objects) {
        super(context, R.layout.item_row_layout, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.stories = (ArrayList < Story > ) objects;

    }

    @Override
    
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row_layout, parent, false);
        }

        Story story = stories.get(position);

        //get views
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView titleText = (TextView) convertView.findViewById(R.id.textView1);
        TextView dateText = (TextView) convertView.findViewById(R.id.textView2);
        TextView teaserText = (TextView) convertView.findViewById(R.id.textView3);


        if (story.getImageURL() != null) {
            Picasso.with(context).load(story.getImageURL()).into(iv);
        } else {
            Picasso.with(context).load(R.drawable.no_image).into(iv);
        }


        titleText.setText(story.getTitle());
        dateText.setText(story.getPublicationDate());
        teaserText.setText(story.getTeaser());
        return convertView;
    }



}