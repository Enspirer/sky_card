package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.models.Model;
import com.example.myapplication.MyHolder;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.myapplication.MyCardsActivity.shareUrl;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyHolder> {

    Activity context;
    List<Model> models;

    public MyRecyclerViewAdapter(Activity c, ArrayList<Model> models) {
        this.context = c;
        this.models = models;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_cards, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.tvName.setText(models.get(position).getName());
        holder.tvTitle.setText(models.get(position).getTitle());
        holder.tvEmail.setText(models.get(position).getEmail());
        holder.tvWebsite.setText(models.get(position).getWebsite());
        holder.tvAddress.setText(models.get(position).getAddress());
        holder.tvPhoneNum1.setText(models.get(position).getPhoneNumber1());
        holder.tvPhoneNum2.setText(models.get(position).getPhoneNumber2());
        Glide.with(this.context)
                .load(models.get(position)
                        .getProfilePicture()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        })
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(100))
                        .error(R.drawable.avatar)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .override(150, 75)
                .into(holder.ivProfilePic);

        Glide.with(this.context).load(models.get(position).getCoverImage()).into(holder.ivCover);

        holder.buttonViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewCardActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
