package com.example.fieldforceapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, WelcomeFragment.OnLogoutListener {
    public static PrefConfig prefConfig;
    private static final String TAG = "MainActivity";

    public void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        /*Notification code start HP*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        /*Notification code end HP*/

        prefConfig = new PrefConfig(this);

        if (findViewById(R.id.fregment_container) != null) {


           /* if (savedInstanceState == null) {
                ;
           /* getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();*/
            //   return;
            //}
            if (prefConfig.readLoginStatus()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new LoginFragment()).commit();
            }
        }


        /*Notification*/
/*
      Button subscribeButton = findViewById(R.id.subscribeButton);
       subscribeButton.setOnClickListener(new View.OnClickListener() {

          public void onClick ( View v ) {
                Log.d(TAG, "Subscribing to weather topic");
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("weather")
                        .addOnCompleteListener(new OnCompleteListener< Void >() {
                            @Override
                            public void onComplete ( @NonNull Task< Void > task ) {
                                String msg = getString(R.string.msg_subscribed);
                                if (!task.isSuccessful()) {
                                    msg = getString(R.string.msg_subscribe_failed);
                                }
                                Log.d(TAG, msg);
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                // [END subscribe_topics]
            }
        });



        /*Notification*/

    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container,
                new RegistrationFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performResetpassword() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new ResetpasswordFragment()).addToBackStack(null).commit();

    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new WelcomeFragment()).commit();
    }

    @Override
    public void logoutperformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new LoginFragment()).commit();

    }


    /*Notification*/


}
