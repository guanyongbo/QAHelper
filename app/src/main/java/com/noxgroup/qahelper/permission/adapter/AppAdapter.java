package com.noxgroup.qahelper.permission.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;

import java.util.List;


public class AppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> ques;
    private OnClickListener onClickListener;

    public AppAdapter(Context context, List<String> ques) {
        this.context = context;
        this.ques = ques;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (i >= 0 && i < ques.size()) {
            holder.tvContent.setText(ques.get(i));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ques.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface OnClickListener {
        boolean onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
