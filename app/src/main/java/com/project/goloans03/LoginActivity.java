package com.project.goloans03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText inpEmail, inpPwd;
    TextView unregUser;
    ProgressBar progressBar;
    FirebaseAuth fauth;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inpEmail= findViewById(R.id.logemail);
        inpPwd= findViewById(R.id.logPwd);
        unregUser= findViewById(R.id.unregisted_txt);
        progressBar= findViewById(R.id.progressBarlog);
        loginBtn= findViewById(R.id.login_btn);

        fauth= FirebaseAuth.getInstance();

        unregUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inpEmail.getText().toString().trim();
                String pwd= inpPwd.getText().toString().trim();

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

                fauth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in SuccessFully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

    }
}
