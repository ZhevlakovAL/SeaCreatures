package com.zhevlakov.seacreatures;

/**
 * Created by aleksey on 02.03.17.
 */

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static Position create(int row, int column) {
        return new Position(row, column);
    }
}
