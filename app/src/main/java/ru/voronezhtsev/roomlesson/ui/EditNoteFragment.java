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

import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.Note;

public class EditNoteFragment extends Fragment {
    public static final String TAG = "EditNoteFragment";
    public static final String KEY_ID = "ID";
    private static final String SAVED_MSG = "Сохранено";
    private EditText mEditText;
    private NotesRepository mNotesRepository;

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
        try {
            mNotesRepository = (NotesRepository) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity should implements NotesRepository interface", e);
        }
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
        mEditText.setTextColor(Color.parseColor(mNotesRepository.getTextColor()));
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(mNotesRepository.getTextSize()));
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mEditText.setText(mNotesRepository.getNote(bundle.getLong(KEY_ID)).getText());
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
                mNotesRepository.insert(note);
            } else {
                Note note = new Note(bundle.getLong(KEY_ID), mEditText.getText().toString(),
                        "", "");
                mNotesRepository.update(note);
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
