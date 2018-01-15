package com.leothosthoren.moodtracker.model;

import android.widget.ImageButton;

import java.util.Calendar;


/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodItem {
    private int mColor;
    private int mBtnComment;
    private Calendar mDate;


    public ListMoodItem(int color, int btnComment, Calendar date) {
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

    public Calendar getDate() {
        return mDate;
    }
}
