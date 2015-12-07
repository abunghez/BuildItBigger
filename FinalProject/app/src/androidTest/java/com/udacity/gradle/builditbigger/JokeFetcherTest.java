package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrei on 07.12.2015.
 */
public class JokeFetcherTest extends AndroidTestCase {
    CountDownLatch signal;

    class MainActivityTest extends MainActivity {
        public String retrievedJoke;
        public void tellJoke() {
            JokeFetcher fetcher = new EndpointsFetcherTest();

            fetcher.execute();

        }
        class EndpointsFetcherTest extends EndpointsJokeFetcher {
            @Override
            protected void onPostExecute(String aString) {
                retrievedJoke = joke;
                signal.countDown();
            }
        }
    }

    public void testVerifyJokeFetcherAsyncTask() {

        MainActivityTest at;

        at = new MainActivityTest();



        for (int i = 0; i < 20; i++) {
            signal = new CountDownLatch(1);
            at.tellJoke();
            try {

                assertTrue(signal.await(10, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
                assertTrue(false);
            }


            assertTrue(at.retrievedJoke != null);
            assertFalse(at.retrievedJoke.equals(""));

        }
    }
}
