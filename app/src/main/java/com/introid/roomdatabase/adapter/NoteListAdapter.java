package com.introid.roomdatabase.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.introid.roomdatabase.EditNoteActivity;
import com.introid.roomdatabase.Entity.Note;
import com.introid.roomdatabase.MainActivity;
import com.introid.roomdatabase.R;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public interface OnDeleteClickListener{
        void OndeleteClickListener(Note myNote);
    }

private final LayoutInflater layoutInflater;
private Context mContext;
private List<Note> mNotes;
private OnDeleteClickListener onDeleteClickListener;

    public NoteListAdapter(Context context,OnDeleteClickListener listener){
        layoutInflater= LayoutInflater.from( context );
        mContext=context;
        this.onDeleteClickListener= listener;

    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate( R.layout.list_item, parent, false);
        return new NoteViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {
        if (mNotes != null){
            Note note = mNotes.get( position );
            holder.setData(note.getNote(),position);
            holder.setListeners();
        }else{
            holder.noteItemView.setText( "No Notes" );
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes!= null) return mNotes.size();
        else return 0;
    }

    public void setNotes(List<Note> notes){
        mNotes= notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView noteItemView;
        public int mPosition;
        Button delete,edit;

        public NoteViewHolder(@NonNull View itemView) {
            super( itemView );
            noteItemView= itemView.findViewById( R.id.tv_notes_item );
            delete= itemView.findViewById( R.id.delete );
            edit= itemView.findViewById( R.id.edit );
        }
        public void setData(String note,int position){
            noteItemView.setText( note );
            mPosition= position;
        }
        public void setListeners(){
            delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null){
                        onDeleteClickListener.OndeleteClickListener( mNotes.get( mPosition ) );
                    }
                }
            } );
            edit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(mContext, EditNoteActivity.class );
                    intent.putExtra( "note_id",mNotes.get( mPosition ).getId() );
                    ((Activity)mContext).startActivityForResult( intent, MainActivity.UPDATE_NOTE_ACTIVITY_CODE );
                }
            } );

        }
    }
}
