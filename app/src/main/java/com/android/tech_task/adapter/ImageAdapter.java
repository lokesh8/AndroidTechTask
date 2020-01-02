package com.android.tech_task.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tech_task.R;
import com.android.tech_task.model.Image;
import com.android.tech_task.ui.main.ImageSelectedListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final ImageSelectedListener imageSelectedListener;
    private ArrayList<Image> imageArrayList;
    public static int VIEW_TYPE_LOADING = 1;

    public ImageAdapter(ArrayList<Image> imageArrayList, ImageSelectedListener imageSelectedListener) {
        this.imageArrayList = imageArrayList;
        this.imageSelectedListener=imageSelectedListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==VIEW_TYPE_LOADING){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_progress_bar,viewGroup,false);
            return new ImageViewHolder(view);
        }else {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_image,viewGroup,false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imageArrayList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Image image = imageArrayList.get(i);
        if(image.getViewType()!= VIEW_TYPE_LOADING){
            Glide.with(imageViewHolder.imageView.getContext())
                    .load(image.getLargeImageURL())
                    .into(imageViewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return (long) imageArrayList.get(position).getId();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(view -> {
                int position=getAdapterPosition();
                if (position > RecyclerView.NO_POSITION) {
                    imageSelectedListener.onImageSelected(imageArrayList.get(position));
                }
            });
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        ProgressViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
