package ru.fix0r.babyland;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by Fix0r on 01.12.2015.
 */
public class hint_place extends AppCompatActivity{

    ImageView butOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.learn_hint_place);

        Typeface mytypeface = Typeface.createFromAsset(this.getAssets(),"wiguru2.ttf");

        ((TextView) findViewById(R.id.textView4)).setTypeface(mytypeface);
        //((TextView) findViewById(R.id.textView8)).setTypeface(mytypeface);

        butOk = (ImageView) findViewById(R.id.imageView14);


        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();finish();
            }
        });



    }
    public void ClickVolume() {
        int bsound;
        SharedPreferences mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        if (mSettings.contains("sound")) {
            bsound = mSettings.getInt("sound", 0);
            if (bsound == 1) {
                MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
                mp.start();
            }
        }
    }
}
