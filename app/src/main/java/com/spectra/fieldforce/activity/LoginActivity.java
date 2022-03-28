package com.spectra.fieldforce.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.databinding.ActivityLoginBinding;
import com.spectra.fieldforce.model.LoginRequest;
import com.spectra.fieldforce.model.LoginResponse;
import com.spectra.fieldforce.model.SavetokenRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.fragment.RegistrationFragment;
import com.spectra.fieldforce.fragment.ResetpasswordFragment;
import com.spectra.fieldforce.listener.OnLoginFormActivityListener;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.PrefConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginActivity extends AppCompatActivity implements OnLoginFormActivityListener {
    ActivityLoginBinding binding;
    public static PrefConfig prefConfig;
    private String couponCodeString, userEmail, userEmailN, userName,message,fcmToken;
    private AppCompatActivity activity;
    public static final String PREF ="Login";
    private String strPassword,  strUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        prefConfig = new PrefConfig(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Typeface myTypeFace = Typeface.createFromAsset(activity.getAssets(), "fonts/Spectra-Regular.ttf");
        binding.spectraText.setTypeface(myTypeFace);

        Typeface mytextFace = Typeface.createFromAsset(activity.getAssets(), "fonts/helveticaneue-font/helveticaneue-light.ttf");
        binding.userName.setTypeface(mytextFace);
        binding.userPass.setTypeface(mytextFace);

        binding.loginBtn.setOnClickListener(view -> {
             strUserName = Objects.requireNonNull(binding.userName.getText()).toString();
             strPassword = Objects.requireNonNull(binding.userPass.getText()).toString();

            if(strUserName.isEmpty()){
                Toast.makeText(this, AppConstants.PLEASE_ENTER_USERNAME,Toast.LENGTH_LONG).show();
            }else if(strPassword.isEmpty()){
                Toast.makeText(this,AppConstants.PLEASE_ENTER_PASSWORD,Toast.LENGTH_LONG).show();
            }else{
                performLogin();
            }
        });
        binding.registerTxt.setOnClickListener(view -> performResetpassword());
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
        prefConfig.writeUserName(strUserName);
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }

    @Override
    public void performLogout() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName(Constants.User);
    }

    @Override
    public void Login(String email, String name) {
        prefConfig.writeName(email);
        prefConfig.writeUserName(strUserName);
        finish();
        performLogin(email, name);
        startActivity(new Intent(activity, MainActivity.class));
    }

    private void performLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAction(Constants.ACTION_AUTHENTICATION);
        loginRequest.setUser_name(binding.userName.getText().toString());
        loginRequest.setUser_password(binding.userPass.getText().toString());
        loginRequest.setAuthkey(Constants.AUTH_KEY);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.performUserLogin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {
                                MainActivity.prefConfig.LoginStatus(true);
                                SharedPreferences sp = getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                              /*myEdit.putString("InstallationAuth", response.body().getResponse().getInstallAuth());
                                myEdit.putString("FFA", response.body().getResponse().getFfaAuth());
                                myEdit.putString("VenderCode", response.body().getResponse().getVendorCode());
                                myEdit.putString("EnggId", response.body().getResponse().getUserID());
                                myEdit.putString("EnggName",response.body().getResponse().getUsername());
                                myEdit.putString("EmailId",response.body().getResponse().getName());
                                myEdit.putString("EnggName",response.body().getResponse().getUsername());*/
                                myEdit.putString("EmailId",response.body().getResponse().getName());
                                myEdit.putString("UserName",strUserName);
                                myEdit.putString("Password",strPassword);
                                myEdit.commit();
                                userEmailN = response.body().getResponse().getName();
                                Log.e("ffalogin", String.valueOf(sp));
                                Login(response.body().getResponse().getName(), response.body().getResponse().getUsername());
                                getSaveToken();
                            } else {
                                message = "Login Failed..Please try again...";
                                binding.errortxt.setText(message);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
        binding.errortxt.setText("");
    }


    private void getSaveToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }
                    fcmToken = Objects.requireNonNull(task.getResult()).getToken();
                    Log.d("FCMToken", fcmToken);
                    performSaveToken();
                });
    }


    private void performSaveToken() {
        SavetokenRequest savetokenRequest = new SavetokenRequest();
        savetokenRequest.setAction(Constants.SAVE_DEVICE_TOKEN);
        savetokenRequest.setEmail(userEmailN);
        savetokenRequest.setUser_token(fcmToken);
        savetokenRequest.setAuthkey(Constants.AUTH_KEY);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.performSaveToken(savetokenRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}