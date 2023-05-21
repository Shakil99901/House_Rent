package com.ghorami.rongpencill.barivara;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.opengl.GLES10;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL10;

import static android.os.Build.VERSION.SDK_INT;

public class Login extends AppCompatActivity {
    Button login;
    Button Register;
    Button Back, btnSkip, btnJob, btnEN, btnBN;
    Button btnNewAc;
    String url = "http://barivara.com/api/getuserdetails.php";

    String urlbachelor = "http://barivara.com/api/arent.php?t_spot=bachelor&lang=BN&caten=rent";
    String urlfamilys = "http://barivara.com/api/arent.php?t_spot=family&lang=BN&caten=rent";
    String urlgirls = "http://barivara.com/api/arent.php?t_spot=gstudent&lang=BN&caten=rent";
    String urlstudent = "http://barivara.com/api/arent.php?t_spot=student&lang=BN&caten=rent";
    String urlothers = "http://barivara.com/api/arent.php?t_spot=others&lang=BN&caten=rent";
    String urlwomen = "http://barivara.com/api/arent.php?t_spot=women&lang=BN&caten=rent";
    String urlsell = "http://barivara.com/api/test.php";

    JSONArray sellJ, womenJ, otherJ, studentJ, girlsJ,familyJ,bachelorJ;

    String Name,idnumber,Batch,Phone,Email,Department, message, Type, Pass, image2;
    String ID, Password, PhoneN;
    String uReferId, Sopnoid1, uProfession1, uCompany1, uDistrict1, uNationality1, uNID1, uName1, uMobile1, uType1, uVerify1, iPassword, uPass1, uEmail1, uLocation1, AdL1, SellAd1, uAddress;
    SharedPreferences prf, pref;
    SharedPreferences prefer;
    Context context;
    String uType;

    EditText lPhone, lPassword;
    ImageView profileImage, logImage;
    TextView tvWelcome, tvWelcome2, tvSignTo, tvSignTo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// In Activity's onCreate() for instance
        String  languageToLoad = "bn";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        setContentView(R.layout.login);
/**
        if(height > width) setContentView(R.layout.login);
        else setContentView(R.layout.login_wide);

        **/
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);

        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        ParseAllData();
        tvWelcome = (TextView)findViewById(R.id.tvWelcome);
        tvWelcome2 = (TextView)findViewById(R.id.tvWelcome2);
        tvSignTo = (TextView)findViewById(R.id.tvSignTo);
        tvSignTo2 = (TextView)findViewById(R.id.tvSignTo2);

        Back = (Button)findViewById(R.id.btnBack);
        btnBN = (Button)findViewById(R.id.btnBN);
        btnEN = (Button)findViewById(R.id.btnEN);

        btnBN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String  languageToLoad = "bn";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,getResources().getDisplayMetrics());
            }
        });

        btnEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
              String  languageToLoad = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,getResources().getDisplayMetrics());


            }
        });




        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent2 = new Intent(Login.this, BasicHomeOffline.class);
                startActivity(intent2);
            }
        });

        btnSkip = (Button)findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent2 = new Intent(Login.this, BasicHomeOffline.class);
                startActivity(intent2);
            }
        });

        btnJob = (Button)findViewById(R.id.btnJob);
        btnJob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent2 = new Intent(Login.this, CarrerJob.class);
                startActivity(intent2);
            }
        });


        lPhone = (EditText)findViewById(R.id.etid);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        logImage = (ImageView)findViewById(R.id.logImage);

        lPhone.setShadowLayer(1, 0, 0, Color.BLACK);
        lPassword = (EditText) findViewById(R.id.etpassword);
       // lPassword.setInputType(TYPE_NULL);
        lPassword.setShadowLayer(1, 0, 0, Color.BLACK);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        login = (Button) findViewById(R.id.btnlogin);
        login.setAnimation(myAnim);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation(myAnim);
                // TODO Auto-generated method stub
                getDate();


            }
        });
        Register = (Button) findViewById(R.id.btnregister);
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               RegisterNew();
            }
        });

        btnNewAc = (Button)findViewById(R.id.btnNewAc);
        btnNewAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNewAc.setVisibility(View.INVISIBLE);
                profileImage.setVisibility(View.GONE);
                tvWelcome.setVisibility(View.VISIBLE);
                tvSignTo.setVisibility(View.VISIBLE);
                tvWelcome2.setVisibility(View.GONE);
                tvSignTo2.setVisibility(View.GONE);
                lPassword.setVisibility(View.VISIBLE);
                lPhone.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
                logImage.setVisibility(View.VISIBLE);
            }
        });


        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {

         String   Phonep  = prf.getString("uMobile1", null);
          String  Imagep = prf.getString("uImage1", null);
           String Passwp = prf.getString("uPass1", null);
           uType = prf.getString("uType1", null);
           // new JSONAsyncTask().execute(url);
            lPhone.setText(Phonep);
           lPhone.setVisibility(View.GONE);
         //   lPhone.setInputType(TYPE_NULL);
            lPassword.setText(Passwp);
            lPassword.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            logImage.setVisibility(View.GONE);
            profileImage.setVisibility(View.VISIBLE);
            tvWelcome.setVisibility(View.GONE);
            tvSignTo.setVisibility(View.GONE);
            tvWelcome2.setVisibility(View.VISIBLE);
            tvSignTo2.setVisibility(View.VISIBLE);
             Picasso.get().load(Imagep).transform(new CircleTransform()).into(profileImage);

            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(uType.equals("agent")) {

                        Intent Classr = new Intent(Login.this, AgentDashboard.class);
                        startActivity(Classr);
                       // Toast.makeText(Login.this, "আপনি একজন বাড়ীওয়ালা", Toast.LENGTH_LONG).show();

                    }else if (uType.equals("general")){
                     //   Toast.makeText(Login.this, "আপনি একজন ভাড়াটিয়া", Toast.LENGTH_LONG).show();
                        Intent Classr = new Intent(Login.this, UserHome.class);
                        startActivity(Classr);
                    }
                }
            });



        } else{
            tvWelcome.setVisibility(View.VISIBLE);
            tvSignTo.setVisibility(View.VISIBLE);
            tvWelcome2.setVisibility(View.GONE);
            tvSignTo2.setVisibility(View.GONE);
            btnNewAc.setVisibility(View.INVISIBLE);
            profileImage.setVisibility(View.GONE);
            lPassword.setVisibility(View.VISIBLE);
            lPhone.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
            logImage.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "your session is expired", Toast.LENGTH_LONG).show();
        }

    }

    private void RegisterNew() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chose Your category");
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        builder.setNeutralButton(R.string.Close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });

// Set up the buttons
        builder.setPositiveButton(R.string.UserBn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent2 = new Intent(Login.this, UserSignup.class);
                intent2.putExtra("category","general");
                startActivity(intent2);

            }
        });




        builder.setNegativeButton(R.string.AgentBn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent2 = new Intent(Login.this, AgentSignup.class);
                intent2.putExtra("category","agent");
                startActivity(intent2);
            }
        });

        builder.show();

    }



    private void getDate() {

        PhoneN = ((EditText) findViewById(R.id.etid)).getText().toString();
        Password = ((EditText) findViewById(R.id.etpassword)).getText().toString();
        new JSONAsyncTask().execute(url);

    }


    private void ParseAllData() {

        /**
         *  SharedPreferences.Editor editor2 = getSharedPreferences("sopno_bache", MODE_PRIVATE).edit();
         *                 editor2.putString("bachelorJ", String.valueOf(bachelorJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_selle", MODE_PRIVATE).edit();
         *                 editor2.putString("sellJ", String.valueOf(sellJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_fame", MODE_PRIVATE).edit();
         *                 editor2.putString("familyJ", String.valueOf(familyJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_girle", MODE_PRIVATE).edit();
         *                 editor2.putString("girlsJ", String.valueOf(girlsJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_studente", MODE_PRIVATE).edit();
         *                 editor2.putString("studentJ", String.valueOf(studentJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_womene", MODE_PRIVATE).edit();
         *                 editor2.putString("womenJ", String.valueOf(womenJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_othere", MODE_PRIVATE).edit();
         *                 editor2.putString("otherJ", String.valueOf(otherJ));
         *
         *
         */

        new JSONAsyncRentBachelor().execute(urlbachelor);
        new JSONAsyncRentSell().execute(urlsell);
        new JSONAsyncRentfamily().execute(urlfamilys);
        new JSONAsyncRentGirls().execute(urlgirls);
        new JSONAsyncRentStudent().execute(urlstudent);
        new JSONAsyncRentOthers().execute(urlothers);
        new JSONAsyncRentWomens().execute(urlwomen);

    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(Login.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("MobileNumber",PhoneN));
                nameValuePairs.add(new BasicNameValuePair("Password",Password));
              //  nameValuePairs.add(new BasicNameValuePair("Phone", PhoneN));
                //nameValuePairs.add(new BasicNameValuePair("Password", Password));
                //nameValuePairs.add(new BasicNameValuePair("adserial", adserial));
                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("resultPro");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(0);
                        //Inner object is their using below code:
                       // String Name,idnumber,Batch,Phone,Email,Department;

                      // image2 = object.getString("pImage");
                        // tvTour.setText(arent);


                        uProfession1= object.getString("uprofession");
                        uNationality1= object.getString("unationality");
                        uNID1= object.getString("unid");
                        uCompany1= object.getString("ucompany");
                        Sopnoid1= object.getString("sopnoid");
                        AdL1= object.getString("rentalAd");
                        SellAd1= object.getString("sellAd");
                        uLocation1= object.getString("userLocation");
                        uDistrict1= object.getString("userDistrict");
                        uName1= object.getString("username");
                        uEmail1 = object.getString("userEmail");
                        uType1 = object.getString("userType");
                        uVerify1 = object.getString("verify");
                        uPass1 = object.getString("UserPass");
                        uMobile1= object.getString("userMobile");
                        uAddress= object.getString("userAddress");
                        image2= object.getString("userimage");
                        uReferId= object.getString("uReferID");


                        Log.e("success", uName1);
                        //  Toast.makeText(getApplicationContext(), arent, Toast.LENGTH_LONG).show();
                    }

                    //   Toast.makeText(MainActivity.this, (CharSequence) jarray, Toast.LENGTH_LONG).show();
                    return true;
                }


            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            setData();
            //adapter.notifyDataSetChanged();
            if (result == null) {
                Toast.makeText(Login.this, "Unable to link with server", Toast.LENGTH_LONG).show();
            }
        }

        private void setData() {
            if(Sopnoid1.equals("")){
                Toast.makeText(Login.this, "Something is Wrong", Toast.LENGTH_LONG).show();
            } else{
                SharedPreferences.Editor editor2 = getSharedPreferences("sopno_details", MODE_PRIVATE).edit();
             /**   editor2.putString("uImage1", image2);
                editor2.putString("uLocation1", uLocation1);
                editor2.putString("uRentAd1", AdL1);
                editor2.putString("uSellAd1", SellAd1);
                editor2.putString("uName1", uName1);
                editor2.putString("uEmail1", uEmail1);
                editor2.putString("uType1", uType1);
                editor2.putString("uVerify1", uVerify1);
                editor2.putString("uAddress1", uAddress);
                **/
                editor2.putString("uType1", uType1);
                editor2.putString("Sopnoid1", Sopnoid1);
                editor2.putString("uMobile1", uMobile1);
                editor2.putString("uPass1", Password);
                editor2.putString("uReferId1", uReferId);
                editor2.apply();
                // setData();
                if(uType1.equals("agent")) {
                    Intent Classr = new Intent(Login.this, AgentDashboard.class);
                    startActivity(Classr);
                   // Toast.makeText(Login.this, "আপনি একজন বাড়ীওয়ালা", Toast.LENGTH_LONG).show();

                }else{
                  //  Toast.makeText(Login.this, "আপনি একজন ভাড়াটিয়া", Toast.LENGTH_LONG).show();
                    Intent Classr = new Intent(Login.this, UserHome.class);
                    startActivity(Classr);
                }
            }



        }


    }

    protected class JSONAsyncRentBachelor extends AsyncTask<String, Void, Boolean> {



        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlbachelor);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);


                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    bachelorJ = jsonoj.getJSONArray("tour");
                    SharedPreferences preferences =getSharedPreferences("sopno_bache",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_bache", MODE_PRIVATE).edit();
                    editor2.putString("bachelorJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;

        }


        protected void onPostExecute(Boolean result) {
            //  setData();
          //  adapterAgentRentbachelor.notifyDataSetChanged();
            // adapterAgentRentfamily.notifyDataSetChanged();
            if (result == false){

            }
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }

    protected class JSONAsyncRentSell extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlsell);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    sellJ = jsonoj.getJSONArray("tour");

                    SharedPreferences preferences =getSharedPreferences("sopno_selle",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_selle", MODE_PRIVATE).edit();
                    editor2.putString("sellJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
          //  adapterAgentRentsell.notifyDataSetChanged();
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }
    protected class JSONAsyncRentfamily extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlfamilys);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                   // JSONArray sellJ, womenJ, otherJ, studentJ, girlsJ,familyJ,bachelorJ;

                    familyJ = jsonoj.getJSONArray("tour");
                    SharedPreferences preferences =getSharedPreferences("sopno_fame",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_fame", MODE_PRIVATE).edit();
                    editor2.putString("familyJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();

                }

                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
           // adapterAgentRentfamily.notifyDataSetChanged();
            if (result == false){

            }
              //  Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }
    protected class JSONAsyncRentGirls extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlgirls);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    girlsJ = jsonoj.getJSONArray("tour");

                    SharedPreferences preferences =getSharedPreferences("sopno_girle",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_girle", MODE_PRIVATE).edit();
                    editor2.putString("girlsJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
           // adapterAgentRentgirls.notifyDataSetChanged();
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }
    protected class JSONAsyncRentStudent extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlstudent);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    studentJ = jsonoj.getJSONArray("tour");

                    SharedPreferences preferences =getSharedPreferences("sopno_studente",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_studente", MODE_PRIVATE).edit();
                    editor2.putString("studentJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
           // adapterAgentRentstudent.notifyDataSetChanged();
            if (result == false){

            }
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }
    protected class JSONAsyncRentWomens extends AsyncTask<String, Void, Boolean> {



        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlwomen);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    womenJ = jsonoj.getJSONArray("tour");

                    SharedPreferences preferences =getSharedPreferences("sopno_womene",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_womene", MODE_PRIVATE).edit();
                    editor2.putString("womenJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
          //  adapterAgentRentwomen.notifyDataSetChanged();
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }
    protected class JSONAsyncRentOthers extends AsyncTask<String, Void, Boolean> {



        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlothers);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    //otherJ = jsonoj.getJSONArray("tour");

                    SharedPreferences preferences =getSharedPreferences("sopno_othere",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editor2 = getSharedPreferences("sopno_othere", MODE_PRIVATE).edit();
                    editor2.putString("otherJ", String.valueOf(jsonoj));
                    editor2.putString("aId", String.valueOf(jsonoj.length()));
                    editor2.apply();
                    return true;
                }


                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            //  setData();
            if (result == false){

            }
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }



}
