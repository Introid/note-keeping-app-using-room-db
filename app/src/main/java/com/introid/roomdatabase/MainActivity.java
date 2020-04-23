package com.introid.roomdatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.introid.roomdatabase.Entity.Note;
import com.introid.roomdatabase.adapter.NoteListAdapter;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private NoteViewModel noteViewModel;
    public static final int NEW_NOTE_ACTIVITY_CODE=1;
    public static final int UPDATE_NOTE_ACTIVITY_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        FloatingActionButton fab= findViewById( R.id.floatingActionButton );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent= new Intent(MainActivity.this,NewNodeActivity.class);
            startActivityForResult( intent,NEW_NOTE_ACTIVITY_CODE );

            }
        } );
        noteViewModel= ViewModelProviders.of( this ).get( NoteViewModel.class );
        RecyclerView rv_main= findViewById( R.id.rv_main );
        final NoteListAdapter noteListAdapter= new NoteListAdapter(this,this);
        rv_main.setAdapter(noteListAdapter);

        noteViewModel.getmAllNotes().observe( this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes( notes );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode== NEW_NOTE_ACTIVITY_CODE && resultCode == RESULT_OK){
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id,data.getStringExtra( NewNodeActivity.NOTE_ADDED ));
            noteViewModel.insert( note );

            Toast.makeText( this, "Saved Successfully", Toast.LENGTH_SHORT ).show();
        }
        else if (requestCode == UPDATE_NOTE_ACTIVITY_CODE && resultCode == RESULT_OK){
            Note note = new Note(
                    data.getStringExtra( EditNoteActivity.NOTE_ID ),
                    data.getStringExtra( EditNoteActivity.UPDATE_NOTE ));
                    noteViewModel.update( note );

            Toast.makeText( this, "Updated Successfully", Toast.LENGTH_SHORT ).show();


        }

        else{
            Toast.makeText( this, "Failed to save", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void OndeleteClickListener(Note myNote) {
        noteViewModel.delete( myNote );
    }
}
