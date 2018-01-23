package com.leothosthoren.moodtracker.controler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.MoodAlarmReceiver;

import java.util.Calendar;

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
    private MediaPlayer mediaPlayer;
    //    private static final int SWIPE_MAX_OFF_PATH = 250;
//    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private ImageButton mBtnComment;
    private ImageButton mBtnHistory;
    private ImageView mSmileyImg;
    private RelativeLayout mRelativeLayout;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnComment = (ImageButton) findViewById(R.id.main_activity_comment);
        mBtnHistory = (ImageButton) findViewById(R.id.main_activity_history);
        mSmileyImg = (ImageView) findViewById(R.id.main_activity_img);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_layout_backgound);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        //The alarm is launched
//        schedAlarm(this);

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
    * This method allow you to add sound or music from raw folder in your app by using MediaPlayer
    * */
    public void getSound(int sound) {
        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.start();
    }

    /*
    * @setEmptyComment
    *
    * This method reset the comment every time the user swipe. Indeed it's impossible
     * to have a previous comment which doesn't match with the next mood
    * */
    public void setEmptyComment() {
        comment = "";
    }

    /*
    * @schedAlarm method
    * @context param
    *
    * This method call the alarm service to perform a Task every day at midnight
    * The operation to perform is in MoodAlarmReceiver class
    * */
    private void schedAlarm(Context context) {
        //The scheddule is set to be launch at midnight
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);

        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(context, MoodAlarmReceiver.class);
        int interval = 10000;
        mPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Above we prepare pending intent and Alarm manager, then the alarm is triggered
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPendingIntent);
    }

    /*
    * Class which manage the swipe gesture
    * Every time you slide your finger on the screen the background color and the smiley are switching
    * A sound is perform too. It indicates the user whether it's from the top or from the bottom
    * */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        /*
        * @onDown method
        * @MotionEvent parameter
        *
        * it is necessary to return true from onDown for the onFling event to register
        * */
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }


        /*
        * @onFling method
        * @event1 param
        * @event2 param
        *
        * This method allow to swipe on the device. It gets the positions event1 and event2
        * When finger moves event1 and 2 become two coordinates and with the constant SWIPE_MIN_DISTANCE,
         * we can handle the distance between those coordinates
        * */
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            //This condition handle when the user swipes the screen from bottom to the top
            if (indexMood < LIST_COLOR_IMG[0].length - 1 && event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE) {
                //Image, sound and background color change every time you swipe in a direction
                getSound(R.raw.smb_coin);
                indexMood++;
                mSmileyImg.setImageResource(LIST_COLOR_IMG[1][indexMood]);
                mRelativeLayout.setBackgroundColor(getResources().getColor(LIST_COLOR_IMG[0][indexMood]));
                setEmptyComment();

                //This condition handle when the user swipes the screen from top to the bottom
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
