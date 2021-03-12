package com.noxgroup.qahelper.ftp.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.ftp.bean.FileInfo;

import java.util.ArrayList;
import java.util.List;


public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FileInfo> fileList;
    private OnClickListener onClickListener;
    private Boolean isEditing = false;

    public FileAdapter(Context context, List<FileInfo> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_file, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (i >= 0 && i < fileList.size()) {
            FileInfo fileInfo = fileList.get(i);
            holder.tvName.setText(fileInfo.getFileName());
            if (fileInfo.isApk()) {
                holder.ivIcon.setImageDrawable(getAPKIcon(fileInfo.getFilePath()));
            } else {
                holder.ivIcon.setImageResource(R.mipmap.icon_file);
            }
            holder.checkBox.setChecked(fileInfo.isCheck());
            holder.checkBox.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    boolean isCheck = holder.checkBox.isChecked();
                    holder.checkBox.setChecked(!isCheck);
                    if (i >= 0 && i < fileList.size()) {
                        fileList.get(i).setCheck(!isCheck);
                    }
                } else {
                    if (onClickListener != null) {
                        onClickListener.onClick(i);
                    }
                }
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = holder.checkBox.isChecked();
                holder.checkBox.setChecked(!isCheck);
                if (i >= 0 && i < fileList.size()) {
                    fileList.get(i).setCheck(!isCheck);
                }
            }
        });
    }

    public void setEdit(boolean isEdit) {
        isEditing = isEdit;
        notifyDataSetChanged();
    }

    public List<FileInfo> getSelectList() {
        List<FileInfo> selectList = new ArrayList<>();
        for (FileInfo fileInfo : fileList
        ) {
            if (fileInfo.isCheck()) {
                selectList.add(fileInfo);
            }
        }
        return selectList;
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon;
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface OnClickListener {
        boolean onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private Drawable getAPKIcon(String filePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            // 必须加这两句，不然下面icon获取是default icon而不是应用包的icon
            appInfo.sourceDir = filePath;
            appInfo.publicSourceDir = filePath;
            // 得到图标信息
            return pm.getApplicationIcon(appInfo);
        }
        return null;
    }
}
