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

import com.bgz.jokedisplayerlibrary.JokeDisplayActivity;
import com.bgz.jokes.jokesAPI.JokesAPI;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    //BoredomRepellent br;
    Button jokeButton;
    JokesAPI myApiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //br = new BoredomRepellent();
        jokeButton = (Button) findViewById(R.id.jokeButton);
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

    public void tellJoke(View view){


        jokeButton.setEnabled(false);

        JokeFetcher fetcher = new EndpointsJokeFetcher();

        fetcher.execute();

    }

    class JokeFetcher extends AsyncTask<Pair<Context, String>, Void, String> {

        String joke;
        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            //Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
            //joke = br.getNextJoke();

            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);
            Intent i = new Intent(MainActivity.this, JokeDisplayActivity.class);

            if (joke != null)
                i.putExtra(JokeDisplayActivity.EXTRA_JOKE, joke);
            else
                i.putExtra(JokeDisplayActivity.EXTRA_JOKE, getString(R.string.no_joke_found));

            startActivity(i);
            jokeButton.setEnabled(true);

        }
    }

    class EndpointsJokeFetcher extends JokeFetcher {

        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            if (myApiService == null) {
                JokesAPI.Builder builder = new JokesAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://192.168.1.4:8080/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                myApiService = builder.build();
            }

            try {
                joke =  myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
                joke = null;
            }
            return null;
        }
    }

}
