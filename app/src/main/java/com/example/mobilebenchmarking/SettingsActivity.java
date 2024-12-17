package com.example.mobilebenchmarking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "app_preferences";
    private static final String DARK_MODE_KEY = "dark_mode_enabled";
    private static final String NOTIFICATIONS_KEY = "notifications_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize switches and set up listeners (optional)
        Switch darkModeSwitch = findViewById(R.id.darkModeSwitch);
        Switch notificationsSwitch = findViewById(R.id.notificationsSwitch);

        // Load saved preferences for dark mode and notifications
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkModeEnabled = preferences.getBoolean(DARK_MODE_KEY, false);
        boolean areNotificationsEnabled = preferences.getBoolean(NOTIFICATIONS_KEY, false);

        // Set the switches according to saved preferences
        darkModeSwitch.setChecked(isDarkModeEnabled);
        notificationsSwitch.setChecked(areNotificationsEnabled);

        // Toggle dark mode based on switch state
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(SettingsActivity.this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(SettingsActivity.this, "Dark mode disabled", Toast.LENGTH_SHORT).show();
            }

            // Save the dark mode preference
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(DARK_MODE_KEY, isChecked);
            editor.apply();
        });

        // Toggle notifications based on switch state
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Notifications enabled" : "Notifications disabled";
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();

            // Save the notifications preference
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(NOTIFICATIONS_KEY, isChecked);
            editor.apply();
        });
    }
}
