package ru.voronezhtsev.roomlesson.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import ru.voronezhtsev.roomlesson.App;
import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.Note;
import ru.voronezhtsev.roomlesson.data.PreferencesDAO;

public class EditNoteFragment extends Fragment {
    public static final String TAG = "EditNoteFragment";
    public static final String KEY_ID = "ID";
    private static final String SAVED_MSG = "Сохранено";
    private EditText mEditText;
    private PreferencesDAO mPreferencesDAO;

    public static EditNoteFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_ID, id);
        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPreferencesDAO = new PreferencesDAO(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.data_text_view);
        mEditText.setTextColor(Color.parseColor(mPreferencesDAO.getTextColor()));
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(mPreferencesDAO.getTextSize()));
        final Bundle bundle = getArguments();

        if (bundle != null) {
            mEditText.setText(App.getDatabase()
                    .getNotesDAO().getNote(bundle.getLong(KEY_ID)).getText());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveItem = menu.add(R.string.save_button);

        saveItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        saveItem.setOnMenuItemClickListener(item -> {
            Bundle bundle = getArguments();
            if (bundle == null) {
                Note note = new Note(mEditText.getText().toString(), "", "");
                App.getDatabase().getNotesDAO().insert(note);
            } else {
                Note note = new Note(bundle.getLong(KEY_ID), mEditText.getText().toString(),
                        "", "");
                App.getDatabase().getNotesDAO().update(note);
            }
            Toast.makeText(getContext(), SAVED_MSG, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
