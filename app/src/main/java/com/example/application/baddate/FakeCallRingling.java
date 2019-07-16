package com.example.application.baddate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.Manifest.permission.CALL_PHONE;


public class FakeCallRingling extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String incomingContact;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_ringling);
        if (getIntent().hasExtra("contact")) {
            incomingContact = getIntent().getStringExtra("contact");
        } else {
            throw new IllegalArgumentException("Activity cannot find extras ");
        }
        System.out.println("in onCreate: "+ incomingContact);
        setContentView(R.layout.activity_fake_call_ringling);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(incomingContact);

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
                StartRealPhoneCall(incomingContact);
            }
        });


    }
    public void StartRealPhoneCall(String contact){
        Intent intent = new Intent(Intent.ACTION_CALL);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        intent.setData(Uri.parse(contact));
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
