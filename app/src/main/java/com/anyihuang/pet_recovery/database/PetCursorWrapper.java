package com.anyihuang.pet_recovery.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.anyihuang.pet_recovery.Pet;
import com.anyihuang.pet_recovery.database.PetDbSchema.PetTable;

import java.util.Date;
import java.util.UUID;

public class PetCursorWrapper extends CursorWrapper {
    public PetCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Pet getPet() {
        String uuidString = getString(getColumnIndex(PetTable.Cols.UUID));
        String name = getString(getColumnIndex(PetTable.Cols.NAME));
        String location = getString(getColumnIndex(PetTable.Cols.LOCATION));
        long date = getLong(getColumnIndex(PetTable.Cols.DATE));
        String detail = getString(getColumnIndex(PetTable.Cols.DETAIL));
        int isFound = getInt(getColumnIndex(PetTable.Cols.FOUND));
        String photo = getString(getColumnIndex(PetTable.Cols.PHOTO));
        int isMale = getInt(getColumnIndex(PetTable.Cols.MALE));

        Pet pet = new Pet(UUID.fromString(uuidString));
        pet.setName(name);
        pet.setLocation(location);
        pet.setDate(date);
        pet.setDetail(detail);
        pet.setFound(isFound != 0);
        pet.setmPhoto(photo);
        pet.setmMale(isMale);

        return pet;
    }

}
