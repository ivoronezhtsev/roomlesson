package ru.voronezhtsev.roomlesson.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.PreferencesDAO;

public class MainActivity extends AppCompatActivity {

    private NotesListFragment mNotesListFragment;

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
        new Thread(()->{
                SharedPreferences sharedPreferences = getSharedPreferences(PrefsFragment.SP_NAME,
                        MODE_PRIVATE);
                if (TextUtils
                        .isEmpty(sharedPreferences
                                .getString(PrefsFragment.PREFS_TEXT_SIZE, ""))) {
                    sharedPreferences
                            .edit()
                            .putString(PrefsFragment.PREFS_TEXT_SIZE,
                                    PrefsFragment.DEFAULT_TEXT_SIZE)
                            .commit();
                }
                if(TextUtils.isEmpty(sharedPreferences
                        .getString(PrefsFragment.PREFS_TEXT_COLOR, ""))) {
                    sharedPreferences
                            .edit()
                            .putString(PrefsFragment.PREFS_TEXT_COLOR,
                                    PreferencesDAO.DEFAULT_TEXT_COLOR)
                            .commit();
                }
            }).start();
    }
}
