package com.example.naamkaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageButton extends AppCompatActivity {

    ImageView b1,b2;
    MediaPlayer mpButtonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_button);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        b1 = (ImageView) findViewById(R.id.imgLogo1);
        b2 = (ImageView) findViewById(R.id.imgLogo2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpButtonClick  = MediaPlayer.create(ImageButton.this,R.raw.helloboy);
                mpButtonClick.start();
                Intent i = new  Intent(ImageButton.this, TabActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpButtonClick  = MediaPlayer.create(ImageButton.this,R.raw.hello);
                mpButtonClick.start();
                Intent i = new  Intent(ImageButton.this, TabActivity.class);
                startActivity(i);
            }
        });


    }
}
