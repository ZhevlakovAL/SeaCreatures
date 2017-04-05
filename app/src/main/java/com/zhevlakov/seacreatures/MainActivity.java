package com.zhevlakov.seacreatures;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements GameLauncherFragment.OnFragmentInteractionListener {

    private String TAG = MainActivity.class.getSimpleName();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log("onCreate");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment firstFragment = fragmentManager.findFragmentByTag(String.valueOf(Level.DEFAULT_LEVEL));
        if(firstFragment == null) {
            FragmentTransaction fTrans = fragmentManager.beginTransaction();
            if(fTrans.isEmpty()) {
                for(int level = 1; level <= Level.LEVEL_COUNT; level++) {
                    fTrans.add(R.id.main_linear_layout, GameLauncherFragment.newInstance(level), String.valueOf(level));
                }
            }
            fTrans.commit();
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-3737226338389559~2215500627");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("6F55980C76F868D42D38797BE56931E3")
                .addTestDevice("45D461EE38FA9BA71F5BA6E9F0FD63CB")
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);


    }

    private void startGame(int level) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(Level.LEVEL, level);
        startActivity(intent);
    }

    private void log(String message) {
        if(Constants.DEBUG) {
            Log.d(TAG, message);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int level) {
        startGame(level);
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
        log("onDestroy");
    }
}
