package com.example.ecgmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecgmonitor.onboarding.onboardingpertama;

public class splashscreen extends AppCompatActivity {
    Animation atas, bawah;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        atas = AnimationUtils.loadAnimation(this, R.anim.atasanimasi);
        bawah = AnimationUtils.loadAnimation(this, R.anim.bawahanimasi);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        textView.setAnimation(bawah);
        imageView.setAnimation(atas);

        final Intent intent = new Intent(splashscreen.this, onboardingpertama.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}