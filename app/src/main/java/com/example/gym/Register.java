package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText email, jelszo, felhasznalonev;
    Button button;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textview;

    FirebaseFirestore database;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Logout.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        jelszo = findViewById(R.id.password);
        felhasznalonev = findViewById(R.id.username);
        button = findViewById(R.id.signupbt);
        progressBar = findViewById(R.id.progressbar);
        textview = findViewById(R.id.loginnow);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email1, jelszo1, felhasznalonev1;
                email1 = String.valueOf(email.getText());
                jelszo1 = String.valueOf(jelszo.getText());
                felhasznalonev1 = String.valueOf(felhasznalonev.getText());

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(Register.this, "Add meg az email címet!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(jelszo1)) {
                    Toast.makeText(Register.this, "Add meg az jelszavad!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email1, jelszo1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Sikeres regisztráció!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Map<String,Object> felhasznalo = new HashMap<>();
                felhasznalo.put("email", email1);
                felhasznalo.put("felhasznalonev",felhasznalonev1 );
                felhasznalo.put("jelszo", jelszo1);
                database.collection("felhasznalo").add(felhasznalo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this, "Sikeres hozzáadás", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent3);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Nem volt sikeres...", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}