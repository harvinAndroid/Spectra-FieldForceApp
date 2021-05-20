package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.model.LoginRequest;
import com.spectra.fieldforce.model.SavetokenRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.fragment.RegistrationFragment;
import com.spectra.fieldforce.fragment.ResetpasswordFragment;
import com.spectra.fieldforce.listener.OnLoginFormActivityListener;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.PrefConfig;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginActivity extends AppCompatActivity implements OnLoginFormActivityListener {

    public static PrefConfig prefConfig;
    private TextView RegText, StatusMess, ErrorMessage,textView;
    private EditText UserName, UserPassword;
    private Button LoginBn;
    private String couponCodeString, userEmail, userEmailN, userName,message,fcmToken;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        prefConfig = new PrefConfig(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        Typeface myTypeFace = Typeface.createFromAsset(activity.getAssets(), "fonts/Spectra-Regular.ttf");
        textView = findViewById(R.id.spectraText);
        textView.setTypeface(myTypeFace);

        RegText = findViewById(R.id.register_txt);
        // RegText = null;
        UserName = findViewById(R.id.user_name);
        UserPassword = findViewById(R.id.user_pass);
       // StatusMess = findViewById(R.id.error_text);
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
        finish();
        startActivity(new Intent(activity, MainActivity.class));
      /*  startActivity(new Intent(activity, DashBoardActivity.class));*/
    }

    @Override
    public void performLogout() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName(Constants.User);
       // getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new LoginFragment()).commit();
    }

    private void performLogin() {
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setAction(Constants.ACTION_AUTHENTICATION);
        loginRequest.setUser_name(username);
        loginRequest.setUser_password(password);
        loginRequest.setAuthkey(Constants.AUTH_KEY);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.performUserLogin(loginRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
        ErrorMessage.setText("");
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
                try {
                    if (response.isSuccessful()) {
                        //  parse(String.valueOf(response.body()!=null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}