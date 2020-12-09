package com.app.dailyexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editEmail, editPassword;
    Button btn_login;
    private String email, password;
    String check,check_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        mAuth = FirebaseAuth.getInstance();
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=editEmail.getText().toString().trim();
                String password=editPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                            Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    };
                                }

                        });

            }
        });
    }
    public void btn_signup(View view) {
        startActivity(new Intent(getApplicationContext(),SignupActivity.class));
    }
}
