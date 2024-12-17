package com.example.mobilebenchmarking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebenchmarking.db.AppDatabase;
import com.example.mobilebenchmarking.models.TestResult;
import com.example.mobilebenchmarking.tests.CPUTests;
import com.example.mobilebenchmarking.tests.MemoryGPUTests;
import com.example.mobilebenchmarking.tests.HardwareTests;

public class OverallTestActivity extends AppCompatActivity {

    private TextView resultTextView;
    private AppDatabase testHistoryDatabase;

    // Test scores for individual components
    private int cpuSimpleArithmeticScore = 0;
    private int cpuFloatingPointScore = 0;
    private int cpuSortingScore = 0;
    private int cpuPrimeNumbersScore = 0;
    private int gpuPerformanceScore = 0;
    private int memoryUsageScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_tests);

        testHistoryDatabase = AppDatabase.getInstance(this);

        Button startTestsButton = findViewById(R.id.startTestsButton);
        resultTextView = findViewById(R.id.resultTextView);

        startTestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the tests asynchronously
                new RunTestsTask().execute();
            }
        });
    }

    private class RunTestsTask extends AsyncTask<Void, String, String> {

        private int totalScore = 0;
        private int testCount = 0;
        private StringBuilder results = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            resultTextView.setText("Starting tests...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Run CPU Tests
            runCpuTests();
            // Run GPU and Memory Tests
            runGpuAndMemoryTests();
            // Optionally Run Hardware Tests
            //runHardwareTests();

            // Calculate the overall score
            int overallScore = testCount > 0 ? totalScore / testCount : 0;

            // Append the overall score to the results
            results.append("\nOverall Score: ").append(overallScore);
            return results.toString();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            resultTextView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultTextView.setText(result);

            // Calculate total execution time and save test result to database
            long overallExecutionTime = calculateTotalExecutionTime();
            int overallScore = testCount > 0 ? totalScore / testCount : 0;
            saveTestResultToDatabase("Overall Test", overallExecutionTime, overallScore);
        }

        private void saveTestResultToDatabase(String testName, long executionTime, int performanceScore) {
            AsyncTask.execute(() -> {
                TestResult testResult = new TestResult();
                testResult.setTestName(testName);
                testResult.setExecutionTime(executionTime);
                testResult.setPerformanceScore(performanceScore);
                testResult.setTimestamp(System.currentTimeMillis());

                // Set individual test scores
                testResult.setCpuSimpleArithmeticScore(cpuSimpleArithmeticScore);
                testResult.setCpuFloatingPointScore(cpuFloatingPointScore);
                testResult.setCpuSortingScore(cpuSortingScore);
                testResult.setCpuPrimeNumbersScore(cpuPrimeNumbersScore);
                testResult.setGpuPerformanceScore(gpuPerformanceScore);
                testResult.setMemoryUsageScore(memoryUsageScore);

                // Calculate and set mean scores
                testResult.setCpuMeanScore(calculateMean(cpuSimpleArithmeticScore, cpuFloatingPointScore, cpuSortingScore, cpuPrimeNumbersScore));
                testResult.setMemoryMeanScore(calculateMean(memoryUsageScore));  // Only one memory test
                testResult.setGpuMeanScore(calculateMean(gpuPerformanceScore));  // Only one GPU test

                testHistoryDatabase.testResultDao().insertTestResult(testResult);
            });
        }

        private float calculateMean(int... scores) {
            if (scores.length == 0) return 0;
            int sum = 0;
            for (int score : scores) {
                sum += score;
            }
            return sum / (float) scores.length;
        }

        private long calculateTotalExecutionTime() {
            long totalExecutionTime = 0;
            // Accumulate execution times from the tests
            totalExecutionTime += CPUTests.testSimpleArithmetic().time;
            totalExecutionTime += CPUTests.testFloatingPointPrecision().time;
            totalExecutionTime += CPUTests.testSorting().time;
            totalExecutionTime += CPUTests.testPrimeNumbers().time;
            totalExecutionTime += MemoryGPUTests.testGpuRendering().time;
            totalExecutionTime += MemoryGPUTests.testMemoryAllocation().time;

            return totalExecutionTime;
        }

        private void runCpuTests() {
            publishProgress("Running CPU Simple Arithmetic Test...");
            CPUTests.Result cpuResult = CPUTests.testSimpleArithmetic();
            cpuSimpleArithmeticScore = cpuResult.score;
            totalScore += cpuResult.score;
            testCount++;
            results.append("CPU Simple Arithmetic Test\nTime: ").append(cpuResult.time).append(" ms\nScore: ").append(cpuResult.score).append("\n\n");

            publishProgress("Running CPU Floating Point Test...");
            cpuResult = CPUTests.testFloatingPointPrecision();
            cpuFloatingPointScore = cpuResult.score;
            totalScore += cpuResult.score;
            testCount++;
            results.append("CPU Floating Point Test\nTime: ").append(cpuResult.time).append(" ms\nScore: ").append(cpuResult.score).append("\n\n");

            publishProgress("Running CPU Sorting Test...");
            cpuResult = CPUTests.testSorting();
            cpuSortingScore = cpuResult.score;
            totalScore += cpuResult.score;
            testCount++;
            results.append("CPU Sorting Test\nTime: ").append(cpuResult.time).append(" ms\nScore: ").append(cpuResult.score).append("\n\n");

            publishProgress("Running CPU Prime Number Test...");
            cpuResult = CPUTests.testPrimeNumbers();
            cpuPrimeNumbersScore = cpuResult.score;
            totalScore += cpuResult.score;
            testCount++;
            results.append("CPU Prime Number Test\nTime: ").append(cpuResult.time).append(" ms\nScore: ").append(cpuResult.score).append("\n\n");
        }

        private void runGpuAndMemoryTests() {
            publishProgress("Running GPU Performance Test...");
            MemoryGPUTests.Result gpuResult = MemoryGPUTests.testGpuRendering();
            gpuPerformanceScore = gpuResult.score;
            totalScore += gpuResult.score;
            testCount++;
            results.append("GPU Performance Test\nTime: ").append(gpuResult.time).append(" ms\nScore: ").append(gpuResult.score).append("\n\n");

            publishProgress("Running Memory Usage Test...");
            MemoryGPUTests.Result memoryResult = MemoryGPUTests.testMemoryAllocation();
            memoryUsageScore = memoryResult.score;
            totalScore += memoryResult.score;
            testCount++;
            results.append("Memory Usage Test\nTime: ").append(memoryResult.time).append(" ms\nScore: ").append(memoryResult.score).append("\n\n");
        }

        private void runHardwareTests() {
            publishProgress("Running Vibration Motor Test...");
            HardwareTests.testVibrationMotor(OverallTestActivity.this, message -> results.append(message).append("\n\n"));

            publishProgress("Running Flashlight Test...");
            HardwareTests.testFlashlight(OverallTestActivity.this, message -> results.append(message).append("\n\n"));

            publishProgress("Running Speaker Test...");
            HardwareTests.testSpeaker(OverallTestActivity.this, message -> results.append(message).append("\n\n"));

            // Example for other hardware tests
            publishProgress("Running Proximity Sensor Test...");
            HardwareTests.testProximitySensor(OverallTestActivity.this, message -> results.append(message).append("\n\n"));
        }

    }
}
