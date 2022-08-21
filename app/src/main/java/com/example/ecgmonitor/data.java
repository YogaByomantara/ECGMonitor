package com.example.ecgmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class data extends AppCompatActivity {
    MaterialButton register;
    private FirebaseAuth mAuth;
    TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        username = (TextView) findViewById(R.id.username_daftar);
        password = (TextView) findViewById(R.id.password_daftar);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register_access);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regisUserAccount();
            }
        });
    }

    private void regisUserAccount()
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
        mAuth.createUserWithEmailAndPassword(kalimat_Username, kalimat_Password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Berhasil Mendaftar, Silahkan Login",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    Intent intent
                                            = new Intent(data.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }

                                else {
                                    Toast.makeText(getApplicationContext(),"Kesalahan Daftar, Silahkan coba lagi",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }
}