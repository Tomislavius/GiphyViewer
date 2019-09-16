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
import com.example.giphyviewer.models.GifData;
import com.example.giphyviewer.models.GifModel;

import java.util.ArrayList;

import io.realm.RealmList;

public class GifRecyclerVIewAdapter extends RecyclerView.Adapter<GifRecyclerVIewAdapter.GifViewHolder> {

    private ArrayList<GifData> list = new ArrayList<>();
    private OnLoadMoreListener loadMoreListener;
    private boolean isFromSearch;

    public GifRecyclerVIewAdapter(OnLoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false);
        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GifViewHolder holder, final int position) {
        loadGif(holder, position);
        openGIF(holder, position);

        // TODO: check if this works with ==
        boolean isLastItem = position == (getItemCount() - 1);
        if (isLastItem) {
            loadMoreListener.onLoadMore(getItemCount(), isFromSearch);
        }
    }

    private void openGIF(@NonNull GifViewHolder holder, int position) {
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

    private void loadGif(@NonNull GifViewHolder holder, int position) {
        Glide.with(holder.gif.getContext())
                .asGif()
                .load(list.get(position).getImages().getPreview().getUrl())
                .into(holder.gif);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRefreshedListOfTrendingGifs(GifModel gifModel) {
        list.clear();
        isFromSearch = gifModel.isFromSearch();
        list.addAll(gifModel.getGifData());
        notifyDataSetChanged();
    }

    public void setTrendingListWithPagination(RealmList<GifData> list){
        this.list.addAll(list);
        notifyItemRangeInserted(getItemCount() - list.size(), list.size());
    }

    class GifViewHolder extends RecyclerView.ViewHolder {
        private ImageView gif;

        GifViewHolder(@NonNull View itemView) {
            super(itemView);
            gif = itemView.findViewById(R.id.gif_iv);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int i, boolean isFromSearch);
    }
}