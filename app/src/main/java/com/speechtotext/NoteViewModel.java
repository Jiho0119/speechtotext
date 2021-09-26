package com.speechtotext;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends ViewModel {

    private NoteRepository repository = new NoteRepository();
    private LiveData<List<Note>> liveData = new MutableLiveData<>();

    LiveData<List<Note>> getNotes = liveData;

    public void insert(Note note) { repository.insert(note); }
}
