package com.leothosthoren.moodtracker.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Sofiane M. alias Leothos Thoren on 15/01/2018
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ListMoodViewHolder> {

    private static final int NUMBER_ITEM = 8;
    private ArrayList<ListMoodItem> mListMoodItems;
    private OnItemClickListener mListener;
    private Context mContext;

    public MoodAdapter(ArrayList<ListMoodItem> listMoodItems, Context context) {
        mListMoodItems = listMoodItems;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ListMoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        view.getLayoutParams().height = parent.getHeight() / mListMoodItems.size();
        ListMoodViewHolder lmvh = new ListMoodViewHolder(view, mListener);
        return lmvh;
    }

    @Override
    public void onBindViewHolder(ListMoodViewHolder holder, int position) {
        final ListMoodItem currentItem = mListMoodItems.get(position);

        holder.mRelativeLayout.setBackgroundResource(currentItem.getColor());
        holder.mTextView.setText(currentItem.getDate());
        holder.mImageViewComment.setImageResource(currentItem.getBtnComment());
        holder.mToastView.setText(currentItem.getComment());

        if (currentItem.getComment().equals(""))
            holder.mImageViewComment.setVisibility(View.GONE);

        //Display metric can catch the width and height of the device
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //This variable take the width of the device and with the index value and a calculation
        //in percent the layout size is adjusting
        int devicewidth = (displaymetrics.widthPixels * 20 * (currentItem.getSmileyValue() + 1)) / 100;
        holder.mRelativeLayout.getLayoutParams().width = devicewidth;

    }

    @Override
    public int getItemCount() {
        if (mListMoodItems.size() == NUMBER_ITEM)
            mListMoodItems.remove(0);
        return mListMoodItems.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ListMoodViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mRelativeLayout;
        public TextView mTextView;
        public ImageView mImageViewComment;
        public TextView mToastView;


        private ListMoodViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.item_history_layout);
            mTextView = itemView.findViewById(R.id.item_history_text);
            mImageViewComment = itemView.findViewById(R.id.item_history_commentBtn);
            mToastView = itemView.findViewById(R.id.item_history_toast);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
