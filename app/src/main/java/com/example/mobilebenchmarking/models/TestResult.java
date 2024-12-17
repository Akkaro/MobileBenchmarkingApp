package com.example.mobilebenchmarking.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_results")
public class TestResult {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String testName;
    private long executionTime;
    private int performanceScore;
    private long timestamp;

    // CPU test scores
    private int cpuSimpleArithmeticScore;
    private int cpuFloatingPointScore;
    private int cpuSortingScore;
    private int cpuPrimeNumbersScore;

    // GPU and Memory test scores
    private int gpuPerformanceScore;
    private int memoryUsageScore;

    // Mean scores
    private float cpuMeanScore;
    private float memoryMeanScore;
    private float gpuMeanScore;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public long getExecutionTime() { return executionTime; }
    public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }

    public int getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(int performanceScore) { this.performanceScore = performanceScore; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getCpuSimpleArithmeticScore() { return cpuSimpleArithmeticScore; }
    public void setCpuSimpleArithmeticScore(int cpuSimpleArithmeticScore) { this.cpuSimpleArithmeticScore = cpuSimpleArithmeticScore; }

    public int getCpuFloatingPointScore() { return cpuFloatingPointScore; }
    public void setCpuFloatingPointScore(int cpuFloatingPointScore) { this.cpuFloatingPointScore = cpuFloatingPointScore; }

    public int getCpuSortingScore() { return cpuSortingScore; }
    public void setCpuSortingScore(int cpuSortingScore) { this.cpuSortingScore = cpuSortingScore; }

    public int getCpuPrimeNumbersScore() { return cpuPrimeNumbersScore; }
    public void setCpuPrimeNumbersScore(int cpuPrimeNumbersScore) { this.cpuPrimeNumbersScore = cpuPrimeNumbersScore; }

    public int getGpuPerformanceScore() { return gpuPerformanceScore; }
    public void setGpuPerformanceScore(int gpuPerformanceScore) { this.gpuPerformanceScore = gpuPerformanceScore; }

    public int getMemoryUsageScore() { return memoryUsageScore; }
    public void setMemoryUsageScore(int memoryUsageScore) { this.memoryUsageScore = memoryUsageScore; }

    public float getCpuMeanScore() { return cpuMeanScore; }
    public void setCpuMeanScore(float cpuMeanScore) { this.cpuMeanScore = cpuMeanScore; }

    public float getMemoryMeanScore() { return memoryMeanScore; }
    public void setMemoryMeanScore(float memoryMeanScore) { this.memoryMeanScore = memoryMeanScore; }

    public float getGpuMeanScore() { return gpuMeanScore; }
    public void setGpuMeanScore(float gpuMeanScore) { this.gpuMeanScore = gpuMeanScore; }
}
