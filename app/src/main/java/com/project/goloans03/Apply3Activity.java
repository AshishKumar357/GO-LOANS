package com.project.goloans03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Apply3Activity extends AppCompatActivity {

    Spinner genderSpinner;
    AdView mAdView;
    Button proBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply3);

        proBtn=findViewById(R.id.button);
        myaddview();
        genderSpinner();

        proBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Apply3Activity.this,
                        "OnClickListener : " + "\n Gender Spinner : "+ genderSpinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void genderSpinner() {

        genderSpinner= findViewById(R.id.spinnerGender);
        ArrayAdapter<String> genderadapter=  new ArrayAdapter<>(Apply3Activity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderadapter);
    }
    private void myaddview() {
        mAdView = findViewById(R.id.adView5);
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
                Toast.makeText(Apply3Activity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Apply3Activity.this,"Ad closed",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad Closed");
            }
        });
    }
}
