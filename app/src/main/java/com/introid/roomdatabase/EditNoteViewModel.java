package com.introid.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.introid.roomdatabase.Entity.Note;
import com.introid.roomdatabase.dao.NoteDao;
import com.introid.roomdatabase.database.NoteRoomDatabase;

public class EditNoteViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private NoteRoomDatabase db;

    public EditNoteViewModel(@NonNull Application application) {
        super( application );
        db= NoteRoomDatabase.getDatabase( application );
        noteDao= db.noteDao();
    }
    public LiveData<Note> getNote(String noteId){
        return noteDao.getNote( noteId );
    }
}
