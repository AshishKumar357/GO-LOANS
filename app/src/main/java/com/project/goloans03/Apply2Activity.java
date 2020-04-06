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
import java.util.regex.Pattern;

public class Apply2Activity extends AppCompatActivity {

    Button NextBtn;
    AdView mAdView;
    EditText applyEmail,applyPhno,applyAddress,applyPincode,applyAdhno,applyPANno;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;
    int flag = 2;
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
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        myAd();
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = applyEmail.getText().toString().trim();
                final String phno = applyPhno.getText().toString().trim();
                final String add = applyAddress.getText().toString();
                final String pincode = applyPincode.getText().toString().trim();
                final String aadharNo = applyAdhno.getText().toString().trim();
                final String panNo = applyPANno.getText().toString().trim();

                if (email.isEmpty() || isEmailValid(email)) {
                    applyEmail.setError("Email is Badly Formatted");

                } else if (phno.isEmpty() || isValidMobile(phno)) {
                    applyPhno.setError("Mobile Number Badly Formatted");

                } else if (add.isEmpty()) {
                    applyAddress.setError("Address Field is Compulsory ");

                } else if (pincode.isEmpty() || isValidPincode(pincode)) {
                    applyPincode.setError("Please Enter a valid Pincode");
                } else if (aadharNo.isEmpty() || isValidAadhaarNo(aadharNo)) {
                    applyPhno.setError("Please Enter a valid Aadhar Number");

                } else if (panNo.isEmpty()) {

                    applyAddress.setError("PAN number Field is Compulsory ");
                } else {
                    userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fstore.collection("Applications").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Email", email);
                    user.put("Phone Number", phno);
                    user.put("Address", add);
                    user.put("Pincode", pincode);
                    user.put("Aadhaar Number", aadharNo);
                    user.put("PAN Number", panNo);
                    user.put("Flag", flag);
                    documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(getApplicationContext(), Apply3Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Error " + e.toString());
                            Toast.makeText(Apply2Activity.this, e.toString() + " ", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 9;
        }
        return false;
    }

    private boolean isValidPincode(String pin) {
        if (!Pattern.matches("[a-zA-Z]+", pin)) {
            return pin.length() == 5;
        }
        return false;
    }

    private boolean isValidAadhaarNo(String adno) {
        if (!Pattern.matches("[a-zA-Z]+", adno)) {
            return adno.length() == 10;
        }
        return false;
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
