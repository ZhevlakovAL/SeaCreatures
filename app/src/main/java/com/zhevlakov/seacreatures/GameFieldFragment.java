package com.zhevlakov.seacreatures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import static android.content.Context.MODE_PRIVATE;

public class GameFieldFragment extends Fragment implements View.OnClickListener, IGameFieldFragment {
    private static final String TAG = GameFieldFragment.class.getSimpleName();

    private static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WEIGHT_ONE = 1;
    private TableLayout mainTable;
    TitleTimer titleTimer;

    IWinEventListener winEventListener;
    Data data;

    @Override
    public void init() {
        int level = data.getLevel();
        String savedData = getStateFromPreferences(level);
        if(savedData == null ) {
            fillNewData();
        } else {
            data.getMatrix().restoreData(savedData);
        }
    }

    @Override
    public void save() {
        if(data.getMatrix().checkWin()) {
            reset();
        } else {
            SharedPreferences sPref = getSharedPreferences();
            SharedPreferences.Editor ed = sPref.edit();
            String tilesPosition = data.getMatrix().getState();
            ed.putInt(Level.LEVEL, data.getLevel());
            ed.putString(getKeyForSavedData(data.getLevel()), tilesPosition);
            ed.putBoolean(Constants.SHOW_NUMBER, data.getMatrix().isShowNumber());
            ed.apply();
        }
    }

    @Override
    public void reset() {
        SharedPreferences sPref = getSharedPreferences();
        SharedPreferences.Editor ed = sPref.edit();
        ed.remove(getKeyForSavedData(data.getLevel()));
        ed.apply();
        titleTimer.reset();
    }

    @Override
    public void changeLevel(int level) {
        initData(level);
    }

    @Override
    public int getLevel() {
        return data.getLevel();
    }

    @Override
    public void changeVisibilityNumber() {
        data.getMatrix().changeVisibilityNumber();
    }

    private String getKeyForSavedData(int level) {
        return Constants.TILE_POSITION+"_"+level;
    }

    public interface IWinEventListener {
        public void win();
    }

    public GameFieldFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity=(Activity) context;
            try {
                winEventListener = (IWinEventListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement IWinEventListener");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_field,container,false);
        mainTable = (TableLayout) view.findViewById(R.id.main_table);
        return view;
    }

    private void initData(int level) {
        data = new Data(level);
        mainTable.removeAllViews();
        addControl();
        init();
        boolean showNumber = getSharedPreferences().getBoolean(Constants.SHOW_NUMBER, false);
        data.getMatrix().setShowNumber(showNumber);
        titleTimer = TitleTimer.getInstance(getActivity(), level);
        titleTimer.onCreate();
    }

    private void addControl() {
        Context context = getActivity();
        for (int row = 0; row < getMatrixSize();row ++) {
            TableRow tableRow = getTableRow(context);
            mainTable.addView(tableRow);
            for (int column = 0; column < getMatrixSize();column ++) {
                Tile tile = new Tile(context, data.getLevel(), Position.create(row, column));
                tile.setOnClickListener(this);
                TableRow.LayoutParams params = getMatchParentTableRowLayout();
                tableRow.addView(tile, params);
                data.getMatrix().setTile(Position.create(row, column), tile);
            }
        }
    }

    private int getMatrixSize() {
        return data.getMatrix().getSize();
    }

    private TableRow getTableRow(Context context) {
        TableRow tableRow = new TableRow(context);
        tableRow.setGravity(Gravity.CENTER);
        tableRow.setLayoutParams(getMatchParentTableLayout());
        return tableRow;
    }

    private TableLayout.LayoutParams getMatchParentTableLayout() {
        return new TableLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT, WEIGHT_ONE);
    }

    private TableRow.LayoutParams getMatchParentTableRowLayout() {
        return new TableRow.LayoutParams(MATCH_PARENT,MATCH_PARENT, WEIGHT_ONE);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Tile) {
            Tile currentTile = (Tile) v;
            Tile emptyNeighbour = data.getMatrix().getEmptyNeighbour(currentTile);
            if(emptyNeighbour != null) {
                //replace
                titleTimer.increaseStep();
                emptyNeighbour.setValue(currentTile.getValue());
                currentTile.setValue(Constants.EMPTY_VALUE);
                if(data.getMatrix().checkWin()) {
                    titleTimer.stop();
                    currentTile.setVisibility(View.VISIBLE);
                    winEventListener.win();
                }
            }
        }
    }

    private String getStateFromPreferences(int level) {
        SharedPreferences sPref = getSharedPreferences();
        return sPref.getString(getKeyForSavedData(level), null);
    }

    private void fillNewData() {
        data.getMatrix().fillNewData();
    }

    private SharedPreferences getSharedPreferences() {
        return getActivity().getPreferences(MODE_PRIVATE);
    }

    private void log(String message) {
        if(Constants.DEBUG) {
            Log.d(TAG, message);
        }
    }

    @Override
    public void onDestroyView() {
        save();
        titleTimer.stop();
        titleTimer.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(titleTimer.getStep() > 0) {
            titleTimer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        titleTimer.stop();
    }
}