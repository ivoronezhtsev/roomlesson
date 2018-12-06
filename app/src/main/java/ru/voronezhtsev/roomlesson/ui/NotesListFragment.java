package ru.voronezhtsev.roomlesson.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.voronezhtsev.roomlesson.R;

public class NotesListFragment extends Fragment implements NotesActions {
    public static final String TAG = "NotesListFragment";
    private RecyclerView mRecyclerView;
    private NotesAdapter mNotesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditNoteFragment mEditNoteFragment;
    private NotesRepository mNotesRepository;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNotesAdapter = new NotesAdapter(
                mNotesRepository.getNotes(),
                this,
                Integer.parseInt(mNotesRepository.getTextSize()),
                mNotesRepository.getTextColor());
        mRecyclerView.setAdapter(mNotesAdapter);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditNoteFragment = new EditNoteFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, mEditNoteFragment, EditNoteFragment.TAG)
                        .addToBackStack(EditNoteFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void update(long noteId) {
        if (mEditNoteFragment == null) {
            mEditNoteFragment = EditNoteFragment.newInstance(noteId);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong(EditNoteFragment.KEY_ID, noteId);
            mEditNoteFragment.setArguments(bundle);
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mEditNoteFragment, EditNoteFragment.TAG)
                .addToBackStack(EditNoteFragment.TAG)
                .commit();
        }
}
