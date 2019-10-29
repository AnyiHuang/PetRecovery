package com.anyihuang.pet_recovery;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PetLab {
    private static PetLab sPetLab;
    private List<Pet> mPets;

    public static PetLab get(Context context){
        if (sPetLab == null){
            sPetLab = new PetLab(context);
        }
        return sPetLab;
    }
    private PetLab(Context context) {
        mPets = new ArrayList<>();
        for (int i = 0; i<10; i++){
            Pet pet = new Pet();
            pet.setName("Pet #" + i);
            //pet.setSolved(i % 2 == 0);//Every other one
            pet.setSolved(i % 1 == 0);//every one
            mPets.add(pet);
        }
    }

    public List<Pet> getPets(){
        return mPets;
    }

    public Pet getPet(UUID id){
        for(Pet pet : mPets){
            if(pet.getId().equals(id)){
                return pet;
            }
        }
        return null;
    }
}
