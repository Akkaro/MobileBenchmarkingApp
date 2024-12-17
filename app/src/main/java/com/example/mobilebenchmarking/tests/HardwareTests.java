// HardwareTest in the tests package
package com.example.mobilebenchmarking.tests;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.mobilebenchmarking.R;

import java.io.File;
import java.io.IOException;
import android.graphics.Color;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HardwareTests {

    // Vibration Motor Test
    public static void testVibrationMotor(Context context, TestCallback callback) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            //vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            callback.onTestCompleted("Vibration Motor Test: Check if you feel the vibration.");
        } else {
            callback.onTestCompleted("Vibration Motor Test: Vibration not supported.");
        }
    }

    // Flashlight Test
    public static void testFlashlight(Context context, TestCallback callback) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            callback.onTestCompleted("Flashlight Test: Check if the flashlight is on.");
            // Turn it off after 3 seconds
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            callback.onTestCompleted("Flashlight Test: Error accessing the camera.");
        }
    }


    // Screen Test
    public static void testScreen(Context context, TestCallback callback) {
        // Create a new layout with a white background
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.WHITE);  // Set the background to white

        // Add this layout to the current activity's window
        ((AppCompatActivity) context).setContentView(layout);

        // Set a delay to revert back to the original screen after 3 seconds
        new Handler().postDelayed(() -> {
            // After 3 seconds, set the content back to the previous screen (if needed)
            callback.onTestCompleted("Screen Test: Check if all pixels are working correctly.");
            // Revert back to the previous layout (Optional, could be the original layout)
            ((AppCompatActivity) context).setContentView(R.layout.activity_hardware_tests);
        }, 3000);  // 3000 milliseconds (3 seconds)
    }

    // Speaker Test
    public static void testSpeaker(Context context, TestCallback callback) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.test_sound); // Add a test sound in res/raw
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                callback.onTestCompleted("Speaker Test: Check if you heard the sound.");
            });
            mediaPlayer.start();
        } else {
            callback.onTestCompleted("Speaker Test: Error playing the sound.");
        }
    }

    // Microphone Test
    public static void testMicrophone(Context context, TestCallback callback) {
        File audioFile = new File(context.getExternalCacheDir(), "test_audio.3gp");

        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioFile.getAbsolutePath());

        try {
            recorder.prepare();
            recorder.start();
            // Record for 3 seconds
            Thread.sleep(3000);
            recorder.stop();
            recorder.release();

            // Playback recorded audio
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                audioFile.delete();  // Clean up
                callback.onTestCompleted("Microphone Test: Check if you heard the recorded sound.");
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (InterruptedException | IOException e) {
            callback.onTestCompleted("Microphone Test: Error recording or playing back audio.");
        }
    }

    // Proximity Sensor Test
    public static void testProximitySensor(Context context, TestCallback callback) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                        if (event.values[0] < proximitySensor.getMaximumRange()) {
                            callback.onTestCompleted("Proximity Sensor Test: Object detected near the sensor.");
                        } else {
                            callback.onTestCompleted("Proximity Sensor Test: No object detected.");
                        }
                        sensorManager.unregisterListener(this);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            }, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            callback.onTestCompleted("Proximity Sensor Test: Sensor not available.");
        }
    }

    // Ambient Light Sensor Test
    public static void testLightSensor(Context context, TestCallback callback) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                        callback.onTestCompleted("Light Sensor Test: Light level detected: " + event.values[0] + " lux.");
                        sensorManager.unregisterListener(this);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            }, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            callback.onTestCompleted("Light Sensor Test: Sensor not available.");
        }
    }

    // Accelerometer Test
    public static void testAccelerometer(Context context, TestCallback callback) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                        callback.onTestCompleted("Accelerometer Test: Accelerometer detected movement: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
                        sensorManager.unregisterListener(this);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            callback.onTestCompleted("Accelerometer Test: Sensor not available.");
        }
    }
    public interface TestCallback {
        void onTestCompleted(String message);
    }
}
