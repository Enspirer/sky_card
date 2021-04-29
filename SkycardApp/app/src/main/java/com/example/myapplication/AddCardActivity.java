package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {

    ImageView iviewAddCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        iviewAddCard= findViewById(R.id.ivAddCardPrev);
        previewCard();

    }


    private void previewCard() {

    }
}