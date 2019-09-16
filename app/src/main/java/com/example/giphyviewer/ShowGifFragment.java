package com.example.giphyviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.giphyviewer.databinding.FragmentShowGifBinding;

public class ShowGifFragment extends Fragment {

    private FragmentShowGifBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url = getArguments().get("URL").toString();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_gif, container, false);

        loadGIF(url);

        return binding.getRoot();
    }

    private void loadGIF(String url) {
        Glide.with(this)
                .asGif()
                .load(url)
                .error(R.drawable.no_image_available)
                .into(binding.gifFullScreenIv);
    }
}