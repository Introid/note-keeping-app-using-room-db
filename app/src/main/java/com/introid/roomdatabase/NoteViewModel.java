package com.introid.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.introid.roomdatabase.Entity.Note;
import com.introid.roomdatabase.dao.NoteDao;
import com.introid.roomdatabase.database.NoteRoomDatabase;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRoomDatabase noteDb;
    private NoteDao noteDao;
    private LiveData<List<Note>> mAllNotes;


    public NoteViewModel(@NonNull Application application) {
        super( application );

        noteDb= NoteRoomDatabase.getDatabase(application);
        noteDao= noteDb.noteDao();
        mAllNotes= noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InserAsyncTask( noteDao ).execute(note);
    }
    LiveData<List<Note>> getmAllNotes(){
        return mAllNotes;
    }

    public void update(Note note ){
        new UpdateAsyncTask( noteDao ).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    private static class InserAsyncTask extends AsyncTask<Note,Void,Void>{
    NoteDao mNoteDao;
    InserAsyncTask(NoteDao mNoteDao){
        this.mNoteDao= mNoteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insert( notes[0] );
        return null;
    }

    }

    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDao mNoteDao;
        UpdateAsyncTask(NoteDao noteDao) {
            this.mNoteDao= noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update( notes[0] );
            return null;
        }
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }


    private class DeleteAsyncTask extends AsyncTask<Note,Void,Void>  {
        NoteDao mNoteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.mNoteDao= noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete( notes[0] );
            return null;
        }
    }
}
