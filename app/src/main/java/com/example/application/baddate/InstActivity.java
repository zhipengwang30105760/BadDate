package com.example.application.baddate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inst);

        TextView tv = (TextView) findViewById(R.id.textView_main);
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    public void getStarted(View view){
        startActivity(new Intent(InstActivity.this, MainActivity.class));
    }
}
