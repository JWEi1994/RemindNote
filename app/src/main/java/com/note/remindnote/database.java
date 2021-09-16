package com.note.remindnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    private static final String IMG_TABLE = "imgtable";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String TIME = "time";
    public static final String MODE = "mode";
    private static final String IMG = "image";


    public database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = ("CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT NOT NULL,"
                + CONTENT + " TEXT NOT NULL,"
                + IMG + " BLOB,"
                + TIME + " TEXT NOT NULL,"
                + MODE + " INTEGER DEFAULT 1)"
        );
    }

    @Override
    //for upgrade database version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
//
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

//        for (int i = oldVersion;i<newVersion;i++){
//            switch (i){
//                case 1:
//                    break;
//                case 2:
//                    updateMode(db);
//                default:
//                    break;
//            }
//        }
    }
}
