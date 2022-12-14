package com.spectra.fieldforce.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.application.App;
import com.spectra.fieldforce.model.LoginResponse;

import java.util.concurrent.ConcurrentHashMap;
public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public void LoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public boolean LoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.login_status), false);
    }

    public void writeName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_userEmail), name);
        editor.commit();
    }

    public String readName() {
        return sharedPreferences.getString(context.getString(R.string.pref_userEmail), "userName");

    }

    public void writeUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_username), name);
        editor.commit();
    }

    public String readUserName() {
        return sharedPreferences.getString(context.getString(R.string.pref_username), "userName");

    }

    public void dispalyToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
