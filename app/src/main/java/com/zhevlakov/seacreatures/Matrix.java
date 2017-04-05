package com.zhevlakov.seacreatures;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by aleksey on 01.03.17.
 */

public class Matrix {
    private static final String TAG = GameActivity.class.getSimpleName();

    private int size;
    private Tile[][] tiles;

    public Matrix(int size) {
        this.size = size;
        tiles = new Tile[size][size];
    }

    public int getSize() {
        return size;
    }

    public void setTile(Position position, Tile tile) {
        tiles[position.getRow()][position.getColumn()] = tile;
    }

    public Tile getTile(Position position) {
        return tiles[position.getRow()][position.getColumn()];
    }

    public String getState() {
        StringBuilder chipPosition = new StringBuilder();
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                chipPosition.append(Constants.SPLITTER);
                chipPosition.append(tiles[row][column].getValue());
            }
        }
        return chipPosition.toString();
    }

    public void fillNewData() {
        HashSet<Integer> existingValues = new HashSet<>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                tiles[row][column].setValue(getValue(existingValues));
            }
        }
        correctedData();

    }

    private void correctedData() {
        int inversion = getInversionCount();
        boolean nechet = isNechet(inversion);
        Log.d(TAG, "inversion="+inversion+"; nechet="+nechet+";");
        if(nechet) {
            changeNonZeroTiles();
        }
    }

    private void changeNonZeroTiles() {
        int rowWIthoutZero=0;
        for(;rowWIthoutZero<size;rowWIthoutZero++) {
            boolean isNonZero = true;
            for (int column = 0; column < size; column++) {
                if(tiles[rowWIthoutZero][column].getValue() == 0) {
                    isNonZero=false;
                }
            }
            if(isNonZero) {
                break;
            }

        }
        Log.d(TAG, "changeLastTiles "+rowWIthoutZero+" "+tiles[rowWIthoutZero][size-2].getValue()+" <-> "+tiles[rowWIthoutZero][size-1].getValue());
        int last_value = tiles[rowWIthoutZero][size-1].getValue();
        tiles[rowWIthoutZero][size-1].setValue(tiles[rowWIthoutZero][size-2].getValue());
        tiles[rowWIthoutZero][size-2].setValue(last_value);

    }

    public static boolean isNechet(int value) {
        return (value%2)!=0;
    }
    //Алгоритм расчета описан здесь http://pyatnashki.wmsite.ru/kombinacyi
    private int getInversionCount() {
        int[] tileToRow = new int[size*size-1];
        int index = 0;
        int zeroRow = 1;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(tiles[row][column].getValue() != 0) {
                    tileToRow[index++] = tiles[row][column].getValue();
                } else {
                    zeroRow=row+1;
                }
            }
        }
        int inversion = 0;
        for(index = 0; index<tileToRow.length-1; index++) {
            int inv = 0;
            for(int internalIndex = index+1; internalIndex<tileToRow.length; internalIndex++) {
                inv += tileToRow[index] > tileToRow[internalIndex] ? 1 : 0;
            }
            inversion+=inv;
        }
        if(size == 4) {
            inversion+=zeroRow;
        }
        return inversion;
    }


    public void restoreData(String savedData) {
        int values[] = getSavedValues(savedData);
        int index = 0;
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                tiles[row][column].setValue(values[index++]);
            }
        }
    }

    private int[] getSavedValues(String savedData) {
        String[] splitValues = savedData.split(Constants.SPLITTER);
        int values[] = new int[size * size];
        int index = 0;
        for(String value : splitValues) {
            if(value != null && value.length() > 0) {
                values[index++] = Integer.valueOf(value);
            }
        }
        return values;
    }

    private int getValue(HashSet<Integer> existingValues) {
        int value = getRandomValue();
        if(existingValues.contains(value)) {
            return getValue(existingValues);
        }
        existingValues.add(value);
        return value;
    }

    private int getRandomValue() {
        int leftBorder = Constants.EMPTY_VALUE;
        int rightBorder = size*size;
        return leftBorder + (int) (Math.random() * rightBorder);
    }

    public Tile getEmptyNeighbour(Tile currentTile) {
        Tile emptyNeighbour = null;
        if(currentTile.getValue() > 0) {
            int row = currentTile.getPosition().getRow();
            int colomn = currentTile.getPosition().getColumn();
            //bottom
            if(row > 0) {
                if(tiles[row-1][colomn].getValue() == 0 &&
                        tiles[row-1][colomn].getVisibility() != View.VISIBLE) {
                    emptyNeighbour = tiles[row-1][colomn];
                }
            }
            //left
            if(emptyNeighbour == null && colomn > 0) {
                if(tiles[row][colomn-1].getValue() == 0 &&
                        tiles[row][colomn-1].getVisibility() != View.VISIBLE) {
                    emptyNeighbour = tiles[row][colomn-1];
                }
            }
            //top
            if(emptyNeighbour == null && row < size-1) {
                if(tiles[row+1][colomn].getValue() == 0 &&
                        tiles[row+1][colomn].getVisibility() != View.VISIBLE) {
                    emptyNeighbour = tiles[row+1][colomn];
                }
            }
            //right
            if(emptyNeighbour == null && colomn < size-1) {
                if(tiles[row][colomn+1].getValue() == 0 &&
                        tiles[row][colomn+1].getVisibility() != View.VISIBLE) {
                    emptyNeighbour = tiles[row][colomn+1];
                }
            }
        }
        return emptyNeighbour;
    }

    public boolean checkWin() {
        int value = 0;
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                value++;
                if(value != size*size && tiles[row][column].getValue() != value) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isShowNumber() {
        return tiles[0][0].isShowNumber();
    }

    public void setShowNumber(boolean show) {
        if(show) {
            showNumber();
        } else {
            hideNumber();
        }

    }
    public void showNumber() {
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                tiles[row][column].showNumber();
            }
        }
    }


    public void hideNumber() {
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                tiles[row][column].hideNumber();
            }
        }
    }

    public void changeVisibilityNumber() {
        for (int row = 0; row < size;row ++) {
            for (int column = 0; column < size;column ++) {
                tiles[row][column].changeVisibilityNumber();
            }
        }
    }

}
