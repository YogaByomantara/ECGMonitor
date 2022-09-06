package com.example.ecgmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class antares extends AppCompatActivity implements AntaresHTTPAPI.OnResponseListener{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView txtData, userLogin;
    private String TAG = "ANTARES-API";
    private AntaresHTTPAPI antaresAPIHTTP;
    private String dataDevice;
    private ImageView history;
    private MaterialButton savedata;
//    User userData;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antares);

        if (user != null) {
            String email = user.getEmail();
            userLogin = findViewById(R.id.textView2);
            userLogin.setText("Selamat Datang, \n"+email);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pasien").child(user.getUid());

        txtData = (TextView) findViewById(R.id.txtData);
        history = findViewById(R.id.history);
        savedata = findViewById(R.id.simpanbtn);
        antaresAPIHTTP = new AntaresHTTPAPI();
        antaresAPIHTTP.addListener(this);
        antaresAPIHTTP.getLatestDataofDevice("1778f0e47e254be3:e264961148f12896","HeartMonitor2022","HeartMonitoring2022");

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(antares.this, com.example.ecgmonitor.history.class);
                startActivity(intent);
            }
        });

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(dataDevice);
            }
        });

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                antaresAPIHTTP.getLatestDataofDevice("1778f0e47e254be3:e264961148f12896","HeartMonitor2022","HeartMonitoring2022");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();


    }

    @Override
    public void onResponse(AntaresResponse antaresResponse) {
        if(antaresResponse.getRequestCode()==0){
            try {
                JSONObject body = new JSONObject(antaresResponse.getBody());
                dataDevice = body.getJSONObject("m2m:cin").getString("con");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataDevice = dataDevice.replace("(", " ");
                        dataDevice = dataDevice.replace(")", " ");
                        dataDevice = dataDevice.replace("[", " ");
                        dataDevice = dataDevice.replace("]", " ");
                        dataDevice = dataDevice.replace("{", " ");
                        dataDevice = dataDevice.replace("}", " ");
                        dataDevice = dataDevice.replace("'", " ");
                        txtData.setText(dataDevice);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addData(String dataDevice) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).child("Rate").setValue(dataDevice)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        antaresAPIHTTP.storeDataofDevice(1,"1778f0e47e254be3:e264961148f12896","HeartMonitor2022","DataRate", "{\\\"statuss\\\":\\\"$key\\\"}");
                        Toast.makeText(antares.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(antares.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}