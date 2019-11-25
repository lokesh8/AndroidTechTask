package com.android.tech_task.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private double id;
    private String largeImageURL;
    private String previewURL;
    private String type;

    private Image(Parcel in) {
        id = in.readDouble();
        largeImageURL = in.readString();
        previewURL = in.readString();
        type = in.readString();
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(id);
        parcel.writeString(largeImageURL);
        parcel.writeString(previewURL);
        parcel.writeString(type);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
