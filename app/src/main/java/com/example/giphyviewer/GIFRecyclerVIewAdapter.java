package com.example.giphyviewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GIFRecyclerVIewAdapter extends RecyclerView.Adapter<GIFRecyclerVIewAdapter.GIFViewHolder> {

    private ArrayList<GIFData> list;

    GIFRecyclerVIewAdapter(ArrayList<GIFData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GIFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false);
        return new GIFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GIFViewHolder holder, int position) {
        Glide.with(holder.gif.getContext())
                .asGif()
                .load(list.get(position).getImages().getFixedHeight().getUrl())
                .into(holder.gif);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GIFViewHolder extends RecyclerView.ViewHolder {

        private ImageView gif;

        GIFViewHolder(@NonNull View itemView) {
            super(itemView);

            gif = itemView.findViewById(R.id.gif_iv);
        }
    }
}