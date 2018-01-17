package com.leothosthoren.moodtracker.model;


/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodItem {
    private int mColor;
    private int mSmileyValue;
    private String comment;
    private int mBtnComment;
    private String mDate;


    public ListMoodItem(int color, int smileyValue, String comment, int btnComment, String date) {
        mColor = color;
        mSmileyValue = smileyValue;
        this.comment = comment;
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
        return comment;
    }

    public int getBtnComment() {
        return mBtnComment;
    }

    public String getDate() {
        return mDate;
    }

    public int getSize(int measuredWidth) {
        return measuredWidth;
    }

}
