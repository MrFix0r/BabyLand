package ru.fix0r.babyland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Fix0r on 01.12.2015.
 */
public class games_menu extends AppCompatActivity {


    ImageView butCatch;
    ImageView butPair;
    ImageView butHome;
    ImageView butSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_games_menu);


        butCatch = (ImageView) findViewById(R.id.imageViewCatch);
        butPair = (ImageView) findViewById(R.id.imageViewPair);
        butHome = (ImageView) findViewById(R.id.IV_SetS);
        butSet = (ImageView) findViewById(R.id.IV_Settings);

        butPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this, pairs_game.class);
                startActivity(intent);
            }
        });

        butCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this, catch_game.class);
                startActivity(intent);
            }
        });

        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this, mainsettingsactivity.class);
                startActivity(intent);
            }
        });


        ImageView IV_hint_pair = (ImageView) findViewById(R.id.IV_HintPair);
        ImageView IV_hint_catch = (ImageView) findViewById(R.id.IV_HintCatch);
        //ImageView IV_hint_speaking = (ImageView) findViewById(R.id.IV_HintSpeaking);

        IV_hint_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this,hint_pair.class);
                startActivity(intent);
            }
        });
        IV_hint_catch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(games_menu.this,hint_catch.class);
                startActivity(intent);
            }
        });
        //IV_hint_speaking.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View view) {
         //       Intent intent = new Intent(StudyMainMenu_Activity.this,hint_speaking.class);
         //       startActivity(intent);
         //   }
        //});
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
