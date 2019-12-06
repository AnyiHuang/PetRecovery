package com.anyihuang.pet_recovery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anyihuang.pet_recovery.database.PetBaseHelper;
import com.anyihuang.pet_recovery.database.PetCursorWrapper;
import com.anyihuang.pet_recovery.database.PetDbSchema;
import com.anyihuang.pet_recovery.database.PetDbSchema.PetTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class PetLab {
    private static PetLab sPetLab;
    //private List<Pet> mPets;

    //database creation
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PetLab get(Context context) {
        if (sPetLab == null) {
            sPetLab = new PetLab(context);
        }
        return sPetLab;
    }

    private PetLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PetBaseHelper(mContext).getWritableDatabase();
    }

    public void addPet(Pet p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(PetTable.NAME, null, values);
    }

    public void delectPet(Pet pet) {
        String uuidString = pet.getId().toString();
        String[] args = {uuidString};
        mDatabase.delete(PetTable.NAME, PetTable.Cols.UUID + "=?", args);
    }

    public void delectAllPet() {
        List<Pet> pets = getPets();
        for (Pet pet : pets) {
            delectPet(pet);
        }
    }

    public List<Pet> getPets() {
        //return new ArrayList<>();
        List<Pet> crimes = new ArrayList<>();
        PetCursorWrapper cursor = queryPets(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getPet());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Pet getPet(UUID id) {
        PetCursorWrapper cursor = queryPets(
                PetTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPet();
        } finally {
            cursor.close();
        }
    }

    public void updatePet(Pet pet) {
        String uuidString = pet.getId().toString();
        ContentValues values = getContentValues(pet);
        mDatabase.update(PetTable.NAME, values,
                PetTable.Cols.UUID + " =?",
                new String[]{uuidString});
    }

    private PetCursorWrapper queryPets(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PetTable.NAME,
                null,//Columns - null selects all columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null//orderBy
        );
        return new PetCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Pet pet) {
        ContentValues values = new ContentValues();
        values.put(PetTable.Cols.UUID, pet.getId().toString());
        values.put(PetTable.Cols.NAME, pet.getName());
        values.put(PetTable.Cols.LOCATION, pet.getLocation());
        values.put(PetTable.Cols.DETAIL, pet.getDetail());
        values.put(PetTable.Cols.DATE, pet.getDate());
        values.put(PetTable.Cols.FOUND, pet.isFound() ? 1 : 0);
        values.put(PetTable.Cols.PHOTO, pet.getmPhoto());
        values.put(PetTable.Cols.MALE, pet.getmMale());
        return values;
    }


}
