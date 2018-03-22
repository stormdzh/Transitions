package com.stromdzh.animationtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stromdzh.animationtest.util.AbsAnimationListener;
import com.stromdzh.animationtest.util.ActivityAnimationHelper;

/**
 * author : dzh .
 * date   : 2018/3/22
 * desc   :
 */
public class TransitionsAnimActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);

        id = getIntent().getStringExtra("id");

        Toast.makeText(this, "得到的id是：" + id, Toast.LENGTH_SHORT).show();
    }

    public static Intent actionIntent(Context context, String id) {
        Intent intent = new Intent(context, TransitionsAnimActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    boolean isRunAnim; // 是否已经运行过动画

    @Override
    protected void onStart() {
        super.onStart();
        if (!isRunAnim) {
            isRunAnim = true;
            ActivityAnimationHelper.animScaleUp(this, getIntent(), null);
        }

    }

    @Override
    public void finish() {
        ActivityAnimationHelper.animScaleDown(this, new AbsAnimationListener() {
            @Override
            public void onAnimationEnd() {
                TransitionsAnimActivity.super.finish();
            }
        });
    }
}
