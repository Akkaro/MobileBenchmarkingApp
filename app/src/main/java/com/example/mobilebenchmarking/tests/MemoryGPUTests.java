package com.example.mobilebenchmarking.tests;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MemoryGPUTests {

    // Reference times for scoring (in milliseconds)
    private static final long MEMORY_ALLOCATION_REFERENCE_TIME = 150;
    private static final long MATRIX_MULTIPLICATION_REFERENCE_TIME = 150;
    private static final long GPU_RENDER_REFERENCE_TIME = 3000;
    // Memory Allocation Test
    public static Result testMemoryAllocation() {
        long startTime = System.currentTimeMillis();

        // Allocate memory for a large array
        int[] largeArray = new int[10000000]; // Array with 10 million elements
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate score based on the reference time
        int score = calculateScore(MEMORY_ALLOCATION_REFERENCE_TIME, timeTaken);

        return new Result(timeTaken, score);
    }

    // Matrix Multiplication Test (simulating a GPU-like operation)
    public static Result testMatrixMultiplication() {
        long startTime = System.currentTimeMillis();

        // Perform a matrix multiplication
        int size = 200;
        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];
        int[][] resultMatrix = new int[size][size];

        // Initialize matrices with some values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = i + j;
                matrixB[i][j] = i - j;
            }
        }

        // Multiply matrices
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultMatrix[i][j] = 0;
                for (int k = 0; k < size; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate score based on the reference time
        int score = calculateScore(MATRIX_MULTIPLICATION_REFERENCE_TIME, timeTaken);

        return new Result(timeTaken, score);
    }

    public static Result testGpuRendering() {
        long startTime = System.currentTimeMillis();

        // Create a Bitmap to render on
        int width = 1080;
        int height = 1920;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Create a Canvas to draw on the Bitmap
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        for(int i = 0; i<50; i++) {
            // Perform complex rendering operations
            for (int y = 0; y < height; y += 20) {
                for (int x = 0; x < width; x += 20) {
                    paint.setColor(Color.rgb((x * y) % 256, (x + y) % 256, (x - y + 256) % 256));
                    canvas.drawCircle(x, y, 10, paint);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Set a reference time for scoring

        int score = calculateScore(GPU_RENDER_REFERENCE_TIME, timeTaken);
        return new Result(timeTaken, score);
    }


    // Method to calculate the score based on reference time
    private static int calculateScore(long referenceTime, long actualTime) {
        if (actualTime <= 0) {
            return 100; // Perfect performance
        }
        // Calculate score based on reference time
        int score = (int) ((referenceTime / (double) actualTime) * 100);

        // Ensure the score is within a reasonable range
        if (score > 100) {
            score = 100;
        } else if (score < 0) {
            score = 0;
        }
        return score;
    }

    // Class to hold the results (time and score)
    public static class Result {
        public final long time;
        public final int score;

        public Result(long time, int score) {
            this.time = time;
            this.score = score;
        }
    }
}
