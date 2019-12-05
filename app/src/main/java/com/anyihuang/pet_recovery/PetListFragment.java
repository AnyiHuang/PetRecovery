package com.anyihuang.pet_recovery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;


public class PetListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mPetRecyclerView;
    private PetAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_list, container, false);

        mPetRecyclerView = view
                .findViewById(R.id.pet_recycler_view);
        mPetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_pet_list, menu);
        //menu.findItem(R.id.remove_pet);
        //menu.removeItem(R.id.remove_pet);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_pet:
                Pet pet = new Pet();
                PetLab.get(getActivity()).addPet(pet);
                Intent intent = PetPagerActivity.newIntent(getActivity(), pet.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.remove_pet:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("You will delete all pet data")
                .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PetLab.get(getContext()).delectAllPet();
                        updateUI();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    //Subtitled
    private void updateSubtitle() {
        PetLab petLab = PetLab.get(getActivity());
        int petCount = petLab.getPets().size();
        String subtitle = getString(R.string.subtitle_format, petCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        PetLab petLab = PetLab.get(getActivity());
        List<Pet> pets = petLab.getPets();

        if (mAdapter == null) {
            mAdapter = new PetAdapter(pets);
            mPetRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setPets(pets);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    private class PetHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Pet mPet;
        private TextView mNameTextView;
        private TextView mLocationTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private ImageView mPic;

        public PetHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_pet, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.pet_name);
            mPic = itemView.findViewById(R.id.pet_pic);
            mLocationTextView = (TextView) itemView.findViewById(R.id.pet_location);
            mDateTextView = (TextView) itemView.findViewById(R.id.pet_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.pet_solved);
        }

        public void bind(Pet pet) {
            mPet = pet;
            mNameTextView.setText(mPet.getName());
            mLocationTextView.setText(mPet.getLocation());
            mDateTextView.setText(mPet.getDate().toString());
            mSolvedImageView.setVisibility(mPet.isFound() ? View.VISIBLE : View.GONE);
            String photo = mPet.getmPhoto();
            if (!TextUtils.isEmpty(photo)) {
                mPic.setImageURI(Uri.fromFile(new File(photo)));
            } else {
                mPic.setImageResource(R.drawable.cat);
            }
        }

        @Override
        public void onClick(View view) {
            //Intent intent = new Intent(getActivity(),MainActivity.class);
            //Intent intent = PetActivity.newIntent(getActivity(),mPet.getId());
            Intent intent = PetPagerActivity.newIntent(getActivity(), mPet.getId());
            startActivity(intent);
        }
    }

    private class PetAdapter extends RecyclerView.Adapter<PetHolder> {
        private List<Pet> mPets;

        public PetAdapter(List<Pet> pets) {
            mPets = pets;
        }

        @Override
        public PetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PetHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PetHolder holder, int position) {
            Pet pet = mPets.get(position);
            holder.bind(pet);
        }

        @Override
        public int getItemCount() {
            return mPets.size();
        }

        public void setPets(List<Pet> pets) {
            mPets = pets;
        }
    }

}
