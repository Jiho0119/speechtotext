package com.speechtotext;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    SpeechConfig speechConfig = SpeechConfig.fromSubscription("ab07df133f6f4e99b6e7b531f40be5a2", "westus2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SpeechSDK and request required permissions.
        try {
            // a unique number within the application to allow
            // correlating permission request responses with the request.
            int permissionRequestId = 5;

            // Request permissions needed for speech recognition
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, INTERNET, READ_EXTERNAL_STORAGE}, permissionRequestId);
        }
        catch(Exception ex) {
            Log.e("SpeechSDK", "could not init sdk, " + ex.toString());
        }

        try {
            fromMic(speechConfig);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void fromMic(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

        Log.e("minmin", "Speak into your microphone.");
        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        Log.e("minmin", ": " + result.getText());
    }
}