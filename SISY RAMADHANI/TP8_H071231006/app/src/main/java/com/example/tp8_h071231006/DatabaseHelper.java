package com.example.tp8_h071231006;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_UPDATED_AT = "updated_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_CREATED_AT + " TEXT," +
                COLUMN_UPDATED_AT + " TEXT" +
                ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long addNote(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase(); //database mode writeable
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);

        String currentDateTime = getCurrentDateTime();
        values.put(COLUMN_CREATED_AT, currentDateTime);
        values.put(COLUMN_UPDATED_AT, currentDateTime);

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_UPDATED_AT, getCurrentDateTime());

        int rowsAffected = db.update(TABLE_NOTES, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteNote(long id) { //menghapus note sesuai id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_UPDATED_AT + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    @SuppressLint("Range")
    public ArrayList<Note> searchNotes(String query) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES +
                " WHERE " + COLUMN_TITLE + " LIKE '%" + query + "%'" +
                " ORDER BY " + COLUMN_UPDATED_AT + " DESC"; //search

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}