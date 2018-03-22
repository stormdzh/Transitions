package com.stromdzh.animationtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.stromdzh.animationtest.widget.WaveRevealView;

public class WaveActivity extends AppCompatActivity {

    private final static String TAG = "WaveActivity";
    private boolean hasAnimationStarted;

    private WaveRevealView backgroundView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        backgroundView = findViewById(R.id.backgroundView);
        image = findViewById(R.id.image);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && !hasAnimationStarted) {
            setupRevaalBackground();
        }

    }

    public void setupRevaalBackground() {
        backgroundView.setOnStateChangeListener(new WaveRevealView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (state == WaveRevealView.STATE_FINSHED) {
                    Toast.makeText(WaveActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取动画开始位置
        final int[] location = new int[2];
        image.getLocationOnScreen(location);

        //启动动画
        backgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                backgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                backgroundView.setFILL_TIME(4000);
                backgroundView.setFillPaintColor(Color.parseColor("#2196F3"));
                backgroundView.startFromLocation(location, true);
                hasAnimationStarted = true;
                return true;

            }
        });

    }
}
