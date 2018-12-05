package ru.voronezhtsev.roomlesson.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.voronezhtsev.roomlesson.R;
import ru.voronezhtsev.roomlesson.data.Note;

/**
 * Адаптер для recyclerview с заметками для блокнота
 *
 * @author Воронежцев Игорь on 05.12.2018
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<Note> mNotes;
    private final NotesActions mNotesActions;
    private NotesAdapter.NotesViewHolder mViewHolder;
    private int mTextSize = 14;
    private String mTextColor;

    public NotesAdapter(List<Note> notes, NotesActions notesActions, int textSize, String textColor) {
        mNotes = notes;
        mNotesActions = notesActions;
        mTextSize = textSize;
        mTextColor = textColor;
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);

        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        mViewHolder = holder;
        holder.mDataTextView.setText(mNotes.get(position).getText());
        holder.mDataTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        holder.mDataTextView.setTextColor(Color.parseColor(mTextColor));
        final int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotesActions.update(mNotes.get(pos).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView mDataTextView;

        public NotesViewHolder(View itemView) {
            super(itemView);
            mDataTextView = itemView.findViewById(R.id.note_data_textView);
        }
    }
}
