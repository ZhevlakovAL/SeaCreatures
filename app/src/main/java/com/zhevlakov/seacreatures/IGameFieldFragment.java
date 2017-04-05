package com.zhevlakov.seacreatures;

/**
 * Created by aleksey on 02.03.17.
 */

public interface IGameFieldFragment {
    public void init();
    public void save();
    public void reset();
    public void changeLevel(int level);
    public int getLevel();
    public void changeVisibilityNumber();

}
