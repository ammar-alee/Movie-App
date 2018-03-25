package com.interview.ammaryali.movieapp.datamodel;

import java.io.Serializable;

public class SearchMovieModel implements Serializable {
    private String title, backdropPath, originalTitle, posterPath, releaseDate, overView,genres;
    private int id;
    private String rating;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(String rating){
        this.rating=rating;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getOverView() {
        return overView;
    }

    public String getRating(){
        return rating;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getGenres() {
        return genres;
    }
}
