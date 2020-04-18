package com.note.database;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.note.utils.Constant;
import com.note.utils.SampleDataProvider;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private AppDatabase mDatabase;
    public static AppRepository ourInstance;
    public LiveData<List<NoteEntity>> mNoteList;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context){
        return ourInstance = new AppRepository(context);
    }

    private AppRepository(Context context){
        mDatabase=AppDatabase.getInstance(context);
        mNoteList = getAllNotes();
    }

    public NoteEntity loadNote(int noteId) {
        return mDatabase.notesDao().getNoteById(noteId);
    }

    private LiveData<List<NoteEntity>> getAllNotes(){
        return mDatabase.notesDao().getAllNotes();
    }

    public void insertNote(NoteEntity noteEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertNote(noteEntity);
            }
        });
    }

    public void addSampleData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertAll(SampleDataProvider.getSampleData());
            }
        });
    }

    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int notes = mDatabase.notesDao().deleteAllNotes();
                Log.d(Constant.TAG,"run: Notes Deleted:"+notes);
            }
        });
    }

    public void deleteNote(NoteEntity noteEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().deleteNote(noteEntity);
            }
        });
    }
}
