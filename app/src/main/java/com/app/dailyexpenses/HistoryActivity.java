package com.app.dailyexpenses;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class HistoryActivity extends AppCompatActivity {
        private DrawerLayout dl;
        private ActionBarDrawerToggle t;
        private NavigationView nv;
        private TextView title,textView;
        String[] title_send;
        String[] text;

        private FirebaseDatabase firebaseDatabase;
        ListAdapter list_adapter;
        ListView lv;
        private DatabaseReference databaseReference;
        private FloatingActionButton floatingActionButton;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_history);
                ActionBar actionBar=getSupportActionBar();
                actionBar.setTitle("Transaction History");
                dl = (DrawerLayout)findViewById(R.id.activity_history);
                t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
                floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        }
                });
                title =(TextView)findViewById(R.id.title);
                textView=(TextView)findViewById(R.id.textView);

                String Uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference=FirebaseDatabase.getInstance().getReference(Uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int i=0;
                                long n= snapshot.child("send").getChildrenCount();
                                n=n+snapshot.child("received").getChildrenCount();
                                title_send= new String[(int) n];
                                text= new String[(int) n];
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                        String mode= ds.getKey();
                                        for (DataSnapshot ds2 : ds.getChildren()) {

                                                        int amount = ds2.child("amount").getValue(Integer.class);
                                                        String amt2 = String.valueOf(amount);
                                                        String date = ds2.child("date").getValue(String.class);
                                                        String person = ds2.child("person").getValue(String.class);
                                                        String reason = ds2.child("reason").getValue(String.class);
                                                        title_send[i]=mode;
                                                        String send_str="\namount :" + amt2 + "\nDate :" + date + "\nperson :" + person + "\nreason :" + reason;
                                                        text[i]=send_str;
                                                        i+=1;

                                        }
                                }
                                list_adapter=new ListAdapter(HistoryActivity.this,
                                        title_send,
                                        text);
                                lv= findViewById(R.id.lv);
                                lv.setAdapter(list_adapter);
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
                                                Toast.makeText(HistoryActivity.this, "Total",Toast.LENGTH_SHORT).show();break;
                                        case R.id.history:
                                                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                                                Toast.makeText(HistoryActivity.this, "History",Toast.LENGTH_SHORT).show();break;
                                        case R.id.logout:
                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                Toast.makeText(HistoryActivity.this, "Logout",Toast.LENGTH_SHORT).show();break;
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

