package com.project.goloans03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Apply3Activity extends AppCompatActivity {

    private static final String TAG = "a3";
    Spinner genderSpinner, spinnerSalaryType, spinnerLoanType;
    AdView mAdView;
    Button proBtn;
    EditText msalary, LAmount, LInterest, dob;
    String temp;
    CheckBox cbShare, cbPromo;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;
    int flag = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply3);

        dob = findViewById(R.id.applyDOB);
        genderSpinner = findViewById(R.id.spinnerGenderApply);
        spinnerSalaryType = findViewById(R.id.spinnerSalaryType);
        spinnerLoanType = findViewById(R.id.spinnerLoanType);
        proBtn = findViewById(R.id.SubmitBtn);
        LAmount = findViewById(R.id.LAmount);
        msalary = findViewById(R.id.applySalary);
        LInterest = findViewById(R.id.LInterest);
        cbShare = findViewById(R.id.ShareCheckBox);
        cbPromo = findViewById(R.id.PromotionCheckBox);
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        myaddview();
        genderSpinner();
        salaryTypeSpinner();
        loanTypeSpinner();

        proBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gender = genderSpinner.getSelectedItem().toString();
                final String dateofbirth = dob.getText().toString().trim();
                final String msal = msalary.getText().toString().trim();
                final String saltype = spinnerSalaryType.getSelectedItem().toString();
                final String loantype = spinnerLoanType.getSelectedItem().toString();
                final String lamount = LAmount.getText().toString().trim();
                final String linterest = LInterest.getText().toString().trim();

                if (gender.equals("Gender")) {
                    Toast.makeText(Apply3Activity.this, "Select a gender", Toast.LENGTH_SHORT).show();

                } else if (dateofbirth.isEmpty()) {
                    dob.setError("Monthly salary is Compulsory to fill");

                } else if (msal.isEmpty()) {
                    msalary.setError("Monthly salary is Compulsory to fill");

                } else if (saltype.equals("Salary Type")) {
                    Toast.makeText(Apply3Activity.this, "Select a your Salary Type", Toast.LENGTH_SHORT).show();
                } else if (loantype.equals("Type Of Loan")) {
                    Toast.makeText(Apply3Activity.this, "Select a your Loan Type", Toast.LENGTH_SHORT).show();
                } else if (lamount.isEmpty()) {
                    LAmount.setError("Please Enter a Loan Amount");
                } else if (linterest.isEmpty()) {
                    LInterest.setError("Please enter a number for preferred interest on loan ");
                } else if (!cbShare.isChecked() || !cbPromo.isChecked()) {
                    Toast.makeText(Apply3Activity.this, "Please check the Terms and conditions", Toast.LENGTH_SHORT).show();
                } else {
                    userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fstore.collection("Applications").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Gender", gender);
                    user.put("Date Of Birth", dateofbirth);
                    user.put("Monthly Salary", msal);
                    user.put("Type Of Salary", saltype);
                    user.put("Type Of Loan", loantype);
                    user.put("Loan Amount", lamount);
                    user.put("Interest", linterest);
                    user.put("Flag", flag);

                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(getApplicationContext(), BlogActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Error " + e.toString());
                            Toast.makeText(Apply3Activity.this, e.toString() + " ", Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(Apply3Activity.this,
                            "YOU APPLICATION IS SUBMITTED", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
    private void genderSpinner() {
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(Apply3Activity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }

    private void salaryTypeSpinner() {
        ArrayAdapter<String> SalaryTypeAdapter = new ArrayAdapter<>(Apply3Activity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.salary_type));
        SalaryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSalaryType.setAdapter(SalaryTypeAdapter);
    }

    private void loanTypeSpinner() {
        ArrayAdapter<String> LoanTypeAdapter = new ArrayAdapter<>(Apply3Activity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.loan_type));
        LoanTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoanType.setAdapter(LoanTypeAdapter);
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
