package com.leothosthoren.moodtracker.model;


import com.leothosthoren.moodtracker.R;

import java.util.Date;


/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodItem {
    private int mColor;
    private int mBtnComment;
    private Date mDate;
    private String mToastComment;


    public ListMoodItem(int color, int btnComment, Date date, String toastComment) {
        mColor = color;
        mBtnComment = btnComment;
        mDate = date;
        mToastComment = toastComment;
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

    public String getToastComment() {
        return mToastComment;
    }
}
