package com.example.carforum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConvoViewHolder> {

    public interface Listener {
        void onConversationSelected(String threadId);
    }

    private ArrayList<MessageThread> threads;
    private final Listener listener;

    public ConversationAdapter(List<MessageThread> threads, Listener listener) {
        this.threads = new ArrayList<>(threads);
        this.listener = listener;
    }

    public void update(List<MessageThread> newThreads) {
        this.threads = new ArrayList<>(newThreads);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConvoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new ConvoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConvoViewHolder holder, int position) {
        MessageThread thread = threads.get(position);
        holder.name.setText(thread.getContact());
        String lastPreview = "No messages yet";
        if (!thread.getMessages().isEmpty()) {
            Message last = thread.getMessages().get(thread.getMessages().size() - 1);
            lastPreview = (last.isOwn() ? "You: " : "") + last.getBody();
        }
        holder.preview.setText(lastPreview);
        holder.itemView.setOnClickListener(v -> listener.onConversationSelected(thread.getId()));
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    static class ConvoViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView preview;

        ConvoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.convoName);
            preview = itemView.findViewById(R.id.convoPreview);
        }
    }
}
