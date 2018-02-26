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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the top tracks category
        TextView topTracks = (TextView) findViewById(R.id.top_tracks);

        // Set a click listener on that View
        topTracks.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the top tracks category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link TrackActivity}
                Intent topTracksIntent = new Intent(MainActivity.this, TrackActivity.class);

                // Start the new activity
                startActivity(topTracksIntent);
            }
        });

        // Find the View that shows the top artists category
        TextView topArtists = (TextView) findViewById(R.id.top_artists);

        // Set a click listener on that View
        topArtists.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the top tracks category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ArtistActivity}
                Intent topArtistsIntent = new Intent(MainActivity.this, ArtistActivity.class);

                // Start the new activity
                startActivity(topArtistsIntent);
            }
        });

        // Find the View that shows the top tags category
        TextView topTags = (TextView) findViewById(R.id.top_tags);

        // Set a click listener on that View
        topTags.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the top tags category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link TagActivity}
                Intent topTagsIntent = new Intent(MainActivity.this, TagActivity.class);

                // Start the new activity
                startActivity(topTagsIntent);
            }
        });
    }
}
