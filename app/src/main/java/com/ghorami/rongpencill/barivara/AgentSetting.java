package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;


public class AgentSetting extends AppCompatActivity {

    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    Toolbar toolbar;
    public Context context;
    CollapsingToolbarLayout collapsingToolbarLayout;
    final Context c = this;
    TextView tvAdL2, EditPro, sellAd, tvFeedback, tvShare, tvAbout, tvPrivacy, tvTerms, tvDeveloper;
    ImageView imageView, profileImage;
    Typeface custom_font;
    String message, m_Textreq;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_setting);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/NotoSansBengali.ttf");
        initInstancesDrawer();
        //getUserData();

        tvFeedback = (TextView)findViewById(R.id.tvfeedback);
        tvShare = (TextView)findViewById(R.id.tvshare);
        tvAbout = (TextView)findViewById(R.id.tvAbout);
        tvPrivacy = (TextView)findViewById(R.id.tvPrivacy);
        tvTerms = (TextView)findViewById(R.id.tvTerms);
        tvDeveloper = (TextView)findViewById(R.id.tvDeveloper);

        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Classr = new Intent(AgentSetting.this, AgentSettingDetails.class);
                Classr.putExtra("Topics", "about");
                startActivity(Classr);
            }
        });

        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Classr = new Intent(AgentSetting.this, AgentSettingDetails.class);
                Classr.putExtra("Topics", "terms");
                startActivity(Classr);
            }
        });

        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Classr = new Intent(AgentSetting.this, AgentSettingDetails.class);
                Classr.putExtra("Topics", "privacy");
                startActivity(Classr);
            }
        });


        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appShare();
            }
        });



    }

    private void getUserData() {
        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {

            uMobile1  = prf.getString("uMobile1", null);
            uPass1 = prf.getString("uPass1", null);
            uImage1 = prf.getString("uImage1", null);
            Sopnoid1 = prf.getString("Sopnoid1", null);
            uLocation1 = prf.getString("uLocation1", null);
            uRentAd1 = prf.getString("uRentAd1", null);
            uSellAd1 = prf.getString("uSellAd1", null);
            uName1 = prf.getString("uName1", null);
            uEmail1 = prf.getString("uEmail1", null);
            uType1 = prf.getString("uType1", null);
            uVerify1 = prf.getString("uVerify1", null);
            uAddress1 = prf.getString("uAddress1", null);
            tMessage = prf.getString("uMessage1", null);

            // new JSONAsyncTask().execute(url);


        } else{
            Toast.makeText(getApplicationContext(), "You are not signin yet! Please login again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, BasicHome.class);
            startActivity(intent);
            finish();
        }
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("About us");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

   /**
    @Override
    public void onBackPressed(){
        onDestroy();
    }

**/

    private void appShare() {


        String v4=("https://play.google.com/store/apps/details?id=com.ghorami.rongpencill.barivara").toString();
        String v1=("#barivara").toString();
        String v2=("earn money from your house in easy way").toString();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, v4+"\n"+v1+"\n"+v2+"\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));



    }

    private void sendFeedback() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        Log.e("MyActivity", "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease
        );
        String v2=("MyActivity"+ "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease).toString();

        String[] TO = {"tpucse@gmail.com"};
        Uri uri = Uri.parse("mailto:tpucse@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", String.valueOf("#barivara"))
                .appendQueryParameter("body", "Please leave your feedback here.\nPlease do not edit the information below:\n"+v2)
                .build();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_blink, menu);


        return super.onCreateOptionsMenu(menu);
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
