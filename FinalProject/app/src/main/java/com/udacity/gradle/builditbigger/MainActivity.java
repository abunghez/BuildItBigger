package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bgz.jokedisplayerlibrary.JokeDisplayActivity;
import com.bgz.jokes.BoredomRepellent;


public class MainActivity extends ActionBarActivity {

    BoredomRepellent br;
    Button jokeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        br = new BoredomRepellent();
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

        JokeFetcher fetcher = new JokeFetcher();

        fetcher.execute();

    }

    class JokeFetcher extends AsyncTask<Void, Void, Void> {

        String joke;
        @Override
        protected Void doInBackground(Void... params) {

            //Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
            joke = br.getNextJoke();

             return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(MainActivity.this, JokeDisplayActivity.class);

            if (joke != null)
                i.putExtra(JokeDisplayActivity.EXTRA_JOKE, joke);
            else
                i.putExtra(JokeDisplayActivity.EXTRA_JOKE, getString(R.string.no_joke_found));

            startActivity(i);
            jokeButton.setEnabled(true);

        }
    }
}
