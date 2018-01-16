package com.leothosthoren.moodtracker.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.model.MoodDataStorage;
import com.leothosthoren.moodtracker.view.ListMoodAdapter;

import java.util.ArrayList;
import java.util.Date;

import static com.leothosthoren.moodtracker.controler.MainActivity.COMMENT;
import static com.leothosthoren.moodtracker.controler.MainActivity.INDEX;
import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;

public class HistoryActivity extends AppCompatActivity {

    MoodDataStorage data = new MoodDataStorage();
    ArrayList<ListMoodItem> mListMoodItems = new ArrayList<>();
    Date now = new Date();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        data.loadData(mListMoodItems, this);
        buildRecyclerView();

        //Button for testing sharepreferences
        Button btnSave = (Button) findViewById(R.id.Btn_save);
        Button btnDelete = (Button) findViewById(R.id.Btn_delete);
        Button btnInsert = (Button) findViewById(R.id.Btn_insert);
        btnComment = (ImageButton) findViewById(R.id.item_history_commentBtn);

        if (COMMENT == null || COMMENT.equals("")) {
            btnComment.setEnabled(false);
            btnComment.setVisibility(View.INVISIBLE);
        }

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HistoryActivity.this, COMMENT, Toast.LENGTH_LONG).show();
            }
        });

        /**Series of button for test**/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.saveData(mListMoodItems, HistoryActivity.this);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clearData(HistoryActivity.this);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListMoodItems.add(new ListMoodItem(LIST_COLOR_IMG[0][INDEX],
                        R.drawable.ic_comment_black_48px,
                        now,
                        COMMENT));
            }
        });

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListMoodAdapter(mListMoodItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
