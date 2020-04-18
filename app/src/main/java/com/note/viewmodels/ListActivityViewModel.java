package com.note.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.note.database.AppRepository;
import com.note.database.NoteEntity;

import java.util.List;

public class ListActivityViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNoteList;
    private AppRepository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNoteList = mRepository.mNoteList;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllData();
    }
}
