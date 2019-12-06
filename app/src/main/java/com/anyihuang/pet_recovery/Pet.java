package com.anyihuang.pet_recovery;

import java.util.Date;
import java.util.UUID;

public class Pet {
    private UUID mId;
    private String mName;
    private String mLocation;
    private String mDetail;
    private long mDate;
    private int mMale;// 0:unknown 1:male 2:female
    //private boolean mFemale;
    private boolean mFound;
    private String mPhoto;

    public Pet() {
        this(UUID.randomUUID());
    }

    public Pet(UUID id) {
        mId = id;
        mDate = System.currentTimeMillis();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public void setmMale(int mMale) {
        this.mMale = mMale;
    }

    public int getmMale() {
        return mMale;
    }

    public boolean isFound() {
        return mFound;
    }

    public void setFound(boolean found) {
        mFound = found;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmPhoto() {
        return mPhoto;
    }
}
