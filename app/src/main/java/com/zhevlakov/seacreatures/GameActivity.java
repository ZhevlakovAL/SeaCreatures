package com.zhevlakov.seacreatures;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends AppCompatActivity implements GameFieldFragment.IWinEventListener {

    private String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        int level = intent.getIntExtra(Level.LEVEL, Level.DEFAULT_LEVEL);
        getGameFieldFragment().changeLevel(level);
    }

    private IGameFieldFragment getGameFieldFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        IGameFieldFragment gameFieldFragment = (IGameFieldFragment)fragment;
        return gameFieldFragment;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new:
                newGame();
                break;
            case R.id.menu_info:
                showImage();
                break;
            case R.id.menu_number:
                showNumber();
                break;
            case R.id.main_menu:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNumber() {
        getGameFieldFragment().changeVisibilityNumber();
    }

    private void log(String message) {
        if(Constants.DEBUG) {
            Log.d(TAG, message);
        }
    }

    private void showImage() {
        Intent intent = new Intent(this, InfoActivity.class);
        int level = getGameFieldFragment().getLevel();
        intent.putExtra(Level.LEVEL, level);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("MainActivity: onDestroy()");
    }

    @Override
    public void win() {
        openWinDialog();
    }

    private void newGame() {
        //showImage();
        getGameFieldFragment().reset();
        getGameFieldFragment().init();
    }

    private void openWinDialog() {

        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle(R.string.congratulations_you_have_won);

        quitDialog.setPositiveButton(R.string.repeat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGame();
            }
        });

        quitDialog.setNegativeButton(R.string.main_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGame();
                getGameFieldFragment().save();
                finish();
            }
        });

        quitDialog.show();
    }
}
