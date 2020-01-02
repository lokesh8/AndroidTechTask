package com.android.tech_task.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.tech_task.repository.ImageRepository;
import com.android.tech_task.response.ImageResponse;

public class MainViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<ImageResponse> imageResponseLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);

        imageRepository = new ImageRepository();
    }

    public void getImages(int page) {
        this.imageResponseLiveData = imageRepository.getImages(page,"cars", "photo");
    }

    public LiveData<ImageResponse> getImageResponseLiveData() {
        return imageResponseLiveData;
    }
}
