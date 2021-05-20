package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class MyHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvTitle, tvPhoneNum1,tvPhoneNum2, tvEmail, tvWebsite,tvAddress;
    ImageView ivCover, ivProfilePic;
    Button buttonShareCard,buttonViewCard;

    public MyHolder(View itemView) {
        super(itemView);

        this.tvName = itemView.findViewById(R.id.userMyCardName);
        this.tvTitle = itemView.findViewById(R.id.userMyCardTitle);
        this.tvPhoneNum1 = itemView.findViewById(R.id.myCardPhoneNum1);
        this.tvPhoneNum2 = itemView.findViewById(R.id.myCardPhoneNum2);
        this.tvWebsite = itemView.findViewById(R.id.myCardWebsite);
        this.tvEmail = itemView.findViewById(R.id.myCardPhoneEmail);
        this.ivCover = itemView.findViewById(R.id.userMycardTheme);
        this.ivProfilePic = itemView.findViewById(R.id.myCardProfilePic);
        this.tvAddress = itemView.findViewById(R.id.myCardAddress);
        this.buttonViewCard = itemView.findViewById(R.id.buttonViewCard);
        this.buttonShareCard =itemView.findViewById(R.id.buttonShareCard);
    }
}
