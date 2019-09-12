package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GIFData;

import java.util.ArrayList;

public interface LocalRepository {

    void saveGIFs(ArrayList<GIFData> gifData);
    void getGIFs();
}
