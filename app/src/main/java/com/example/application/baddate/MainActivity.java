package com.example.application.baddate;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import static android.Manifest.permission.CALL_PHONE;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    private String message;
    private String contact;
    private boolean checked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);


        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        Button inst = findViewById(R.id.inst_button);
        inst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InstActivity.class));
            }
        });
        //check if the check box is selected or not
        CheckBox enablePhoneCall = findViewById(R.id.checkBox);
        enablePhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    checked = true;
                }
                else{
                    checked = false;
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);


        if (checked == true) {
            startAlarmWithPhoneCall(c);

        }
        else {
            startAlarm(c);
        }

    }

    private void updateTimeText(Calendar c) {
        String timeText = "Time to go: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }
    //new method to activate the phone call instead of message.
    private void startAlarmWithPhoneCall(Calendar c){
        long difference  = c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        if (difference <= 0)
        {
            c.add(Calendar.DATE, 1);
            difference  = c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        }
        CountDownTimer mCountDownTimer = new CountDownTimer(difference, 1000){

                public void onTick(long millsUntilFinished){

                }
                public void onFinish(){

                    StartPhoneCall();
                }
        }.start();
    }
    /*
    private void StartPhoneCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        intent.setData(Uri.parse("tel:(650) 555-1212"));
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
       // startActivity(intent);
    }
    */
    private void StartPhoneCall(){
        Intent intent = new Intent(MainActivity.this, FakeCallRingling.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }


    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("contact", contact);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }


    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Canceled");
    }
    public void sendMessage(View view)
    {
        EditText editText = findViewById(R.id.contact);
        EditText edit2 = findViewById(R.id.message);

        contact = editText.getText().toString();
        message = edit2.getText().toString();
    }
}