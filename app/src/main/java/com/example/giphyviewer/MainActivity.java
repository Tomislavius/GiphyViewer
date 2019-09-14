package com.example.giphyviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.giphyviewer.databinding.ActivityMainBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private String selectedVideoPath;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        initObservers();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
            }
        });
    }

    private void initObservers() {
        viewModel.getUploadFileSuccessResponse().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    Toast.makeText(MainActivity.this, "File upload success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getUploadFileErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null){
                    Toast.makeText(MainActivity.this, "File upload error: "
                            + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == Constants.READ_EXTERNAL_STORAGE_PERMISSION_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.fab.performClick();
            } else {
                Toast.makeText(this, "Permission is needed to read file from gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_VIDEO) {
                selectedVideoPath = getPath(data.getData());
                try {
                    if (selectedVideoPath == null) {
                        Toast.makeText(this, "Selected file not found!", Toast.LENGTH_SHORT).show();
                    } else {
                        MultipartBody.Part body = createRequestBodyFromFile();
                        viewModel.uploadFile(body);
                    }
                } catch (Exception e) {
                    Log.e("FileSelectorActivity", "File select error", e);
                }
            }
        }
    }

    private MultipartBody.Part createRequestBodyFromFile() {
        File file = new File(selectedVideoPath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

    private void checkForPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        } else {
            pickFileFromGallery();
        }
    }

    private void pickFileFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.SELECT_VIDEO);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } else {
            return null;
        }
    }
}