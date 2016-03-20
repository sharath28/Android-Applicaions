package com.example.sharath.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sharath on 20/3/16.
 */
public class Movie implements Parcelable {
    String id;
    String poster_image;
    String title;
    String release_date;
    double rating;
    String overview;
    String background_image;

    public Movie(String id,String poster_image,String title,String release_date,double rating,String overview,String background_image) {
        this.id = id;
        this.poster_image = poster_image;
        this.title = title;
        this.release_date = release_date;
        this.rating = rating;
        this.overview = overview;
        this.background_image =background_image;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        poster_image = in.readString();
        title = in.readString();
        release_date = in.readString();
        rating = in.readDouble();
        overview = in.readString();
        background_image = in.readString();
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
        dest.writeString(poster_image);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeDouble(rating);
        dest.writeString(overview);
        dest.writeString(background_image);
    }
}
