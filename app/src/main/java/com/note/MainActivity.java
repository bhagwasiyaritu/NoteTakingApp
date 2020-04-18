package com.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.note.database.NoteEntity;
import com.note.model.NotesAdapter;
import com.note.utils.SampleDataProvider;
import com.note.viewmodels.ListActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private List<NoteEntity> mNotesList = new ArrayList<>();
    private ListActivityViewModel mViewModel;
    NotesAdapter mNotesAdapter;

    @BindView(R.id.notes_recyclerview)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab_add_note)
    void OnFabClicked(){
        Intent intent = new Intent(MainActivity.this,EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViewModel();

        ButterKnife.bind(this);
        initRecyclerView();
   }

    private void initViewModel() {
        Observer <List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                mNotesList.clear();
                mNotesList.addAll(noteEntities);

                if(mNotesAdapter == null){
                    mNotesAdapter = new NotesAdapter(MainActivity.this,mNotesList);
                    mRecyclerView.setAdapter(mNotesAdapter);
                }else {
                    mNotesAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(ListActivityViewModel.class);

        mViewModel.mNoteList.observe(MainActivity.this,notesObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_sample_data:{
                addSampleData();
                return true;
            }
            case R.id.delete_all_data:{
                deleteAllData();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }
}
