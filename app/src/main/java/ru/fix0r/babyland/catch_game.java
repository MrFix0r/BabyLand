package ru.fix0r.babyland;

/**
 * Created by milkweed on 30.11.2015.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Random;
import java.util.*;

import android.content.Context;
import android.widget.ImageView;

public class catch_game extends AppCompatActivity {

    public  ImageView imgBut;
    ImageView butHome;
    ImageView butSet;
    int[] ButtonIds = {
            R.id.imageButton,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8};


    ImageView bonus;
    public long startTime;
    public long elapsedTime;
    public TextView textView;
    public TimerTask tTask;
    public int mouse_gen;
    public Handler mHandler = new Handler();
    public Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            GAME_OVER();
        }
    };

    public Runnable genRunnable = new Runnable() {

        @Override
        public void run() {
            become_vis(rnd.nextInt(ButtonIds.length));
        }
    };

    int count=0;
    Random rnd=new Random();

    public Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_catch_game);
        //InitializeMenu();
        timer = new Timer();
        textView=(TextView) findViewById(R.id.textView);
        bonus =(ImageView) findViewById(R.id.bonus_id);
        initialize_clicks();
        InitializeMenu();
        tTask= new MyTimerTask();
        timer.schedule(tTask, 0, 4000);
        startTime = System.currentTimeMillis();
        mHandler.postDelayed(mRunnable, 30000);
    }



    public void initialize_clicks()
    {
        ImageButton mouse1=(ImageButton) findViewById(R.id.imageButton);
        ImageButton mouse2=(ImageButton) findViewById(R.id.imageButton2);
        ImageButton mouse3=(ImageButton) findViewById(R.id.imageButton3);
        ImageButton mouse4=(ImageButton) findViewById(R.id.imageButton4);
        ImageButton mouse5=(ImageButton) findViewById(R.id.imageButton5);
        ImageButton mouse6=(ImageButton) findViewById(R.id.imageButton6);
        ImageButton mouse7=(ImageButton) findViewById(R.id.imageButton7);
        ImageButton mouse8=(ImageButton) findViewById(R.id.imageButton8);


        mouse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                mouse_click(0);
            }
        });
        mouse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                mouse_click(1);
            }
        });
        mouse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                mouse_click(2);
            }
        });
        mouse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();mouse_click(3);
            }
        });
        mouse5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();mouse_click(4);
            }
        });
        mouse6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();mouse_click(5);
            }
        });
        mouse7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();mouse_click(6);
            }
        });
        mouse8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();mouse_click(7);
            }
        });

        bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                timer.cancel();
                timer.purge();
                for(int i = 0; i < ButtonIds.length; i++) {
                    imgBut = (ImageView) findViewById(ButtonIds[i]);
                    imgBut.setEnabled(true);
                    imgBut.setVisibility(View.VISIBLE);
                }
                timer = new Timer();
                tTask=new MyTimerTask();
                timer.schedule(tTask, 2000, 1000);

            }
        });


    }



    public void mouse_click(int id)
    {
        imgBut = (ImageView) findViewById(ButtonIds[id]);
        imgBut.setVisibility(View.INVISIBLE);
        count++;
        textView.setText(Integer.toString(count));
        imgBut.setEnabled(false);
    }
    public void become_invis()
    {
        for(int i = 0; i < ButtonIds.length; i++) {
            imgBut = (ImageView) findViewById(ButtonIds[i]);
            imgBut.setEnabled(false);
            imgBut.setVisibility(View.INVISIBLE);
        }
    }

    public void become_vis(int id)
    {
        imgBut = (ImageView) findViewById(ButtonIds[id]);
        imgBut.setEnabled(true);
        imgBut.setVisibility(View.VISIBLE);
    }




    private void GAME_OVER()
    {
        timer.cancel();
        Intent intent = new Intent(catch_game.this, Success_for_catch_Activity.class);
        intent.putExtra("score", textView.getText().toString());
        startActivity(intent);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    become_invis();
                    mouse_gen = rnd.nextInt(5);
                    for(int i = 0 ;i < mouse_gen; i++)
                    {
                        mHandler.postDelayed(genRunnable, rnd.nextInt(5)*500);
                    }
                    become_vis(rnd.nextInt(ButtonIds.length));
                }
            });
        };
    }
    private void InitializeMenu()
    {

        butHome = (ImageView) findViewById(R.id.home_catch);
        butSet = (ImageView) findViewById(R.id.imageView11);
        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sethome_ClickVolume();
                mHandler.removeCallbacks(mRunnable);
                Intent intent = new Intent(catch_game.this,MainActivity.class);
                startActivity(intent);
            }
        });
        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mRunnable);
                elapsedTime = System.currentTimeMillis()-startTime;
                sethome_ClickVolume();
                Intent intent = new Intent(catch_game.this,mainsettingsactivity.class);
                startActivityForResult(intent,1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == catch_game.RESULT_CANCELED) {
                mHandler.postDelayed(mRunnable, 30000-elapsedTime);
            }
        }
    }//onActivityResult

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(mRunnable);
    }
    public void ClickVolume() {
        int bsound;
        SharedPreferences mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        if (mSettings.contains("sound")) {
            bsound = mSettings.getInt("sound", 0);
            if (bsound == 1) {
                MediaPlayer mp2 = MediaPlayer.create(this, R.raw.punch);
                mp2.start();
            }
        }
    }
    public void sethome_ClickVolume() {
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