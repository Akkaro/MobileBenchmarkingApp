package com.example.mobilebenchmarking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobilebenchmarking.tests.HardwareTests;

public class HardwareTestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_tests);

        Button vibrationButton = findViewById(R.id.vibrationButton);
        Button flashlightButton = findViewById(R.id.flashlightButton);
        Button speakerButton = findViewById(R.id.speakerButton);
        Button microphoneButton = findViewById(R.id.microphoneButton);
        Button proximityButton = findViewById(R.id.proximityButton);
        Button lightButton = findViewById(R.id.lightButton);
        Button accelerometerButton = findViewById(R.id.accelerometerButton);
        Button screenTestButton = findViewById(R.id.screenTestButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        vibrationButton.setOnClickListener(v -> HardwareTests.testVibrationMotor(this, resultTextView::setText));
        flashlightButton.setOnClickListener(v -> HardwareTests.testFlashlight(this, resultTextView::setText));
        speakerButton.setOnClickListener(v -> HardwareTests.testSpeaker(this, resultTextView::setText));
        microphoneButton.setOnClickListener(v -> HardwareTests.testMicrophone(this, resultTextView::setText));
        proximityButton.setOnClickListener(v -> HardwareTests.testProximitySensor(this, resultTextView::setText));
        lightButton.setOnClickListener(v -> HardwareTests.testLightSensor(this, resultTextView::setText));
        accelerometerButton.setOnClickListener(v -> HardwareTests.testAccelerometer(this, resultTextView::setText));
        screenTestButton.setOnClickListener(v ->   HardwareTests.testScreen(this, resultTextView::setText));
    }
}
