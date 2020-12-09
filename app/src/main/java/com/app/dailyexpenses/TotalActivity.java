package com.app.dailyexpenses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TotalActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    DatabaseReference databaseReference;
    TextView textView,textView2;
    private NavigationView nv;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Total Transactions");
        dl = (DrawerLayout)findViewById(R.id.activity_total);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        String Uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference(Uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int total_s=0,total_r=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    String mode= ds.getKey();
                    if(mode.equals("send"))
                    {
                        for(DataSnapshot ds2:ds.getChildren()) {

                            int amount = ds2.child("amount").getValue(Integer.class);
                            total_s+=amount;
                        }
                    }
                    if(mode.equals("received"))
                    {

                        for(DataSnapshot ds2:ds.getChildren()) {
                            int amount = ds2.child("amount").getValue(Integer.class);
                            total_r+=amount;
                        }
                    }
                }
                String total_spent=String.valueOf(total_s);
                textView.append("\t\t\t\t\t\t\t\t\tTotal spent \n\t\t\t\t\t\t\t\t\t"+total_spent);
                String total_received=String.valueOf(total_r);
                textView2.append("\t\t\t\t\t\t\t\t\tTotal received \n\t\t\t\t\t\t\t\t\t"+total_received);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.total:
                        startActivity(new Intent(getApplicationContext(), TotalActivity.class));
                        Toast.makeText(TotalActivity.this, "Total",Toast.LENGTH_SHORT).show();break;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        Toast.makeText(TotalActivity.this, "History",Toast.LENGTH_SHORT).show();break;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(TotalActivity.this, "Logout",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}

