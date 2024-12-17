package com.example.mobilebenchmarking.history;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.R;
import com.example.mobilebenchmarking.db.AppDatabase;
import com.example.mobilebenchmarking.models.TestResult;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestHistoryChartActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history_chart);

        lineChart = findViewById(R.id.lineChart);

        // Load and display test results on the chart
        loadTestHistory();
    }

    private void loadTestHistory() {
        AsyncTask.execute(() -> {
            // Retrieve all test results from the database
            List<TestResult> results = AppDatabase.getInstance(getApplicationContext())
                    .testResultDao()
                    .getAllResults();

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>(); // List to store test dates

            for (int i = 0; i < results.size(); i++) {
                TestResult result = results.get(i);

                // Create an Entry object for each test result
                // X-axis: Index (temporary), Y-axis: Performance score
                entries.add(new Entry(i, result.getPerformanceScore()));

                // Format the test date and store it for X-axis labels
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String formattedDate = dateFormat.format(new Date(result.getTimestamp()));
                dates.add(formattedDate);
            }

            runOnUiThread(() -> {
                if (!entries.isEmpty()) {
                    // Create a dataset and apply it to the chart
                    LineDataSet dataSet = new LineDataSet(entries, "");
                    LineData lineData = new LineData(dataSet);
                    lineChart.setData(lineData);

                    // Disable the legend
                    lineChart.getLegend().setEnabled(false);

                    // Customize the X-axis to display test dates
                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f); // Ensure labels are displayed at each step
                    xAxis.setLabelRotationAngle(-45); // Rotate labels to a 45-degree angle
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            int index = (int) value;
                            if (index >= 0 && index < dates.size()) {
                                return dates.get(index); // Display the date at the index
                            }
                            return "";
                        }
                    });

                    // Set X-axis to adjust based on zoom level
                    xAxis.setGranularityEnabled(true); // Enable granularity for zooming
                    xAxis.setLabelCount(5, false); // Set default label count to avoid clutter

                    lineChart.setVisibleXRangeMaximum(10); // Show a maximum of 10 entries at a time
                    lineChart.setDragEnabled(true); // Enable dragging
                    lineChart.setScaleEnabled(true); // Enable scaling/zooming

                    lineChart.invalidate(); // Refresh the chart
                }
            });
        });
    }


}
