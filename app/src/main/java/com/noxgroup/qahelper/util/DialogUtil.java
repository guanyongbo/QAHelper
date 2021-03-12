package com.noxgroup.qahelper.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.permission.adapter.AppAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: SongRan
 * @Date: 2021/1/20
 * @Desc:
 */
public class DialogUtil {

    public static Dialog showAppDialog(@NonNull WeakReference<Activity> weakReference, @NonNull AppDataListener appDataListener) {
        AlertDialog dialog = new AlertDialog.Builder(weakReference.get(), R.style.Theme_Custom_Dialog).create();
        View view = View.inflate(weakReference.get(), R.layout.dialog_app, null);
        dialog.setView(view);
        dialog.setCancelable(true);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Map<String, String> appMap = AppDataConfig.getAppData();
        List<String> list = new ArrayList<>(appMap.keySet());
        AppAdapter adapter = new AppAdapter(weakReference.get(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(weakReference.get()));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new AppAdapter.OnClickListener() {
            @Override
            public boolean onClick(int position) {
                dialog.dismiss();
                String packageName = appMap.get(list.get(position));
                appDataListener.onSelect(packageName);
                return false;
            }
        });

        if (dialog != null && !dialog.isShowing() && isAlive(weakReference.get())) {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.gravity = Gravity.CENTER;
            lp.width = (int) (ScreenUtil.getScreenWidth(weakReference.get()) * 0.85f);
            window.setAttributes(lp);
        }
        return dialog;
    }

    public static Dialog showHelpDialog(@NonNull WeakReference<Activity> weakReference, @NonNull View.OnClickListener onClickListener) {
        AlertDialog dialog = new AlertDialog.Builder(weakReference.get(), R.style.Theme_Custom_Dialog).create();
        View view = View.inflate(weakReference.get(), R.layout.dialog_help, null);
        dialog.setView(view);
        dialog.setCancelable(true);
        TextView tvHelp = view.findViewById(R.id.tv_help);
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickListener.onClick(v);
            }
        });

        if (dialog != null && !dialog.isShowing() && isAlive(weakReference.get())) {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.gravity = Gravity.CENTER;
            lp.width = (int) (ScreenUtil.getScreenWidth(weakReference.get()) * 0.85f);
            window.setAttributes(lp);
        }
        return dialog;
    }

    public static Dialog showDownloadDialog(@NonNull WeakReference<Activity> weakReference, @NonNull DownloadClickListener downloadClickListener) {
        AlertDialog dialog = new AlertDialog.Builder(weakReference.get(), R.style.Theme_Custom_Dialog).create();
        View view = View.inflate(weakReference.get(), R.layout.dialog_common, null);
        dialog.setView(view);
        dialog.setCancelable(true);
        TextView tvDownload = view.findViewById(R.id.tv_download);
        TextView tvDownloadAndOpen = view.findViewById(R.id.tv_download_and_open);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downloadClickListener.onDownload();
            }
        });
        tvDownloadAndOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downloadClickListener.onDownloadAndOpen();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downloadClickListener.onCancel();
            }
        });

        if (dialog != null && !dialog.isShowing() && isAlive(weakReference.get())) {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.gravity = Gravity.CENTER;
            lp.width = (int) (ScreenUtil.getScreenWidth(weakReference.get()) * 0.85f);
            window.setAttributes(lp);
        }
        return dialog;
    }

    public interface DownloadClickListener {
        void onDownload();

        void onDownloadAndOpen();

        void onCancel();
    }

    public interface AppDataListener {
        void onSelect(String packageName);
    }

    private static boolean isAlive(@NonNull Activity activity) {
        return !activity.isFinishing() && !activity.isDestroyed();
    }
}
