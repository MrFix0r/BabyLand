package ru.fix0r.babyland;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class mainsettingsactivity extends ActionBarActivity implements OnClickListener {


    CheckBox CB_music;
    CheckBox CB_sounds;
    EditText ET_Name;
    String name;

    int bmusic;
    int bsound;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

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
        setContentView(R.layout.activity_mainsettingsactivity);


        doBindService();


        Typeface mytypeface = Typeface.createFromAsset(this.getAssets(),"wiguru2.ttf");

        //((TextView) findViewById(R.id.textView4)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView2)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView3)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView5)).setTypeface(mytypeface);
        ((TextView) findViewById(R.id.textView6)).setTypeface(mytypeface);

        ET_Name =(EditText)findViewById(R.id.editText);
        ET_Name.setTypeface(mytypeface);


        CB_music = (CheckBox)findViewById(R.id.CB_music);
        CB_sounds = (CheckBox)findViewById(R.id.CB_sound);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        editor = mSettings.edit();

        if(mSettings.contains("music"))
        {
            bmusic = mSettings.getInt("music",0);
            if(bmusic==1)
                CB_music.setChecked(true);
        }
        if(mSettings.contains("sound"))
        {
            bsound = mSettings.getInt("sound",0);
            if(bsound==1)
                CB_sounds.setChecked(true);
        }

        if(mSettings.contains("Name"))
        {
            name=mSettings.getString("Name","");
            if(name!="")
                ET_Name.setText(name);
        }

        //CB_music.setChecked(true);
        //CB_sounds.setChecked(true);

        //Button bback = (Button)findViewById(R.id.button4);
        //bback.setOnClickListener(this);
        ((TextView) findViewById(R.id.textView6)).setOnClickListener(this);
        ((TextView) findViewById(R.id.textView2)).setOnClickListener(this);
        ((TextView) findViewById(R.id.textView3)).setOnClickListener(this);


        CB_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                if (CB_music.isChecked()) {
                    mServ.resumeMusic();
                    editor.putInt("music", 1);
                    editor.apply();
                } else {
                    mServ.pauseMusic();
                    editor.putInt("music", 0);
                    editor.apply();
                }
            }
        });

        CB_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                if (CB_sounds.isChecked())
                {
                    editor.putInt("sound", 1);
                    editor.apply();
                }
                else
                {
                    editor.putInt("sound", 0);
                    editor.apply();
                }
            }
        });

    }



    public void onClick(View v) {
        //startActivity(SecAct);

        switch(v.getId()) {
            case R.id.textView6:
                ClickVolume();
                editor.putString("Name",ET_Name.getText().toString());
                editor.apply();
                setResult(RESULT_CANCELED);
                finish();
                //Intent intent1 = new Intent(this, MainActivity.class);
                //intent1.setFlags(Fla)
                //startActivityForResult(intent1,1);
                break;
            case R.id.textView2:
                ClickVolume();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "©4011 Group(fix0r, milkweed, vasilevs, ninja_luba, prosto_lera", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.textView3:
                ClickVolume();
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Бесполезная кнопка ;(", Toast.LENGTH_SHORT);
                toast2.show();
                break;

        }
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

