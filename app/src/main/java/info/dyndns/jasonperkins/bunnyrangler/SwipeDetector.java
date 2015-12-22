package info.dyndns.jasonperkins.bunnyrangler;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*
 *  Original came from http://stackoverflow.com/questions/4373485/android-swipe-on-list/9340202#9340202
 */
public class SwipeDetector implements View.OnTouchListener {

    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SwipeDetector";
    private static final int HORIZONTAL_MIN_DISTANCE = 100;
    private static final int VERTICAL_MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;
    private static final boolean DEBUG = false; // Turning off logging for no debug

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                if(DEBUG) {
                    String message = "downX: " + downX + " downY: " + downY + " upX: " + upX + " upY: " + upY;
                    Log.i(logTag, message);
                }

                // horizontal swipe detection
                if (Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        if(DEBUG){Log.i(logTag, "Swipe Left to Right");}
                        mSwipeDetected = Action.LR;
                        return true;
                    }
                    if (deltaX > 0) {
                        if(DEBUG){Log.i(logTag, "Swipe Right to Left");}
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                } else

                    // vertical swipe detection
                    if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            if(DEBUG){Log.i(logTag, "Swipe Top to Bottom");}
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            if(DEBUG){Log.i(logTag, "Swipe Bottom to Top");}
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                return true;
            }
        }
        return false;
    }
}
