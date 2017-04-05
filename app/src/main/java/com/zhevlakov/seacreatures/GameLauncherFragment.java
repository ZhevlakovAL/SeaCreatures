package com.zhevlakov.seacreatures;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GameLauncherFragment extends Fragment implements View.OnClickListener {

    private int level;

    private OnFragmentInteractionListener mListener;

    public GameLauncherFragment() {
        // Required empty public constructor
    }

    public static GameLauncherFragment newInstance(int level) {
        GameLauncherFragment fragment = new GameLauncherFragment();
        Bundle args = new Bundle();
        args.putInt(Level.LEVEL, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            level = getArguments().getInt(Level.LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_launcher, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.launcher_image);

        Glide.with(this).load(Image.getImageInfo(getActivity(), level)).into(image);

        image.setOnClickListener(this);

        TextView matrixSizeText = (TextView) view.findViewById(R.id.matrix_size);
        matrixSizeText.setText(Level.getMatrixSizeStr(level));

        TextView levelNameText = (TextView) view.findViewById(R.id.level_name);
        levelNameText.setText(Level.getNameId(level));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(level);
        }
    }

    public interface OnFragmentInteractionListener {
        void onClick(int level);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
