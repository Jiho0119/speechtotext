package com.speechtotext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.speechtotext.databinding.FragmentNoteBinding;
import com.speechtotext.databinding.FragmentSpeechBinding;

public class NoteFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    int mNum;
    private FragmentNoteBinding binding;
    private NoteViewModel viewModel;
    private NoteRecyclerViewAdapter adapter;

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NoteViewModel.class);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater);
        adapter = new NoteRecyclerViewAdapter();
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.schoolLiveData.observe(getViewLifecycleOwner(), notes -> {
            adapter.updateData(notes);
        });
        viewModel.restaurantLiveData.observe(getViewLifecycleOwner(), notes -> {
            adapter.updateData(notes);
        });
        viewModel.workLiveData.observe(getViewLifecycleOwner(), notes -> {
            adapter.updateData(notes);
        });

        return binding.getRoot();
    }
}
