package com.anyihuang.pet_recovery;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import java.util.UUID;

public class PetActivity extends SingleFragmentActivity{
    private static final String EXTRA_PET_ID =
            "com.anyihuang.pet_recovery.pet_id";

    public static Intent newIntent(Context packageContext, UUID petId) {
        Intent intent = new Intent(packageContext,PetActivity.class);
        intent.putExtra(EXTRA_PET_ID,petId);
        return intent;
    }
    @Override
    protected Fragment createFragment(){
        UUID petId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PET_ID);
        return PetFragment.newInstance(petId);
    }
}
