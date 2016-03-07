package com.example.pratik.popularmovies3;

/**
 * Created by prati on 3/6/2016.
 */

public class Movie {
    String title,overview,image_url,release_date;
    final String BASE_URL="http://image.tmdb.org/t/p/w500/";
    double ratings;
    String final_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage_url() {
        return final_url;
    }

    public void setImage_base_url(String image_base_url) {
        this.image_url = image_base_url;
        final_url=BASE_URL+image_base_url;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }
}
