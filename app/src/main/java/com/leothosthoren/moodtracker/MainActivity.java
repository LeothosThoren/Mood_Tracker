package com.leothosthoren.moodtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 240;
    private static final int SWIPE_MAX_OFF_PATH = 500;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private ImageButton mBtnComment;
    private ImageButton mBtnHistory;
    private ImageButton mBtnSmiley;
    private RelativeLayout mRelativeLayout;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnComment = (ImageButton) findViewById(R.id.main_activity_comment);
        mBtnHistory = (ImageButton) findViewById(R.id.main_activity_history);
        mBtnSmiley = (ImageButton) findViewById(R.id.main_activity_img);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_layout_backgound);

        /*
        *@mBtnCommentis a button on the left bottom wich open an AlertDialog on click
        * */

        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) mRelativeLayout;

        // Set the touch listener for the main view to be our custom gesture listener
        mainview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        mBtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }

    /*@addComment method
    * Create an alert dialog with text input and two buttons 'Valider' and 'Annuler'
    * */
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

    /*@ MyGestureDetector it checks when user swipe the screen*/
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            // Up to Down swipe
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                mRelativeLayout.setBackgroundColor(getColor(R.color.light_sage));
                mBtnSmiley.setImageResource(R.mipmap.smiley_happy);

                // Down to Up swipe
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                mRelativeLayout.setBackgroundColor(getColor(R.color.banana_yellow));
                mBtnSmiley.setImageResource(R.mipmap.smiley_super_happy);
            }

            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }


}
