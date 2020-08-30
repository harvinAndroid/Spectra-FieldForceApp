package com.spectra.fieldforce.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;


import com.spectra.fieldforce.BaseActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UtilityFunctions {

    private TextView allMissions, completedMissions;
    private View completedView, missionView;

    /**
     * Method to hide software keyboard
     *
     * @param mActivity
     */
    public static void hideKeyBoard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mActivity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @return True if an Intent with the specified action can be sent and
     * responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 0.0 will return {@code color1}, 0.5 will give an even blend,
     *              1.0 will return {@code color2}.
     */
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    /**
     * to check whether the string is empty or not
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim()) || "null".contentEquals(string.trim());
    }

    public static boolean isCheckBoxChecked(Activity activity, int viewId) {
        CheckBox checkBox = (CheckBox) activity.findViewById(viewId);
        return checkBox.isChecked();
    }

    public static void openPopUpMenu(Context context, int menuId, View anchorVIew, OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(context, anchorVIew);
        popupMenu.setOnMenuItemClickListener(listener);
        popupMenu.inflate(menuId);
        popupMenu.show();
    }

    /**
     * Method to get text from text view
     *
     * @param activity
     * @param viewId
     * @return
     */
    public static String getTextForView(BaseActivity activity, int viewId) {
        TextView view = (TextView) activity.findViewById(viewId);
        return view.getText().toString().trim();
    }

    /**
     * Method to set error on edit text
     *
     * @param errors
     * @param editText
     */
    public static void setErrorOnView(EditText editText, ArrayList<String> errors) {
        if (errors != null && errors.size() > 0)
            editText.setError(errors.get(0));
    }

    /**
     * Method to set text on the empty textView.
     *
     * @param view
     * @param texttoshow
     */
    public static void setTextOnEmptyView(TextView view, String texttoshow) {
        view.setVisibility(View.VISIBLE);
        view.setText(texttoshow);
    }

    public static String getPath(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static long fileSize(File file) {
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }


    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String filePath) {
        if (TextUtils.isEmpty(filePath))
            return null;
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtils.getMimeType(file)), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}