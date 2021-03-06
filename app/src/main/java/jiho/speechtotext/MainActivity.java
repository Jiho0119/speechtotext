package jiho.speechtotext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
import com.speechtotext.R;
import com.speechtotext.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity {
    static final int NUM_ITEMS = 2;
    MyAdapter mAdapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAdapter = new MyAdapter(this);

        binding.pager.setAdapter(mAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Recording");
                tab.setIcon(R.drawable.mic);
            } else if (position == 1) {
                tab.setText("Note");
                tab.setIcon(R.drawable.note);
            }
        }).attach();
    }

    public static class MyAdapter extends FragmentStateAdapter {
        public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Return a NEW fragment instance in createFragment(int)
            if (position == 0) {
                return new SpeechFragment();
            } else {
                return new NoteFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_ITEMS;
        }
    }
}
