package com.leothosthoren.moodtracker.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.model.MoodDataStorage;
import com.leothosthoren.moodtracker.view.MoodAdapter;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;
import static com.leothosthoren.moodtracker.model.MoodDataStorage.mListMoodItems;
import static com.leothosthoren.moodtracker.view.MoodAdapter.NUMBER_ITEM;

public class HistoryActivity extends AppCompatActivity {

    public static MoodAdapter mAdapter;
    //switch to true if you want to enable the developer/debug mode
    private final boolean DEV_MODE = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnDelete;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //First the activity load the data
        MoodDataStorage.loadData(this);
        //Then we build the layout with the data stored before
        buildRecyclerView();
        //This method handle two buttons for testing
        setButton();
    }


    //Button for testing shared preferences saving and delete not needed in the app
    public void setButton() {
        btnSave = (Button) findViewById(R.id.Btn_save);
        btnDelete = (Button) findViewById(R.id.Btn_delete);

        if (!DEV_MODE) {
            btnSave.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        /**Series of button for test**/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListMoodItems.size() == NUMBER_ITEM)
                    mListMoodItems.remove(0);

                if (mListMoodItems.size() < NUMBER_ITEM) {
                    mListMoodItems.add(new ListMoodItem(
                            LIST_COLOR_IMG[0][indexMood],
                            indexMood,
                            comment,
                            R.drawable.ic_comment_black_48px,
                            "date"
                    ));
                    mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    mAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemRangeChanged(mListMoodItems.size(), mAdapter.getItemCount());
                    MoodDataStorage.saveData(HistoryActivity.this);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodDataStorage.clearData(HistoryActivity.this);
            }
        });
    }

    /*
    * @toastMaker
    * @position param  the current index of the item list
    *
    * This method make a custom toast
    * */
    public void toastMaker(int position) {
        //First, get the comment store in the variable
        String text = mListMoodItems.get(position).getComment();

        //Second inflate the layout from XML and set text
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView toastText = (TextView) layout.findViewById(R.id.text);
        toastText.setText(text);

        //Finally creation of the toast and set some properties
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        //Notify the adapter of recycler view of changing
        mAdapter.notifyItemChanged(position);
    }

    /*
    * @buildRecyclerView method
    *
    * This method handle the construction of an adaptive layout
    * Two classes are called whenever this methods is used
    * */
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //Call new classes
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MoodAdapter(mListMoodItems, this);

        //Set them with natives methods
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Here we call a new class with his interface in order to handle the click on each item (meaning the layout)
        mAdapter.setOnItemClickListener(new MoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Here we allow the toast text to appear only if the comment is not empty at his own position
                if (!mListMoodItems.get(position).getComment().equals(""))
                    toastMaker(position);
            }
        });
    }

}
