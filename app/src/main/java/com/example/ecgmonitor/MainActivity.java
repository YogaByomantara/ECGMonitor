package com.example.ecgmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Inisialisasi Tombol Untuk melihat data
//    MaterialButton register;
    private FirebaseAuth mAuth;
    TextView username, password;
    TextView regischoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi TextView Untuk username dan password
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);


        mAuth = FirebaseAuth.getInstance();

        //Menempatkan tombol register terhadap nama yang telah di inisialisasi
//        register = findViewById(R.id.register);
        regischoose = findViewById(R.id.registerchoose);
        //Inisialisasi Tombol Login terhadap dokter
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

        regischoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, data.class);
                startActivity(intent);
                finish();
            }
        });
    }

        private void loginUserAccount()
        {
            String kalimat_Username = username.getText().toString();
            String kalimat_Password = password.getText().toString();

            if (TextUtils.isEmpty(kalimat_Username)) {
                Toast.makeText(getApplicationContext(),
                        "Silahkan Mengisi Email",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (TextUtils.isEmpty(kalimat_Password)) {
                Toast.makeText(getApplicationContext(),
                        "Silahkan Mengisi Password",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // signin existing user
            mAuth.signInWithEmailAndPassword(kalimat_Username, kalimat_Password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                "Berhasil Login",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        Intent intent
                                                = new Intent(MainActivity.this,
                                                antares.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else {
                                        Toast.makeText(getApplicationContext(),"Kesalahan Login, Silahkan coba lagi",Toast.LENGTH_LONG).show();
                                    }
                    }
            });
        }

    }