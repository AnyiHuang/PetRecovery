package com.anyihuang.pet_recovery;

import java.util.Date;
import java.util.UUID;

public class Pet {
    private UUID mId;
    private String mName;
    private String mLocation;
    private String mDetail;
    private Date mDate;
    private boolean mMale;
    private boolean mFemale;
    private boolean mSolved;

    public Pet(){
        mId = UUID.randomUUID();
        mDate = new Date();
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
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isMale() {
        return mMale;
    }

    public void setMale(boolean male) {
        mMale = male;
    }
    public boolean isFemale() {
        return mFemale;
    }

    public void setFemale(boolean female) {
        mFemale = female;
    }
    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
