package com.bgz.jokes;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class BoredomRepellent {
    private static final String JOKE_URL="http://api.icndb.com/jokes/random";
    private static final int JOKE_CACHE_CHUNK=10;

    private ArrayList<String> jokeCache;
    private Iterator<String> jokeIterator;
    private BoredomRepellent() {
        jokeCache = new ArrayList<String>();
        jokeIterator = jokeCache.iterator();
    }

    public String getNextJoke() {
        String randomJoke;

        if (jokeIterator.hasNext())
            return jokeIterator.next();

        // no more jokes in this iterator, download a new bunch

        // the old cache is not needed anymore, so clear it
        jokeCache.clear();

        for (int i = 0; i < JOKE_CACHE_CHUNK; i++)
        {
            randomJoke = getRandomJoke();
            jokeCache.add(randomJoke);
        }


        return jokeIterator.next();
    }

    private String getRandomJoke() {

        JSONObject json;

        HttpURLConnection con;
        URL url;
        BufferedReader br;
        String line, jsonText, joke;
        try {
            url = new URL(JOKE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            con = (HttpURLConnection) url.openConnection();
            con.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            con.disconnect();
            return null;
        }

        jsonText = "";

        try {
            while ((line=br.readLine()) != null) {
                jsonText = jsonText+line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            con.disconnect();
            return null;
        }

        con.disconnect();
        json=new JSONObject(jsonText);

        json=json.getJSONObject("value");

        joke=json.getString("joke");


        return joke;

    }
}
