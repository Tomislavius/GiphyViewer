package com.example.giphyviewer.trendinggifs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.giphyviewer.MainActivity;
import com.example.giphyviewer.R;
import com.example.giphyviewer.adapters.GIFRecyclerVIewAdapter;
import com.example.giphyviewer.databinding.FragmentGiphyTrendingBinding;
import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.trendinggifs.TrendingGifsViewModel;

import java.util.ArrayList;

import io.realm.RealmList;

public class TrendingGifsFragment extends Fragment {

    private GIFRecyclerVIewAdapter adapter;
    private FragmentGiphyTrendingBinding binding;
    private TrendingGifsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giphy_trending, container, false);
        viewModel = new ViewModelProvider(this).get(TrendingGifsViewModel.class);

        initObserver();
        setupRecyclerViewAdapter();
        setupRefreshListener();
        setupTextWatcher();

        return binding.getRoot();
    }

    private void setupRefreshListener() {
        binding.refreshSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.loadTrendingGifs();
            }
        });
    }

    private void setupRecyclerViewAdapter() {
        adapter = new GIFRecyclerVIewAdapter();
        binding.gifsRv.setAdapter(adapter);
    }

    private void setupTextWatcher() {
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    if (viewModel.getData().getValue() == null) {
                        viewModel.loadTrendingGifs();
                    }
                } else {
                    viewModel.getResultsFromSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //not used
            }
        });
    }

    private void initObserver() {
        viewModel.getData().observe(this, new Observer<RealmList<GIFData>>() {
            @Override
            public void onChanged(RealmList<GIFData> gifData) {
                adapter.setData(gifData);
                binding.refreshSr.setRefreshing(false);
            }
        });

        viewModel.getLoadingGifsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    binding.connectionError.setVisibility(View.VISIBLE);
//                    Toast.makeText(getContext(), "No connection, showing offline results", Toast.LENGTH_SHORT).show();
                } else {
                    binding.connectionError.setVisibility(View.GONE);
                }
            }
        });
    }
}