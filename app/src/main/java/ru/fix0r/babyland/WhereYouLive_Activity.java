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
import android.widget.Toast;

//import com.larvalabs.svgandroid.SVG;
//import com.larvalabs.svgandroid.SVGParser;

import java.util.Random;

public class WhereYouLive_Activity extends AppCompatActivity {

    ImageView imgWater;
    ImageView imgSurface;
    ImageView imgAir;
    ImageView imgGround;
    ImageView imgAnimal;
    //TextView txt;


    int animal, place;

    //Массивы с животными соответствующими своей среде обитания
    int[] aniWater = {
            R.drawable.learn_place_animal_fish1,
            R.drawable.learn_place_animal_fish2,
            R.drawable.learn_place_animal_fish3,
            R.drawable.learn_place_animal_octopus,
            R.drawable.learn_place_animal_shark};

    int[] aniSurface = {
            R.drawable.learn_place_animal_deer,
            R.drawable.learn_place_animal_pig,
            R.drawable.learn_place_animal_snake,
            R.drawable.learn_place_animal_hedgehog,
            R.drawable.learn_place_animal_hippo};

    int[] aniAir = {
            R.drawable.learn_place_animal_raven,
            R.drawable.learn_place_animal_pelican,
            R.drawable.learn_place_animal_sparrow};

    int[] aniGround = {R.drawable.learn_place_animal_worm,
            R.drawable.learn_place_animal_ant,
            R.drawable.learn_place_animal_millipede};


    //1 - Небо, 2 - Поверхность, 3 - Земля, 4 - Вода
    int[][] aniArr = {aniAir,aniSurface,aniGround,aniWater};
    int last_animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_where_you_live);


        InitializeMenu();
        InitializeViews();
        last_animal = 0;
        NewAnimal();
        //txt.setText( String.valueOf(place) + " " + String.valueOf(animal) + " ");

    }

    public  void InitializeViews()
    {
        imgWater = (ImageView) findViewById(R.id.imageViewWater);
        imgSurface = (ImageView) findViewById(R.id.imageViewSurface);
        imgAir = (ImageView) findViewById(R.id.imageViewAir);
        imgGround = (ImageView) findViewById(R.id.imageViewGround);
        imgAnimal = (ImageView) findViewById(R.id.imageViewAnimal);
        //txt =  (TextView) findViewById(R.id.textView3);
        imgWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                CheckAnimal(3);
            }
        });
        imgGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                CheckAnimal(2);
            }
        });
        imgSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                CheckAnimal(1);
            }
        });
        imgAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                CheckAnimal(0);
            }
        });
    }

    public void CheckAnimal(int answer)
    {
            if(answer == place){
                RightAnswer();
                NewAnimal();
            }
            else{
               WrongAnswer();
            }

    }

    public void NewAnimal()
    {
        Random rnd = new Random();
        place = rnd.nextInt(aniArr.length);
        animal = rnd.nextInt(aniArr[place].length);
        if(last_animal == animal) {
            NewAnimal();
            return;
        }
        else {
            imgAnimal.setImageResource(aniArr[place][animal]);
            last_animal = animal;
        }

    }

    //-------------//
    //Методы для победы или пройгрыша
    int[] livesViewId = {R.id.IV_LiveStar3,R.id.IV_LiveStar2,R.id.IV_LiveStar1};
    int countRightAnswers = 0;
    int countLive = livesViewId.length - 1;

    //Метод отвечающий за действия в случае выйгрыша
    private void Success()
    {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Все правильно", Toast.LENGTH_SHORT);
        toast.show();
        WhereYouLive_Activity.super.recreate();
        Intent intent=new Intent(this, Success_Activity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent,1);
    }

    //Метод отвечающий за действия в случае неверного ответа
    private void WrongAnswer()
    {
        if(countLive != 0) {
            ImageView imgView = (ImageView) findViewById(livesViewId[countLive]);
            imgView.setImageResource(R.drawable.learn_live_stars_emptylives);
            countLive--;

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверно. -1 жизнь", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
            Unsuccess();
    }

    int maxRightAnswers = 6;
    private void RightAnswer()
    {
        if(countRightAnswers == maxRightAnswers) Success();
        else
        {
            countRightAnswers++;
            //Toast toast = Toast.makeText(getApplicationContext(),
            //"Молодец. Угадано " + Integer.toString(countRightAnswers) + " из " + Integer.toString(maxRightAnswers),
            //        Toast.LENGTH_SHORT);
            //toast.show();
        }

    }

    //Метод отвечающий за действия в случае пройгрыша
    private  void Unsuccess()
    {
        //Toast toast = Toast.makeText(getApplicationContext(),
        //        "В следующий раз получится", Toast.LENGTH_SHORT);
        //toast.show();
        WhereYouLive_Activity.super.recreate();
        Intent intent = new Intent(WhereYouLive_Activity.this,Unsuccess_Activity.class);
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
                Intent intent = new Intent(WhereYouLive_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imgViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickVolume();
                Intent intent = new Intent(WhereYouLive_Activity.this, mainsettingsactivity.class);
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
