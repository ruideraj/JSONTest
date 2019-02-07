package com.example.jsontest.photos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jsontest.R;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Photo> photos;

    public PhotosAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder) viewHolder;

        Photo photo = photos.get(position);

        RequestOptions options = new RequestOptions().fitCenter();
        Glide.with(mContext).load(photo.getUrl()).apply(options).into(vh.image);
    }

    @Override
    public int getItemCount() {
        return photos != null ? photos.size() : 0;
    }

    public void setData(List<Photo> list) {
        photos = list;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.photo_image);
        }
    }

}
