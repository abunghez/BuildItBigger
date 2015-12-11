package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by andrei on 10.12.2015.
 */
public class MainFreeActivity extends MainActivity {

    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7539375348569671/4268157944");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(MainFreeActivity.this, "Ad closed", Toast.LENGTH_LONG).show();
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Toast.makeText(MainFreeActivity.this, "Ad failed to load", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Toast.makeText(MainFreeActivity.this, "Ad left application", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Toast.makeText(MainFreeActivity.this, "Ad opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(MainFreeActivity.this, "Ad loaded", Toast.LENGTH_LONG).show();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("6B8DCB6D6911B199BF792EC2015AB93B")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    protected void startFetching() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        super.startFetching();
    }
}
