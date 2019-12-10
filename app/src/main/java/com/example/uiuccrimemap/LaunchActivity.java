package com.example.uiuccrimemap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

public class LaunchActivity extends AppCompatActivity {
    EditText inputtedYear;
    String yearString;
    String addressString;
    EditText inputtedAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Button launchStartButton = (Button) findViewById(R.id.startButton);
        inputtedYear = findViewById(R.id.yearInput);
        inputtedAddress = findViewById(R.id.addressInput);

        launchStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, MapsActivity.class);
                yearString = inputtedYear.getText().toString();
                addressString = inputtedAddress.getText().toString();
                intent.putExtra("Date", addressString);
                intent.putExtra("Year", yearString);

            startActivity(intent);
            finish();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXlaunchCall:" + yearString + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
        });
    }
}
