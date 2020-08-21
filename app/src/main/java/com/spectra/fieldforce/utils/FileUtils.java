package com.spectra.fieldforce.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.spectra.fieldforce.BaseActivity;
import com.spectra.fieldforce.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    public static final int NO_STORAGE_ERROR = -1;
    public static final int CANNOT_STAT_ERROR = -2;
    public static int FILE_REQUEST_CODE = 1050;
    public static int IMAGE_REQUEST_CODE = 2050;
    public static int DOWNLOAD_FILE = 2;


    /**
     * Method creates an image file in the application folder.
     *
     * @param context application context
     * @return an image file with .jpg extension or video file with .mp4 extension
     */
    public static File makeMediaFile(Context context, boolean photo) {
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + (photo ? ".jpg" : ".mp4");

        String path = createDirectory(context).getAbsolutePath();

        File imageFile = new File(path
                + File.separator
                + fileName);

        try {
            if (imageFile.exists())
                imageFile.delete();

            imageFile.createNewFile();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method creates an directory with application name if already exists
     * then returns that directory otherwise crete new directory.
     *
     * @param context application context
     * @return directory with application name
     */
    public static File createDirectory(Context context) {
        String state = Environment.getExternalStorageState();
        String directoryName = context.getString(R.string.app_name);
        directoryName = directoryName.replaceAll(" ", "");
        File folder = null;
        if (state.contains(Environment.MEDIA_MOUNTED)) {
            folder = new File(Environment.getExternalStorageDirectory() + File.separator + directoryName);
        } else {
            folder = new File(context.getFilesDir() + File.separator + directoryName);
        }

        folder.mkdir();

        return folder;
    }

    public static void showStorageToast(Activity activity) {

    }

    public static int calculatePicturesRemaining(Activity activity) {

        try {
            /*if (!ImageManager.hasStorage()) {
                return NO_STORAGE_ERROR;
            } else {*/
            String storageDirectory = "";
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                storageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                storageDirectory = activity.getFilesDir().toString();
            }
            StatFs stat = new StatFs(storageDirectory);
            float remaining = ((float) stat.getAvailableBlocks()
                    * (float) stat.getBlockSize()) / 400000F;
            return (int) remaining;
            //}
        } catch (Exception ex) {
            // if we can't stat the filesystem then we don't know how many
            // pictures are remaining.  it might be zero but just leave it
            // blank since we really don't know.
            return CANNOT_STAT_ERROR;
        }
    }

    /**
     * Method to get file uri
     *
     * @param path
     * @return
     */
    public static Uri getFileUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public static void deleteFile(String path) {
        File file = null;
        if (!TextUtils.isEmpty(path))
            file = new File(path);
        if (file != null && file.exists())
            file.delete();
    }

    /**
     * @return The MIME type for the given file.
     */
    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }


    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * Opens the file manager to choose the file.
     *
     * @param context
     */
    public static void showFileChooser(Context context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        try {
            ((BaseActivity) context).startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            ((BaseActivity) context).displayToast(R.string.please_wait);
        }

       /* Intent intent = new Intent(context, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowVideos(false)
                .setShowFiles(true)
                .setShowAudios(false)
                .setSuffixes("jpg","jpeg","pdf","doc","docx","png")
                .enableImageCapture(true)
                .setMaxSelection(1).setPortraitSpanCount(2)
                .setSkipZeroSizeFiles(true)
                .build());
        ((BaseActivity) context).startActivityForResult(intent, FILE_REQUEST_CODE);*/
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    /**
     * This method checks the extension of the file to be uploaded.
     *
     *
     */
    public static boolean checkExtension(Context context, Uri uri) {
        if (FileUtils.getMimeType(context, uri).equalsIgnoreCase("jpeg")
                || FileUtils.getMimeType(context, uri).equalsIgnoreCase("jpg") ||
                FileUtils.getMimeType(context, uri).equalsIgnoreCase("png") ||
                FileUtils.getMimeType(context, uri).equalsIgnoreCase("gif") ||
                FileUtils.getMimeType(context, uri).equalsIgnoreCase("pdf") ||
                FileUtils.getMimeType(context, uri).equalsIgnoreCase("docx") ||
                FileUtils.getMimeType(context, uri).equalsIgnoreCase("doc")) {
            return true;
        }
        return false;
    }


    public static void downloadFile(String url, Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_FILE);
            return;
        }
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Mentyor/" + System.currentTimeMillis() + url.substring(url.lastIndexOf(".", url.length() - 1)));

        String path = Environment.DIRECTORY_DOWNLOADS+"/Mentyor/" + System.currentTimeMillis() + url.substring(url.lastIndexOf(".", url.length() - 1));
        long refid = downloadManager != null ? downloadManager.enqueue(request) : 0;
//        Log.d("log", "File: " + System.currentTimeMillis() + url.substring(url.lastIndexOf(".", url.length() - 1)));  "Your file will be downloaded shortly in the Downloads folder."
        Toast.makeText(context,path , Toast.LENGTH_SHORT).show();
    }

    /**
     * Used to open the file sent to server.
     *
     * @param filename
     * @param context
     */
    public static void openFile(String filename, Activity context) {
        File file = new File(filename);

        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".JPG");

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), mime);
        context.startActivityForResult(intent, 10);
    }

}
