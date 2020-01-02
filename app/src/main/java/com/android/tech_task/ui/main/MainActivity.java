package com.android.tech_task.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tech_task.R;
import com.android.tech_task.adapter.ImageAdapter;
import com.android.tech_task.model.Image;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageSelectedListener {

    private TextView tvMsg;
    private View btnRetry;
    private View progressBar;
    private MainViewModel mainViewModel;
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private int page = 1;
    private boolean mLoading = true;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {
        tvMsg = findViewById(R.id.tvMsg);
        btnRetry = findViewById(R.id.btnRetry);
        progressBar = findViewById(R.id.progressBar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        imageAdapter = new ImageAdapter(imageArrayList, this);
        recyclerView.setAdapter(imageAdapter);

        btnRetry.setOnClickListener(view -> getImages());
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        getImages();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mLoading) {
                    if (dy > 0) {//check for scroll down
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            mLoading = false;
                            getImages();
                            imageArrayList.add( new Image(ImageAdapter.VIEW_TYPE_LOADING));
                            imageAdapter.notifyItemRangeInserted(imageArrayList.size()-1 ,1);
                        }
                    }

                }
            }
        });
    }

    private void getImages() {
        showProgressBar();
        mainViewModel.getImages(page);
        mainViewModel.getImageResponseLiveData().observe(this, imageResponse -> {
            hideProgressBar();
            if (imageResponse != null) {
                int positionStart;
                if (imageArrayList.size() > 0) {
                    positionStart = imageArrayList.size() - 1;
                } else {
                    positionStart = 0;
                }

                if (imageArrayList.size() > 0 && imageArrayList.get(positionStart).getViewType() == ImageAdapter.VIEW_TYPE_LOADING) {
                    imageArrayList.remove(positionStart);
                    imageAdapter.notifyItemRangeRemoved(positionStart, 1);
                    positionStart -= 1;
                }

                if (imageResponse.getImages().size() > 0) {
                    page++;
                    imageArrayList.addAll(imageResponse.getImages());
                    imageAdapter.notifyItemRangeInserted(positionStart+1, imageResponse.getImages().size());
                    if (imageResponse.getImages().size() == 20) {//call get order api only if items.size equal to count
                        mLoading = true;
                    }
                }
            } else {
                showErrorView();
            }
        });
    }

    private void showErrorView() {
        if(imageArrayList.size()==0) {
            tvMsg.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        if(imageArrayList.size()==0){
            progressBar.setVisibility(View.VISIBLE);
            tvMsg.setVisibility(View.GONE);
            btnRetry.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageSelected(Image image) {
        //Do Something on image select
    }
}
