package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Add extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab;
    EditText gyak_nev, sor_szam, ism_szam, pr;
    String id,gyak_nev1, sor_szam1, ism_szam1, pr1;
    TextView cim;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nav = findViewById(R.id.bottomNaqvigationView);
        fab = findViewById(R.id.fab);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        gyak_nev = findViewById(R.id.gyakorlat_nev);
        sor_szam = findViewById(R.id.sorozat_szam);
        ism_szam = findViewById(R.id.ismetles_szam);
        pr = findViewById(R.id.max_suly);
        cim = findViewById(R.id.addTitle);

        firestore=FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            cim.setText("Módosítás");
            id=bundle.getString("id");
            gyak_nev1=bundle.getString("gyakorlatNev");
            ism_szam1=bundle.getString("ismetlesSzam");
            pr1=bundle.getString("maxSuly");
            sor_szam1=bundle.getString("sorozatSzam");
        }else{
            cim.setText("Létrehozás");
        }

        gyak_nev.setText(gyak_nev1);
        ism_szam.setText(ism_szam1);
        pr.setText(pr1);
        sor_szam.setText(sor_szam1);

        notificationHandler = new NotificationHandler(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gyaknev = gyak_nev.getText().toString();
                String ism = ism_szam.getText().toString();
                String sor = sor_szam.getText().toString();
                String maxsuly = pr.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id2 = id;
                    updateToFireStore(id2, gyaknev, ism, sor, maxsuly);
                }else{
                    String id2 = UUID.randomUUID().toString();
                    saveToFireStore(id2, gyaknev, ism, sor, maxsuly);
                }

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

    private void updateToFireStore(String id , String gyaknev , String ism, String sor, String maxsuly){

        firestore.collection("edzes").document(id).update("gyakorlatNev" , gyaknev , "ismetlesSzam" , ism, "maxSuly", maxsuly, "sorozatSzam", sor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Add.this, "Adat módosítva!", Toast.LENGTH_SHORT).show();
                            notificationHandler.send("Adat módosítva lett!");
                            Intent intent = new Intent(getApplicationContext(), Proba.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(Add.this, "Hiba!" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void saveToFireStore(String id , String gyaknev , String ism, String sor, String maxSuly){

        String email = user.getEmail();
        if (!gyaknev.isEmpty() && !ism.isEmpty() && !sor.isEmpty() && !maxSuly.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("gyakorlatNev" , gyaknev);
            map.put("ismetlesSzam" , ism);
            map.put("maxSuly" , maxSuly);
            map.put("sorozatSzam" , sor);
            map.put("email", email);

            firestore.collection("edzes").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Add.this, "Adat elmentve!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Proba.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add.this, "HIBA!!!", Toast.LENGTH_SHORT).show();
                        }
                    });


        }else
            Toast.makeText(this, "Mindent ki kell tölteni", Toast.LENGTH_SHORT).show();
    }
}