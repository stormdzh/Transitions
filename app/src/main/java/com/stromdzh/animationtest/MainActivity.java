package com.stromdzh.animationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stromdzh.animationtest.util.ActivityAnimationHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.to_big).setOnClickListener(this);
        findViewById(R.id.change_anim).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_big:
                startActivity(new Intent(this, WaveActivity.class));
                break;
            case R.id.change_anim:
                ActivityAnimationHelper.startActivity(this, TransitionsAnimActivity.actionIntent(this, "110"), v);
                break;
        }
    }
}
