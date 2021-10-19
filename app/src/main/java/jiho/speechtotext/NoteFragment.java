package jiho.speechtotext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.speechtotext.R;
import com.speechtotext.databinding.FragmentNoteBinding;

import java.util.List;

public class NoteFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private FragmentNoteBinding binding;
    private NoteViewModel viewModel;
    private NoteRecyclerViewAdapter adapter;
    private final Observer<List<Note>> notesObserver = new Observer<List<Note>>() {
        @Override
        public void onChanged(List<Note> notes) {
            adapter.updateData(notes);
        }
    };

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
        binding = FragmentNoteBinding.inflate(inflater);
        adapter = new NoteRecyclerViewAdapter();
        binding.recyclerview.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(binding.recyclerview.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.linecolor));
        binding.recyclerview.addItemDecoration(itemDecoration);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObserveLiveData(viewModel.restaurantLiveData);
            }
        });

        binding.school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObserveLiveData(viewModel.schoolLiveData);
            }
        });

        binding.work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startObserveLiveData(viewModel.workLiveData);
            }
        });

        binding.work.performClick();

        return binding.getRoot();
    }

    private void startObserveLiveData(LiveData<List<Note>> liveData) {
        liveData.removeObserver((notesObserver));
        liveData.observe(getViewLifecycleOwner(), notesObserver);
    }
}
