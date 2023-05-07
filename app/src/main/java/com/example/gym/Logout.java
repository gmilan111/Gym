package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class Logout extends AppCompatActivity {
    BottomNavigationView nav;
    FloatingActionButton fab;
    Button button;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView felh, email;
    FirebaseFirestore db;
    ArrayList<model_felhasznalo> datalist;
    myadapter adapter;
    Proba proba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        auth = FirebaseAuth.getInstance();
        nav = findViewById(R.id.bottomNaqvigationView);
        nav.getMenu().findItem(R.id.profil).setChecked(true);
        fab = findViewById(R.id.fab);
        button = findViewById(R.id.logout_bt);
        user = auth.getCurrentUser();
        felh = findViewById(R.id.felhasznalonev);
        email = findViewById(R.id.email_logout);
        db=FirebaseFirestore.getInstance();
        datalist=new ArrayList<>();

        db.collection("felhasznalo").whereEqualTo("email",user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        datalist.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            model_felhasznalo model = new model_felhasznalo(snapshot.getString("felhasznalonev"), snapshot.getString("email"), snapshot.getString("jelszo"));
                            felh.setText("Felhasználónév: "+model.getFelhasznalonev());
                            email.setText("Email: "+model.getEmail());
                        }
                    }
                });

        if(user==null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

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
}