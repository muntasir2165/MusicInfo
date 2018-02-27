package com.example.android.musicinfo;

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

public class ArtistActivity extends AppCompatActivity {

    private String LOG_TAG = ArtistActivity.class.getSimpleName();
    private ListView listView;
    ArrayList<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_container);

        artistList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        new GetArtist().execute();
    }

    private class GetArtist extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //url to get the top trending artists
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=42094536a8c1eae34d84df5f26daf2b7&format=json";


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
                    //get the JSONObject node and name it "artists"
                    JSONObject artists = jsonObject.getJSONObject("artists");
                    //get the JSONArray node and name it "artist"
                    JSONArray artist = artists.getJSONArray("artist");

                    // looping through all JSON objects in track
                    for (int i = 0; i < artist.length(); i++) {
                        //get the JSONObject and its three attributes
                        JSONObject currentArtistItem = artist.getJSONObject(i);
                        String artistName = currentArtistItem.getString("name");
                        String artistUrl = currentArtistItem.getString("url");
                        //the image JSON array at index 2 contains the url to a large size artist image
                        String artistImageUrl = ((currentArtistItem.getJSONArray("image")).getJSONObject(2).getString("#text"));

                        //create an artist object with the parsed data
                        Artist artistObject = new Artist(artistName, artistUrl, artistImageUrl);

                        // adding an artist to our artist list
                        artistList.add(artistObject);
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
            ArtistAdapter artistAdapter = new ArtistAdapter(ArtistActivity.this, artistList, R.color.artist_listing);
            listView.setAdapter(artistAdapter);

            // Set a click listener to open the listView when the list item is clicked on
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Get the {@link Artist} object at the given position the user clicked on
                    Artist artist = artistList.get(position);
                    String artistUrl = artist.getArtistUrl();
                    // Create a new intent to view the artist URI
                    Intent openWebPage = new Intent(Intent.ACTION_VIEW);
                    // Convert the String URL into a URI object (to set data on the Intent openWebPage)
                    openWebPage.setData(Uri.parse(artistUrl));
                    // Send the intent to launch a new activity
                    startActivity(openWebPage);
                }
            });
        }
    }
}
