/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.musicinfo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link TrackAdapter} is an {@link ArrayAdapter} that can provide the layout for each track list item
 * based on a data source, which is a list of {@link Track} objects.
 */
public class TrackAdapter extends ArrayAdapter<Track> {

    /**
     * Resource ID for the background color for this list of tracks
     */
    private int mColorResourceId;

    /**
     * Create a new {@link TrackAdapter} object.
     *
     * @param context         is the current context (i.e. Activity) that the adapter is being created in.
     * @param trackList       is the list of {@link Track}s to be displayed.
     * @param colorResourceId is the resource ID for the background color for this list of tracks
     */
    public TrackAdapter(Context context, ArrayList<Track> trackList, int colorResourceId) {
        super(context, 0, trackList);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.track_item, parent, false);
        }

        // Get the {@link Track} object located at this position in the list
        Track currentTrack = getItem(position);

        // Find the TextView in the track_item.xml layout with the ID track_name_view.
        TextView trackNameTextView = (TextView) listItemView.findViewById(R.id.track_name_text_view);
        trackNameTextView.setText(currentTrack.getTrackName());

        // Find the TextView in the track_item.xml layout with the ID artist_name_text_view.
        TextView artistNameTextView = (TextView) listItemView.findViewById(R.id.artist_name_text_view);
        artistNameTextView.setText(currentTrack.getArtistName());

        // Find the ImageView in the list_item.xml layout with the ID artist_image_view.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.artist_image_view);
        // Download and display the image based on the resource ID
        new DownloadImageTask(imageView).execute(currentTrack.getImageUrl());
        // Make sure the view is visible
        imageView.setVisibility(View.VISIBLE);


        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout so that it can be shown in the ListView.
        return listItemView;
    }

}