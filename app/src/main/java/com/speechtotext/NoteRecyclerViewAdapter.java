package com.speechtotext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewHolder> {

    public List<Note> data = new ArrayList<>();

    @NonNull
    @Override
    public NoteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_note, parent, false);
        return new NoteRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewHolder holder, int position) {
        holder.textViewEnglish.setText(data.get(position).english);
        holder.textViewKorean.setText(data.get(position).korean);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Note> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}

class NoteRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewEnglish;
    public TextView textViewKorean;

    public NoteRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textViewEnglish = itemView.findViewById(R.id.english_text);
        this.textViewKorean = itemView.findViewById(R.id.korean_text);
    }
}