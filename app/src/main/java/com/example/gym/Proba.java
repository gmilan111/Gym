package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Proba extends AppCompatActivity {

    BottomNavigationView nav;
    RecyclerView recview;
    ArrayList<model> datalist;
    FirebaseFirestore db;
    myadapter adapter;
    FloatingActionButton fab;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba);

        nav = findViewById(R.id.bottomNaqvigationView);
        nav.getMenu().findItem(R.id.home).setChecked(true);
        fab = findViewById(R.id.fab);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        recview=findViewById(R.id.recview);
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter(this, datalist);
        recview.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recview);
        showData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.home:
                        Intent intent3 = new Intent(getApplicationContext(), Proba.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.food:
                        Intent intent4 = new Intent(getApplicationContext(), Food.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.profil:
                        Intent intent = new Intent(getApplicationContext(), Logout.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.stopwatch:
                        Intent intent2 = new Intent(getApplicationContext(), Stopwatch.class);
                        startActivity(intent2);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
    public void showData(){
        db.collection("edzes").whereEqualTo("email",user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        datalist.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){

                            model model = new model(snapshot.getString("id") , snapshot.getString("gyakorlatNev") , snapshot.getString("ismetlesSzam"), snapshot.getString("maxSuly"), snapshot.getString("sorozatSzam"));
                            datalist.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Proba.this, "HIBA", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}