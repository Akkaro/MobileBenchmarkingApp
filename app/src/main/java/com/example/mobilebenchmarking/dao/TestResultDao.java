package com.example.mobilebenchmarking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.mobilebenchmarking.models.TestResult;

import java.util.List;

@Dao
public interface TestResultDao {
    @Insert
    void insertTestResult(TestResult testResult);

    @Query("SELECT * FROM test_results ORDER BY timestamp ASC")
    List<TestResult> getAllResults();

    @Delete
    void delete(TestResult testResult);

    @Query("DELETE FROM test_results")
    void deleteAll();

    @Query("SELECT * FROM test_results ORDER BY timestamp DESC LIMIT 1")
    TestResult getLastTestResult();  // To get the most recent result

}

