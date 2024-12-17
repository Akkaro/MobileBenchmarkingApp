package com.example.mobilebenchmarking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.tests.MemoryGPUTests;

public class MemoryGpuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_gpu_tests);

        // Set up buttons and result display
        Button memoryTestButton = findViewById(R.id.memoryTestButton);
        Button gpuTestButton = findViewById(R.id.gpuTestButton);
        Button gpuPixelTestButton = findViewById(R.id.btn_gpu_pixel_test);
        TextView resultTextView = findViewById(R.id.resultTextView);


        // Set button click listeners for Memory and GPU tests
        memoryTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryGPUTests.Result result = MemoryGPUTests.testMemoryAllocation();
                resultTextView.setText("Memory Allocation Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

        gpuTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryGPUTests.Result result = MemoryGPUTests.testMatrixMultiplication();
                resultTextView.setText("Matrix Multiplication (GPU) Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

        gpuPixelTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryGPUTests.Result result = MemoryGPUTests.testGpuRendering();
                resultTextView.setText("GPU rendering Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

    }
}
