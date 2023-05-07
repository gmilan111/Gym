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

public class Add_food extends AppCompatActivity {
    BottomNavigationView nav;
    FloatingActionButton fab;
    EditText etel_nev, mennyiseg, kaloria;
    String id,etel_nev1, mennyiseg1, kaloria1;
    TextView cim;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        nav = findViewById(R.id.bottomNaqvigationView);
        fab = findViewById(R.id.fab);
        nav.getMenu().findItem(R.id.food).setChecked(true);

        etel_nev = findViewById(R.id.etel_nev);
        mennyiseg = findViewById(R.id.mennyiseg);
        kaloria = findViewById(R.id.kaloria_szam);
        cim = findViewById(R.id.addFood);

        firestore=FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            cim.setText("Módosítás");
            id=bundle.getString("id");
            etel_nev1=bundle.getString("etelNev");
            mennyiseg1=bundle.getString("mennyiseg");
            kaloria1=bundle.getString("kaloria");
        }else{
            cim.setText("Létrehozás");
        }

        etel_nev.setText(etel_nev1);
        mennyiseg.setText(mennyiseg1);
        kaloria.setText(kaloria1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String etelnev = etel_nev.getText().toString();
                String mennyiseg2 = mennyiseg.getText().toString();
                String kaloria2 = kaloria.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id2 = id;
                    updateToFireStore(id2, etelnev, mennyiseg2, kaloria2);
                }else{
                    String id2 = UUID.randomUUID().toString();
                    saveToFireStore(id2, etelnev, mennyiseg2, kaloria2);
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
    private void updateToFireStore(String id , String etelnev , String menny, String kal){

        firestore.collection("etel").document(id).update("etelNev" , etelnev , "mennyiseg" , menny, "kaloria", kal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Add_food.this, "Adat módosítva!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Food.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Add_food.this, "Hiba!" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_food.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveToFireStore(String id , String etelnev , String menny, String kal){
        String email = user.getEmail();
        if (!etelnev.isEmpty() && !menny.isEmpty() && !kal.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("etelNev" , etelnev);
            map.put("mennyiseg" , menny);
            map.put("kaloria" , kal);
            map.put("email",email);

            firestore.collection("etel").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Add_food.this, "Adat elmentve!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Food.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add_food.this, "HIBA!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Mindent ki kell tölteni", Toast.LENGTH_SHORT).show();
    }
}