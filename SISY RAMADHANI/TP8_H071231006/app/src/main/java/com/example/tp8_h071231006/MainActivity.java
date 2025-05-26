package com.example.tp8_h071231006;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {
    private DatabaseHelper databaseHelper;
    private ArrayList<Note> notes;
    private NoteAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvNoData;
    private EditText etSearch;
    private LinearLayout searchContainer;
    private ImageView ivSearch, ivCloseSearch;
    private TextView tvHeaderTitle;

    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupDatabaseAndAdapter();
        setupRecyclerView();
        checkIfEmpty();
        setupClickListeners();
    }

    private void initializeViews() {
        tvHeaderTitle = findViewById(R.id.tv_title);
        ivSearch = findViewById(R.id.iv_search);
        ivCloseSearch = findViewById(R.id.iv_close_search);
        searchContainer = findViewById(R.id.search_container);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycler_view);
        tvNoData = findViewById(R.id.tv_no_data);
    }

    private void setupDatabaseAndAdapter() {
        databaseHelper = new DatabaseHelper(this);
        notes = databaseHelper.getAllNotes();
        adapter = new NoteAdapter( notes, this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(v -> startAddNoteActivity());

        ivSearch.setOnClickListener(v -> showSearchBar());
        ivCloseSearch.setOnClickListener(v -> hideSearchBar());

        etSearch.addTextChangedListener(new TextWatcher() { //pada saat search digunakan
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNotes(s.toString());
            }
        });
    }

    private void showSearchBar() {
        searchContainer.setVisibility(View.VISIBLE);
        etSearch.requestFocus();
        showKeyboard();
    }

    private void hideSearchBar() {
        searchContainer.setVisibility(View.GONE);
        etSearch.setText("");
        searchNotes("");
        hideKeyboard();
    }

    private void showKeyboard() { // menampilkan keyboard saat menekan searchbar
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }

    private void checkIfEmpty() {
        if (notes.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void searchNotes(String query) {
        ArrayList<Note> searchedNotes = query.isEmpty() ?
                databaseHelper.getAllNotes() :
                databaseHelper.searchNotes(query);

        adapter.updateList(searchedNotes);
        checkIfEmpty();
    }

    private void startAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }

    private void startEditNoteActivity(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("note", note);
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshNotes(); //memperbarui catatan setelah di edit & tambah
        }
    }

    private void refreshNotes() {
        notes = databaseHelper.getAllNotes();
        adapter.updateList(notes);
        checkIfEmpty();
    }

    @Override
    public void onItemClick(Note note) {
        startEditNoteActivity(note);
    }
}