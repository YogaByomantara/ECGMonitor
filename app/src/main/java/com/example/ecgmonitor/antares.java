package com.example.ecgmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    User userData;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antares);
        userData = new User();
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
        antaresAPIHTTP.getLatestDataofDevice("cb4b71b76a78d7bd:20077e1680d237be","HeartMonitor","HeartMonitoring22");

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
//                antaresAPIHTTP.getLatestDataofDevice("cb4b71b76a78d7bd:20077e1680d237be", "HeartMonitor", "HeartMonitoring22");

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
                                antaresAPIHTTP.getLatestDataofDevice("cb4b71b76a78d7bd:20077e1680d237be","HeartMonitor","HeartMonitoring22");
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

//    private void addData(String dataDevice) {
//        userData.setRate(dataDevice);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String key =  databaseReference.push().getKey();
//                databaseReference.child(key).child("Hasil").setValue(userData);
//
//                // after adding this data we are showing toast message.
//                Toast.makeText(antares.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(antares.this,"Gagal Menyimpan Data" + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



//    @Override
//    protected void onResume() {
////
////        antaresAPIHTTP = new AntaresHTTPAPI();
////        antaresAPIHTTP.addListener(this);
//        antaresAPIHTTP.getLatestDataofDevice("cb4b7b76a78d7bd:20077e1680d237be", "HeartMonitor", "HeartMonitoring22");
//        super.onResume();
//    }
}