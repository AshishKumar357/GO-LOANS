package com.project.goloans03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    Button profilebtn, applybtn;
    AdView mAdView,mAdView2;
    private static final String TAG = "TAGGG";
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;
    Long var;
    String flag = "Flag";
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applybtn = findViewById(R.id.applyBtn);
        profilebtn=findViewById(R.id.profile_btn);
        mAdView = findViewById(R.id.adViewMain1);
        mAdView2 = findViewById(R.id.adViewMain2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i("TAG","Initialization");
            }
        });
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4198122205168696~1070741929");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        myad();

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();


        applybtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                DocumentReference documentReference = fstore.collection("Applications").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            if (document.exists()) {
                                var = (Long) document.get("Flag");
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData() + var);
                                if (var.equals(Long.valueOf("1"))) {
                                    Intent intent = new Intent(getApplicationContext(), Apply2Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else if (var.equals(Long.valueOf("2"))) {
                                    Intent intent = new Intent(getApplicationContext(), Apply3Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else if (var.equals(Long.valueOf("2"))) {
                                    Toast.makeText(getApplicationContext(), "You Have already Applied For Loan", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(MainActivity.this, Apply1Activity.class));
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

//                        MainActivity.this, new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                        assert documentSnapshot != null;
//                        if(Objects.equals(documentSnapshot.getString(flag), "1"))
//                        {
//                            Intent intent = new Intent(getApplicationContext(),Apply2Activity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//                        else if(Objects.equals(documentSnapshot.getString(flag), "2"))
//                        {
//                            Intent intent = new Intent(getApplicationContext(),Apply3Activity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }else if(Objects.equals(documentSnapshot.getString(flag), "3"))
//                        {
//                            Toast.makeText(getApplicationContext(),"You Have already Applied For Loan",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//                        else
//                            startActivity(new Intent(MainActivity.this, Apply1Activity.class));
//                    }
//                });

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
                Toast.makeText(MainActivity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this,"Ad closed",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad Closed");
            }
        });
        mAdView2.setAdListener(new AdListener() {
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
                Toast.makeText(MainActivity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this,"Ad closed",Toast.LENGTH_SHORT).show();
                Log.d("TAG","Ad Closed");
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void openProfileActivity(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
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
                    Toast.makeText(MainActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "Ad closed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
    }

    public void lendMoney(View view) {
        Intent intent = new Intent(MainActivity.this, Lend1Activity.class);
        startActivity(intent);
    }

    public void OpenBlog(View view) {
        Intent intent = new Intent(MainActivity.this, BlogActivity.class);
        startActivity(intent);
    }
}
