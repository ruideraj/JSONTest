package com.example.jsontest.albums;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jsontest.R;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter {

    private AlbumsViewModel mViewModel;
    private List<Album> mData;

    public AlbumsAdapter(AlbumsViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.album_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder) viewHolder;

        Album album = mData.get(position);

        vh.title.setText(album.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Album> list) {
        mData = list;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.album_title);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mViewModel.onClick(position);
        }
    }
    
}
