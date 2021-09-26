package com.speechtotext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
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
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PlannerViewModel.class);

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
                SpeechFragment fragment = new SpeechFragment();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(SpeechFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                return fragment;
            } else {
                SpeechFragment fragment = new SpeechFragment();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(SpeechFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                return fragment;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_ITEMS;
        }
    }
}
