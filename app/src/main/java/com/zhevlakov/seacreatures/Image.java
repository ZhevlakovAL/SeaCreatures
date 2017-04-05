package com.zhevlakov.seacreatures;

import android.content.Context;

/**
 * Created by aleksey on 02.03.17.
 */

public class Image {

    private Image() {
    }

    public static int get(Context context, int value, int level) {
        int matrixSize = Level.getMatrixSize(level);
        if(matrixSize == 3) {
            return getLevelFirst(context, value, level);
        } else if(matrixSize == 4) {
            return getLevelSecond(context, value, level);
        } else {
            throw new RuntimeException("unknown level "+level);
        }
    }

    public static int getLevelFirst(Context context, int value, int level) {
        switch (value) {
            case 1:
                return getImage(context, 0, 0, level);
            case 2:
                return getImage(context, 0, 1, level);
            case 3:
                return getImage(context, 0, 2, level);
            case 4:
                return getImage(context, 1, 0, level);
            case 5:
                return getImage(context, 1, 1, level);
            case 6:
                return getImage(context, 1, 2, level);
            case 7:
                return getImage(context, 2, 0, level);
            case 8:
                return getImage(context, 2, 1, level);
            case 0:
                return getImage(context, 2, 2, level);

        }
        throw new RuntimeException("unknown value");
    }

    public static int getLevelSecond(Context context, int value, int level) {
        switch (value) {
            case 1:
                return getImage(context, 0, 0, level);
            case 2:
                return getImage(context, 0, 1, level);
            case 3:
                return getImage(context, 0, 2, level);
            case 4:
                return getImage(context, 0, 3, level);
            case 5:
                return getImage(context, 1, 0, level);
            case 6:
                return getImage(context, 1, 1, level);
            case 7:
                return getImage(context, 1, 2, level);
            case 8:
                return getImage(context, 1, 3, level);
            case 9:
                return getImage(context, 2, 0, level);
            case 10:
                return getImage(context, 2, 1, level);
            case 11:
                return getImage(context, 2, 2, level);
            case 12:
                return getImage(context, 2, 3, level);
            case 13:
                return getImage(context, 3, 0, level);
            case 14:
                return getImage(context, 3, 1, level);
            case 15:
                return getImage(context, 3, 2, level);
            case 0:
                return getImage(context, 3, 3, level);

        }
        throw new RuntimeException("unknown value");
    }

    private static int getImage(Context context, int row, int column, int level) {
        String stringName = "level_"+level+"_"+row+"_"+column;
        return getImage(context, stringName);
    }

    public static int getImageInfo(Context context, int level) {
        String stringName = "level_"+level;
        return getImage(context, stringName);
    }


    private static int getImage(Context context, String sourceName) {
        return context.getResources().getIdentifier(sourceName, "drawable", context.getPackageName());
    }


}
