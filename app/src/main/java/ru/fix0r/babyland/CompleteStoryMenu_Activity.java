package ru.fix0r.babyland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class CompleteStoryMenu_Activity extends AppCompatActivity {


    ImageView butFirstSnowStory;
    ImageView butHome;
    ImageView butSet;
    ImageView butBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_complete_story_menu_);
        InitializeMenu();

        butFirstSnowStory = (ImageView) findViewById(R.id.IV_Story_FirstSnow);
        butFirstSnowStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(CompleteStoryMenu_Activity.this, CompleteStoryShow_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void InitializeMenu()
    {
        butHome = (ImageView) findViewById(R.id.IV_GoHomeS);
        butSet = (ImageView) findViewById(R.id.IV_SetS);
        butBack = (ImageView) findViewById(R.id.IV_BackS);
        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(CompleteStoryMenu_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();finish();
            }
        });
        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(CompleteStoryMenu_Activity.this,mainsettingsactivity.class);
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