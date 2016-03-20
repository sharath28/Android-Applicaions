package com.example.sharath.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sharath on 20/3/16.
 */
public class Movie implements Parcelable {
    String id;
    String image;
    String title;
    String release_date;
    float rating;
    String overview;

    public Movie(String id,String image,String title,String release_date,float rating,String overview) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.release_date = release_date;
        this.rating = rating;
        this.overview = overview;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        image = in.readString();
        title = in.readString();
        release_date = in.readString();
        rating = in.readFloat();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeFloat(rating);
        dest.writeString(overview);
    }
}
