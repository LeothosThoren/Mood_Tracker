package com.leothosthoren.moodtracker.model;

import java.util.Date;


/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodItem {
    private int mColor;
    private int mBtnComment;
    private Date mDate;


    public ListMoodItem(int color, int btnComment, Date date) {
        mColor = color;
        mBtnComment = btnComment;
        mDate = date;
    }

    public int getColor() {
        return mColor;
    }

    public int getBtnComment() {
        return mBtnComment;
    }

    public Date getDate() {
        return mDate;
    }
}
