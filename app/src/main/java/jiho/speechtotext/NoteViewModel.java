package jiho.speechtotext;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends ViewModel {

    private NoteRepository repository = new NoteRepository();
    public LiveData<List<Note>> schoolLiveData = repository.schoolLiveData;
    public LiveData<List<Note>> workLiveData = repository.workLiveData;
    public LiveData<List<Note>> restaurantLiveData = repository.restaurantLiveData;

    public void insert(Note note) { repository.insert(note); }
}
