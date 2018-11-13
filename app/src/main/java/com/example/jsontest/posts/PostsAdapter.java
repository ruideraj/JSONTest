package com.example.jsontest.posts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jsontest.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter {

    private PostsViewModel mViewModel;
    private List<Post> mData;

    public PostsAdapter(PostsViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder) viewHolder;

        Post post = mData.get(position);

        vh.title.setText(post.getTitle());
        vh.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Post> list) {
        mData = list;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView body;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.main_item_title);
            body = view.findViewById(R.id.main_item_body);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mViewModel.onClick(position);
        }
    }

}
