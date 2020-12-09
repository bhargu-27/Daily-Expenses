package com.app.dailyexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText txt_fullname, txt_email, txt_password, txt_confirm_password, txt_mobile;

    RadioButton txt_male, txt_female;
    Button btn_signup;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Signup Form");
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("member");
        txt_fullname = (EditText) findViewById(R.id.txt_fullname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);
        txt_female = (RadioButton) findViewById(R.id.txt_female);
        txt_mobile = (EditText) findViewById(R.id.txt_mobile);
        txt_male = (RadioButton) findViewById(R.id.txt_male);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname = txt_fullname.getText().toString().trim();
                final String email = txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();
                String confirmpassword = txt_confirm_password.getText().toString().trim();
                final String mobile = txt_mobile.getText().toString().trim();
                if (txt_female.isChecked()) {
                    gender = "Female";
                }
                if (txt_male.isChecked()) {
                    gender = "Male";
                }
                if (TextUtils.isEmpty(fullname)) {
                    Toast.makeText(SignupActivity.this, "Please enter valid name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignupActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(SignupActivity.this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(confirmpassword)) {
                    Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                if (!password.equals(confirmpassword)) {
                    Toast.makeText(SignupActivity.this, "Password isn't matching", Toast.LENGTH_SHORT).show();
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Member member = new Member(fullname, email, gender, mobile);
                                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignupActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                }
                                else{
                                    Toast.makeText(SignupActivity.this, "Task Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
