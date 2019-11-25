package com.android.tech_task.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tech_task.R;
import com.android.tech_task.adapter.ImageAdapter;
import com.android.tech_task.model.Image;
import com.android.tech_task.ui.fullimageview.FullscreenActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageSelectedListener {

    private TextView tvMsg;
    private View btnRetry;
    private View progressBar;
    private SearchView searchView;
    private MainViewModel mainViewModel;
    private ArrayList<Image> imageArrayList=new ArrayList<>();
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {
        tvMsg=findViewById(R.id.tvMsg);
        btnRetry=findViewById(R.id.btnRetry);
        progressBar=findViewById(R.id.progressBar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        imageAdapter=new ImageAdapter(imageArrayList,this);
        recyclerView.setAdapter(imageAdapter);

        btnRetry.setOnClickListener(view ->getImages(searchView.getQuery().toString()));
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getImages(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getImages(String query) {
        if(!query.isEmpty()){
            showProgressBar();
            mainViewModel.searchImages(query);
            mainViewModel.getImageResponseLiveData().observe(this, imageResponse -> {
                hideProgressBar();
                if (imageResponse != null) {
                    imageArrayList.addAll(imageResponse.getImages());
                    imageAdapter.notifyDataSetChanged();
                }else {
                    showErrorView();
                }
            });
        }
    }

    private void showErrorView() {
        tvMsg.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
        imageArrayList.clear();
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageSelected(Image image) {
        Intent intent=new Intent(this, FullscreenActivity.class);
        intent.putExtra("data",image);
        startActivity(intent);
    }
}
