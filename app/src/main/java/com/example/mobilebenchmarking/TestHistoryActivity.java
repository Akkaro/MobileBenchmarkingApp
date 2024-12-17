package com.example.mobilebenchmarking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.db.AppDatabase;
import com.example.mobilebenchmarking.history.TestHistoryChartActivity;
import com.example.mobilebenchmarking.history.TestHistoryTextActivity;
import com.example.mobilebenchmarking.models.TestResult;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import java.util.List;

public class TestHistoryActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        barChart = findViewById(R.id.barChart);

        // Load the latest test results and display in the BarChart
        loadLastTestResults();

        Button textViewButton = findViewById(R.id.textViewButton);
        Button chartViewButton = findViewById(R.id.chartViewButton);

        textViewButton.setOnClickListener(v -> {
            // Navigate to the text-based result activity
            Intent intent = new Intent(TestHistoryActivity.this, TestHistoryTextActivity.class);
            startActivity(intent);
        });

        chartViewButton.setOnClickListener(v -> {
            // Navigate to the line chart activity
            Intent intent = new Intent(TestHistoryActivity.this, TestHistoryChartActivity.class);
            startActivity(intent);
        });
    }

    private void loadLastTestResults() {
        AsyncTask.execute(() -> {
            // Retrieve the most recent test result from the database
            List<TestResult> results = AppDatabase.getInstance(getApplicationContext())
                    .testResultDao()
                    .getAllResults();

            if (results.isEmpty()) {
                return; // No results found, exit the method
            }

            TestResult lastResult = results.get(results.size() - 1); // Get the last result

            // Prepare data for the BarChart
            // Assume the TestResult contains fields like CPU score, memory score, and GPU score
            BarEntry cpuEntry = new BarEntry(0, lastResult.getCpuMeanScore());
            BarEntry memoryEntry = new BarEntry(1, lastResult.getMemoryMeanScore());
            BarEntry gpuEntry = new BarEntry(2, lastResult.getGpuMeanScore());

            // Create a list of BarEntries
            List<BarEntry> entries = List.of(cpuEntry, memoryEntry, gpuEntry);

            // Set up the BarDataSet with labels for each feature
            BarDataSet dataSet = new BarDataSet(entries, "Test Features");
            BarData barData = new BarData(dataSet);

            runOnUiThread(() -> {
                // Display the bar chart with the test result data
                barChart.setData(barData);
                barChart.invalidate(); // Refresh the chart
            });
        });
    }
}
