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
import com.interview.ammaryali.movieapp.activities.ActorDetailsActivity;
import com.interview.ammaryali.movieapp.datamodel.ActorsListModelClass;
import com.interview.ammaryali.movieapp.datamodel.CastModel;
import com.interview.ammaryali.movieapp.utils.UrlUtils;

import java.util.ArrayList;

public class RecyclerAdapterMovieCast extends RecyclerView.Adapter<RecyclerAdapterMovieCast.MyHolder> {
    private ArrayList<CastModel> castModels;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerAdapterMovieCast(Context context, ArrayList<CastModel> castModels) {
        this.context = context;
        this.castModels = castModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_cast_item, parent, false);
        return new RecyclerAdapterMovieCast.MyHolder(view);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String url = UrlUtils.IMAGE_BASE_URL_185 + (castModels.get(position).getActorProfilePath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop().placeholder(R.drawable.error_image_pic).diskCacheStrategy(DiskCacheStrategy.ALL);//.error(R.drawable.error_image_pic);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).into(holder.imageView);
        holder.textViewName.setText(castModels.get(position).getActorName());
        holder.textViewCharName.setText(castModels.get(position).getCharacterName());
    }

    @Override
    public int getItemCount() {
        return castModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewName, textViewCharName;

        MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCastItem);
            textViewName = itemView.findViewById(R.id.castActorName);
            textViewCharName = itemView.findViewById(R.id.castCharacterName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (context != null) {
                ActorsListModelClass actorsListModelClass = new ActorsListModelClass();
                actorsListModelClass.setActorName(castModels.get(getAdapterPosition()).getActorName());
                actorsListModelClass.setID(castModels.get(getAdapterPosition()).getActorID());
                actorsListModelClass.setProfilePic(castModels.get(getAdapterPosition()).getActorProfilePath());
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, ActorDetailsActivity.class);
                intent.putExtra("actorData", actorsListModelClass);
                Pair<View, String> p1 = Pair.create((View) imageView, context.getString(R.string.actor_image_transition_name));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                activity.startActivity(intent, optionsCompat.toBundle());
            }
        }
    }


}
