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

public class TagActivity extends AppCompatActivity {

    private String LOG_TAG = TagActivity.class.getSimpleName();
    private ListView listView;
    ArrayList<Tag> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_container);

        tagList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        new GetTag().execute();
    }

    private class GetTag extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //url to get the top trending tags
            String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key=42094536a8c1eae34d84df5f26daf2b7&format=json";


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
                    //get the JSONObject node and name it "tags"
                    JSONObject tags = jsonObject.getJSONObject("tags");
                    //get the JSONArray node and name it "tag"
                    JSONArray tag = tags.getJSONArray("tag");

                    // looping through all JSON objects in track
                    for (int i = 0; i < tag.length(); i++) {
                        //get the JSONObject and its three attributes
                        JSONObject currentTagItem = tag.getJSONObject(i);
                        String tagName = currentTagItem.getString("name");
                        String tagUrl = currentTagItem.getString("url");

                        //create a tag object with the parsed data
                        Tag tagObject = new Tag(tagName, tagUrl);

                        // adding a tag to our tag list
                        tagList.add(tagObject);
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
            TagAdapter tagAdapter = new TagAdapter(TagActivity.this, tagList, R.color.tag_listing);
            listView.setAdapter(tagAdapter);

            // Set a click listener to open the tag's www.last.fm webpage when the list item is clicked on
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Get the {@link Tag} object at the given position the user clicked on
                    Tag tag = tagList.get(position);
                    String tagUrl = tag.getTagUrl();
                    // Create a new intent to view the tag URI
                    Intent openWebPage = new Intent(Intent.ACTION_VIEW);
                    // Convert the String URL into a URI object (to set data on the Intent openWebPage)
                    openWebPage.setData(Uri.parse(tagUrl));
                    // Send the intent to launch a new activity
                    startActivity(openWebPage);
                }
            });
        }
    }
}
