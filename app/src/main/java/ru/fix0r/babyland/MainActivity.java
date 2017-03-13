package ru.fix0r.babyland;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView mname;
    //Button bsetting = (Button)findViewById(R.id.button3);
    int bsound;
    Intent itoset;
    Button bgame;

    public static final String APP_PREFERENCES_MUSIC = "music";
    public static final String APP_PREFERENCES_SOUND = "sound";

    private SharedPreferences mSettings;

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mSettings.edit();
        //editor.putInt(APP_PREFERENCES_MUSIC,1);
        //editor.putInt(APP_PREFERENCES_SOUND,1);
        //editor.apply();

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        if (mSettings.contains("music")) {
            bsound = mSettings.getInt("music", 0);
            if (bsound == 1) {
                startService(music);
            }
        }
        //startService(music);



        //mname = (TextView)findViewById(R.id.textView);
        //mname.setText("BabyLand");

        Typeface mytypeface = Typeface.createFromAsset(this.getAssets(),"wiguru2.ttf");
        TextView tvgame = (TextView)findViewById(R.id.TV_Play);
        TextView tvstudy = (TextView)findViewById(R.id.TV_Study);
        Button bsetting = (Button)findViewById(R.id.button3);
        ((TextView) findViewById(R.id.TV_Study)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.TV_Play)).setTypeface(mytypeface);
        tvgame.setOnClickListener(this);
        tvstudy.setOnClickListener(this);
        bsetting.setOnClickListener(this);
        //Intent itoset = new Intent(MainActivity.this, mainsettingsactivity.class);
    }

    public void onClick(View v) {
        //startActivity(SecAct);
        switch(v.getId()) {
            case R.id.button3:
                ClickVolume();
                Intent intent1 = new Intent(this, mainsettingsactivity.class);
                startActivityForResult(intent1,1);
                break;

            case R.id.TV_Study:
                ClickVolume();
                Intent intent2=new Intent(this, StudyMainMenu_Activity.class);
                //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent2, 1);
                break;

            case R.id.TV_Play:
                ClickVolume();
                Intent intent3=new Intent(this, games_menu.class);
                //intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent3, 1);
                break;
        }
    }

    @Override
    protected void onPause() {
        //mServ.stopMusic();
        //doUnbindService();
        super.onPause();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mServ.stopMusic();
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
