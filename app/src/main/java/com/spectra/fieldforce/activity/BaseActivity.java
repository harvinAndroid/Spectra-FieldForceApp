package com.spectra.fieldforce.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spectra.fieldforce.R;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean isDestroyed;
    LinearLayout layoutProgressBar;

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

    @Override
    public void onClick(View view) {

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

    public boolean requestReadWriteCameraPermission() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
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

    public String getTextAsString(EditText editText) {
        return editText.getText().toString().trim();
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


   /* public void showProgressDialog() {
        try {
            dialog_loading = findViewById(R.id.dialog_loading);

            if (mProgressDialog == null) {
                mProgressDialog = new Dialog(this);
                mProgressDialog.setContentView(R.layout.dialog_loading);
                mProgressDialog.setCancelable(false);
                try {
                    mProgressDialog.show();
                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
            if (!isFinishing())
                mProgressDialog.show();
        } catch (Exception ex) {
            ex.getStackTrace();
        }

    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing() && !isFinishing())
            mProgressDialog.dismiss();
    }
*/







}
