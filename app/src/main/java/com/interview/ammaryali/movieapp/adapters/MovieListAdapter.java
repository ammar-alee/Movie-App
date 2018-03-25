package com.interview.ammaryali.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;
import com.interview.ammaryali.movieapp.utils.UrlUtils;

import java.util.ArrayList;


public class MovieListAdapter extends BaseAdapter {


    private Context movieContext;
    private ArrayList<MovieModel> movieModelArrayList;
    private LayoutInflater mInflater;

    public MovieListAdapter(Context context, ArrayList<MovieModel> arrayList) {
        super();
        movieContext = context;
        movieModelArrayList = arrayList;
        mInflater = (LayoutInflater) movieContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.suggested_movie_list_item, parent, false);
        }
        ImageView ivPoster = convertView.findViewById(R.id.iv_actors_pic);
        String imagePath = UrlUtils.IMAGE_BASE_URL_185 + (movieModelArrayList.get(position).getPosterPath());
        Glide.with(movieContext).load(imagePath).apply(RequestOptions.placeholderOf(R.drawable.error_image_1)).into(ivPoster);
//                .error(R.drawable.error_image_1)).into(ivPoster);
        TextView tvReleaseYear = convertView.findViewById(R.id.tv_release_year);
        TextView tvMovieTitle = convertView.findViewById(R.id.tv_title);
        TextView tvRating = convertView.findViewById(R.id.tv_Rating);
        tvRating.setText(movieModelArrayList.get(position).getRating());
        TextView tvGenre = convertView.findViewById(R.id.tv_genre);
        if (movieModelArrayList.get(position).getReleaseDate() != null) {
            tvReleaseYear.setText(getDateInFormat(movieModelArrayList.get(position).getReleaseDate()));
        } else {
            tvReleaseYear.setText("N/A");
        }
        tvMovieTitle.setText(movieModelArrayList.get(position).getTitle());
        tvGenre.setText(movieModelArrayList.get(position).getGenres());
        return convertView;
    }

    private String getDateInFormat(String date) {
        String[] strings = date.replaceAll(" ", "").split("-");
        try {
            return getMonthName(strings[1]) + " " + strings[2] + ", " + strings[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private String getMonthName(String s) {
        int month = Integer.parseInt(s);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month];

    }
}
