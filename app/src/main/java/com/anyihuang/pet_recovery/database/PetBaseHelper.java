package com.anyihuang.pet_recovery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anyihuang.pet_recovery.database.PetDbSchema.PetTable;

public class PetBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "petBase.db";

    public PetBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PetTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PetTable.Cols.UUID + ", " +
                PetTable.Cols.NAME + ", " +
                PetTable.Cols.LOCATION + ", " +
                PetTable.Cols.DETAIL + ", " +
                PetTable.Cols.DATE + ", " +
                PetTable.Cols.FOUND +
                PetTable.Cols.PHOTO +", " +
                PetTable.Cols.MALE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}