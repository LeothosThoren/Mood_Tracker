package com.leothosthoren.moodtracker.model;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/01/2018
 */
public class ListMoodData {
    private int mColor;
    private String mComment;
    private Date mDate;


    public ListMoodData(int color, String comment, Date date) {
        mColor = color;
        mComment = comment;
        mDate = date;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

}
