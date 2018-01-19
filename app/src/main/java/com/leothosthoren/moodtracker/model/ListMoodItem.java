package com.leothosthoren.moodtracker.model;


/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodItem {
    private int mColor;
    private int mSmileyValue;
    private String mComment;
    private int mBtnComment;
    private String mDate;


    public ListMoodItem(int color, int smileyValue, String comment, int btnComment, String date) {
        mColor = color;
        mSmileyValue = smileyValue;
        mComment = comment;
        mBtnComment = btnComment;
        mDate = date;
    }

    public int getColor() {
        return mColor;
    }

    public int getSmileyValue() {
        return mSmileyValue;
    }

    public String getComment() {
        return mComment;
    }

    public int getBtnComment() {
        return mBtnComment;
    }

    public String getDate(int value) {

        if (value == 0)
            mDate = "Aujourd'hui";
        else if (value == 1)
            mDate = "Hier";
        else
            mDate = "il y a " + value + " jours";
        return mDate;
    }

    public int getSize(int measuredWidth) {
        return measuredWidth;
    }

}
