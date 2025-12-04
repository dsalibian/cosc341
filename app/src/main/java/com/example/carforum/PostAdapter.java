package com.example.carforum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public interface PostInteractionListener {
        void onViewPost(String postId);
        void onEditPost(String postId);
        void onDeletePost(String postId);
        void onUpvote(Post post);
        void onDownvote(Post post);
    }

    private ArrayList<Post> posts;
    private final PostInteractionListener listener;
    private final boolean showVoting;

    public PostAdapter(List<Post> posts, PostInteractionListener listener, boolean showVoting) {
        this.posts = new ArrayList<>(posts);
        this.listener = listener;
        this.showVoting = showVoting;
    }

    public void updateData(List<Post> posts) {
        this.posts = new ArrayList<>(posts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());

        String preview = post.getContent();
        if (preview.length() > 140) {
            preview = preview.substring(0, 137) + "...";
        }
        holder.preview.setText(preview);
        String tags = String.join(", ", post.getTags());
        String meta = post.getAuthor();
        if (!tags.isEmpty()) {
            meta = meta + " · " + tags;
        }
        holder.meta.setText(meta);

        if (showVoting) {
            holder.editButton.setText("Upvote");
            holder.deleteButton.setText("Downvote");
            holder.score.setVisibility(View.VISIBLE);
            holder.score.setText(String.valueOf(post.getScore()));
            holder.editButton.setOnClickListener(v -> listener.onUpvote(post));
            holder.deleteButton.setOnClickListener(v -> listener.onDownvote(post));
        } else {
            holder.editButton.setText("Edit");
            holder.deleteButton.setText("Delete");
            holder.score.setVisibility(View.GONE);
            holder.editButton.setOnClickListener(v -> listener.onEditPost(post.getId()));
            holder.deleteButton.setOnClickListener(v -> listener.onDeletePost(post.getId()));
        }

        holder.itemView.setOnClickListener(v -> listener.onViewPost(post.getId()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView preview;
        TextView meta;
        TextView score;
        Button editButton;
        Button deleteButton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            preview = itemView.findViewById(R.id.postPreview);
            meta = itemView.findViewById(R.id.postMeta);
            score = itemView.findViewById(R.id.postScore);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
