package com.example.giphyviewer.trendinggifs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.giphyviewer.R;
import com.example.giphyviewer.Utils;
import com.example.giphyviewer.adapters.GifRecyclerVIewAdapter;
import com.example.giphyviewer.databinding.FragmentGiphyTrendingBinding;
import com.example.giphyviewer.models.GifData;
import com.example.giphyviewer.models.GifModel;

import java.util.Objects;

import io.realm.RealmList;

public class TrendingGifsFragment extends Fragment implements GifRecyclerVIewAdapter.OnLoadMoreListener {

    private GifRecyclerVIewAdapter adapter;
    private FragmentGiphyTrendingBinding binding;
    private TrendingGifsViewModel viewModel;
    private String userInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
                if (!Utils.isNetworkConnected(Objects.requireNonNull(getContext())) && null != binding.refreshSr) {
                        binding.refreshSr.setRefreshing(false);
                        binding.searchEt.setVisibility(View.INVISIBLE);
                        binding.connectionError.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), getContext().getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
                    } else {
                        binding.searchEt.setText("");
                        binding.searchEt.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        private void setupRecyclerViewAdapter () {
            adapter = new GifRecyclerVIewAdapter(this);
            binding.gifsRv.setAdapter(adapter);
        }

        private void setupTextWatcher () {
            binding.searchEt.addTextChangedListener(new TextWatcher() {
                //TODO Set trending gifs when user use swipe to refresh or delete text from search bar
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (after == 0) {
                        viewModel.loadTrendingGifs(0);
                    }
                }

                //TODO Set results from search every time when user change text
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    //TODO da li treba ova provjera: if (viewModel.getTrendingList().getValue() == null)
                    userInput = s.toString();
                    if (count != 0) {
                        viewModel.getResultsFromSearch(0, userInput);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        private void initObserver () {
            viewModel.getTrendingList().observe(this, new Observer<RealmList<GifData>>() {
                @Override
                public void onChanged(RealmList<GifData> gifData) {
                    adapter.setTrendingListWithPagination(gifData);
                    binding.progressBarLoadMorePb.setVisibility(View.GONE);
                }
            });

            viewModel.getLoadingGifsError().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean != null && aBoolean) {
                        binding.connectionError.setVisibility(View.VISIBLE);
                        binding.searchEt.setVisibility(View.INVISIBLE);
                    } else {
                        binding.connectionError.setVisibility(View.INVISIBLE);
                    }
                }
            });

            viewModel.getRefreshTrendingList().observe(this, new Observer<GifModel>() {
                @Override
                public void onChanged(GifModel gifData) {
                    adapter.setRefreshedListOfTrendingGifs(gifData);
                    binding.refreshSr.setRefreshing(false);
                }
            });

            viewModel.getResponseInformation().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    String errorMessage = Utils.getErrorMessage(s);
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onLoadMore ( int offset, boolean isFromSearch){
            if (isFromSearch) {
                viewModel.getResultsFromSearch(offset, userInput);
            } else {
                viewModel.loadTrendingGifs(offset);
            }
            binding.progressBarLoadMorePb.setVisibility(View.VISIBLE);
        }
    }