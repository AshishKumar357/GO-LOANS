package com.project.goloans03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Apply1Activity extends AppCompatActivity {

    Button proceedbtn;
    EditText applyFName, applyLName;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply1);

        proceedbtn= findViewById(R.id.proceedBtn);
        applyFName= findViewById(R.id.applyFName);
        applyLName=findViewById(R.id.applyLName);
        myad();

        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Apply1Activity.this, Apply2Activity.class));
            }
        });
    }

    private void myad() {
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("TAG","Ad loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG","Ad failed to load");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("TAG","Ad opened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(Apply1Activity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad clicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("TAG","Adleftapplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(Apply1Activity.this,"Ad closed",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad Closed");
            }
        });


    }


}
