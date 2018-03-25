package com.interview.ammaryali.movieapp.utils;

public interface UrlUtils {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String TMDB_API_KEY = "b2d58afef7f76a97b0d481780cbd1d90";
    String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    String IMAGE_BASE_URL_185 = "https://image.tmdb.org/t/p/w185/";
    String IMAGE_BASE_URL_320 = "https://image.tmdb.org/t/p/w320/";
    String IMAGE_BASE_URL_500 = "https://image.tmdb.org/t/p/w500/";


    String RESULTS_PAGES = "https://api.themoviedb.org/3/search/movie?query=";
    String NEXT_PAGE = "&api_key=" + TMDB_API_KEY + "&page=";


    String MOVIES_SEARCH = "http://api.themoviedb.org/3/search/movie" +
            "?api_key=" + TMDB_API_KEY + "&query=";

    String UPCOMING_MOVIES = "http://api.themoviedb.org/3/movie/upcoming" +
            "?api_key=" + TMDB_API_KEY;
    String NOW_PLAYING_MOVIES = "http://api.themoviedb.org/3/movie/now_playing" +
            "?api_key=" + TMDB_API_KEY;

    String POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular" +
            "?api_key=" + TMDB_API_KEY;
    String TOP_RATED_MOVIES = "http://api.themoviedb.org/3/movie/top_rated" +
            "?api_key=" + TMDB_API_KEY;

    String MOVIE = "https://api.themoviedb.org/3/movie/";   //{movie_id} to be added b/w MOVIE and CREDITS.
    String CREDITS_MOVIE_CAST_AND_CREW = "/credits?api_key=" + TMDB_API_KEY + "&language=en-US&page=1";


    String MOVIE_CAST_AND_CREW = "https://api.themoviedb.org/3/movie/10757/credits?api_key=b2d58afef7f76a97b0d481780cbd1d90";

    String TMDB_YOUTUBE_KEYS = "http://api.themoviedb.org/3/movie/10757/videos?api_key=b2d58afef7f76a97b0d481780cbd1d90";
    String TMDB_YOUTUBE_VIDEO_LIST_URL = "http://api.themoviedb.org/3/movie/";
    String VIDEOS_KEY_END = "/videos?api_key=" + TMDB_API_KEY;
    String YOUTUBE_URL = "http;//youtube.com/watch?v=";
    String TMDB_URL_MOVIE = "https://themoviedb.org/movie/";
    String TMDB_URL_ACTOR = "https://themoviedb.org/person/";

    String PERSON_CREDITS_URL = "https://api.themoviedb.org/3/person/";
    String PERSON_MOVIE_CREDITS = "movie_credits?api_key=" + TMDB_API_KEY;

}
