package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bgz.jokedisplayerlibrary.JokeDisplayActivity;
import com.bgz.jokes.jokesAPI.JokesAPI;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    //BoredomRepellent br;
    Button jokeButton;
    JokesAPI myApiService = null;
    ProgressBar spinner;
    String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //br = new BoredomRepellent();
        jokeButton = (Button) findViewById(R.id.jokeButton);
        spinner = (ProgressBar) findViewById(R.id.jokeLoadProgressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void startFetching() {
        JokeFetcher fetcher = new EndpointsJokeFetcher(getString(R.string.endpoint_url));
        fetcher.execute();

    }
    public void tellJoke(View view){
        jokeButton.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);

        startFetching();
    }

    protected boolean canDisplayJoke() {
        return true;
    }
    protected void startDisplayerActivity() {
        Intent i = new Intent(MainActivity.this, JokeDisplayActivity.class);

        if (!canDisplayJoke())
            return;
        if (mJoke != null)
            i.putExtra(JokeDisplayActivity.EXTRA_JOKE, mJoke);
        else
            i.putExtra(JokeDisplayActivity.EXTRA_JOKE, getString(R.string.no_joke_found));

        startActivity(i);
        jokeButton.setEnabled(true);
        spinner.setVisibility(View.INVISIBLE);
    }

    class JokeFetcher extends AsyncTask<Pair<Context, String>, Void, String> {


        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            //Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
            //mJoke = br.getNextJoke();

            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);

            startDisplayerActivity();
        }
    }

    public class EndpointsJokeFetcher extends JokeFetcher {

        String mUrl;

        public EndpointsJokeFetcher(String url) {
            mUrl = url;
        }


        @Override
        protected String doInBackground(Pair<Context, String>... params) {


            if (myApiService == null) {

                if (mUrl == null) {
                    mJoke=null;
                    return null;
                }
                JokesAPI.Builder builder = new JokesAPI.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                        .setRootUrl(mUrl);
                myApiService = builder.build();
            }

            try {
                mJoke =  myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
                mJoke = null;
            }
            return null;
        }
    }

}
