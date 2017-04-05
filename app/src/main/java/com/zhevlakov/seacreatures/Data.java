package com.zhevlakov.seacreatures;

/**
 * Created by aleksey on 01.03.17.
 */

public class Data {

    private Matrix matrix;
    private int level;

    public Data(int level) {
        this.level = level;
        int matrixSize = Level.getMatrixSize(level);
        matrix = new Matrix(matrixSize);
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getLevel() {
        return level;
    }

}
