package ru.voronezhtsev.roomlesson.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Доступ к БД блокнота, используя room
 *
 * @author Воронежцев Игорь on 05.12.2018
 */
@Dao
public interface NotesDAO {

    /**
     * @return все записи блокнота
     */
    @Query("select * from notes")
    List<Note> getNotes();

    /**
     * Вернуть заметку по ключу
     * @param id ключ заметки {@link Note#getId()}
     * @return заметка, или {@code null} если не найдено
     */
    @Query("select * from notes where id = :id")
    Note getNote(long id);

    /**
     * Вставить заметку в БД
     * @param note заметка, поле id можно не заполнять
     * @return id вставленной заметки
     */
    @Insert
    long insert(Note note);

    /**
     * Обновить заметку в БД
     * @param note заметка
     */
    @Update
    void update(Note note);
}
