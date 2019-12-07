package com.example.uiuccrimemap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Button launchStartButton = (Button) findViewById(R.id.startButton);

        launchStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(LaunchActivity.this, MapsActivity.class));
            System.out.println("User Switched to a Map filled with Markers indicating crimes.");
            }
        });
    }
}
