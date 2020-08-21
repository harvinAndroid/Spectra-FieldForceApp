package com.spectra.fieldforce;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public abstract class BaseActivity extends AppCompatActivity {
    private boolean isDestroyed;


    /**
     * Method used to switch from current activity to other
     *
     * @param destinationActivity activity to open
     */
    public void switchActivity(Class<?> destinationActivity) {
        startActivity(new Intent(this, destinationActivity));
    }

    /**
     * Method used to switch from current activity to other with assignmentData
     *
     * @param destinationActivity activity to open
     * @param bundle              assignmentData that carry to destination activity
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param bundle              assignmentData that carry to destination activity
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }


    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Method to back activity with result ok with out assignmentData
     */
    public void backActivityWithResultOk() {
        backActivityWithResultOk(null);
    }

    /**
     * Method to back activity with result ok with assignmentData
     *
     * @param bundle
     */
    public void backActivityWithResultOk(Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        } else
            setResult(RESULT_OK);
        finish();
    }

    /**
     * Method used to display short duration toast
     *
     * @param message message to be displayed
     */
    public void displayToast(String message) {
        if (TextUtils.isEmpty(message) || message.equalsIgnoreCase("null"))
            return;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method used to display short duration toast
     *
     * @param resId resource id of the message string to be displayed
     */
    public void displayToast(int resId) {
        displayToast(getString(resId));
    }














}
