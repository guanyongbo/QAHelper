package com.noxgroup.qahelper.permission.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.noxgroup.qahelper.R;

/**
 * @Author: SongRan
 * @Date: 2021/1/22
 * @Desc: 带返回按钮的TextView
 */
public class BackTextView extends AppCompatTextView {

    public BackTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public BackTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setCompoundDrawablePadding(20);
        setCompoundDrawablesRelative(getContext().getDrawable(R.mipmap.icon_back_white), null, null, null);
    }
}
