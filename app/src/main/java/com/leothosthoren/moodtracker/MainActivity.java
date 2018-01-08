package com.leothosthoren.moodtracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton mBtnComment;
    private ImageButton mBtnHistory;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnComment = (ImageButton) findViewById(R.id.main_activity_comment);
        mBtnHistory = (ImageButton) findViewById(R.id.main_activity_history);

        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

    }

    private void addComment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);

        builder.setMessage("Commentaire")
                .setView(edittext)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create()
                .show();
    }

}
