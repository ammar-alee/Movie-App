package com.interview.ammaryali.movieapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.activities.MovieDetailsActivity;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;
import com.interview.ammaryali.movieapp.interfaces.RecyclerItemHomeClick;
import com.interview.ammaryali.movieapp.utils.UrlUtils;

import java.util.ArrayList;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.MyHolder> {
    private ArrayList<MovieModel> movieModels;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerAdapterHome(Context context, ArrayList<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item_home, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String url = UrlUtils.IMAGE_BASE_URL_185 + (movieModels.get(position).getPosterPath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.error_image_1).diskCacheStrategy(DiskCacheStrategy.ALL);
//                .error(R.drawable.error_image_1);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).into(holder.imageView);
        holder.textView.setText(movieModels.get(position).getTitle());
        holder.genre.setText(movieModels.get(position).getGenres());
        holder.rating.setText(movieModels.get(position).getRating());
    }

    public void setRecyclerItemHomeClick(RecyclerItemHomeClick recyclerItemHomeClick) {
        RecyclerItemHomeClick recyclerItemHomeClick1 = recyclerItemHomeClick;
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, genre, rating;

        MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_recycler_item_home);
            textView = itemView.findViewById(R.id.tv_recycler_item_home);
            genre = itemView.findViewById(R.id.tv_genre);
            rating = itemView.findViewById(R.id.tv_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (context != null) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, MovieDetailsActivity.class);
                intent.putExtra("movieData", movieModels.get(getAdapterPosition()));

                Pair<View, String> p1 = Pair.create((View) imageView, context.getString(R.string.movie_image_transition_name));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                activity.startActivity(intent, optionsCompat.toBundle());
            }
        }
    }
}

