package com.example.carforum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final ArrayList<NotificationItem> items;

    public NotificationAdapter(List<NotificationItem> items) {
        this.items = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = items.get(position);
        holder.icon.setImageResource(item.getIconRes());
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.timeAgo.setText(item.getTimeAgo());
        holder.dot.setColorFilter(item.getDotColor());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        ImageView dot;
        TextView title;
        TextView subtitle;
        TextView timeAgo;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.notificationIcon);
            dot = itemView.findViewById(R.id.notificationDot);
            title = itemView.findViewById(R.id.notificationTitle);
            subtitle = itemView.findViewById(R.id.notificationSubtitle);
            timeAgo = itemView.findViewById(R.id.notificationTime);
        }
    }
}
