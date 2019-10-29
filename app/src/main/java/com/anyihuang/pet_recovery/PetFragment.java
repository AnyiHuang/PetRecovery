package com.anyihuang.pet_recovery;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;
public class PetFragment extends Fragment {
    private static final String ARG_PET_ID = "pet_id";

    private Pet mPet;
    private EditText mNameField;
    private EditText mLocationField;
    private EditText mDetailField;
    private CheckBox mGenderMale;
    private CheckBox mGenderFemale;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static PetFragment newInstance(UUID petId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET_ID, petId);

        PetFragment fragment = new PetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID petId = (UUID) getArguments().getSerializable(ARG_PET_ID);
        mPet = PetLab.get(getActivity()).getPet(petId);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_pet,container,false);

        mNameField = (EditText)v.findViewById(R.id.pet_name);
        mNameField.setText(mPet.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPet.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This one too
            }
        });
        mLocationField = (EditText)v.findViewById(R.id.pet_location);
        mLocationField.setText(mPet.getLocation());
        mLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPet.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This one too
            }
        });
        mDetailField = (EditText)v.findViewById(R.id.pet_detail);
        mDetailField.setText(mPet.getDetail());
        mDetailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPet.setDetail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This one too
            }
        });
        mGenderMale = (CheckBox)v.findViewById(R.id.pet_gender_male);
        mGenderMale.setChecked(mPet.isMale());
        mGenderMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPet.setMale(isChecked);
            }
        });

        mGenderFemale = (CheckBox)v.findViewById(R.id.pet_gender_female);
        mGenderFemale.setChecked(mPet.isFemale());
        mGenderFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPet.setFemale(isChecked);
            }
        });
        mDateButton = (Button) v.findViewById(R.id.pet_date);
        mDateButton.setText(mPet.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.pet_solved);
        mSolvedCheckBox.setChecked(mPet.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPet.setSolved(isChecked);
            }
        });
        return v;
    }
    private class PetHolder extends RecyclerView.ViewHolder{
        public PetHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_pet, parent, false));
        }
    }
}