package com.speechtotext;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognitionResult;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;
import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.speechtotext.databinding.ActivitySpeechBinding;

import java.util.Map;

public class SpeechFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    int mNum;
    SpeechTranslationConfig translationConfig = SpeechTranslationConfig.fromSubscription("70fbabf826564efd828ae9c8868512b7", "westus2");
    private ActivitySpeechBinding binding;


    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivitySpeechBinding.inflate(inflater);

        // Initialize SpeechSDK and request required permissions.
        try {
            // a unique number within the application to allow
            // correlating permission request responses with the request.
            int permissionRequestId = 5;

            // Request permissions needed for speech recognition
            ActivityCompat.requestPermissions(SpeechFragment.this.getActivity(), new String[]{RECORD_AUDIO, INTERNET, READ_EXTERNAL_STORAGE}, permissionRequestId);
        } catch (Exception ex) {
            Log.e("SpeechSDK", "could not init sdk, " + ex.toString());
        }

        binding.listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpeechFragment.this.getActivity(), "Now Listening!", Toast.LENGTH_SHORT).show();

                AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();

                translationConfig.setSpeechRecognitionLanguage("en-US");
                translationConfig.addTargetLanguage("ko");
                TranslationRecognizer translationRecognizer = new TranslationRecognizer(translationConfig, audioConfig);

                TranslationRecognitionResult result = null;
                try {
                    result = translationRecognizer.recognizeOnceAsync().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result.getReason() == ResultReason.TranslatedSpeech) {
                    binding.englishText.setText(result.getText());
                    for (Map.Entry<String, String> pair : result.getTranslations().entrySet()) {
                        binding.koreanText.setText(pair.getValue());
                    }
                }
            }
        });
        binding.noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SpeechFragment.this.getActivity(), "Note saved", Toast.LENGTH_SHORT).show();

            }
        });

        return binding.getRoot();
    }

    static SpeechFragment newInstance(int num) {
        SpeechFragment f = new SpeechFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }
}

