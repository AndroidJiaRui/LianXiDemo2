package com.example.lianxidemo2;

/**
 * @Created by xww.
 * @Creation time 2018/8/18.
 * @Email 767412271@qq.com
 * @Blog https://blog.csdn.net/smile_running
 */

public class Contact {
    private String mName;
    private String mPinyin;

    Contact(String name) {
        this.mName = name;
        this.mPinyin = PinYinUtils.getPinYin(mName);
    }

    public String getName() {
        return mName;
    }

    public String getPinyin() {
        return mPinyin;
    }
}
