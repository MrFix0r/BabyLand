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

public class StudyMainMenu_Activity extends AppCompatActivity {

    ImageView butWhereYouLive;
    ImageView butHowYouSound;
    ImageView butCompleteStory;
    ImageView butHome;
    ImageView butSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_learn_menu_);

        InitializeMenu();

        butWhereYouLive = (ImageView) findViewById(R.id.imageViewWhereYouLive);
        butHowYouSound = (ImageView) findViewById(R.id.imageViewHowYouSound);
        butCompleteStory = (ImageView) findViewById(R.id.imageViewCompleteStory);

        //Навешивание события на кнопку для отправки на экран игры "Где ты живешь"
        butWhereYouLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,WhereYouLive_Activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //Навешивание события на кнопку для отправки на экран игры "Как ты звучишь"
        butHowYouSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,HowYouSounds_Activity.class);
                startActivity(intent);
            }
        });

        //Навешивание события на кнопку для отправки на экран игры "Закончи историю"
        butCompleteStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,CompleteStoryMenu_Activity.class);
                startActivity(intent);
            }
        });


    }

    //Метод для описания поведения элементов верхнего меню
    private void InitializeMenu()
    {
        butHome = (ImageView) findViewById(R.id.IV_GoHomeS);
        butSet = (ImageView) findViewById(R.id.IV_SetS);
        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this, mainsettingsactivity.class);
                startActivity(intent);
            }
        });

        ImageView IV_hint_story = (ImageView) findViewById(R.id.IV_HintStory);
        ImageView IV_hint_place = (ImageView) findViewById(R.id.IV_HintPlace);
        ImageView IV_hint_speaking = (ImageView) findViewById(R.id.IV_HintSpeaking);

        IV_hint_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,hint_story.class);
                startActivity(intent);
            }
        });
        IV_hint_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,hint_place.class);
                startActivity(intent);
            }
        });
        IV_hint_speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(StudyMainMenu_Activity.this,hint_speaking.class);
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
