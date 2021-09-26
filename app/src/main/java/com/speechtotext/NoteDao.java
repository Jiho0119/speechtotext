package com.speechtotext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete()
    void deleteItem(Note note);

    @Query("SELECT * FROM note_table WHERE category == category ORDER BY english ASC")
    LiveData<List<Note>> getNote(String category);
}
