package com.example.giphyviewer.repositories;

public interface RemoteRepository {
    void loadTrendingGifs();
    void searchGIF(String userInput);
}