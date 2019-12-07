package com.anyihuang.pet_recovery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class PetFragment extends Fragment {
    private static final String ARG_PET_ID = "pet_id";
    private static final int REQUEST_PICK_IMAGE = 11101;
    private Pet mPet;
    private CheckBox mGenderMale;
    private CheckBox mGenderFemale;
    private CheckBox mFoundCheckBox;
    private ImageView mPhotoField;

    static PetFragment newInstance(UUID petId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET_ID, petId);

        PetFragment fragment = new PetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments) {
            UUID petId = (UUID) arguments.getSerializable(ARG_PET_ID);
            if (null != petId)
                mPet = PetLab.get(getActivity()).getPet(petId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        PetLab.get(getActivity()).updatePet(mPet);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                if (data != null) {
                    Uri uri = data.getData();
                    String realFilePath = getRealFilePath(getContext(), uri);
                    mPet.setmPhoto(realFilePath);
                    mPhotoField.setImageURI(uri);
                } else {
                    Toast.makeText(getContext(), "Picture is damaged, please select again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    @SuppressLint("IntentReset")
    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pet, container, false);
        mPhotoField = v.findViewById(R.id.pet_photo);
        mPhotoField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
        String photo = mPet.getmPhoto();
        if (!TextUtils.isEmpty(photo)) {
            Uri uri = Uri.fromFile(new File(photo));
            mPhotoField.setImageURI(uri);
        }

        EditText mNameField = v.findViewById(R.id.pet_name);
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
        EditText mLocationField = v.findViewById(R.id.pet_location);
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
        EditText mDetailField = v.findViewById(R.id.pet_detail);
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
        mGenderMale = v.findViewById(R.id.pet_gender_male);
        mGenderMale.setChecked(mPet.getmMale() == 1);
        mGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPet.setmMale(mGenderMale.isChecked() ? 1 : 2);
                mGenderFemale.setChecked(!mGenderMale.isChecked());
            }
        });

        mGenderFemale = v.findViewById(R.id.pet_gender_female);
        mGenderFemale.setChecked(mPet.getmMale() == 2);
        mGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPet.setmMale(mGenderFemale.isChecked() ? 2 : 1);
                mGenderMale.setChecked(!mGenderFemale.isChecked());
            }
        });
        Button mDateButton = v.findViewById(R.id.pet_date);
        mDateButton.setText(new Date(mPet.getDate()).toString());
        mDateButton.setEnabled(false);

        mFoundCheckBox = v.findViewById(R.id.pet_found);
        mFoundCheckBox.setChecked(mPet.isFound());
        mFoundCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPet.setFound(isChecked);
                if (isChecked) {
                    showDialog(mPet);
                }
            }
        });
        return v;
    }

    private void showDialog(Pet pet) {
        String name = pet.getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(TextUtils.isEmpty(name) ? "This pet has no name" : name)
                .setMessage("This pet has been found, the data will be deleted!").setPositiveButton("found", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delectedPet();
                    }
                }).setNegativeButton("not found", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFoundCheckBox.setChecked(false);
                        mPet.setFound(false);
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false);
        builder.create().show();
    }

    private void delectedPet() {
        PetLab.get(getContext()).delectPet(mPet);
        Toast.makeText(getContext(), "deleted", Toast.LENGTH_LONG).show();
        PetPagerActivity activity = (PetPagerActivity) getActivity();
        if (null != activity)
            activity.delectedPet();
    }

}