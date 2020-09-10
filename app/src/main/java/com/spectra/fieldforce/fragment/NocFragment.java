package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.R;

import java.util.Objects;

public class NocFragment extends Fragment {
    private WebView webView;
    private ImageView backpressed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_webview,container,false);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webView);
        backpressed = view.findViewById(R.id.backpressed);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://cs.spectra.co/nocportal/index.php?account_no_bil=9063188");
       // init();
    }

    private void init(){
        SRDetailFragment myFragment = new SRDetailFragment();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, myFragment).addToBackStack(null).commit();
        getActivity().finish();
    }

}
