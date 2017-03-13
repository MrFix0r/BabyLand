package ru.fix0r.babyland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class CompleteStoryShow_Activity extends AppCompatActivity {

    /*int[] story1 = {
            R.drawable.learn_stories_pic1,
            R.drawable.learn_stories_pic2,
            R.drawable.learn_stories_pic3,
            R.drawable.learn_stories_pic4,
            R.drawable.learn_stories_pic5,
            R.drawable.learn_stories_pic6,
            R.drawable.learn_stories_pic7,
            R.drawable.learn_stories_pic8,
            R.drawable.learn_stories_pic9,
            R.drawable.learn_stories_pic10,
            R.drawable.learn_stories_pic11,
            R.drawable.learn_stories_pic12,
            R.drawable.learn_stories_pic13,
            R.drawable.learn_stories_pic14
    };
    */
    int[] story1 = {
            R.drawable.learn_stories_pic1,
            R.drawable.learn_stories_pic2,
            R.drawable.learn_stories_pic3,
            R.drawable.learn_stories_pic4,
            R.drawable.learn_stories_pic5,
            R.drawable.learn_stories_pic6,
            R.drawable.learn_stories_pic7,
            R.drawable.learn_stories_pic8,
            R.drawable.learn_stories_pic9,
            R.drawable.learn_stories_pic10,
            R.drawable.learn_stories_pic11,
            R.drawable.learn_stories_pic12,
            R.drawable.learn_stories_pic13,
            R.drawable.learn_stories_pic14
    };

    int current_image = 1;
    ImageView scene;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_story_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        InitializeMenu();
        scene = (ImageView) findViewById(R.id.IV_Story);

        scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                NextImage();
            }
        });

        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                NextImage();
            }
        },0,2000);*/
    }

    @Override
    protected  void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    private void InitializeMenu()
    {
        ImageView butHome;
        ImageView butSet;
        ImageView butBack;
        butHome = (ImageView) findViewById(R.id.IV_GoHomeS);
        butSet = (ImageView) findViewById(R.id.IV_Settings);
        butBack = (ImageView) findViewById(R.id.IV_BackS);
        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(CompleteStoryShow_Activity.this,MainActivity.class);
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
                Intent intent = new Intent(CompleteStoryShow_Activity.this, mainsettingsactivity.class);
                startActivity(intent);
            }
        });
    }

    public void NextImage() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (current_image < story1.length) {
                    scene.setImageResource(story1[current_image]);
                    current_image++;
                } else {
                    timer.cancel();
                    CompleteStoryShow_Activity.super.recreate();
                    Intent intent = new Intent(CompleteStoryShow_Activity.this, CompleteStoryTask_Activity.class);
                    startActivity(intent);
                }
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
