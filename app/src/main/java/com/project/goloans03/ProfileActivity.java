package com.project.goloans03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView uname,uemail,uphno;
    Button logoutbtn;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userID;
    AdView mAdView, mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uname = findViewById(R.id.uname);
        uemail= findViewById(R.id.uemail);
        uphno= findViewById(R.id.uphno);
        logoutbtn=findViewById(R.id.logoutbtn);
        mAdView = findViewById(R.id.adViewprofile1);
        mAdView2 = findViewById(R.id.adViewprofile2);

        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();
        myad();
        DocumentReference documentReference= fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                uphno.setText(documentSnapshot.getString("PhoneNo"));
                uemail.setText(documentSnapshot.getString("Email"));
                uname.setText(documentSnapshot.getString("FullName"));
            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    private void myad() {

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("TAG", "Ad loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG", "Ad failed to load");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("TAG", "Ad opened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(ProfileActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Ad clicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("TAG", "Adleftapplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(ProfileActivity.this, "Ad closed", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Ad Closed");
            }
        });
        mAdView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("TAG", "Ad loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG", "Ad failed to load");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("TAG", "Ad opened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(ProfileActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Ad clicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("TAG", "Adleftapplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(ProfileActivity.this, "Ad closed", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Ad Closed");
            }
        });
    }
}

