package com.zhevlakov.seacreatures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by aleksey on 21.03.17.
 */

public class TitleTimer {

    private Activity activity;
    private int level;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS", Locale.US);

    private long startMillis;
    private long lastTimerValueInMillis;

    private int step;
    private boolean startTimer;

    private Handler h;

    private TitleTimer() {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static TitleTimer getInstance(Activity activity, int level) {
        TitleTimer instance =  new TitleTimer();
        instance.setActivity(activity);
        instance.setLevel(level);
        return instance;
    }

    public void onCreate() {
        init();
        refreshTitle();
        h = new Handler();
        h.post(updateTimer);
    }

    private Runnable updateTimer = new Runnable() {
        public void run() {
            if(startTimer) {
                refreshTitle();
            }
            h.postDelayed(updateTimer, 100);
        }
    };

    private void refreshTitle() {
        long millis = getCurrentMillis();
        refreshTitle(millis, step);
    }

    private long getCurrentMillis() {
        if(!startTimer) {
            return lastTimerValueInMillis;
        }
        return System.currentTimeMillis() - startMillis;
    }

    private void refreshTitle(long millis, int step) {
        String time = sdf.format(new Date(millis));
        String text = activity.getString(R.string.time)+": " + time + " "+
                activity.getString(R.string.step)+": " + step;
        activity.setTitle(text);
    }

    public void start() {
        if(!startTimer) {
            correctTime();
            startTimer = true;
        }
    }

    public void stop() {
        if(startTimer) {
            startTimer = false;
            stopTimer();
        }
    }

    private void stopTimer() {
        lastTimerValueInMillis = System.currentTimeMillis() - startMillis;
    }

    public void reset() {
        step=0;
        lastTimerValueInMillis = 0;
        startMillis=System.currentTimeMillis();
        refreshTitle();
        stop();
    }

    public void increaseStep() {
        if(step == 0) {
            if(!startTimer) {
                start();
            }
        }
        if(startTimer) {
            step++;
        }
    }

    public void onDestroy() {
        h.removeCallbacks(updateTimer);
        if(startTimer) {
            stopTimer();
        }
        save();
        startTimer = false;
    }

    private void save() {
        SharedPreferences sPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong(prefix("lastTimerValueInMillis"), lastTimerValueInMillis);
        ed.putLong(prefix("startMillis"), startMillis);
        ed.putInt(prefix("step"), step);
        ed.putBoolean(prefix("startTimer"), startTimer);
        ed.apply();
    }

    private String prefix(String name) {
        return String.valueOf(level)+"_"+name;
    }

    private void init() {
        SharedPreferences sPref = activity.getPreferences(Context.MODE_PRIVATE);
        lastTimerValueInMillis = sPref.getLong(prefix("lastTimerValueInMillis"), 0L);
        startMillis = sPref.getLong(prefix("startMillis"), System.currentTimeMillis());
        step = sPref.getInt(prefix("step"), 0);
        startTimer = sPref.getBoolean(prefix("startTimer"), false);
        if(startTimer) {
            correctTime();
        }
    }

    private void correctTime() {
        startMillis = System.currentTimeMillis()-lastTimerValueInMillis;
        lastTimerValueInMillis=0;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public int getStep() {
        return step;
    }
}
