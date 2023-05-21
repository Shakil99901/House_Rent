package com.ghorami.rongpencill.barivara;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConnetNetwork extends AppCompatActivity implements View.OnClickListener  {
    final Context c = this;
    TextView wifi, data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connet_network);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        wifi = (TextView)findViewById(R.id.wifi);
        wifi.setOnClickListener(this);

        data = (TextView)findViewById(R.id.data);
        data.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
            final Intent mainIntent2 = new Intent(this, Welcome.class);
            //   mainIntent.putExtra("Lang",v2);

            this.startActivity(mainIntent2);
            this.finish();

        }



    }



    @Override
    public void onClick(View v) {

        //   MainActivity.this.finish();

        switch (v.getId()) {
            case R.id.wifi:
                // do something
               EnterBd();
                break;
            case R.id.data:
                // do something

                EnterDt();
                break;

        }
    }



    public void EnterBd(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable

            String v2=("BN").toString();
            Intent intent2 = new Intent(getApplicationContext(), BasicHome.class);
            //intent2.putExtra("spot",v1);
            intent2.putExtra("Lang",v2);
            //  intent2.putExtra("spot",v1);
            startActivity(intent2);
        }else{
            //no connection

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);
        }

    }

    public void EnterDt(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable

            String v2=("BN").toString();
            Intent intent2 = new Intent(getApplicationContext(), BasicHome.class);
            //intent2.putExtra("spot",v1);
            intent2.putExtra("Lang",v2);
            //  intent2.putExtra("spot",v1);
            startActivity(intent2);
        }else{
            //no connection

            Intent intent2 = new Intent(Intent.ACTION_MAIN);
            intent2.setComponent(new ComponentName("com.android.settings",
                    "com.android.settings.Settings$DataUsageSummaryActivity"));
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);

        }

    }

}
