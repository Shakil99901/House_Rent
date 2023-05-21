package com.ghorami.rongpencill.barivara;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class SopnoWeb extends AppCompatActivity {

    String WebUrl, WebAddr;
    TextView toolh;
    EditText edittext;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopno_web);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        edittext = (EditText) findViewById(R.id.tool_header);
        ImageView proPic = (ImageView) findViewById(R.id.userImage);

        proPic.setImageDrawable(getResources().getDrawable(R.drawable.men_refresh));

        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWebNow();
            }
        });


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //  getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


        Intent in = getIntent();
        WebUrl = in.getExtras().getString("WebUrl");
        // caten = in.getExtras().getString("caten");
        edittext.setText(WebUrl);


        webView = (WebView) findViewById(R.id.webTest);
        webView.getSettings().setJavaScriptEnabled(true);

        goWebNow();


        // webView.loadUrl(WebAddr);
        WebSettings WebSettings = webView.getSettings();
        WebSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {
                Toast.makeText(SopnoWeb.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String tempCurrentUrl = url;
                if (tempCurrentUrl.isEmpty()) {
                    Log.i(String.valueOf(SopnoWeb.this), "No url returned!");
                } else {
                    Log.i(String.valueOf(SopnoWeb.this), tempCurrentUrl);
                    edittext.setText(tempCurrentUrl);
                    goWebNow();

                }
                return true;
            }

            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                String tempCurrentUrl = req.getUrl().toString();


            }

        });


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    private void goWebNow() {

        WebAddr= edittext.getText().toString();
        if (edittext.getText().toString().contains("http://")
                || edittext.getText().toString()
                .contains("https://")) {
            webView.loadUrl(WebAddr);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittext.getWindowToken(),
                    0);
        } else if (edittext.getText().toString().contains(".com")
                || edittext.getText().toString().contains(".net")
                || edittext.getText().toString().contains(".org")
                || edittext.getText().toString().contains(".bd")
                || edittext.getText().toString().contains(".gov")) {
            webView.loadUrl("http://"
                    + WebAddr);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittext.getWindowToken(),
                    0);
        } else {
            webView.loadUrl("https://www.google.com/search?q="
                    + WebAddr);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittext.getWindowToken(),
                    0);
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        else {
            finish();
        }

        super.onBackPressed();
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //onBackHome();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
