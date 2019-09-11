package com.example.giphyviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giphyviewer.databinding.FragmentGiphyTrendingBinding;
import com.example.giphyviewer.networking.GiphyAPI;
import com.example.giphyviewer.networking.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiphyTrendingFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private FragmentGiphyTrendingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giphy_trending, container, false);

        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.getTrendingGIFs().enqueue(new Callback<GIFModel>() {
            @Override
            public void onResponse(Call<GIFModel> call, Response<GIFModel> response) {
                adapter = new GIFRecyclerVIewAdapter(response.body().getGifData());
                binding.gifsRv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GIFModel> call, Throwable t) {
                int i = 0;
            }
        });
/*                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_giphyTrendingFragment_to_showGIFFragment);*/
        return binding.getRoot();
    }
}