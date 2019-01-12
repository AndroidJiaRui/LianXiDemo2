package com.example.lianxidemo2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ContactIndexView mIndexView;
    private TextView mShowTextView;

    private Random mRandom;
    private CountDownTimer mCountDownTimer;
    private ContactAdapter mAdapter;
    private ArrayList<Contact> mContacts;
    private String mName = null;
    private String mLetter;

    //姓
    private String[] surname = {"李", "钟", "王", "安", "许", "施", "扬", "高", "刘", "潘",
            "钱", "欧", "林", "徐", "郭", "赖", "翁", "薛", "蔡", "郝", "宋", "马", "张", "吴"};

    private String[] name1 = {"笑", "偶", "枚", "与", "兰", "巧", "中", "都", "科", "盘"};
    private String[] name2 = {"分", "会", "年", "为", "入", "静", "六", "和", "图", "本"};
    private String[] name3 = {"Java", "Tomcat", "Python", "Kotlin", "PHP", "Android", "Windows", "Linux",
            "Ruby", "JavaScript", "Swift", "Pert", "Objective-C"};

    private static final String[] NAME = new String[]{
            "露娜", "李白", "韩信", "太乙真人", "李元芳", "阿珂", "夏侯惇", "关羽", "张飞", "刘备", "貂蝉", "吕布", "王昭君", "武则天",
            "百里守约", "百里玄策", "司马懿", "孙策", "干将莫邪", "裴擒虎", "张良", "诸葛亮", "达摩", "蒙奇", "曹操", "钟馗", "钟无艳",
            "程咬金", "米莱狄", "狄仁杰", "后羿", "大乔", "小乔", "刘邦", "杨玉环", "马可波罗", "狂铁", "苏烈", "赵云", "公孙离", "鬼谷子",
            "成吉思汗", "哪吒", "杨戬", "嬴政", "女娲", "周瑜", "弈星", "扁鹊", "甄姬墨子", "高渐离", "亚瑟", "姜子牙", "宫本武藏",
            "牛魔", "庄周", "蔡文姬", "黄忠", "鲁班七号", "铠", "妲己", "白起", "安其拉", "不知火舞", "芈月", "项羽", "刘禅", "橘右京",
            "兰陵王", "典韦", "元歌", "明世隐", "雅典娜", "娜可露露", "东皇太一", "花木兰", "孙尚香", "孙膑", "虞姬", "孙悟空", "老夫子"
    };

    @SuppressWarnings("unchecked")
    private <T extends View> T $(int id) {
        View view = findViewById(id);
        return (T) view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = $(R.id.list_contact);
        mIndexView = $(R.id.view_contact);
        mShowTextView = $(R.id.tv_show_letter);

        initData();
        initListener();
    }

    private void initData() {
        mRandom = new Random();
        mContacts = new ArrayList<>();
//        final int surname_len = surname.length;
//        final int name1_len = name1.length;
//        final int name2_len = name2.length;
//        final int name3_len = name3.length;
//        for (int i = 0; i < 66; i++) {
//            final int rNumber = mRandom.nextInt(3);
//            if (rNumber == 0) {
//                mName = surname[mRandom.nextInt(surname_len)]
//                        + name1[mRandom.nextInt(name1_len)]
//                        + name2[mRandom.nextInt(name2_len)];
//            } else if (rNumber == 1) {
//                mName = surname[mRandom.nextInt(surname_len)]
//                        + name1[mRandom.nextInt(name1_len)];
//            } else {
//                mName = name3[mRandom.nextInt(name3_len)];
//            }
//            mContacts.add(new Contact(mName));
//        }

        for (String name : NAME) {
            mContacts.add(new Contact(name));
        }

        //Collections.sort(mContacts, (o1, o2) -> o1.getPinyin().compareTo(o2.getPinyin()));
        Collections.sort(mContacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getPinyin().compareTo(t1.getPinyin());
            }
        });
        mAdapter = new ContactAdapter(mContacts);
        mListView.setAdapter(mAdapter);
    }

    @SuppressWarnings("UnusedAssignment")
    private void initListener() {
        //右侧点击事件
        /*mIndexView.setOnShowLetter(letter -> {
            mShowTextView.setText(letter);
            mLetter = letter;

            if (mCountDownTimer == null) {
                mCountDownTimer = new CountDownTimer(1500, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mShowTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mShowTextView.setVisibility(View.INVISIBLE);
                        if (mCountDownTimer != null) {
                            mCountDownTimer.cancel();
                            mCountDownTimer = null;
                        }
                    }
                }.start();
            } else {
                mCountDownTimer.start();
            }

            //选择首字母移动到当前首字母字母分类上
            for (int i = 0; i < mContacts.size(); i++) {
                final String letterName = mContacts.get(i).getPinyin().substring(0, 1);
                if (letterName.equals(mLetter)) {
                    mListView.setSelection(i);
                    return;
                }
            }
        });*/
        mIndexView.setOnShowLetter(new ContactIndexView.onShowLetterListener() {
            @Override
            public void showLatter(String letter) {
                mShowTextView.setText(letter);
                mLetter = letter;

                if (mCountDownTimer == null) {
                    mCountDownTimer = new CountDownTimer(1500, 500) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mShowTextView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            mShowTextView.setVisibility(View.INVISIBLE);
                            if (mCountDownTimer != null) {
                                mCountDownTimer.cancel();
                                mCountDownTimer = null;
                            }
                        }
                    }.start();
                } else {
                    mCountDownTimer.start();
                }

                //选择首字母移动到当前首字母字母分类上
                for (int i = 0; i < mContacts.size(); i++) {
                    final String letterName = mContacts.get(i).getPinyin().substring(0, 1);
                    if (letterName.equals(mLetter)) {
                        mListView.setSelection(i);
                        return;
                    }
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String letter = mContacts.get(firstVisibleItem).getPinyin().substring(0, 1);
                boolean isLast = false;
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isLast = true;
                }
                EventBus.getDefault().post(new ScrollEvent(letter, isLast));
            }
        });
    }
}
