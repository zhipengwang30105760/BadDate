package com.example.application.baddate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.CALL_PHONE;


public class FakeCallRingling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_ringling);

        Button reject = (Button) findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FakeCallRingling.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button accept = (Button) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRealPhoneCall();
            }
        });


    }
    public void StartRealPhoneCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        intent.setData(Uri.parse("tel: 911"));
        /*
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
        */
         startActivity(intent);
    }
}
