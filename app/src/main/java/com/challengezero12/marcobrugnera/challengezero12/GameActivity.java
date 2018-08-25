package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class GameActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.addView(new HandleView(this));
    }


}
