package com.project.goloans03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    TextView uname,uemail,uphno;
    Button logoutbtn;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uname = findViewById(R.id.uname);
        uemail= findViewById(R.id.uemail);
        uphno= findViewById(R.id.uphno);
        logoutbtn=findViewById(R.id.logoutbtn);

        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fauth.getCurrentUser().getUid();

        DocumentReference documentReference= fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
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
}
