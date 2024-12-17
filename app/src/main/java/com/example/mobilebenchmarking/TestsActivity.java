package com.example.mobilebenchmarking;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class TestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Button overallTestingButton = findViewById(R.id.overallTestButton);
        overallTestingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestsActivity.this, OverallTestActivity.class);
                startActivity(intent);
            }
        });


        // Set up button click listener for CPU Test
        Button cpuTestButton = findViewById(R.id.cpuTestButton);
        cpuTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CPUActivity when the button is clicked
                startActivity(new Intent(TestsActivity.this, CPUActivity.class));
            }
        });

        // Button for Memory/GPU tests
        Button memoryGpuTestButton = findViewById(R.id.memoryGpuTestButton);
        memoryGpuTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestsActivity.this, MemoryGpuActivity.class));
            }
        });

        Button hardwareTestsButton = findViewById(R.id.hardwareTestButton);
        hardwareTestsButton.setOnClickListener(v -> {
            Intent intent = new Intent(TestsActivity.this, com.example.mobilebenchmarking.HardwareTestsActivity.class);
            startActivity(intent);
        });

    }
}
