package com.example.android.musicinfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TrackActivity extends AppCompatActivity {

    private String LOG_TAG = TrackActivity.class.getSimpleName();
    private ListView listView;
    ArrayList<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_container);

        trackList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        new GetTrack().execute();
    }

    private class GetTrack extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //url to get the top trending tracks
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=42094536a8c1eae34d84df5f26daf2b7&format=json";


            String jsonString = "";
            try {
                //make a request to the URL
                jsonString = sh.makeHttpRequest(createUrl(url));

            } catch (IOException e) {
                return null;
            }

            Log.e(LOG_TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    //create a new JSONObject
                    JSONObject jsonObject = new JSONObject(jsonString);
                    //get the JSONObject node and name it "tracks"
                    JSONObject tracks = jsonObject.getJSONObject("tracks");
                    //get the JSONArray node and name it "track"
                    JSONArray track = tracks.getJSONArray("track");

                    // looping through all JSON objects in track
                    for (int i = 0; i < track.length(); i++) {
                        //get the JSONObject and its three attributes
                        JSONObject currentTrackItem = track.getJSONObject(i);
                        String trackName = currentTrackItem.getString("name");
                        String artistName = (currentTrackItem.getJSONObject("artist")).getString("name");
                        //the image JSON array at index 2 contains the url to a large size artist image
                        String artistImageUrl = ((currentTrackItem.getJSONArray("image")).getJSONObject(2).getString("#text"));

                        //create a track object with the parsed data
                        Track trackObject = new Track(trackName, artistName, artistImageUrl);

                        // adding a track to our track list
                        trackList.add(trackObject);
                    }
                } catch (final JSONException e) {
                    Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(LOG_TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TrackAdapter trackAdapter = new TrackAdapter(TrackActivity.this, trackList, R.color.track_listing);
            listView.setAdapter(trackAdapter);

            // Set a click listener to open Youtube to play the track when the list item is clicked on
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Get the {@link Track} object at the given position the user clicked on
                    Track track = trackList.get(position);
                    //create a search term by concatenating the track and artist name to search in
                    //Youtube
                    String trackSearchTerm = track.getTrackName() + " " + track.getArtistName();

                    new YoutubeVideoIdTask(TrackActivity.this, trackSearchTerm).execute();


                }
            });
        }
    }

    private class YoutubeVideoIdTask extends AsyncTask<Void, Void, Void> {

        private String LOG_TAG = YoutubeVideoIdTask.class.getSimpleName();
        Context mContext;
        String mQueryParameter;
        String videoId = "";

        public YoutubeVideoIdTask(Context context, String queryParameter) {
            this.mContext = context;
            this.mQueryParameter = queryParameter;
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //url to get video information for the track and artist name in the query parameter
            String url = "https://www.googleapis.com/youtube/v3/search?part=id&q=" + this.mQueryParameter + "&type=video&maxResults=1&key=AIzaSyDLfAxroAD2RgevLHHR59O2-oPYINqqS00";
            String jsonString = "";

            try {
                //make a request to the URL
                jsonString = sh.makeHttpRequest(createUrl(url));

            } catch (IOException e) {
                return null;
            }

            Log.e(LOG_TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    //create a new JSONObject
                    JSONObject jsonObject = new JSONObject(jsonString);
                    //get the JSONArray node and name it "tracks"
                    JSONArray itemsArray = jsonObject.getJSONArray("items");

                    if (itemsArray.length() >= 1) {
                        JSONObject item = itemsArray.getJSONObject(0);
                        JSONObject id = item.getJSONObject("id");
                        videoId = id.getString("videoId");
                    }
                } catch (final JSONException e) {
                    Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(LOG_TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }
        protected void onPostExecute(Void result) {
            /*the following block of code was copied from
            https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
            and was modified a bit to fit the MusicInfo app's context
            */
            if (!videoId.isEmpty()) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + videoId));
                try {
                    this.mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    this.mContext.startActivity(webIntent);
                }
            }
        }
    }
}
