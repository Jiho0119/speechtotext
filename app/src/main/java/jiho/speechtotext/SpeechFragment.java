package jiho.speechtotext;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import androidx.core.app.ActivityCompat;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.speechtotext.databinding.FragmentSpeechBinding;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeechFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private SpeechTranslationConfig translationConfig = SpeechTranslationConfig.fromSubscription("70fbabf826564efd828ae9c8868512b7", "westus2");
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final Handler resultHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FragmentSpeechBinding binding;
    private NoteViewModel viewModel;
    public static String WORK = "Work";
    public static String RESTAURANT = "Restaurant";
    public static String SCHOOL = "School";

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NoteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSpeechBinding.inflate(inflater);

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
//        binding.noteButton.setEnabled(false);
        binding.listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.listenButton.setEnabled(false);
                binding.noteButton.setEnabled(false);
                Toast.makeText(SpeechFragment.this.getActivity(), "Now Listening!", Toast.LENGTH_SHORT).show();

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Heavy works that take longer time (Recognizing Audio Input) should be run on background thread, so that Main thread is not blocked.
                        // If main thread is blocked, app becomes not responsive (App doesn't recognize user interaction)
                        // After works in background thread are done, in order to update UI based on the result, it needs to communicate the result to Main thread and
                        // update the UI (Setting the text to textView) in Main thread

                        // Run on background thread
                        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();

                        translationConfig.setSpeechRecognitionLanguage("en-US");
                        translationConfig.addTargetLanguage("ko");
                        TranslationRecognizer translationRecognizer = new TranslationRecognizer(translationConfig, audioConfig);
                        try {
                            TranslationRecognitionResult result = translationRecognizer.recognizeOnceAsync().get();
                            notifyResult(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        binding.noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return binding.getRoot();
    }

    private void notifyResult(TranslationRecognitionResult result) {
        resultHandler.post(new Runnable() {
            @Override
            public void run() {
                // Run on Main thread
                if (result.getReason() == ResultReason.TranslatedSpeech) {
                    binding.englishText.setText(result.getText());
                    for (Map.Entry<String, String> pair : result.getTranslations().entrySet()) {
                        binding.koreanText.setText(pair.getValue());
                    }
                }
                binding.listenButton.setEnabled(true);
                binding.noteButton.setEnabled(true);
            }
        });
    }

    private void showDialog() {
        String[] category = new String[] {WORK, RESTAURANT, SCHOOL};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a situation")
            .setCancelable(false)
            .setItems(category, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    String selectedCategory = category[which];
                    String englishText = binding.englishText.getText().toString();
                    String koreanText = binding.koreanText.getText().toString();
                    Note note = new Note(englishText, koreanText, selectedCategory);
                    viewModel.insert(note);
                    Toast.makeText(SpeechFragment.this.getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
                }
            });
        builder.create().show();
        binding.noteButton.setEnabled(false);

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

