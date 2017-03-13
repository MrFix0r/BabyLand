package ru.fix0r.babyland;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.media.AudioAttributes;

import java.util.ArrayList;
import java.util.Random;

public class HowYouSounds_Activity extends AppCompatActivity {


    //Массив со всми изображениями всех животных в этой игре
    int[]bufferAnimals = {
            R.drawable.learn_speaking_pic_bear,
            R.drawable.learn_speaking_pic_cat,
            R.drawable.learn_speaking_pic_cow,
            R.drawable.learn_speaking_pic_dog,
            R.drawable.learn_speaking_pic_duck,
            R.drawable.learn_speaking_pic_hedgehog,
            R.drawable.learn_speaking_pic_pig,
            R.drawable.learn_speaking_pic_raven,
            R.drawable.learn_speaking_pic_sheep};

    //Массив со всеми ImageView с животными (для того чтобы присвоить им события)
    int[] ViewIds = {
            R.id.imageViewSpeaking1,
            R.id.imageViewSpeaking2,
            R.id.imageViewSpeaking3,
            R.id.imageViewSpeaking4,
            R.id.imageViewSpeaking5,
            R.id.imageViewSpeaking6,
            R.id.imageViewSpeaking7,
            R.id.imageViewSpeaking8};

    //Массив со всеми звуками животных, соответствующие массиву с картинками животных
    int[] bufferAudioVoices = {
            R.raw.learn_speaking_voice_bear,
            R.raw.learn_speaking_voice_cat,
            R.raw.learn_speaking_voice_cow,
            R.raw.learn_speaking_voice_dog,
            R.raw.learn_speaking_voice_duck,
            R.raw.learn_speaking_voice_hedgehog,
            R.raw.learn_speaking_voice_pig,
            R.raw.learn_speaking_voice_raven,
            R.raw.learn_speaking_voice_sheep};

    //Массив с текстом звука животного, соответствующий массиву с картинками животных
    int[] bufferTextVoices = {
            R.string.learn_speaking_string_bear,
            R.string.learn_speaking_string_cat,
            R.string.learn_speaking_string_cow,
            R.string.learn_speaking_string_dog,
            R.string.learn_speaking_string_duck,
            R.string.learn_speaking_string_hedgehog,
            R.string.learn_speaking_string_pig,
            R.string.learn_speaking_string_raven,
            R.string.learn_speaking_string_sheep};


    ArrayList<Integer> AnimalsId ;
    int currAnimalId;

    //Основные массивы для работы, в которых будут лежать уже перемешанные соответствующие эллементы
    int[]Animals;
    int[]AudioVoices;
    int[]TextVoices;

    int currentAnimal;
    int last_animal = 0;
    //TextView txt; //текстовое поле для вывода написания звка животного
    ImageView txt;
    int soundId;

    private SoundPool mSoundPool;
    private int mStreamID;
    //private AssetManager mAssetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_how_you_sounds_);

        InitializeMenu();
        StartGame();
        playSound(soundId);

    }


    //Просто объединение всех методов запускающих работу приложения
    private void StartGame()
    {
        //Вообще стоит это использовать, но там какая то проблема с новой версией, так что пока не будем
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
             createNewSoundPool();
        }


        ShuffleContent();
        InitializeViews();
        newAnimal();

        ImageView imgView = (ImageView) findViewById(R.id.imageViewRepeate);
        //Повторить звук животного
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(soundId);
            }
        });
    }

    //Метод для случайного перемешивания и звуков (чтобы каждый раз разное расположение)
    private void ShuffleContent()
    {
        //Просто служебные переменные для работы с алгоритмом перемешивания
        Random rnd = new Random();
        //txt = (TextView) findViewById(R.id.textView);
        int rndInt;
        int countEl = bufferAnimals.length;
        AnimalsId = new ArrayList<Integer>();
        Animals = new int[ViewIds.length]; // тут будут хранится изображения в количестве соответствующем количество ImageView
        AudioVoices = new int[ViewIds.length];
        TextVoices = new int [ViewIds.length];

        //Тут происходит случайно заполнение массива с картинками
        for(int i = 0; i < Animals.length; i++)
        {
            rndInt = rnd.nextInt(countEl);
            Animals[i] = bufferAnimals[rndInt];
            AudioVoices[i] = bufferAudioVoices[rndInt];
            TextVoices[i] = bufferTextVoices[rndInt];
            for(int j = rndInt; j < countEl - 1; j++)
            {
                bufferAnimals[j] = bufferAnimals[j+1];
                bufferAudioVoices[j] = bufferAudioVoices[j+1];
                bufferTextVoices[j] = bufferTextVoices[j+1];

            }
            countEl--;

            AnimalsId.add(i);
        }
    }

    //Метод для присвоения случайных изображений и событий каждому ImageView(где картинки животных)
    private void InitializeViews()
    {
        //Присваиваем всем ImageView в активити события и картинки в цикле (так удобнее)
        for( int i = 0; i < ViewIds.length; i++)
        {
            ImageView imgView = (ImageView) findViewById(ViewIds[i]);
            imgView.setImageResource(Animals[i]);
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int n;
                    //n = Integer.valueOf(view.getContentDescription().toString());
                    ClickVolume();
                    CheckAnswer(view);
                }
            });}
    }

    //Метод для проверки правильности выбранного животного
    private void CheckAnswer(View view)
    {

        if(Integer.valueOf(view.getContentDescription().toString()) == currentAnimal)
        {
            ImageView imgV = (ImageView) view;
            imgV.setImageDrawable(null);
            view.setEnabled(false);
            RightAnswer();
            newAnimal();
        }
        else
        {
            WrongAnswer();
        }


    }

    //Метод для выбора следующего животного для угадывания
    private  void newAnimal()
    {
        Random rnd = new Random();
        currAnimalId = rnd.nextInt(AnimalsId.size());

        currentAnimal = AnimalsId.get(currAnimalId);

        //currentAnimal = rnd.nextInt(AudioVoices.length - 1);
            int rndAnimal = AudioVoices[currentAnimal];
           // String t = getString(TextVoices[currentAnimal]);
            //txt.setText(t);
            soundId = mSoundPool.load(this, rndAnimal, 1);
            //last_animal = currentAnimal;
            //playSound(soundId);

    }



    ////----////
    // Вспомогательные методы для рвботы с аудиофайлами

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }
    ///

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    //Проиграть звук
    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    //-------------//
    //Методы для победы или пройгрыша
    int[] livesViewId = {R.id.IV_LiveStar3,R.id.IV_LiveStar2,R.id.IV_LiveStar1};
    int countLive = livesViewId.length-1;
    int countRightAnswers = 0;

    //Метод отвечающий за действия в случае выйгрыша
    private void Success()
    {
        Intent intent = new Intent(HowYouSounds_Activity.this,Success_Activity.class);
        startActivity(intent);
        /*Toast toast = Toast.makeText(getApplicationContext(),
                "Все правильно", Toast.LENGTH_SHORT);
        toast.show();
        */

    }

    //Метод отвечающий за действия в случае неверного ответа
    private void WrongAnswer()
    {
        if(countLive != 0) {
            ImageView imgView = (ImageView) findViewById(livesViewId[countLive]);
            imgView.setImageResource(R.drawable.learn_live_stars_emptylives);
            countLive--;

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неправильно", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Unsuccess();
        }

    }

    int maxRightAnswers = ViewIds.length - 1;
    private void RightAnswer()
    {
        if(countRightAnswers == maxRightAnswers) Success();
        else
        {
            countRightAnswers++;

            /*for(int i = currAnimalId; i < AnimalId.length - countRightAnswers; i++)
            {
                AnimalId[i] = AnimalId[i+1];
            }*/
            AnimalsId.remove(currAnimalId);

            /*Toast toast = Toast.makeText(getApplicationContext(),
                    "Молодец. " + countRightAnswers + "  из " + maxRightAnswers, Toast.LENGTH_SHORT);
            toast.show();
            */
        }

    }

    //Метод отвечающий за действия в случае пройгрыша
    private  void Unsuccess()
    {
        /*Toast toast = Toast.makeText(getApplicationContext(),
                "В следующий раз получится", Toast.LENGTH_SHORT);
        toast.show();
        */
        HowYouSounds_Activity.super.recreate();
        Intent intent = new Intent(HowYouSounds_Activity.this,Unsuccess_Activity.class);
        startActivity(intent);
    }

    //Метод для описания поведения элементов верхнего меню
    private void InitializeMenu()
    {
        ImageView imgViewHome = (ImageView) findViewById(R.id.IV_GoHomeS);
        ImageView imgViewSettings = (ImageView) findViewById(R.id.IV_SetS);
        ImageView imgViewBack = (ImageView) findViewById(R.id.IV_BackS);

        imgViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(HowYouSounds_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imgViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(HowYouSounds_Activity.this, mainsettingsactivity.class);
                startActivity(intent);
            }
        });
        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                finish();
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

