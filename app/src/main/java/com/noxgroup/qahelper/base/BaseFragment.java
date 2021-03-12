package com.noxgroup.qahelper.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Author: SongRan
 * @Date: 2021/2/25
 * @Desc:
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;
    protected Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = ((ViewGroup) rootView.getParent());
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    protected abstract @LayoutRes
    int getLayoutId();

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
