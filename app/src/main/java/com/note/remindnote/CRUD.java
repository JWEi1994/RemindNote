package com.note.remindnote;

//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.view.textclassifier.ConversationAction;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CRUD {
//    SQLiteOpenHelper dbHandler;
//    SQLiteDatabase db;
//
//    private static final String[] columns = {
//            database.ID,
//            database.CONTENT,
//            database.TIME,
//            database.MODE
//    };
//
//    public CRUD(Context context) {
//        dbHandler = new database(context);
//    }
//
//    //open database to write it
//    public void open() {
//        db = dbHandler.getWritableDatabase();
//    }
//
//    //close database
//    public void close() {
//        dbHandler.close();
//    }
//
//    //add note to database
//    public Note addNote(Note note) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(database.CONTENT, note.getContent());
//        contentValues.put(database.TIME, note.getTime());
//        contentValues.put(database.MODE, note.getTag());
//        long insertId = db.insert(database.TABLE_NAME, null, contentValues);
//        note.setId(insertId);
//        return note;
//    }
//
//    //get note from database using cursor index
//    public Note getNote(long id) {
//        Cursor cursor = db.query(database.TABLE_NAME, columns, database.ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
//        Note e = new Note(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
//        return e;
//    }
//
//    //Get all the notes
//    @SuppressLint("Range")
//    public List<Note> getAllNote() {
//        Cursor cursor = db.query(database.TABLE_NAME, columns, null, null, null, null, null);
//
//        List<Note> notes = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                Note note = new Note();
//                note.setId(cursor.getLong(cursor.getColumnIndex((database.ID))));
//                note.setContent(cursor.getString(cursor.getColumnIndex((database.CONTENT))));
//                note.setTime(cursor.getString(cursor.getColumnIndex((database.TIME))));
//                note.setTag(cursor.getInt(cursor.getColumnIndex((database.MODE))));
//                note.add(note);
//            }
//        }
//        return notes;
//    }
//
//    //update the existing note
//    public int updateNote(Note note) {
//        ContentValues values = new ContentValues();
//        values.put(database.CONTENT, note.getContent());
//        values.put(database.TIME, note.getTime());
//        values.put(database.MODE, note.getTag());
//        //updating row
//        return db.update(database.TABLE_NAME, values, database.ID + "=?", new String[]{String.valueOf(note.getId())});
//    }
//
//    //delete the note with ID
//    public void removeNote(Note note) {
//        //remove a note according to ID value
//        db.delete(database.TABLE_NAME, database.ID + "=" + note.getId(), null);
//    }
//
//}

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            database.ID,
            database.CONTENT,
            database.TIME,
            database.MODE
    };

    public CRUD(Context context) {
        dbHandler = new database(context);
    }

    //open database to write it
    public void open() {
        db = dbHandler.getWritableDatabase();
    }

    //close database
    public void close() {
        dbHandler.close();
    }

    //add note to database
    public Note addNote(Note note) {
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(database.CONTENT, note.getContent());
        contentValues.put(database.TIME, note.getTime());
        contentValues.put(database.MODE, note.getTag());
        long insertId = db.insert(database.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    //get note from database using cursor index
    public Note getNote(long id) {
        Cursor cursor = db.query(database.TABLE_NAME, columns, database.ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Note e = new Note(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        return e;
    }

    //Get all the notes
    @SuppressLint("Range")
    public List<Note> getAllNotes() {
        Cursor cursor = db.query(database.TABLE_NAME, columns, null, null, null, null, null);
        List<Note> notes = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(database.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(database.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(database.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(database.MODE)));
                notes.add(note);
            }
        }
        return notes;
    }

    //update the existing note
    public int updateNote(Note note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(database.CONTENT, note.getContent());
        values.put(database.TIME, note.getTime());
        values.put(database.MODE, note.getTag());
        //updating row
        return db.update(database.TABLE_NAME, values, database.ID + "=?", new String[]{String.valueOf(note.getId())});
    }

    //delete note by ID
    public void removeNote(Note note) {
        db.delete(database.TABLE_NAME, database.ID + "=" + note.getId(), null);
    }

}