package com.example.mobilebenchmarking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.tests.CPUTests;

public class CPUActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_tests);

        // Set up buttons and result display
        Button simpleArithmeticButton = findViewById(R.id.simpleArithmeticButton);
        Button floatingPointButton = findViewById(R.id.floatingPointButton);
        Button sortingButton = findViewById(R.id.sortingButton);
        Button primeNumbersButton = findViewById(R.id.primeNumbersButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Set button click listeners
        simpleArithmeticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the test and get the result object (time and score)
                CPUTests.Result result = CPUTests.testSimpleArithmetic();
                // Display the time and score
                resultTextView.setText("Simple Arithmetic Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

        floatingPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the test and get the result object (time and score)
                CPUTests.Result result = CPUTests.testFloatingPointPrecision();
                // Display the time and score
                resultTextView.setText("Floating Point Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

        sortingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the test and get the result object (time and score)
                CPUTests.Result result = CPUTests.testSorting();
                // Display the time and score
                resultTextView.setText("Sorting Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });

        primeNumbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the test and get the result object (time and score)
                CPUTests.Result result = CPUTests.testPrimeNumbers();
                // Display the time and score
                resultTextView.setText("Prime Number Test\nTime: " + result.time + " ms\nScore: " + result.score);
            }
        });
    }
}
