package com.noxgroup.qahelper.language.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;

import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> languages;
    private OnClickListener onClickListener;

    public LanguageAdapter(Context context, List<String> languages) {
        this.context = context;
        this.languages = languages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_language, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (i >= 0 && i < languages.size()) {
            holder.tvContent.setText(languages.get(i));
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
        return languages.size();
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
