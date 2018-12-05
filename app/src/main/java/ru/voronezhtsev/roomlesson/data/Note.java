package ru.voronezhtsev.roomlesson.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    long id;
    String text;
    String creationDate;
    String modifyDate;


    public Note(long id, String text, String creationDate, String modifyDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
    }

    @Ignore
    public Note(String text, String creationDate, String modifyDate) {
        this.text = text;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Ignore
    public String getText() {
        return text;
    }
}
