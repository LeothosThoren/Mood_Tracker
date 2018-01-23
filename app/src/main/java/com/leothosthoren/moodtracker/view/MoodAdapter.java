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

/**
 * Created by Sofiane M. alias Leothos Thoren on 15/01/2018
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ListMoodViewHolder> {

    public static final int NUMBER_ITEM = 8;
    private ArrayList<ListMoodItem> mListMoodItems;
    private OnItemClickListener mListener;
    private Context mContext;

    //Constructor used in the build buildRecyclerView method
    public MoodAdapter(ArrayList<ListMoodItem> listMoodItems, Context context) {
        mListMoodItems = listMoodItems;
        mContext = context;
    }

    /*
    * @setOnItemClickListener method
    *
    * @listener param  you can call View.OnClickListener()
     *
     * This method handle the click on items parent or/and their child
    * */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Override methods from RecyclerView class
     * These handle the creation, the holding and the size of the item list
     * This is the heart of the Adapter.
     */
    @Override
    public ListMoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int weight = 7;
        //Here we inflate the xml layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        //Each sub layout height is adapting with the parent (device height) we divide the screen by seven (nbr of day in week)
        view.getLayoutParams().height = parent.getHeight() / weight;
        //Initialize a new class wih all the data holding
        ListMoodViewHolder lmvh = new ListMoodViewHolder(view, mListener);
        return lmvh;
    }

    /*
    * @onBindViewHolder method
    * @holder param to update the RecyclerView.ViewHolder contents with the item at the given position
    * @position param the position of a data item within an Adapter.
    *
    * This method handle the content and the position of each item
    *
    * */
    @Override
    public void onBindViewHolder(ListMoodViewHolder holder, int position) {
        //Define the item position
        final ListMoodItem currentItem = mListMoodItems.get(position);

        //Construction of the item element by using ListMoodViewHolder and ListMoodItem constructor and methods
        holder.mRelativeLayout.setBackgroundResource(currentItem.getColor());
        //Here we substract the array size with the current position, this way we can "switch" the index position
        holder.mTextView.setText(currentItem.getDate((mListMoodItems.size() - 1) - position));
        holder.mImageViewComment.setImageResource(currentItem.getIconComment());
        holder.mToastView.setText(currentItem.getComment());

        //This handle the visibility of comment icon
        if (currentItem.getComment().equals(""))
            holder.mImageViewComment.setVisibility(View.GONE);

        layoutResizer(holder, currentItem);
    }

    /*
    * @getItemCount method
    *
    * This method return the number of item holding
    * */
    @Override
    public int getItemCount() {
        //This allow to keep only 7 items on the screen and then remove the older one when limit is reached
        if (mListMoodItems.size() == NUMBER_ITEM)
            mListMoodItems.remove(0);
        return mListMoodItems.size();
    }

    /*
    * @layoutResizer method
    * @holder param
    * @currentItem param
    *
    * This method allow to modify the layout size useful to display item at different size range
    * */
    public void layoutResizer(ListMoodViewHolder holder, ListMoodItem currentItem) {
        //Display metric can catch the width or/and height of the device
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //This variable take the width of the device and with the index value and a calculation
        //the layout width size is adjusting
        int deviceWidth, deviceHeight;
        deviceWidth = (displaymetrics.widthPixels * 20 * (currentItem.getSmileyValue() + 1)) / 100;
//        deviceHeight = (displaymetrics.heightPixels / mListMoodItems.size()-1);
        //Here we initiate the new width to each item layout
        holder.mRelativeLayout.getLayoutParams().width = deviceWidth;
//        holder.mRelativeLayout.getLayoutParams().height = deviceHeight;
    }

    //This interface provide onItemClick method which allow to click on something in the activities class
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    /**
     * This class work with the adapter
     * It's necessary to hold each elements of the main layout
     * <p>
     * This class allow to call them directly in the adapter for better performance
     */
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


            //Here we give the possibility to click on item (itemView)
            //We can choose to click on a specific element like an image view instead
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
