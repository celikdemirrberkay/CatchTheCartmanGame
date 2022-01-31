package com.berkay.catchthecartman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView timeText;
    TextView scoreText;
    TextView scoreText2;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView [] imageArray;
    Handler handler;
    Runnable runnable;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = this.getSharedPreferences("com.berkay.catchthecartman", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText2= (TextView) findViewById(R.id.scoreText2);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageArray = new ImageView[]{imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
        hideImages();

        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
            timeText.setText("Time : "+l/1000);
            }

            @Override
            public void onFinish() {
            timeText.setText("Time off");
            handler.removeCallbacks(runnable);
            for(ImageView image : imageArray){
                image.setVisibility(View.INVISIBLE);
            }
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Restart");
            alert.setMessage("Do you want to play again ? ");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Restart
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent2 = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent2);
                }
            });
            alert.show();
            }
        }.start();
    // Shared preferences
     int storedAge = sharedPreferences.getInt("storedScore",0);
     if(storedAge==0){
         scoreText2.setText("Last Score : ");
     }
     else{
         scoreText2.setText("Last Score : "+storedAge);
     }

    }
//---------------------------------------------increaseScore---------------------------------------------

    public void increaseScore(View view){

        scoreText.setText("Score : "+score);
        score++;
        scoreText.setText("Score : "+score);
        sharedPreferences.edit().putInt("storedScore",score).apply();
    }
//---------------------------------------------increaseScore----------------------------------------
    public void hideImages(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this,400);
            }
        };

        handler.post(runnable);

    }


}