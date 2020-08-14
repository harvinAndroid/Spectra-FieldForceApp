package com.spectra.fieldforce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.LoginRequest;
import com.spectra.fieldforce.Model.SavetokenRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginActivity extends AppCompatActivity implements OnLoginFormActivityListener {
    public static PrefConfig prefConfig;
    private TextView RegText, StatusMess, ErrorMessage;
    private TextView textView;
    private EditText UserName, UserPassword;
    private Button LoginBn;
    private String couponCodeString, userEmail, userEmailN, userName;
    private String message;
    private String fcmToken;
    AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        prefConfig = new PrefConfig(activity);
        setContentView(R.layout.fragment_login);

        Typeface myTypeFace = Typeface.createFromAsset(activity.getAssets(), "fonts/Spectra-Regular.ttf");
        textView = findViewById(R.id.spectraText);
        textView.setTypeface(myTypeFace);

        RegText = findViewById(R.id.register_txt);
        // RegText = null;
        UserName = findViewById(R.id.user_name);
        UserPassword = findViewById(R.id.user_pass);
        StatusMess = findViewById(R.id.error_text);
        ErrorMessage = findViewById(R.id.errortxt);

        Typeface mytextFace = Typeface.createFromAsset(activity.getAssets(), "fonts/helveticaneue-font/helveticaneue-light.ttf");
        UserName.setTypeface(mytextFace);
        UserPassword.setTypeface(mytextFace);

        LoginBn = findViewById(R.id.login_btn);
        LoginBn.setOnClickListener(view -> performLogin());

        RegText.setOnClickListener(view -> performResetpassword());
    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new RegistrationFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performResetpassword() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new ResetpasswordFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void performLogin(String email, String name) {
        prefConfig.writeName(email);
        prefConfig.writeUserName(name);
//        getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).addToBackStack(null).commit();
        Log.d("Point", "1");
        finish();
        startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    public void performLogout() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new LoginFragment()).commit();
    }

    private void performLogin() {
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setAction("authentication");
        loginRequest.setUser_name(username);
        loginRequest.setUser_password(password);
        loginRequest.setAuthkey("ac7b51de9d888e1458dd53d8aJAN3ba6f");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.performUserLogin(loginRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        couponCodeString = jsonObject.getString("Status");
                        if (couponCodeString.equals("Failure")) {
                            message = "Login Failed..Please try again...";
                            ErrorMessage.setText(message);
                        } else if (couponCodeString.equals("Success")) {
                            userEmail = jsonObject.getString("response");
                            JSONObject jsonObjectN = new JSONObject(String.valueOf(userEmail));
                            userEmailN = jsonObjectN.getString("name");
                            userName = jsonObjectN.getString("username");
                            message = "Welcome " + userName;
                            Log.d("Point", "1");
                            MainActivity.prefConfig.writeLoginStatus(true);
                            performLogin(userEmailN, userName);
                            Log.d("Point", "3");
                            getSaveToken();
                            Log.d("Point", "4");
                        }
                        StatusMess.setText(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {

            }
        });
        UserName.setText("");
        UserPassword.setText("");
        StatusMess.setText("");
        ErrorMessage.setText("");
    }

    private void getSaveToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        fcmToken = task.getResult().getToken();
                        Log.d("FCMToken", fcmToken);
                        performSaveToken();
                    }
                });
    }

    private void performSaveToken() {
        SavetokenRequest savetokenRequest = new SavetokenRequest();
        savetokenRequest.setAction("saveDeviceToken");
        savetokenRequest.setEmail(userEmailN);
        savetokenRequest.setUser_token(fcmToken);
        savetokenRequest.setAuthkey(Constants.AUTH_KEY);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.performSaveToken(savetokenRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        //  parse(String.valueOf(response.body()!=null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {

            }
        });
    }
}