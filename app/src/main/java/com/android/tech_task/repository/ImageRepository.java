package com.android.tech_task.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.tech_task.BuildConfig;
import com.android.tech_task.response.ImageResponse;
import com.android.tech_task.retrofit.ApiRequest;
import com.android.tech_task.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private static final String TAG = ImageRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public ImageRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<ImageResponse> getImages(int page,String query, String type) {
        final MutableLiveData<ImageResponse> data = new MutableLiveData<>();
        apiRequest.getImages(BuildConfig.PIXABAY_KEY, query,type,page)
                .enqueue(new Callback<ImageResponse>() {


                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);

                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
