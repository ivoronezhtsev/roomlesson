package ru.voronezhtsev.roomlesson.ui;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.Note;
import ru.voronezhtsev.roomlesson.data.NotesDatabase;
import ru.voronezhtsev.roomlesson.data.PreferencesDAO;

public class MainActivity extends AppCompatActivity implements NotesRepository{

    private static final String DB_FILE_NAME = "notes.db";
    private PreferencesDAO mPreferencesDAO;
    private NotesDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new NotesListFragment(), NotesListFragment.TAG)
                    .commit();
            initPrefs();
            initDatabase();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new PrefsFragment(), PrefsFragment.TAG)
                        .addToBackStack(PrefsFragment.TAG)
                        .commit();
                return true;
            }
        });
        return true;
    }

    private void initPrefs() {
        mPreferencesDAO = new PreferencesDAO(this);
        if(mPreferencesDAO.getTextSize("").isEmpty()) {
            mPreferencesDAO.saveTextSize(PreferencesDAO.DEFAULT_TEXT_SIZE);
        }
        if(mPreferencesDAO.getTextColor("").isEmpty()) {
            mPreferencesDAO.saveTextColor(PreferencesDAO.DEFAULT_TEXT_COLOR);
        }
    }
    private void initDatabase() {
        mDatabase = Room.databaseBuilder(
                getApplicationContext(),
                NotesDatabase.class,
                DB_FILE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextSize() {
        return mPreferencesDAO.getTextSize(PreferencesDAO.DEFAULT_TEXT_SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextColor() {
        return mPreferencesDAO.getTextColor(PreferencesDAO.DEFAULT_TEXT_COLOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTextSize(String size) {
        mPreferencesDAO.saveTextSize(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTextColor(String color) {
        mPreferencesDAO.saveTextColor(color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Note> getNotes() {
        return mDatabase.getNotesDAO().getNotes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Note getNote(long id) {
        return mDatabase.getNotesDAO().getNote(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Note note) {
        mDatabase.getNotesDAO().insert(note);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Note note) {
        mDatabase.getNotesDAO().update(note);
    }
}
