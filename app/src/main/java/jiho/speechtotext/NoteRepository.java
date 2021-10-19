package jiho.speechtotext;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    public LiveData<List<Note>> workLiveData;
    public LiveData<List<Note>> schoolLiveData;
    public LiveData<List<Note>> restaurantLiveData;

    // Note that in order to unit test the DiaryRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    NoteRepository() {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase();
        noteDao = db.NoteDao();

        workLiveData = noteDao.getNote(SpeechFragment.WORK);
        schoolLiveData = noteDao.getNote(SpeechFragment.SCHOOL);
        restaurantLiveData = noteDao.getNote(SpeechFragment.RESTAURANT);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });
    }
}