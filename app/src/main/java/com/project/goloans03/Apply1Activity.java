package com.project.goloans03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Apply1Activity extends AppCompatActivity {

    Button proceedbtn;
    EditText applyFName, applyLName;
    AdView mAdView;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply1);

        proceedbtn= findViewById(R.id.proceedBtn);
        applyFName= findViewById(R.id.applyFName);
        applyLName=findViewById(R.id.applyLName);
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        myad();

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();



        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = applyFName.getText().toString().trim();
                final String lname = applyLName.getText().toString().trim();
                if (fname.isEmpty()) {
                    applyFName.setError("First Name Field is Compulsory");
                } else if (lname.isEmpty()) {
                    applyLName.setError("Last Name is Compulsory ");
                } else {
                    userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fstore.collection("Applications").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("First Name", fname);
                    user.put("Last Name", lname);
                    user.put("Flag", flag);
                    documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(getApplicationContext(), Apply2Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Error " + e.toString());
                            Toast.makeText(Apply1Activity.this, e.toString() + " ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
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
