package com.example.android.githubreposearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);


    }

    //Create a method called makeGithubSearchQuery

    public void makeGithubSearchQuery (){
    //Within this method, build the URL with the text from the EditText and set the built URL to the TextView
        String gitHubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchURL = NetworkUtils.buildUrl(gitHubQuery);
        mUrlDisplayTextView.setText(githubSearchURL.toString());
    //Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        String gitHubSearchResults = null;
        //Surround the call to getResponseFromHttpUrl with a try / catch block to catch an IOException

      new GithubQueryTask().execute(githubSearchURL);
    }



    private class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            // Create URL object
            URL searchUrl = urls[0];
            String githubSearchResults = null;


            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
               e.printStackTrace();
            }


            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return githubSearchResults;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         *
         */
        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                mSearchResultsTextView.setText(s);
                return;
            }
        }


    }

    //Display or Menu; See Menus form Android Developer console
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemThatWasSelected = item.getItemId();

        if  (menuItemThatWasSelected == R.id.action_search){

            // Call makeGithubSearchQuery when the search menu item is clicked
            makeGithubSearchQuery();
            return true;
        }

        return super.onContextItemSelected(item);

    }
}
