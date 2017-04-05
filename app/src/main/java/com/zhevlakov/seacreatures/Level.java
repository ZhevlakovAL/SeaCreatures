package com.zhevlakov.seacreatures;

/**
 * Created by aleksey on 29.03.17.
 */

public class Level {

    public static final int DEFAULT_LEVEL = 1;
    public static final int LEVEL_COUNT = 10;
    public static final String LEVEL = "level";

    private static Level instance = new Level();

    private Level() {

    }

    public Level getInstance() {
        return instance;
    }


    public static int getMatrixSize(int level) {
        if(level == 1) {
            return Constants.DEFAULT_MATRIX_SIZE;
        } else if(level == 2) {
            return Constants.DEFAULT_MATRIX_SIZE;
        } else if(level == 3) {
            return Constants.DEFAULT_MATRIX_SIZE;
        } else if(level == 4) {
            return Constants.DEFAULT_MATRIX_SIZE;
        } else if(level == 5) {
            return 4;
        } else if(level == 6) {
            return 4;
        } else if(level == 7) {
            return 4;
        } else if(level == 8) {
            return 4;
        } else if(level == 9) {
            return 4;
        } else if(level == 10) {
            return 4;
        } else {
            throw new RuntimeException("unknown level "+level);
        }
    }

    public static String getMatrixSizeStr(int level) {
        int matrixSize = getMatrixSize(level);
        return String.valueOf(matrixSize)+"X"+String.valueOf(matrixSize);
    }

    public static int getNameId(int level) {
        if(level == 1) {
            return R.string.ringed_seal;
        } else if(level == 2) {
            return R.string.ringed_seal;
        } else if(level == 3) {
            return R.string.pup_of_ringed_seal;
        } else if(level == 4) {
            return R.string.leopard_seal;
        } else if(level == 5) {
            return R.string.elephant_seal;
        } else if(level == 6) {
            return R.string.elephant_seal;
        } else if(level == 7) {
            return R.string.elephant_seal;
        } else if(level == 8) {
            return R.string.sea_lion;
        } else if(level == 9) {
            return R.string.sea_lion;
        } else if(level == 10) {
            return R.string.walrus;
        } else {
            throw new RuntimeException("unknown level "+level);
        }
    }
}
