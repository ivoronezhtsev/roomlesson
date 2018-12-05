package ru.voronezhtsev.roomlesson;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.logging.Handler;

import ru.voronezhtsev.roomlesson.data.NotesDatabase;

/**
 * Класс приложения
 */
public class App extends Application {

    private static NotesDatabase mDatabase;

    public static NotesDatabase getDatabase() {
        return mDatabase;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = Room.databaseBuilder(
                getApplicationContext(),
                NotesDatabase.class,
                "notes.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
