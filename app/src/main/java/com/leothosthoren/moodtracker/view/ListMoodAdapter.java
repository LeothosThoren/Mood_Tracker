package com.leothosthoren.moodtracker.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;

import java.util.ArrayList;

/**
 * Created by Sofiane M. alias Leothos Thoren on 15/01/2018
 */
public class ListMoodAdapter extends RecyclerView.Adapter<ListMoodAdapter.ListMoodViewHolder> {
    private static final int NUMBER_ITEM = 8;
    private ArrayList<ListMoodItem> mListMoodItems;
    private OnItemClickListener mListener;

    public ListMoodAdapter(ArrayList<ListMoodItem> listMoodItems) {
        mListMoodItems = listMoodItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ListMoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int weight = 7;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        view.getLayoutParams().height = parent.getHeight() / weight;
        ListMoodViewHolder lmvh = new ListMoodViewHolder(view, mListener);
        return lmvh;
    }

    @Override
    public void onBindViewHolder(ListMoodViewHolder holder, int position) {
        ListMoodItem currentItem = mListMoodItems.get(position);

//        int widthLayout =  hold * (20 * (indexMood + 1)) / 100;
        Log.d("widthlayout value", holder.mFrameLayout.findViewById(R.id.item_history_layout).getWidth() + "");
        Log.d("Holder", holder.toString());

        holder.mFrameLayout.setBackgroundResource(currentItem.getColor());
        holder.mTextView.setText(currentItem.getDate());
        holder.mImageButton.setImageResource(currentItem.getBtnComment());
//
//        TableRow.LayoutParams params = new TableRow.LayoutParams(widthLayout, 100); // (width, height)
//        holder.mFrameLayout.setLayoutParams(params);
//
//        holder.mFrameLayout.setMinimumWidth(currentItem.getSize(widthLayout));
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
        private FrameLayout mFrameLayout;
        private TextView mTextView;
        private ImageButton mImageButton;

        private ListMoodViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mFrameLayout = itemView.findViewById(R.id.item_history_layout);
            mTextView = itemView.findViewById(R.id.item_history_text);
            mImageButton = itemView.findViewById(R.id.item_history_commentBtn);

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
