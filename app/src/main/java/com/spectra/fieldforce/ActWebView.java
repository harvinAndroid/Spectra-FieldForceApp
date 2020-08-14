package com.spectra.fieldforce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActWebView extends AppCompatActivity {
    private WebView webView;
    private ImageView backpressed;

   /* @Nullable
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://cs.spectra.co/nocportal/index.php?account_no_bil=9063188");
    }*/

      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview);
        WebView_init();
        backpressed=findViewById(R.id.backpressed);
       /* backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });*/

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void WebView_init(){
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://cs.spectra.co/nocportal/index.php?account_no_bil=9063188");
    }

  /*  private void back(){
          Intent i = new Intent(this,SRDetail.class);
          startActivity(i);
          finish();

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
