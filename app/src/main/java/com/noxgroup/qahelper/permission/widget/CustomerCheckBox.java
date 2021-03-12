package com.noxgroup.qahelper.permission.widget;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * @Author: SongRan
 * @Date: 2021/1/16
 * @Desc:
 */
public class CustomerCheckBox extends AppCompatCheckBox {
    public CustomerCheckBox(Context context) {
        this(context, null);
    }

    public CustomerCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButtonDrawable(new StateListDrawable());
        setClickable(true);
        setEnabled(true);
    }
}
