package com.zhevlakov.seacreatures;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by aleksey on 01.03.17.
 */

public class Tile extends FrameLayout {
//public class Tile extends android.support.v7.widget.AppCompatImageView {

    private int level;
    private Position position;
    private int value;
    private AppCompatImageView image;
    private TextView text;

    public Tile(Context context, int level, Position position) {
        super(context);
        image = new AppCompatImageView(getContext());
        this.image.setScaleType(ImageView.ScaleType.FIT_XY);
        initTextView();
        this.position = position;
        this.level = level;
        addView(image);
        addView(text);
    }
    private void initTextView() {
        FrameLayout.LayoutParams paramText = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        paramText.gravity = Gravity.CENTER;
        text = new TextView(getContext());
        text.setLayoutParams(paramText);
        text.setPadding(12, 12, 12, 12);
        text.setBackgroundColor(0xAA000000);
        text.setTextColor(0xffffffff);
        text.setTextSize(25);
        text.setVisibility(View.GONE);
    }

    public Position getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        image.setImageResource(Image.get(getContext(), value, level));
        this.text.setText(String.valueOf(value));
        setVisibility(value == 0 ? View.INVISIBLE : VISIBLE);
    }

    public boolean isShowNumber() {
        return text.getVisibility() == View.VISIBLE;
    }

    public void showNumber() {
        text.setVisibility(View.VISIBLE);
    }

    public void hideNumber() {
        text.setVisibility(View.GONE);
    }

    public void changeVisibilityNumber() {
        text.setVisibility(isShowNumber() ? View.GONE : View.VISIBLE);
    }

}
