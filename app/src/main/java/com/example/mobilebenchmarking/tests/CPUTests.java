package com.example.mobilebenchmarking.tests;

public class CPUTests {

    // Reference times for scoring (in milliseconds)
    private static final long SIMPLE_ARITHMETIC_REFERENCE_TIME = 1000; // Reference time in milliseconds
    private static final long FLOATING_POINT_REFERENCE_TIME = 200;
    private static final long SORTING_REFERENCE_TIME = 100;
    private static final long PRIME_NUMBER_REFERENCE_TIME = 50;

    // Test 1: Simple arithmetic operation test
    public static Result testSimpleArithmetic() {
        long startTime = System.currentTimeMillis();

        // Perform some heavy calculations
        long result = 0;
        for (long i = 0; i < 10000000; i++) {
            result += Math.sqrt(i);
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate the score based on reference time
        int score = calculateScore(SIMPLE_ARITHMETIC_REFERENCE_TIME, timeTaken);

        return new Result(timeTaken, score);
    }

    // Test 2: Floating-point precision test
    public static Result testFloatingPointPrecision() {
        long startTime = System.currentTimeMillis();

        // Perform floating-point precision calculations
        double sum = 0;
        for (double i = 0.0; i < 1000000; i++) {
            sum += Math.pow(i, 0.5);
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate the score based on reference time
        int score = calculateScore(FLOATING_POINT_REFERENCE_TIME, timeTaken);

        return new Result(timeTaken, score);
    }

    // Test 3: Sorting test (CPU-bound)
    public static Result testSorting() {
        long startTime = System.currentTimeMillis();

        // Generate a large array and sort it
        int[] array = new int[100000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 100000);
        }
        java.util.Arrays.sort(array);

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate the score based on reference time
        int score = calculateScore(SORTING_REFERENCE_TIME, timeTaken);

        return new Result(timeTaken, score);
    }

    // Test 4: Prime number calculation test
    public static Result testPrimeNumbers() {
        long startTime = System.currentTimeMillis();

        // Perform a prime number calculation
        int limit = 10000;
        for (int i = 2; i < limit; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Calculate the score based on reference time
        int score = calculateScore(PRIME_NUMBER_REFERENCE_TIME, timeTaken);

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
