package com.android.tech_task.retrofit;

import com.android.tech_task.response.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("?")
    Call<ImageResponse> getImages(
            @Query("key") String key,
            @Query("q") String query,
            @Query("image_type") String type
    );
}

