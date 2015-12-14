package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrei on 07.12.2015.
 */
public class JokeFetcherTest extends AndroidTestCase {
    CountDownLatch signal;

    class MainActivityTest extends MainActivity {

        private String mUrl;
        public String retrievedJoke;


        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public void tellJoke() {
            JokeFetcher fetcher = new EndpointsFetcherTest(mUrl);

            fetcher.execute();

        }
        class EndpointsFetcherTest extends EndpointsJokeFetcher {


            public EndpointsFetcherTest(String url) {
                super(url);
            }

            @Override
            protected void onPostExecute(String aString) {
                retrievedJoke = mJoke;
                signal.countDown();
            }
        }
    }

    public void testVerifyJokeFetcherAsyncTaskRemote() {

        MainActivityTest at;

        at = new MainActivityTest();
        at.setUrl(getContext().getString(R.string.endpoint_url));
        getJokes(at);

    }

    public void testVerifyJokeFetcherAsyncTaskLocally() {
        MainActivityTest at;

        at = new MainActivityTest();
        at.setUrl(getContext().getString(R.string.local_url));
        getJokes(at);

    }

    private void getJokes(MainActivityTest at) {

        for (int i = 0; i < 1; i++) {
            signal = new CountDownLatch(1);
            at.tellJoke();
            try {

                assertTrue(signal.await(20, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
                assertTrue(false);
            }


            assertTrue(at.retrievedJoke != null);
            assertFalse(at.retrievedJoke.equals(""));

        }

    }
}
