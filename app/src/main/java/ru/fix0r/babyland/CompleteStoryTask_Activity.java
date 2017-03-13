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

import java.util.Random;

public class CompleteStoryTask_Activity extends AppCompatActivity {

    //Вспомогательный массив для перемешивания
    int[] bufferItems = {R.drawable.learn_stories_firstsnow_obj_carrot,
            R.drawable.learn_stories_firstsnow_obj_hat,
            R.drawable.learn_stories_firstsnow_obj_scarf,
            R.drawable.learn_stories_firstsnow_obj_snowflake,
    };

    //Правильный порядок расстановки элементов
    int[] rightItems = {R.drawable.learn_stories_firstsnow_obj_snowflake,
            R.drawable.learn_stories_firstsnow_obj_carrot,
            R.drawable.learn_stories_firstsnow_obj_scarf,
            R.drawable.learn_stories_firstsnow_obj_hat,
    };

    //ImageView с картнками, которые нужна правильно расположить
    int[] ViewUnsortedIds = {
            R.id.IV_StoryUnsorted1,
            R.id.IV_StoryUnsorted2,
            R.id.IV_StoryUnsorted3,
            R.id.IV_StoryUnsorted4
    };

    //ImageView для ответа
    int[] ViewSortedIds = {
            R.id.IV_StorySorted1,
            R.id.IV_StorySorted2,
            R.id.IV_StorySorted3,
            R.id.IV_StorySorted4
    };

    //счетчик количества элементов, уже расположенных в контейнерах для ответа
    int countSelectedElements = 0;

    //Основной массив с картинками
    int[] Items;


    ImageView butHome;
    ImageView butSet;
    ImageView butBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_complete_story_task_);
        InitializeMenu();
        ShuffleContent();
        InitializeViews();
    }

    //Перемешиваем картинки в случайном порядке
    private void ShuffleContent() {
        //Просто служебные переменные для работы с алгоритмом перемешивания
        Random rnd = new Random();
        //txt = (TextView) findViewById(R.id.textView);
        int rndInt;
        int countEl = bufferItems.length;

        Items = new int[ViewUnsortedIds.length]; // тут будут хранится изображения в количестве соответствующем количество ImageView

        //Тут происходит случайно заполнение массива с картинками
        for (int i = 0; i < Items.length; i++) {
            rndInt = rnd.nextInt(countEl);
            Items[i] = bufferItems[rndInt];

            for (int j = rndInt; j < countEl - 1; j++) {
                bufferItems[j] = bufferItems[j + 1];
            }
            countEl--;
        }
    }

    //Присваиваем ImageView события
    private void InitializeViews() {
        //Присваиваем всем ImageView c несортированными картинками в активити события и картинки в цикле
        for (int i = 0; i < ViewUnsortedIds.length; i++) {
            ImageView imgView = (ImageView) findViewById(ViewUnsortedIds[i]);
            imgView.setTag(Items[i]);
            imgView.setImageResource(Items[i]);

            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClickVolume();
                    ImageView imgV = (ImageView) view;

                    if(imgV.getTag() != null) {
                        SendImage((int) imgV.getTag());
                        imgV.setImageDrawable(null);
                        imgV.setTag(null);
                        if (countSelectedElements == ViewSortedIds.length) {
                            CheckAnswer();
                        }
                    }

                }
            });
        }

            for (int i = 0; i < ViewSortedIds.length; i++) {
                ImageView imgView = (ImageView) findViewById(ViewSortedIds[i]);
                imgView.setImageDrawable(null);
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClickVolume();
                        ImageView imgV = (ImageView) view;

                        if(imgV.getTag() != null) {
                            ReturnImage((int) imgV.getTag());
                            imgV.setImageDrawable(null);
                            imgV.setTag(null);
                        }
                    }
                });
        }
    }


    //Положить изображение в пустой ящичек
    private void SendImage(int imgId)
    {
        for (int i = 0; i < ViewSortedIds.length; i++)
        {
            ImageView imgV = (ImageView) findViewById(ViewSortedIds[i]);
            if (imgV.getDrawable() == null)
            {
                imgV.setTag(imgId);
                imgV.setImageResource(imgId);
                countSelectedElements++;
                return;
            }
        }
    }

    //Вернуть изображение предмета обратно
    private void ReturnImage(int imgId)
    {
        for (int i = 0; i < ViewUnsortedIds.length; i++)
        {
            ImageView imgV = (ImageView) findViewById(ViewUnsortedIds[i]);
            if (imgV.getDrawable() == null)
            {
                imgV.setTag(imgId);
                imgV.setImageResource(imgId);
                countSelectedElements--;
                return;
            }
        }
    }


    //Проверка правильности ответа
    private void CheckAnswer()
    {
        for (int i = 0; i < ViewSortedIds.length; i++)
        {
            //Drawable d = Drawable
            ImageView imgV = (ImageView) findViewById(ViewSortedIds[i]);
            int x = (int)imgV.getTag();
            if (x != rightItems[i])
            {
                WrongAnswer();
                return;
            }
        }
        Success();
    }



    //-------------//
    //Методы для победы или пройгрыша
    int[] livesViewId = {R.id.IV_LiveStar1,R.id.IV_LiveStar2,R.id.IV_LiveStar3};
    int countLive = livesViewId.length - 1;

    //Метод отвечающий за действия в случае выйгрыша
    private void Success()
    {
        /*Toast toast = Toast.makeText(getApplicationContext(),
                "Все правильно", Toast.LENGTH_SHORT);
        toast.show();*/
        finish();
        Intent intent = new Intent(CompleteStoryTask_Activity.this,Success_Activity.class);
        startActivity(intent);
    }

    //Метод отвечающий за действия в случае неверного ответа
    private void WrongAnswer()
    {
        if(countLive != 0) {
            ImageView imgView = (ImageView) findViewById(livesViewId[countLive]);
            imgView.setImageResource(R.drawable.learn_live_stars_emptylives);
            countLive--;

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверный порядок", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Unsuccess();

    }

    //Метод отвечающий за действия в случае пройгрыша
    private  void Unsuccess()
    {
        finish();
        Intent intent = new Intent(CompleteStoryTask_Activity.this,Unsuccess_Activity.class);
        startActivity(intent);
        /*Toast toast = Toast.makeText(getApplicationContext(),
                "В следующий раз получится", Toast.LENGTH_SHORT);
        toast.show();*/
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
                Intent intent = new Intent(CompleteStoryTask_Activity.this,MainActivity.class);
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
                Intent intent = new Intent(CompleteStoryTask_Activity.this, mainsettingsactivity.class);
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

