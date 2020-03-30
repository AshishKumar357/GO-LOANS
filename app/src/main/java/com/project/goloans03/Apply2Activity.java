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

public class Apply2Activity extends AppCompatActivity {

    Button NextBtn;
    AdView mAdView;
    EditText applyEmail,applyPhno,applyAddress,applyPincode,applyAdhno,applyPANno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply2);

        NextBtn= findViewById(R.id.proceedBtn2);
        applyEmail= findViewById(R.id.applyEmail);
        applyPhno= findViewById(R.id.applyPhno);
        applyAddress= findViewById(R.id.applyAddress);
        applyPincode= findViewById(R.id.applyPincode);
        applyAdhno=findViewById(R.id.applyAdhno);
        applyPANno= findViewById(R.id.applyPANno);

        myAd();
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Apply2Activity.this,Apply3Activity.class));

            }
        });

    }

    private void myAd() {
        mAdView = findViewById(R.id.adView4);
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
                Toast.makeText(Apply2Activity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Apply2Activity.this,"Ad closed",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad Closed");
            }
        });
    }
}
