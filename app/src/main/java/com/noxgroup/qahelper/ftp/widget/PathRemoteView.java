package com.noxgroup.qahelper.ftp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.noxgroup.qahelper.R;

import java.io.File;

public class PathRemoteView extends LinearLayout implements View.OnClickListener {
    private TextView tv_card_name;
    private LinearLayout ll_path_items;
    private PathListener mListener;
    private HorizontalScrollView hsv_scroll;
    private String mRootPath = "/";//根目录
    private String mCurPath = "/";

    public PathRemoteView(Context context) {
        this(context, null);
    }

    public PathRemoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathRemoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View.inflate(getContext(), R.layout.wrap_path, this);
        tv_card_name = findViewById(R.id.tv_card_name);
        tv_card_name.setOnClickListener(this);
        ll_path_items = findViewById(R.id.ll_path_items);
        hsv_scroll = findViewById(R.id.hsv_scroll);
    }

    public void initData(String path, PathListener listener) {
        if (mCurPath != null && path != null && mCurPath.length() < path.length()) {
            hsv_scroll.fullScroll(ScrollView.FOCUS_RIGHT);
        }
        tv_card_name.setText("FTP");
        tv_card_name.setTag(path);
        buildPathView(path);
        mCurPath = path;
        mRootPath = path;
        mListener = listener;
    }

    public void buildPathView(String path) {
        if (path != null && mCurPath != null) {
            if (path.length() == mCurPath.length()) {//选择了当前目录，不做操作
                return;
            }
            if (path.length() < mCurPath.length()) {//后退操作
                int count = countStr(mCurPath.substring(path.length()), File.separator);
                if (count == 0) {
                    ll_path_items.removeAllViews();
                } else {
                    ll_path_items.removeViews(ll_path_items.getChildCount() - count, count);
                }
            } else {//前进操作
                View.inflate(getContext(), R.layout.wrap_path_item, ll_path_items);
                TextView tv_item = (TextView) ll_path_items.getChildAt(ll_path_items.getChildCount() - 1);
                tv_item.setTag(path);
                tv_item.setText(path.substring(path.lastIndexOf(File.separator) + 1));
                tv_item.setOnClickListener(this);
            }
            mCurPath = path;
        }
    }

    /**
     * 判断str1中包含str2的个数
     *
     * @param str1
     * @param str2
     * @return counter
     */
    public int countStr(String str1, String str2) {
        int separatorCount = 0;
        if (str1 != null && str2 != null) {
            if (!str1.contains(str2)) {
                return 0;
            } else if (str1.contains(str2)) {
                separatorCount++;
                countStr(str1.substring(str1.indexOf(str2) + str2.length()), str2);
                return separatorCount;
            }
        }
        return separatorCount;
    }

    public String getPath() {
        int count = ll_path_items.getChildCount();
        if (count > 0) {
            return (String) ll_path_items.getChildAt(count - 1).getTag();
        }
        return mCurPath;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_card_name) {//点击了根目录
            boolean pathChanged = false;
            View lastView;
            while (ll_path_items.getChildCount() > 0 && !(lastView = ll_path_items.getChildAt(ll_path_items.getChildCount() - 1)).getTag().equals(v.getTag())) {
                ll_path_items.removeView(lastView);
                pathChanged = true;
            }
            if (pathChanged && mListener != null) {
                mCurPath = mRootPath;
                mListener.onPathChanged(mRootPath);
            }
        } else {
            boolean pathChanged = false;
            View lastView;
            while (ll_path_items.getChildCount() > 0 && (lastView = ll_path_items.getChildAt(ll_path_items.getChildCount() - 1)) != v) {
                ll_path_items.removeView(lastView);
                pathChanged = true;
            }
            if (pathChanged && mListener != null) {
                mCurPath = (String) v.getTag();
                mListener.onPathChanged(mCurPath);
            }
        }
    }

    public interface PathListener {
        void onPathChanged(String path);
    }
}