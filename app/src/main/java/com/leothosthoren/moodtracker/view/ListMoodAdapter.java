package com.leothosthoren.moodtracker.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;

import java.util.ArrayList;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.index;
import static java.text.DateFormat.SECOND_FIELD;

/**
 * Created by Sofiane M. alias Leothos Thoren on 15/01/2018
 */
public class ListMoodAdapter extends RecyclerView.Adapter<ListMoodAdapter.ListMoodViewHolder> {
    private ArrayList<ListMoodItem> mListMoodItems;

    public ListMoodAdapter(ArrayList<ListMoodItem> listMoodItems) {
        mListMoodItems = listMoodItems;
    }

    @Override
    public ListMoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        ListMoodViewHolder lmvh = new ListMoodViewHolder(view);
        return lmvh;
    }

    @Override
    public void onBindViewHolder(ListMoodViewHolder holder, int position) {
        ListMoodItem currentItem = mListMoodItems.get(position);

        holder.mFrameLayout.setBackgroundResource(currentItem.getColor());
        holder.mTextView.setText(currentItem.getDate().toString());
        holder.mImageButton.setImageResource(currentItem.getBtnComment());
    }

    @Override
    public int getItemCount() {
        return mListMoodItems.size();
    }

    public static class ListMoodViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mFrameLayout;
        public TextView mTextView;
        public ImageButton mImageButton;

        public ListMoodViewHolder(View itemView) {
            super(itemView);
            mFrameLayout = itemView.findViewById(R.id.item_history_layout);
            mTextView = itemView.findViewById(R.id.item_history_text);
            mImageButton = itemView.findViewById(R.id.item_history_commentBtn);
        }
    }
}
