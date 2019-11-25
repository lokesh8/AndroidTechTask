package com.android.tech_task.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import com.android.tech_task.R;
import com.android.tech_task.view_model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView tvMsg;
    private View btnRetry;
    private View progressBar;
    private SearchView searchView;
    private MainViewModel mainViewModel;

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


                }
            });
        }
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
    }
}
