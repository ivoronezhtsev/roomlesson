package ru.voronezhtsev.roomlesson.ui;

import java.util.List;
import ru.voronezhtsev.roomlesson.data.Note;
import ru.voronezhtsev.roomlesson.data.NotesDAO;
import ru.voronezhtsev.roomlesson.data.PreferencesDAO;
/**
 * Интерфейс доступа к настройкам и БД блокнота
 */
public interface NotesRepository {
    /**
     * @return размер текста из настроек, sp
     */
    String getTextSize();

    /**
     * @return цвет текста из настроек, например #ff000000
     */
    String getTextColor();

    /**
     * Сохранить размер шрифта в SharedPreferences {@link PreferencesDAO#saveTextSize(String)}
     * @param size размер шрифта, sp
     */
    void saveTextSize(String size);

    /**
     * Сохранить цвет текста SharedPreferences {@link PreferencesDAO#saveTextColor(String)}
     * @param color цвет текста , например #ff000000
     */
    void saveTextColor(String color);

    /**
     * @return все записи блокнота, {@link NotesDAO#getNotes()}
     */
    List<Note> getNotes();

    /**
     * Вернуть заметку по ключу {@link NotesDAO#getNote(long)}
     * @param id ключ заметки в БД
     * @return заметка или {@code null} если ее нет в БД
     */
    Note getNote(long id);

    /**
     * Вставить заметку в БД {@link NotesDAO#insert(Note)}
     * @param note заметка
     */
    void insert(Note note);

    /**
     * Обновить заметку в БД {@link NotesDAO#update(Note)}
     * @param note заметка
     */
    void update(Note note);

}
