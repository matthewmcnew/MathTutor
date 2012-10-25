package com.example.math.tutor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainMathActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_math);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_math, menu);
        return true;
    }
}
