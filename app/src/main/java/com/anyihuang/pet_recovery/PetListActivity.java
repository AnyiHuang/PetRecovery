package com.anyihuang.pet_recovery;
import androidx.fragment.app.Fragment;

public class PetListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new PetListFragment();
    }
}
