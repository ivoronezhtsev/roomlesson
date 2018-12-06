package ru.voronezhtsev.roomlesson.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Класс предоставляет доступ к настройкам приложения
 * {@link SharedPreferences}
 *
 * @author Воронежцев Игорь on 04.12.2018
 */
public class PreferencesDAO {
    public static final String SP_NAME = "RoomLessonPreferences";
    public static final String PREFS_TEXT_SIZE = "text_size";
    public static final String DEFAULT_TEXT_SIZE = "14";
    public static final String PREFS_TEXT_COLOR = "text_color";
    public static final String DEFAULT_TEXT_COLOR = NoteColor.BLACK.getCode();
    private Context mContext;


    /**
     * Конструктор по умолчанию
     *
     * @param context контекст, необходим для доступа к SharedPreferences
     *                {@link Context#getSharedPreferences(String, int)}
     */
    public PreferencesDAO(Context context) {
        mContext = context;
    }

    /**
     * @param defValue - значение по умолчанию, если настройки нет в хранилище.
     * @return размер текста заметок из настроек, sp
     */
    public String getTextSize(String defValue) {
        return getStringValue(PREFS_TEXT_SIZE, defValue);
    }

    /**
     * @param defValue значение по умолчанию, если настройки нет в хранилище.
     * @return цвет текста заметок, например #ff000000
     */
    public String getTextColor(String defValue) {
        return getStringValue(PREFS_TEXT_COLOR, defValue);
    }

    /**
     * Сохранить размер текста, sp
     * @param textSize размер текста, sp
     */
    public void saveTextSize(String textSize) {
        saveStringValue(PREFS_TEXT_SIZE, textSize);
    }

    /**
     * Сохранить цвет текста
     * @param textColor цвет текста, который потом можно распарсить
     * {@link Color#parseColor(String)}
     */
    public void saveTextColor(String textColor) {
        saveStringValue(PREFS_TEXT_COLOR, textColor);
    }

    private String getStringValue(String prefKey, String defaultPrefValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return preferences.getString(prefKey, defaultPrefValue);
    }

    private void saveStringValue(String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString(key, value).apply();
    }
}

