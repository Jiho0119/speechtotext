package com.speechtotext;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends ViewModel {

    private NoteRepository repository = new NoteRepository();

    LiveData<List<Note>> getPlanner(String category) { return repository.getNote(category); }

    public void insert(Note note) { repository.insert(note); }
}
