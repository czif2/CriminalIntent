package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mid;//产生唯一ID值
    private String mTile;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mid=UUID.randomUUID();//产生随机唯一ID值
        mDate=new Date();
    }

    public UUID getMid() {
        return mid;
    }

    public void setMid(UUID mid) {
        this.mid = mid;
    }

    public String getTile() {
        return mTile;
    }

    public void setTile(String tile) {
        mTile = tile;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
