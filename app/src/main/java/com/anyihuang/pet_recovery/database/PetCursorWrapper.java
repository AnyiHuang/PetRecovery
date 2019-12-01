package com.anyihuang.pet_recovery.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.anyihuang.pet_recovery.Pet;
import com.anyihuang.pet_recovery.database.PetDbSchema.PetTable;

import java.util.Date;
import java.util.UUID;

public class PetCursorWrapper extends CursorWrapper {
    public PetCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Pet getPet(){
        String uuidString = getString(getColumnIndex(PetTable.Cols.UUID));
        String name = getString(getColumnIndex(PetTable.Cols.NAME));
        String location = getString(getColumnIndex(PetTable.Cols.LOCATION));
        Long date = getLong(getColumnIndex(PetTable.Cols.DATE));
        String detail = getString(getColumnIndex(PetTable.Cols.DETAIL));
        int isFound = getInt(getColumnIndex(PetTable.Cols.FOUND));

        Pet pet = new Pet(UUID.fromString(uuidString));
        pet.setName(name);
        pet.setLocation(location);
        pet.setDate(new Date(date));
        pet.setDetail(detail);
        pet.setFound(isFound!=0);

        return pet;
    }

}
