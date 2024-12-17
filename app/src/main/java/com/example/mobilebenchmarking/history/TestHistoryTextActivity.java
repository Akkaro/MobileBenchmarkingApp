package com.example.mobilebenchmarking.history;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.R;
import com.example.mobilebenchmarking.db.AppDatabase;
import com.example.mobilebenchmarking.models.TestResult;

import java.util.Date;
import java.util.List;

public class TestHistoryTextActivity extends AppCompatActivity {

    private TextView testHistoryMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history_text);

        testHistoryMessage = findViewById(R.id.testHistoryMessage);

        // Load and display test history
        loadTestHistory();
    }

    private void loadTestHistory() {
        AsyncTask.execute(() -> {
            List<TestResult> results = AppDatabase.getInstance(getApplicationContext())
                    .testResultDao()
                    .getAllResults();

            StringBuilder builder = new StringBuilder();
            for (TestResult result : results) {
                builder.append("Test Name: ").append(result.getTestName())
                        .append("\nExecution Time: ").append(result.getExecutionTime()).append(" ms")
                        .append("\nPerformance Score: ").append(result.getPerformanceScore())
                        .append("\nCPU Mean Score: ").append(result.getCpuMeanScore())
                        .append("\nGPU Mean Score: ").append(result.getGpuMeanScore())
                        .append("\nMemory Mean Score: ").append(result.getMemoryMeanScore())
                        .append("\nTest Date: ").append(new Date(result.getTimestamp()))
                        .append("\n\n");
            }

            runOnUiThread(() -> {
                if (results.isEmpty()) {
                    testHistoryMessage.setText("No test history available.");
                } else {
                    testHistoryMessage.setText(builder.toString());
                }
            });
        });
    }
}
