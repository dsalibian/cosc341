package com.example.carforum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final ArrayList<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.body.setText(message.getBody());
        holder.sender.setText(message.isOwn() ? "You" : message.getSender());
        holder.container.setGravity(message.isOwn() ? android.view.Gravity.END : android.view.Gravity.START);
        holder.body.setBackgroundResource(message.isOwn() ? R.drawable.msg_bubble_self : R.drawable.msg_bubble_other);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView body;
        TextView sender;
        android.widget.LinearLayout container;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.msgBody);
            sender = itemView.findViewById(R.id.msgSender);
            container = itemView.findViewById(R.id.msgContainer);
        }
    }
}
