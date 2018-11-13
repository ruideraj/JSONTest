package com.example.jsontest.comments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jsontest.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter {

    private List<Comment> comments;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder) viewHolder;

        Comment comment = comments.get(position);

        vh.name.setText(comment.getName());
        vh.body.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public void setData(List<Comment> list) {
        comments = list;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView body;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.comment_name);
            body = view.findViewById(R.id.comment_body);
        }
    }
}
