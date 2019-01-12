package com.example.lianxidemo2;

/**
 * @Created by xww.
 * @Creation time 2018/8/18.
 * @Email 767412271@qq.com
 * @Blog https://blog.csdn.net/smile_running
 */

public class ScrollEvent {
    private String letter;
    private boolean isLast;

     ScrollEvent(String letter, boolean isLast) {
        this.letter = letter;
        this.isLast = isLast;
    }

    public String getLetter() {
        return letter;
    }

    public boolean isLast() {
        return isLast;
    }
}
