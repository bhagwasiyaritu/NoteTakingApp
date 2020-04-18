package com.note;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.note.database.NoteEntity;
import com.note.utils.Constant;
import com.note.viewmodels.EditorViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.note.utils.Constant.EDITING_KEY;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.edit_note_text)
    EditText mEditText;
    private EditorViewModel mViewModel;
    private Boolean mNewNote;
    private Boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            isEditing = savedInstanceState.getBoolean(Constant.EDITING_KEY);
        }

        initViewModel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {

        mViewModel= ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if (noteEntity != null) {
                    mEditText.setText(noteEntity.getText());
                }
            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle == null){
            setTitle("New Note");
            mNewNote = true;
        }else{
            setTitle("Edit Note");
            int noteId = bundle.getInt(Constant.NOTE_ID_KEY);
            mViewModel.loadNote(noteId);
            mNewNote = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewNote){
            getMenuInflater().inflate(R.menu.menu_editor,menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            saveAndExit();
            finish();
            return true;
        }else if(item.getItemId() == R.id.action_delete_note){
            deleteNote();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        mViewModel.deleteNote();
    }

    private void saveAndExit() {
        mViewModel.saveAndExit(mEditText.getText().toString());
    }
}