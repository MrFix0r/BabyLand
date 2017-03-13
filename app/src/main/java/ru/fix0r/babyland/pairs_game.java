package ru.fix0r.babyland;

/**
 * Created by milkweed on 30.11.2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.os.Handler;
import com.bettervectordrawable.lib.graphics.drawable.VectorDrawable;
import android.widget.Toast;
import android.content.Intent;
public class pairs_game extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pairs_game);

        StartGame();
        InitializeMenu();

    }
    public Handler mHandler = new Handler();
    public boolean actHint=true;
    public Runnable mRunnable;
    public String check=null;
    public int checkid;
    public int i;
    public int success_count=0;
    public  ImageView imgView;
    public ImageView imgView1;
    int[]bufferAnimals = {
            R.drawable.play_pair_blocks_pair1,
            R.drawable.play_pair_blocks_pair2,
            R.drawable.play_pair_blocks_pair3,
            R.drawable.play_pair_blocks_pair4,
            R.drawable.play_pair_blocks_pair1,
            R.drawable.play_pair_blocks_pair2,
            R.drawable.play_pair_blocks_pair3,
            R.drawable.play_pair_blocks_pair4};

    int[] ViewIds = {
            R.id.imageView,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView6,
            R.id.imageView7,
            R.id.imageView8,
            R.id.imageView9};

    int[]Animals;

    private void StartGame()
    {
        ShuffleContent();
        InitializeViews();
    }


    private void ShuffleContent()
    {
        Random rnd = new Random();
        int rndInt;
        int countEl = bufferAnimals.length;

        Animals = new int[ViewIds.length];

        for(int i = 0; i < Animals.length; i++)
        {
            rndInt = rnd.nextInt(countEl);
            Animals[i] = bufferAnimals[rndInt];

            for(int j = rndInt; j < countEl - 1; j++)
            {
                bufferAnimals[j] = bufferAnimals[j+1];
            }
            countEl--;
        }

    }


    private void InitializeViews()
    {

        for(i = 0; i < ViewIds.length; i++) {
            imgView = (ImageView) findViewById(ViewIds[i]);
            imgView.setImageResource(R.drawable.play_pair_blocks_rubashka);
        }




        final ImageView imageView=(ImageView) findViewById(R.id.imageView);
        final ImageView imageView9=(ImageView) findViewById(R.id.imageView9);
        final ImageView imageView2=(ImageView) findViewById(R.id.imageView2);
        final ImageView imageView3=(ImageView) findViewById(R.id.imageView3);
        final ImageView imageView4=(ImageView) findViewById(R.id.imageView4);
        final ImageView imageView6=(ImageView) findViewById(R.id.imageView6);
        final ImageView imageView7=(ImageView) findViewById(R.id.imageView7);
        final ImageView imageView8=(ImageView) findViewById(R.id.imageView8);
        final ImageView showpair=(ImageView) findViewById(R.id.showpair_view);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[0], 0);
            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[1],1);
            }
        });


        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[2],2);
            }
        });


        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[3],3);
            }
        });


        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[4],4);
            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[5],5);
            }
        });


        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[6],6);
            }
        });


        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickVolume();
                cmon_man(ViewIds[7],7);
            }
        });





    showpair.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (actHint)
            {
                ClickVolume();
            Random rnd = new Random();
            int nomer = rnd.nextInt(Animals.length);
            ImageView jivnost = (ImageView) findViewById(ViewIds[nomer]);
            while (!jivnost.isEnabled()) {
                nomer = rnd.nextInt(Animals.length);
                jivnost = (ImageView) findViewById(ViewIds[nomer]);
            }
            for (int i = 0; i < Animals.length; i++) {
                if (getResources().getString(Animals[nomer]) == getResources().getString(Animals[i]) && (i != nomer)) {
                    final ImageView para1 = (ImageView) findViewById(ViewIds[nomer]);
                    final ImageView para2 = (ImageView) findViewById(ViewIds[i]);
                    para1.setImageResource(Animals[nomer]);
                    para2.setImageResource(Animals[i]);
                    mRunnable = new Runnable() {

                        @Override
                        public void run() {
                            para1.setVisibility(View.INVISIBLE);
                            para2.setVisibility(View.INVISIBLE);
                            para1.setEnabled(false);
                            para2.setEnabled(false);
                        }
                    };

                    mHandler.postDelayed(mRunnable, 1000);
                    success_count++;
                    if (success_count == 4) GAME_OVER();
                    break;
                }
            }
            showpair.setEnabled(false);
        }
        }
    });
    }

    private void block_others()
    {
        for(i = 0; i < ViewIds.length; i++) {
            imgView = (ImageView) findViewById(ViewIds[i]);
            imgView.setEnabled(false);
        }
    }

    private void unlock_others()
    {
        for(i = 0; i < ViewIds.length; i++) {
            imgView = (ImageView) findViewById(ViewIds[i]);
            if(imgView.getVisibility()==View.VISIBLE)imgView.setEnabled(true);
        }
    }
    private  void GAME_OVER()
    {
        /*Toast toast = Toast.makeText(getApplicationContext(),
               "GAME OVER", Toast.LENGTH_SHORT);
        toast.show();*/
        pairs_game.super.recreate();
        Intent intent=new Intent(this, Success_Activity.class);
        startActivityForResult(intent,1);
    }


    private void cmon_man(int viewid, int i)
    {
        actHint=false;
        imgView1 = (ImageView) findViewById(viewid);
        imgView1.setImageResource(Animals[i]);
        if ((check==null)) {
            check = getResources().getString(Animals[i]);
            checkid=viewid;
        }
        else {
            block_others();
            if(check==getResources().getString(Animals[i])&&(checkid!=viewid)) {
                final ImageView imgch=(ImageView) findViewById(checkid);

                mRunnable = new Runnable() {

                    @Override
                    public void run() {
                        imgView1.setVisibility(View.INVISIBLE);
                        imgch.setVisibility(View.INVISIBLE);
                        imgView1.setEnabled(false);
                        imgch.setEnabled(false);
                        success_count++;
                        if(success_count==4)GAME_OVER();
                        unlock_others();
                        actHint=true;
                    }
                };

                mHandler.postDelayed(mRunnable, 1000);
                check = null;
                checkid = 0;
            }
            else{
                final ImageView imgch=(ImageView) findViewById(checkid);
                check = null;
                checkid = 0;
                mRunnable = new Runnable() {

                    @Override
                    public void run() {
                        imgch.setImageResource(R.drawable.play_pair_blocks_rubashka);
                        imgView1.setImageResource(R.drawable.play_pair_blocks_rubashka);
                        unlock_others();
                        ClickVolume();
                        actHint=true;
                    }
                };

                mHandler.postDelayed(mRunnable, 1000);
            }
        }

    }
    private void InitializeMenu()
    {
        ImageView imgViewHome = (ImageView) findViewById(R.id.home_view);
        ImageView imgViewSettings = (ImageView) findViewById(R.id.settings_view);
        //ImageView imgViewBack = (ImageView) findViewById(R.id.IV_Back);

        imgViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sethome_ClickVolume();
                Intent intent = new Intent(pairs_game.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sethome_ClickVolume();
                Intent intent = new Intent(pairs_game.this, mainsettingsactivity.class);
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
                MediaPlayer mp = MediaPlayer.create(this, R.raw.card);
                mp.start();
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

