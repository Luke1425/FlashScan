package com.example.lukemorris.flashscan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.stetho.Stetho;

public class splashScreen extends AppCompatActivity
{
    Button go_next_home_sc;//initializing the only button on the screen so far
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_splash_screen);
        myDb = new DatabaseHelper(this);

        int duration = 1500; // Change back to 1500.

        ImageView splash = (ImageView)findViewById(R.id.splashIcon);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(splash, "alpha", 1f, 0f);
        fadeIn.setDuration(duration);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(splash, "alpha", 0f, 1f);
        fadeOut.setDuration(duration);

        final AnimatorSet aSet = new AnimatorSet();
        aSet.play(fadeIn).after(fadeOut);

        aSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent hs = new Intent(getApplicationContext(), homePage.class);
                startActivity(hs);
                finish();
            }
        });
        aSet.start();
    }
}