package com.zhevlakov.seacreatures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        int level = intent.getIntExtra(Level.LEVEL, Level.DEFAULT_LEVEL);

        ImageView ImageView = (ImageView) findViewById(R.id.image_info);
        ImageView.setImageResource(Image.getImageInfo(this, level));

        Toast.makeText(this, R.string.press_to_screen, Toast.LENGTH_LONG).show();

        setTitle(Level.getNameId(level));
    }

    public void onClick(View view) {
        finish();
    }
}
