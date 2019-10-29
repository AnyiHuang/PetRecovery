package com.anyihuang.pet_recovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;


public class PetListFragment extends Fragment{
    private RecyclerView mPetRecyclerView;
    private PetAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_pet_list,container,false);

        mPetRecyclerView = (RecyclerView) view
                .findViewById(R.id.pet_recycler_view);
        mPetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        PetLab petLab = PetLab.get(getActivity());
        List<Pet> pets = petLab.getPets();

        if (mAdapter == null) {
            mAdapter = new PetAdapter(pets);
            mPetRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }
    private class PetHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private Pet mPet;
        private TextView mNameTextView;
        private TextView mLocationTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public PetHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_pet,parent,false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.pet_name);
            //
            mLocationTextView = (TextView) itemView.findViewById(R.id.pet_location);
            mDateTextView = (TextView) itemView.findViewById(R.id.pet_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.pet_solved);
        }
        public void bind(Pet pet){
            mPet = pet;
            mNameTextView.setText(mPet.getName());
            //
            mLocationTextView.setText(mPet.getLocation());
            mDateTextView.setText(mPet.getDate().toString());
            mSolvedImageView.setVisibility(mPet.isSolved()?View.VISIBLE: View.GONE);
        }
        @Override
        public void onClick(View view){
            //Intent intent = new Intent(getActivity(),MainActivity.class);
            Intent intent = PetActivity.newIntent(getActivity(),mPet.getId());
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
            return new PetHolder(layoutInflater,parent);
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
    }
}
