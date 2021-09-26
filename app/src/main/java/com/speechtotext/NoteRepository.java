package com.speechtotext;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> liveData;

    // Note that in order to unit test the DiaryRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    NoteRepository() {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase();
        noteDao = db.NoteDao();

        liveData = noteDao.getNote("");
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Note>> getNote(String category) {
        liveData = noteDao.getNote(category);
        return liveData;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });
    }

    void deleteItem(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.deleteItem(note);
        });
    }
}