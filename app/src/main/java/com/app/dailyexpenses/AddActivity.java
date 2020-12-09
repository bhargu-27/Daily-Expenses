package com.app.dailyexpenses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText txt_amount;
    EditText txt_person;
    EditText txt_reason;
    TextView txt_date;
    Button btn_sent,btn_received;
    DatePickerDialog dp;
    String db_date;

    String Uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(Uid);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Transaction");
        txt_amount = (EditText) findViewById(R.id.txt_amount);
        txt_person = (EditText) findViewById(R.id.txt_person);
        txt_reason = (EditText) findViewById(R.id.txt_reason);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR); // current year
                final int mMonth = c.get(Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                dp = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txt_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                db_date= String.valueOf(dayOfMonth)+String.valueOf(monthOfYear+1)+String.valueOf(year);
                            }
                        }, mYear, mMonth, mDay);
                dp.show();
            }
        });

        btn_sent = (Button) findViewById(R.id.btn_sent);
        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t_amount = txt_amount.getText().toString();
                final int amount=Integer.parseInt(t_amount);
                final String person  = txt_person.getText().toString().trim();
                final String reason = txt_reason.getText().toString().trim();
                final String date = txt_date.getText().toString().trim();

                Expense expense= new Expense(amount,person,reason,date);
                String key=databaseReference.child("send").push().getKey();
                databaseReference.child("send").child(key).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddActivity.this, "Expense added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        });
        btn_received = (Button) findViewById(R.id.btn_received);
        btn_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t_amount = txt_amount.getText().toString();
                final int amount=Integer.parseInt(t_amount);
                final String person  = txt_person.getText().toString().trim();
                final String reason = txt_reason.getText().toString().trim();
                final String date = txt_date.getText().toString().trim();
                Expense expense= new Expense(amount,person,reason,date);
                String key=databaseReference.child("received").push().getKey();
                databaseReference.child("received").child(key).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddActivity.this, "Expense added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        });
    }
}
