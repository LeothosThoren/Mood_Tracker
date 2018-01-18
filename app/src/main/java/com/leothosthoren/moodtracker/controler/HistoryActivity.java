package com.leothosthoren.moodtracker.controler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.view.ListMoodAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;

public class HistoryActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String MOOD_DATA = "MOOD_DATA";

    ArrayList<ListMoodItem> mListMoodItems = new ArrayList<>();

    Date now = new Date();
    Locale mLocale = Locale.FRANCE;
    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", mLocale);
    String date = sdf1.format(now);

    private RecyclerView mRecyclerView;
    private ListMoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loadData();
        buildRecyclerView();

        //Button for testing sharepreferences
        Button btnSave = (Button) findViewById(R.id.Btn_save);
        Button btnDelete = (Button) findViewById(R.id.Btn_delete);


        /**Series of button for test**/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListMoodItems.add(new ListMoodItem(
                        LIST_COLOR_IMG[0][indexMood],
                        indexMood,
                        comment,
                        R.drawable.ic_comment_black_48px,
                        date
                ));
                saveData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });


//  if (COMMENT.equalsIgnoreCase(""))
//          mBtnComment.setVisibility(View.GONE);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mListMoodItems);
        editor.putString(MOOD_DATA, json).apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MOOD_DATA, null);
        Type type = new TypeToken<ArrayList<ListMoodItem>>() {
        }.getType();
        mListMoodItems = gson.fromJson(json, type);

        if (mListMoodItems == null) {
            mListMoodItems = new ArrayList<>();
        }
    }

    public void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }


    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListMoodAdapter(mListMoodItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ListMoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mListMoodItems.get(position);
            }
        });
    }


}
