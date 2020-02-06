package com.example.fieldforceapp;
//import android.R;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, WelcomeFragment.OnLogoutListener {
    public static PrefConfig prefConfig;


    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        prefConfig = new PrefConfig(this);

        if(findViewById(R.id.fregment_container)!=null) {


           /* if (savedInstanceState == null) {
                ;
           /* getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();*/
             //   return;
            //}
            if(prefConfig.readLoginStatus()){
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container,new WelcomeFragment()).commit();
            }else {
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container,new LoginFragment()).commit();
            }
        }
    }


    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container,
                new RegistrationFragment()).addToBackStack(null).commit();
    }
    @Override
    public void performResetpassword(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container,new ResetpasswordFragment()).addToBackStack(null).commit();

    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container,new WelcomeFragment()).commit();
    }

    @Override
    public void logoutperformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container,new LoginFragment()).commit();

    }
}
