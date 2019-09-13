package com.example.giphyviewer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.giphyviewer.adapters.GIFRecyclerVIewAdapter;
import com.example.giphyviewer.databinding.FragmentGiphyTrendingBinding;
import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.trendinggifs.TrendingGifsViewModel;

import java.util.ArrayList;

public class TrendingGifsFragment extends Fragment {

    private GIFRecyclerVIewAdapter adapter;
    private FragmentGiphyTrendingBinding binding;
    private TrendingGifsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giphy_trending, container, false);
        viewModel = new ViewModelProvider(this).get(TrendingGifsViewModel.class);
        viewModel.loadTrendingGifs();
        adapter = new GIFRecyclerVIewAdapter();
        binding.gifsRv.setAdapter(adapter);

        initObserver();

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getResultsFromSearch(s.toString());
                loadResultsFromSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return binding.getRoot();
    }

    private void initObserver() {
        viewModel.getData().observe(this, new Observer<ArrayList<GIFData>>() {
            @Override
            public void onChanged(ArrayList<GIFData> gifData) {
                adapter.setData(gifData);
            }
        });
    }

    private void loadResultsFromSearch() {
        viewModel.getData().observe(this, new Observer<ArrayList<GIFData>>() {
            @Override
            public void onChanged(ArrayList<GIFData> gifData) {
                adapter.setData(gifData);
            }
        });
    }
}