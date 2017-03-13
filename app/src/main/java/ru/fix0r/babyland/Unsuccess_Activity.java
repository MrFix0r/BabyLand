package ru.fix0r.babyland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Fix0r on 01.12.2015.
 */
public class Unsuccess_Activity extends AppCompatActivity{

    ImageView butRepeat;
    ImageView butMenu;
    SharedPreferences mSettings;
    int bsound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unsuccess);


        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        if(mSettings.contains("sound"))
        {
            bsound = mSettings.getInt("sound",0);
            if(bsound==1) {
                MediaPlayer mp = MediaPlayer.create(this, R.raw.unsuccess);
                mp.start();
            }
        }

        Typeface mytypeface = Typeface.createFromAsset(this.getAssets(),"wiguru2.ttf");

        ((TextView) findViewById(R.id.textView9)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView10)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView11)).setTypeface(mytypeface);

        butRepeat = (ImageView) findViewById(R.id.IV_Repeat);
        butMenu = (ImageView) findViewById(R.id.IV_SetS);

        butRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                finish();
            }
        });


        butMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(Unsuccess_Activity.this, MainActivity.class);
                startActivity(intent);
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
