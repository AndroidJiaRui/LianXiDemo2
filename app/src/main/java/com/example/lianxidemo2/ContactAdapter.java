package com.example.lianxidemo2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @Created by xww.
 * @Creation time 2018/8/18.
 * @Email 767412271@qq.com
 * @Blog https://blog.csdn.net/smile_running
 */

public class ContactAdapter extends BaseAdapter {

    private ArrayList<Contact> mContacts;

    ContactAdapter(ArrayList<Contact> contacts) {
        this.mContacts = contacts;
    }

    @Override
    public int getCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contact, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String name = mContacts.get(position).getName();
        final String pinyin = mContacts.get(position).getPinyin().substring(0, 1);
        if (position == 0) {
            holder.tvPinYin.setVisibility(View.VISIBLE);
        } else {
            //首字母是否与前面一个一致
            final String prePinyin = mContacts.get(position - 1).getPinyin().substring(0, 1);
            if (pinyin.equals(prePinyin)) {
                holder.tvPinYin.setVisibility(View.GONE);
            } else {
                holder.tvPinYin.setVisibility(View.VISIBLE);
            }
        }

        holder.tvName.setText(name);
        holder.tvPinYin.setText(pinyin);
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvName;
        private TextView tvPinYin;

        ViewHolder(View itemView) {
            tvName = itemView.findViewById(R.id.tv_name);
            tvPinYin = itemView.findViewById(R.id.tv_pinyin);
        }
    }
}
