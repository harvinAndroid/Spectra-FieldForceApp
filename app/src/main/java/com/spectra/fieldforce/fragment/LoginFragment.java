package com.spectra.fieldforce.fragment;

import androidx.fragment.app.Fragment;


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
//        View view = inflater.inflate(R.layout.activity_login, container, false);
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