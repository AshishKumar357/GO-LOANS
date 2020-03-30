package com.project.goloans03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText inpName, inpEmail,inpPhno, inpPwd;
    Button regBtn;
    TextView registeredText;
    ProgressBar progressBar;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inpName=findViewById(R.id.inp_name);
        inpEmail=findViewById(R.id.inp_email);
        inpPhno=findViewById(R.id.inp_phno);
        inpPwd=findViewById(R.id.inp_pwd);

        regBtn=findViewById(R.id.register_btn);
        registeredText=findViewById(R.id.registed_txt);
        progressBar=findViewById(R.id.progressBar);

        fauth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


        if(fauth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registeredText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inpEmail.getText().toString().trim();
                String pwd= inpPwd.getText().toString().trim();
                final String fname = inpName.getText().toString();
                final String mphno = inpPhno.getText().toString().trim();

                if(email.isEmpty())
                {
                    inpEmail.setError("Email is Required");
                    return;
                }if(pwd.isEmpty())
                {
                    inpPwd.setError("Password is Required");
                    return;
                }if(pwd.length()< 6)
                {
                    inpPwd.setError("Password must be more that 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_SHORT).show();

                            userID=fauth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("FullName",fname);
                            user.put("Email", email);
                            user.put("PhoneNo", mphno);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess: user profile created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: Error "+ e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error: "+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });

            }
        });


    }
}
