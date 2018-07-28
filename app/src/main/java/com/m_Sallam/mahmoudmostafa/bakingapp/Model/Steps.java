package com.m_Sallam.mahmoudmostafa.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Steps implements Parcelable {

    private String id;
    private String shortDescription;
    private String VideoUrl;
    private String ThumbnailUrl;
    private String Description;

    public Steps(String id, String shortDescription, String videoUrl, String thumbnailUrl, String description) {
        this.id = id;
        this.shortDescription = shortDescription;
        VideoUrl = videoUrl;
        ThumbnailUrl = thumbnailUrl;
        Description = description;
    }

    protected Steps(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        VideoUrl = in.readString();
        ThumbnailUrl = in.readString();
        Description = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return ThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        ThumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(VideoUrl);
        dest.writeString(ThumbnailUrl);
        dest.writeString(Description);
    }
}
