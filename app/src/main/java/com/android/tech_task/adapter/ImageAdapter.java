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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final ImageSelectedListener imageSelectedListener;
    private ArrayList<Image> imageArrayList;

    public ImageAdapter(ArrayList<Image> imageArrayList, ImageSelectedListener imageSelectedListener) {
        this.imageArrayList = imageArrayList;
        this.imageSelectedListener=imageSelectedListener;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_image,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder viewHolder, int i) {
        Image image = imageArrayList.get(i);
        Glide.with(viewHolder.imageView.getContext())
                .load(image.getPreviewURL())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return (long) imageArrayList.get(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        ViewHolder(@NonNull View itemView) {
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
}
