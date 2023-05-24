package com.ghorami.rongpencill.barivara;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private androidx.multidex.MultiDex MultiDex;

    // ArrayList for person names
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
            // Obtain the FirebaseAnalytics instance.
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "419933649540-7ohcotl6ti1l007h53lgfmvf3mmvibh9.apps.googleusercontent.com");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "barivara");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            String languageToLoad = "bn"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

            setContentView(R.layout.activity_main);

            clearData();
//            addNotification();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    final Intent mainIntent = new Intent(MainActivity.this, Login.class);
                    //   mainIntent.putExtra("Lang",v2);

                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            }, 1500);


            // ATTENTION: This was auto-generated to handle app links.
            Intent appLinkIntent = getIntent();
            String appLinkAction = appLinkIntent.getAction();
            Uri appLinkData = appLinkIntent.getData();
        }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void addNotification() {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setContentTitle("Welcome barivara")
                        .setContentText("share space, earn money");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void clearData()
    {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            SharedPreferences mSharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name_bn),  Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.apply();
            int last_version = mSharedPreferences.getInt("last_version", -1);
            if(last_version != mCurrentVersion)
            {

                SharedPreferences preferences =getSharedPreferences("sopno_details",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                SharedPreferences preferences2 =getSharedPreferences("sopno_message",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.clear();
                editor2.commit();

                SharedPreferences preferenceso =getSharedPreferences("sopno_othere",Context.MODE_PRIVATE);
                SharedPreferences.Editor editoro = preferenceso.edit();
                editoro.clear();
                editoro.commit();


                SharedPreferences preferencesw =getSharedPreferences("sopno_womene",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorw = preferencesw.edit();
                editorw.clear();
                editorw.commit();

                SharedPreferences preferencess =getSharedPreferences("sopno_studente",Context.MODE_PRIVATE);
                SharedPreferences.Editor editors = preferencess.edit();
                editors.clear();
                editors.commit();

                SharedPreferences preferencesg =getSharedPreferences("sopno_girle",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorg = preferencesg.edit();
                editorg.clear();
                editorg.commit();

                SharedPreferences preferencesf =getSharedPreferences("sopno_fame",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorf = preferencesf.edit();
                editorf.clear();
                editorf.commit();

                SharedPreferences preferencesse =getSharedPreferences("sopno_selle",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorse = preferencesse.edit();
                editorse.clear();
                editorse.commit();

                SharedPreferences preferencesb =getSharedPreferences("sopno_bache",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorb = preferencesb.edit();
                editorb.clear();
                editorb.commit();


                //clear all your data like database, share preference, local file
                //Note : Don't delete last_version value in share preference
            }
            mEditor.putInt("last_version", mCurrentVersion);
            mEditor.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    }