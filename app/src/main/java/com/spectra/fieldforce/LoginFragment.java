package com.spectra.fieldforce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

//    private TextView RegText, StatusMess, ErrorMessage;
//    private TextView textView;
//    private EditText UserName, UserPassword;
//    private Button LoginBn;
//    private String couponCodeString, userEmail, userEmailN, userName;
//    private String message;
//    private String fcmToken;
//
//    OnLoginFormActivityListener loginFormActivityListener;
//
//    public interface OnLoginFormActivityListener {
//        public void performRegister();
//
//        public void performLogin(String email, String name);
//
//        public void performResetpassword();
//
//        public void performLogout();
//    }
//
//    public LoginFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_login, container, false);
//        Typeface myTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Spectra-Regular.ttf");
//        textView = view.findViewById(R.id.spectraText);
//        textView.setTypeface(myTypeFace);
//
//        RegText = view.findViewById(R.id.register_txt);
//        // RegText = null;
//        UserName = view.findViewById(R.id.user_name);
//        UserPassword = view.findViewById(R.id.user_pass);
//        StatusMess = view.findViewById(R.id.error_text);
//        ErrorMessage = view.findViewById(R.id.errortxt);
//
//        Typeface mytextFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/helveticaneue-font/helveticaneue-light.ttf");
//        UserName.setTypeface(mytextFace);
//        UserPassword.setTypeface(mytextFace);
//
//        LoginBn = view.findViewById(R.id.login_btn);
//        LoginBn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                performLogin();
//            }
//        });
//
//        RegText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginFormActivityListener.performResetpassword();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Activity activity = (Activity) context;
//        loginFormActivityListener = (OnLoginFormActivityListener) activity;
//    }
}