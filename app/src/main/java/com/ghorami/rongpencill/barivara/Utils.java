package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Engineering on 11/1/2018.
 */
public class Utils {


    public static boolean isnetworkekAvable(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo !=null)&& (activeNetworkInfo.isConnected())){
            return true;
        }else{
           // Toast.makeText(context,"Network is not connected",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, OfflineBasic.class);
            context.startActivity(intent);
            return false;
        }

    }


    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
}
