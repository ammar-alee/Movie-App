package com.interview.ammaryali.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.datamodel.ActorsListModelClass;
import com.interview.ammaryali.movieapp.utils.UrlUtils;

import java.util.ArrayList;

public class ActorListAdapter extends BaseAdapter {
    private Context mActorContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ActorsListModelClass> actorsListModelClassList;

    public ActorListAdapter(Context context, ArrayList<ActorsListModelClass> arrayList) {
        mActorContext = context;
        mLayoutInflater = (LayoutInflater) mActorContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actorsListModelClassList = arrayList;
    }

    @Override
    public int getCount() {
        return actorsListModelClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return actorsListModelClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.suggested_actor_list_items, parent, false);
        }
        ImageView ivActorProfilePic = convertView.findViewById(R.id.iv_actors_pic);
        String imagePath = UrlUtils.IMAGE_BASE_URL_185 + actorsListModelClassList.get(position).getProfilePic();

        Glide.with(mActorContext).
                load(imagePath).//apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).
                apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.drawable.error_image_pic)).
//                apply(RequestOptions.placeholderOf(R.drawable.error_image_pic)).
        into(ivActorProfilePic);
        TextView tvActorName = convertView.findViewById(R.id.tv_actor_name);
        tvActorName.setText(actorsListModelClassList.get(position).getActorName());
        return convertView;
    }
}
