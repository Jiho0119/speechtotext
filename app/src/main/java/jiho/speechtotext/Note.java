package jiho.speechtotext;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    @ColumnInfo(name = "english")
    public String english;
    @NonNull
    @ColumnInfo(name = "korean")
    public String korean;
    @NonNull
    @ColumnInfo(name = "category")
    public String category;

    public Note(@NonNull String english, @NonNull String korean, @NonNull String category) {
        this.english = english;
        this.korean = korean;
        this.category = category;
    }
}