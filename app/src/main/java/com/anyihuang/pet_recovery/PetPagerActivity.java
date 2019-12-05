package com.anyihuang.pet_recovery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class PetPagerActivity extends AppCompatActivity {
    private static final String EXTRA_PET_ID = "com.anyihuang.pet_recovery.pet_id";

    private List<Pet> mPets;

    public static Intent newIntent(Context packageContext, UUID petId) {
        Intent intent = new Intent(packageContext, PetPagerActivity.class);
        intent.putExtra(EXTRA_PET_ID, petId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_pager);
        UUID petId = (UUID) getIntent().getSerializableExtra(EXTRA_PET_ID);

        ViewPager mViewPager = findViewById(R.id.pet_view_pager);

        mPets = PetLab.get(this).getPets();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Pet pet = mPets.get(position);
                return PetFragment.newInstance(pet.getId());
            }

            @Override
            public int getCount() {
                return mPets.size();
            }
        });

        for (int i = 0; i < mPets.size(); i++) {
            if (mPets.get(i).getId().equals(petId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void delectedPet() {
        finish();
    }
}
