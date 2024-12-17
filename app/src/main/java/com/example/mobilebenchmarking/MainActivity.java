package com.example.mobilebenchmarking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Tests button to open the Tests page
        Button testsButton = findViewById(R.id.testsButton);
        testsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start TestsActivity when Tests button is clicked
                Intent intent = new Intent(MainActivity.this, TestsActivity.class);
                startActivity(intent);
            }
        });

        // Other navigation buttons
        Button testHistoryButton = findViewById(R.id.testHistoryButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        testHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestHistoryActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }
}

