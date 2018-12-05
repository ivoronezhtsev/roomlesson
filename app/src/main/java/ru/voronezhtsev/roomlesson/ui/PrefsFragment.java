package ru.voronezhtsev.roomlesson.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.NoteColor;
import ru.voronezhtsev.roomlesson.data.PreferencesDAO;

public class PrefsFragment extends PreferenceFragmentCompat {
    public static final String PREFS_TEXT_SIZE = "text_size";
    public static final String DEFAULT_TEXT_SIZE = "14";

    public static final String PREFS_TEXT_COLOR = "text_color";
    public static final String SP_NAME = "ru.voronezhtsev.roomlesson_preferences";
    public static String TAG = "PrefsFragment";

    private Preference mTextSizePreference;
    private Preference mColorPreference;

    private PreferencesDAO mPreferencesDAO;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPreferencesDAO = new PreferencesDAO(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextSizePreference.setSummary(mPreferencesDAO.getTextSize());
        mColorPreference.setSummary(NoteColor.parseColor(mPreferencesDAO.getTextColor()).getName());

        mTextSizePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            mTextSizePreference.setSummary(newValue.toString());
            mPreferencesDAO.saveTextSize(newValue.toString());
            return true;
        });

        mColorPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            mColorPreference.setSummary(NoteColor.parseColor(newValue.toString()).getName());
            mPreferencesDAO.saveTextColor(newValue.toString());
            return true;
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, rootKey);
        mTextSizePreference = getPreferenceManager().findPreference(PREFS_TEXT_SIZE);
        mColorPreference = getPreferenceManager().findPreference(PREFS_TEXT_COLOR);
    }
}
