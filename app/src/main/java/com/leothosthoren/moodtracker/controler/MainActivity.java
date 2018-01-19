package com.leothosthoren.moodtracker.controler;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leothosthoren.moodtracker.R;

public class MainActivity extends AppCompatActivity {

    public static final int BUNDLE_REQUEST_CODE = 42;
    public static final int[][] LIST_COLOR_IMG = {
            {R.color.faded_red,
                    R.color.warm_grey,
                    R.color.cornflower_blue_65,
                    R.color.light_sage,
                    R.color.banana_yellow},
            {R.drawable.smiley_sad,
                    R.drawable.smiley_disappointed,
                    R.drawable.smiley_normal,
                    R.drawable.smiley_happy,
                    R.drawable.smiley_super_happy}};

    private static final int SWIPE_MIN_DISTANCE = 150;

    public static String comment = "";
    public static int indexMood = 3;
    private GestureDetectorCompat mDetector;
    //    private static final int SWIPE_MAX_OFF_PATH = 250;
//    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private ImageButton mBtnComment;
    private ImageButton mBtnHistory;
    private ImageView mSmileyImg;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnComment = (ImageButton) findViewById(R.id.main_activity_comment);
        mBtnHistory = (ImageButton) findViewById(R.id.main_activity_history);
        mSmileyImg = (ImageView) findViewById(R.id.main_activity_img);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_layout_backgound);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        /*
        *@mBtnComments
        *
        *A button on the left bottom which open an AlertDialog on click
        **/
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        /*
        * @mBtnHistory
        *
        * A button to the right bottom of the screen
        * when user click on it, the activity HistoryActivity.java is launched
        **/
        mBtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(historyActivity, BUNDLE_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /*@addComment method

    * Create an alert dialog with text input and two buttons 'Valider' and 'Annuler'
    * The user can write a comment
    **/
    private void addComment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);

        builder.setMessage("Commentaire")
                .setView(edittext)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Need to register the comment if user writes one
                        comment = edittext.getText().toString();
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

    /*
    * @getSound method
    * @sound parameter
    *
    * This method allow you to add sound or music in your app using MediaPlayer
    * */
    public void getSound(int sound) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, sound);
//        MediaPlayer.create(this, sound);
        mediaPlayer.start();
    }

    public void setEmptyComment (){
        comment = "";
    }


    /*
    * Class which manage the swipe gesture
    * Every time you slide your finger on the screen the background color and the smiley are switching
    * A sound is perform too. It indicates the user whether it's from the top or from the bottom
    * */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        /*
        * @onDown method
        * @MotionEvent parameter
        *
        * it is necessary to return true from onDown for the onFling event to register
        * */
        @Override
        public boolean onDown(MotionEvent event) {
//            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
//            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            if (indexMood < LIST_COLOR_IMG[0].length - 1 && event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE) {
                getSound(R.raw.smb_coin);
                indexMood++;
                mSmileyImg.setImageResource(LIST_COLOR_IMG[1][indexMood]);
                mRelativeLayout.setBackgroundColor(getResources().getColor(LIST_COLOR_IMG[0][indexMood]));
                setEmptyComment();


            } else if (indexMood > 0 && event2.getY() - event1.getY() > SWIPE_MIN_DISTANCE) {
                getSound(R.raw.smb_jump);
                indexMood--;
                mSmileyImg.setImageResource(LIST_COLOR_IMG[1][indexMood]);
                mRelativeLayout.setBackgroundColor(getResources().getColor(LIST_COLOR_IMG[0][indexMood]));
                setEmptyComment();

            } else
                return false;

            return true;
        }
    }
}
