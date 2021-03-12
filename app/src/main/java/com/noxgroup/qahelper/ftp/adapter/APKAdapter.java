package com.noxgroup.qahelper.ftp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.ftp.bean.DocumentInfo;
import com.noxgroup.qahelper.util.TimeUtil;

import java.util.List;
import java.util.Locale;


public class APKAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DocumentInfo> documentInfoList;
    private OnClickListener onClickListener;

    public APKAdapter(Context context, List<DocumentInfo> documentInfoList) {
        this.context = context;
        this.documentInfoList = documentInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_apk, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (i >= 0 && i < documentInfoList.size()) {
            DocumentInfo info = documentInfoList.get(i);
            holder.tvName.setText(info.displayName);
            holder.tvTime.setText(TimeUtil.getTimeHms(info.lastModified));
            holder.ivIcon.setImageResource(getIcon(info.displayName));
            holder.tvInstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //安装
                    if (onClickListener != null) {
                        onClickListener.onInstall(i);
                    }
                }
            });
            holder.tvHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //版本历史
                    if (onClickListener != null) {
                        onClickListener.onHistory(i);
                    }
                }
            });

        }
    }

    private int getIcon(String displayName) {
        displayName = displayName.toLowerCase(Locale.ENGLISH).replace(" ", "");
        int iconRes = R.mipmap.icon_apk;
        if (displayName.contains("isecurity")) {
            iconRes = R.mipmap.ic_logo_isecurity;
        } else if (displayName.contains("iclean")) {
            iconRes = R.mipmap.ic_logo_iclean;
        } else if (displayName.contains("noxsecurity")) {
            iconRes = R.mipmap.ic_logo_noxsecurity;
        } else if (displayName.contains("noxcleaner")) {
            iconRes = R.mipmap.ic_logo_noxcleaner;
        } else if (displayName.contains("vibe")) {
            iconRes = R.mipmap.ic_logo_vibe;
        } else if (displayName.contains("fit")) {
            iconRes = R.mipmap.ic_logo_fit;
        } else if (displayName.contains("burn")) {
            iconRes = R.mipmap.ic_logo_burn;
        } else if (displayName.contains("lucky")) {
            iconRes = R.mipmap.ic_logo_lucky;
        } else if (displayName.contains("bloom")) {
            iconRes = R.mipmap.ic_logo_bloom;
        } else if (displayName.contains("animate")) {
            iconRes = R.mipmap.ic_logo_animate;
        } else if (displayName.contains("sleep")) {
            iconRes = R.mipmap.ic_logo_sleep;
        } else if (displayName.contains("lime")) {
            iconRes = R.mipmap.ic_logo_lime;
        }

        return iconRes;
    }

    @Override
    public int getItemCount() {
        return documentInfoList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvHistory;
        TextView tvTime;
        TextView tvInstall;
        ImageView ivIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvHistory = itemView.findViewById(R.id.tv_history);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvInstall = itemView.findViewById(R.id.tv_install);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }

    public interface OnClickListener {
        void onHistory(int position);

        void onInstall(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
