package com.anyihuang.pet_recovery;
import android.Manifest;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
public class PetListActivity extends SingleFragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    @Override
    protected Fragment createFragment(){
        return new PetListFragment();
    }
}
