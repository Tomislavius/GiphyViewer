package com.example.giphyviewer.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giphyviewer.R;
import com.example.giphyviewer.models.GIFData;

import java.util.ArrayList;

import io.realm.RealmList;

public class GIFRecyclerVIewAdapter extends RecyclerView.Adapter<GIFRecyclerVIewAdapter.GIFViewHolder> {

    private ArrayList<GIFData> list = new ArrayList<>();
    private OnLoadMoreListener loadMoreListener;

    public GIFRecyclerVIewAdapter(OnLoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    @NonNull
    @Override
    public GIFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false);
        return new GIFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GIFViewHolder holder, final int position) {
        loadGIF(holder, position);
        openGIF(holder, position);
        if (position >= (getItemCount() - 1)) {
            loadMoreListener.onLoadMore(getItemCount());
        }
    }

    private void openGIF(@NonNull GIFViewHolder holder, int position) {
        final Bundle bundle = new Bundle();
        bundle.putString("URL", list.get(position).getImages().getFixedHeight().getUrl());

        holder.gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_giphyTrendingFragment_to_showGIFFragment, bundle);
            }
        });
    }

    private void loadGIF(@NonNull GIFViewHolder holder, int position) {
        Glide.with(holder.gif.getContext())
                .asGif()
                .load(list.get(position).getImages().getPreview().getUrl())
                .into(holder.gif);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(RealmList<GIFData> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(RealmList<GIFData> list){
        this.list.addAll(list);
        notifyItemRangeInserted(getItemCount() - list.size(), list.size());
    }

    class GIFViewHolder extends RecyclerView.ViewHolder {
        private ImageView gif;

        GIFViewHolder(@NonNull View itemView) {
            super(itemView);
            gif = itemView.findViewById(R.id.gif_iv);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int i);
    }
}