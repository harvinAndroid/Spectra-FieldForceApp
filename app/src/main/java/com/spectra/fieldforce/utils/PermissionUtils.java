package com.spectra.fieldforce.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;


public class PermissionUtils implements AppConstants {

    /**
     * Method to check write permission
     *
     * @return
     */
    public static boolean checkWritePermission(Activity context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE_READ_WRITE);
            return false;
        }
        return true;
    }

    /**
     * Method to check call permission
     *
     * @return
     */
    public static boolean checkCallPermission(Activity context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CALL_PHONE, },
                    PERMISSION_REQUEST_CODE_CALL_PHONE);
            return false;
        }
        return true;
    }

    /**
     * Method to check location permission
     *
     * @return
     */
    public static boolean checkLocationPermission(final Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE_LOCATION);

            return false;
        }
        return true;
    }
}
