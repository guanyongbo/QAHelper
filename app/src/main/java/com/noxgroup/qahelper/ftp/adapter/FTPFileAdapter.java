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

import java.util.List;


public class FTPFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DocumentInfo> documentInfoList;
    private OnClickListener onClickListener;

    public FTPFileAdapter(Context context, List<DocumentInfo> documentInfoList) {
        this.context = context;
        this.documentInfoList = documentInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ftp, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (i >= 0 && i < documentInfoList.size()) {
            holder.tvName.setText(documentInfoList.get(i).displayName);
            holder.ivIcon.setImageResource(documentInfoList.get(i).fileType == DocumentInfo.FILE_TYPE ? R.mipmap.icon_file : R.mipmap.icon_folder);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(i);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onLongClick(i);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentInfoList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }

    public interface OnClickListener {
        boolean onClick(int position);

        void onLongClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
