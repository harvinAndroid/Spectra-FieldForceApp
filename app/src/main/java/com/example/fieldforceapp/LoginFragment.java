package com.example.fieldforceapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fieldforceapp.Model.LoginRequest;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView RegText,StatusMess;
    private TextView textView;
    private EditText UserName, UserPassword;
    private Button LoginBn;
    private String name, salary;
    private String couponCodeString,userEmail,userEmailN;
    private String message;

    OnLoginFormActivityListener loginFormActivityListener;

    public interface OnLoginFormActivityListener {
        public void performRegister();

        public void performLogin(String name);

        public void performResetpassword();
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Typeface myTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Spectra-Regular.ttf");
        textView = view.findViewById(R.id.spectraText);
        textView.setTypeface(myTypeFace);

        RegText = view.findViewById(R.id.register_txt);
        UserName = view.findViewById(R.id.user_name);
        UserPassword = view.findViewById(R.id.user_pass);
        StatusMess=view.findViewById(R.id.error_text);

        Typeface mytextFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/helveticaneue-font/helveticaneue-light.ttf");
        UserName.setTypeface(mytextFace);
        UserPassword.setTypeface(mytextFace);

        LoginBn = view.findViewById(R.id.login_btn);
        LoginBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               performLogin();

            }
        });

        RegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loginFormActivityListener.performRegister();
                loginFormActivityListener.performResetpassword();

            }

        });
        //employeeName = (TextView) view.findViewById(R.id.name);
        //employeeSalary = (TextView) view.findViewById(R.id.salary);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;

    }

    private void performLogin() {
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();
        String result;

        //employeeName = findViewById(R.id.name);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setAction("authentication");
        loginRequest.setUser_name(username);
        loginRequest.setUser_password(password);
        loginRequest.setAuthkey("ac7b51de9d888e1458dd53d8aJAN3ba6f");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //  \"Authkey\":\"ac7b51de9d888e1458dd53d8aJAN3ba6f\",\"Action\":\"authentication\
        Call< JsonElement > call =apiService.performUserLogin(loginRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {

                try
                {
             //get your response....response.body()
                    if (response.isSuccessful()) {

                      //  parse(String.valueOf(response.body()!=null));
                    }

                    //String JsonObj= String.valueOf(response.body());

                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));


                    couponCodeString = jsonObject.getString("Status");

                   // Log.d(TAG, "User Email ID: " + userEmailN);
                    if(couponCodeString.equals("Failure")){

                        message="Login Failed..Please try again...";
                       // MainActivity.prefConfig.dispalyToast("Login Failed..Please try again...");

                    }else if(couponCodeString.equals("Success")){
                        userEmail=jsonObject.getString("response");
                        JSONObject jsonObjectN = new JSONObject(String.valueOf(userEmail));
                        userEmailN=jsonObjectN.getString("name");

                        message="Welcome "+userEmailN;
                        MainActivity.prefConfig.writeLoginStatus(true);
                        loginFormActivityListener.performLogin(userEmailN);

                    }

                   StatusMess.setText(message);


                  //  Log.d(TAG, "RetroFit2 :RetroGetLogin: " + message);
                }
                catch (Exception e)
                {
                e.printStackTrace();
                }

               // if(response.body().getResponse().equals("ok")){
                 //   MainActivity.prefConfig.writeLoginStatus(true);
                   // loginFormActivityListener.performLogin(response.body().getName());

               // }
              //  else if(response.body().getResponse().equals("failled")){

                //    MainActivity.prefConfig.dispalyToast("Login Failed..Please try again...");
               // }


            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {

            }
        });
        UserName.setText("");
        UserPassword.setText("");
        StatusMess.setText("");

    }

}