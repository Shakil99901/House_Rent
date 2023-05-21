package com.ghorami.rongpencill.barivara;

import androidx.appcompat.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.os.Build.VERSION.SDK_INT;

public class AgentDashboard extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    public Context context;
    CollapsingToolbarLayout collapsingToolbarLayout;
    final Context c = this;
    TextView tvAdL2, EditPro, sellAd;
    ImageView imageView, profileImage;
    String sopnoid1, tvuName1,  image2, AdL1, sellAd1, uAddress;

    TextView tvuName, tvsid, tvAdL, tvsellAd, tvPostAd, tvNumber, tvThikana, tvEditPro;
    String iMobile, iPassword;

    Typeface custom_font;
    SharedPreferences prf, pref;
    SharedPreferences prefer;
    ImageView ivprofile, ivrent, ivsell, ivnewrent, ivearn, ivallad, ivsearch, iverify, ivmessage, ivshop, ivReport, ivNotice, ivBill, ivTenant;
    TextView tvProfile, tvRentAd, tvSellAd, tvNewRent, tvEarn, tvAllAdd, tvSearch, tvVerify, tvSetting, tvLogout, tvNotice, tvBill, tvReport, tvTenant;
    TextView tvRentA, tvSellA, tvMessage, tvShop;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1,uNID1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String    AdL, SellAd, image, uReferI, uProfession1, uTentQ1, uLandLord1, uDistrict1, uCompany1, uNationality1,  uAddr, SellAd1, uReferId, uImage, Sopnoid, uLocation, uRentAd, uSellAd, uName, uEmail, uType, uVerify, uAddresse, uMobile, uPass;
    BottomNavigationView bottomNavigationView;
    String message, m_Textreq;

    String urlVerifyReq = "http://barivara.com/api/verifyReq.php";
    String urlk = "http://barivara.com/api/getuserdetailsAdvt.php";
    String urlagent = "http://barivara.com/api/getAllMessage.php";
    String urlagentMessageDetails = "http://barivara.com/api/chatBotGetAll.php";
    String AlertMsg;
    MenuItem msgMenu;
    String tMessage=null;
    String tMsg;
    String Cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_dashboard);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/NotoSansBengali.ttf");
        initInstancesDrawer();
        getUserData();

       // tvPostAd = (TextView) findViewById(R.id.tvPostAd);
        //tvPostAd.setOnClickListener(this);


        tvNotice = (TextView) findViewById(R.id.tvNotice);
        tvNotice.setOnClickListener(this);
      //  tvReport = (TextView) findViewById(R.id.tvReport);
        //tvReport.setOnClickListener(this);
        tvBill = (TextView) findViewById(R.id.tvBill);
        tvBill.setOnClickListener(this);


        tvAdL = (TextView) findViewById(R.id.tvAdL);
        tvAdL.setOnClickListener(this);
        tvAdL2 = (TextView) findViewById(R.id.tvAdL2);
        tvAdL2.setOnClickListener(this);

        tvsellAd = (TextView) findViewById(R.id.tvsellAd);
        tvsellAd.setOnClickListener(this);
        sellAd = (TextView) findViewById(R.id.sellAd);
        sellAd.setOnClickListener(this);

        tvuName = (TextView)findViewById(R.id.tvuName);
        tvsid = (TextView)findViewById(R.id.tvsid);
      //  tvNumber = (TextView)findViewById(R.id.tvNumber);
       // tvThikana = (TextView)findViewById(R.id.tvThikana);
       // tvEditPro = (TextView)findViewById(R.id.tvEditPro);
        //tvEditPro.setOnClickListener(this);

        profileImage = (ImageView)findViewById(R.id.ImageProfile);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeFullPic();
            }


        });

        ivNotice= (ImageView)findViewById(R.id.ivNotice);
        ivNotice.setOnClickListener(this);
        ivBill= (ImageView)findViewById(R.id.ivBill);
        ivBill.setOnClickListener(this);
        //ivReport= (ImageView)findViewById(R.id.ivReport);

        ivmessage= (ImageView)findViewById(R.id.ivmessage);
        ivmessage.setOnClickListener(this);
        tvMessage= (TextView)findViewById(R.id.tvMessage);
        tvMessage.setOnClickListener(this);


        ivprofile= (ImageView)findViewById(R.id.ivprofile);
        ivprofile.setOnClickListener(this);
        ivrent= (ImageView)findViewById(R.id.ivrent);
        ivrent.setOnClickListener(this);
        ivnewrent= (ImageView)findViewById(R.id.ivnewrent);
        ivnewrent.setOnClickListener(this);
        ivearn= (ImageView)findViewById(R.id.ivearn);
        ivearn.setOnClickListener(this);
        ivallad= (ImageView)findViewById(R.id.ivallad);
        ivallad.setOnClickListener(this);
        ivsearch= (ImageView)findViewById(R.id.ivsearch);
        ivsearch.setOnClickListener(this);
        iverify= (ImageView)findViewById(R.id.ivverify);


        tvProfile= (TextView)findViewById(R.id.tvProfile);
        tvProfile.setOnClickListener(this);
        tvRentAd= (TextView)findViewById(R.id.tvRentAd);
        tvRentAd.setOnClickListener(this);
        tvNewRent= (TextView)findViewById(R.id.tvNewRent);
        tvNewRent.setOnClickListener(this);
        tvEarn= (TextView)findViewById(R.id.tvEarn);
        tvEarn.setOnClickListener(this);
        tvAllAdd= (TextView)findViewById(R.id.tvAllAdd);
        tvAllAdd.setOnClickListener(this);
        tvSearch= (TextView)findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(this);
        tvVerify= (TextView)findViewById(R.id.tvVerify);
     //   tvSetting= (TextView)findViewById(R.id.tvSetting);
      //  tvSetting.setOnClickListener(this);
        tvLogout= (TextView)findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);


        setUserData();
    }

    class JSONAsyncTaskUe extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentDashboard.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... urlo) {
            try {

              //  Intent in = getIntent();
                //spot = in.getExtras().getString("spot");
                //caten = in.getExtras().getString("caten");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("MobileNumber",uMobile1));
                nameValuePairs.add(new BasicNameValuePair("Password",uPass1));
                //  nameValuePairs.add(new BasicNameValuePair("Phone", PhoneN));
                //nameValuePairs.add(new BasicNameValuePair("Password", Password));
                //nameValuePairs.add(new BasicNameValuePair("adserial", adserial));
                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlk);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("resultP");

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
                        Sopnoid= object.getString("sopnoid");
                        AdL= object.getString("rentalAd");
                        SellAd= object.getString("sellAd");
                        uLocation= object.getString("userLocation");
                        uDistrict1= object.getString("userDistrict");
                        uName= object.getString("username");
                        uEmail = object.getString("userEmail");
                        uType = object.getString("userType");
                        uTentQ1 = object.getString("userTentQ");
                        uLandLord1 = object.getString("userLandLordQ");
                        uVerify = object.getString("verify");
                        uAddr= object.getString("userAddress");
                        image= object.getString("userimage");
                        uReferI= object.getString("uReferID");
                        tMessage= object.getString("uMessage");


                        Log.e("success", uName);
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
                Toast.makeText(AgentDashboard.this, "Unable to send data to server", Toast.LENGTH_LONG).show();
            }
        }

        private void setData() {
            if(Sopnoid.equals("")){
                Toast.makeText(AgentDashboard.this, "Something is Wrong", Toast.LENGTH_LONG).show();
            } else{
                if(image.equals ("")){
                    profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    ivprofile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                } else {
                    Picasso.get().load(image).transform(new CircleTransform()).into(profileImage);
                    Picasso.get().load(image).transform(new CircleTransform()).into(ivprofile);
                }

                if(AdL.equals ("")){
                    tvRentA.setVisibility(View.GONE);
                } else {
                    tvRentA.setText(AdL);
                    tvAdL.setText(AdL);
                    tvRentA.setVisibility(View.VISIBLE);
                }

                if(SellAd.equals ("")){
                    tvSellA.setVisibility(View.GONE);
                } else {
                    tvSellA.setText(SellAd);
                    tvsellAd.setText(SellAd);
                    tvSellA.setVisibility(View.VISIBLE);
                }

                if(tMessage.equals ("")){
                    tvMessage.setVisibility(View.GONE);
                } else {
                    tvMessage.setText(tMessage);
                    tvMessage.setVisibility(View.VISIBLE);
                }

                if(uVerify.equals ("1")){
                    iverify.setBackgroundResource(R.drawable.cerclebackgroundblue);
                    tvVerify.setText("verified");

                } else {
                    iverify.setBackgroundResource(R.drawable.cerclebackgroundgray);
                    tvVerify.setText("not verified");
                    iverify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userVerify();
                        }
                    });
                    tvVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userVerify();
                        }
                    });

                }


                tvuName.setText(uName);
                new JSONAsyncTaskAgent().execute(urlagent);
            //    new JSONAsyncTaskDetails().execute(urlagentMessageDetails);

                SharedPreferences preferences =getSharedPreferences("sopno_details",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                SharedPreferences.Editor editor2 = getSharedPreferences("sopno_details", MODE_PRIVATE).edit();
                editor2.putString("uImage1", image);
                editor2.putString("uCompany1", uCompany1);
                editor2.putString("uNID1", uNID1);
                editor2.putString("uNationality1", uNationality1);
                editor2.putString("uProfession1", uProfession1);
                editor2.putString("Sopnoid1", Sopnoid);
                editor2.putString("uLocation1", uLocation);
                editor2.putString("uDistrict1", uDistrict1);
                editor2.putString("uRentAd1", AdL);
                editor2.putString("uSellAd1", SellAd);
                editor2.putString("uMessage1", tMessage);
                editor2.putString("uName1", uName);
                editor2.putString("uEmail1", uEmail);
                editor2.putString("uType1", uType);
                editor2.putString("uTentQ1", uTentQ1);
                editor2.putString("uLandLord1", uLandLord1);
                editor2.putString("uVerify1", uVerify);
                editor2.putString("uMobile1", uMobile1);
                editor2.putString("uAddress1", uAddr);
                editor2.putString("uPass1", uPass1);
                editor2.putString("uReferId1", uReferI);
                editor2.apply();
            }



        }


    }


    private void setUserData() {

        new JSONAsyncTaskUe().execute(urlk);

        tvsid.setText(Sopnoid1);


    }

    private void getUserData() {
        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {

          /**
            uImage1 = prf.getString("uImage1", null);
            uLocation1 = prf.getString("uLocation1", null);
            uRentAd1 = prf.getString("uRentAd1", null);
            uSellAd1 = prf.getString("uSellAd1", null);
            uName1 = prf.getString("uName1", null);
            uEmail1 = prf.getString("uEmail1", null);
            **/
            uType1 = prf.getString("uType1", null);
            Sopnoid1 = prf.getString("Sopnoid1", null);
            uMobile1  = prf.getString("uMobile1", null);
            uPass1 = prf.getString("uPass1", null);
            uVerify1 = prf.getString("uVerify1", null);
         //   uAddress1 = prf.getString("uAddress1", null);
            // new JSONAsyncTask().execute(url);




        } else{
            //moveTaskToBack(true);
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);
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
        toolh.setText("Dashboard");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
      //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //   getSupportActionBar().setDisplayShowHomeEnabled(true);
          //  getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

    private void goMessage() {
        Intent intent = new Intent(getApplicationContext(),
                AgentMessageAll.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    private void seeFullPic() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_pic_dialogue, null);
        TouchImageView prImage = (TouchImageView) dialogView.findViewById(R.id.my_image);

        if(image.equals ("")){
            prImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(image).into(prImage);
        }

        builder.setView(dialogView)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    protected class JSONAsyncTaskDetails extends AsyncTask<String, Void, Boolean> {

        String oldk, senderData;


        @Override
        protected Boolean doInBackground(String... urld) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");
                nameValuePairs.add(new BasicNameValuePair("receiverID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("passC", uPass1));
                nameValuePairs.add(new BasicNameValuePair("mobC", uMobile1));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlagentMessageDetails);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoM = new JSONObject(data);
                    JSONArray  jarray = jsonoM.getJSONArray("resultPro");
                    // jarray = jarray.replace("\\", "");



                    for (int i = 0; i < 1; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        if(object.getString("cid").equals("")){
                            oldk="0";
                        }else{
                            oldk = object.getString("cid");
                            Log.v("dk", oldk);
                        }

                    }

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        senderData = object.getString("sender");
                        AndroidVersion tour = new AndroidVersion();
                        SharedPreferences preferences =getSharedPreferences("sopno_mDetails",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        SharedPreferences.Editor editor3 = getSharedPreferences("sopno_mDetails", MODE_PRIVATE).edit();
                        editor3.putString("messageDJ", String.valueOf(jsonoM));
                        editor3.putString("aId", String.valueOf(jsonoM.length()));
                        editor3.putString("Cid", oldk);
                        editor3.putString("senderData", senderData);
                        editor3.apply();
                        // toursad.add(tour);
                    }
                    return true;
                }

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
            //  pDialog.dismiss();

            if (result == false)
                //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha", "Error on data load");
        }
    }

    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {



        @Override
        protected Boolean doInBackground(String... urlsm) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("type", uType1));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("mobilenumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("passC", uPass1));
                nameValuePairs.add(new BasicNameValuePair("mobC", uMobile1));
                Log.e("adsrl", "success");
                // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlagent);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("message");
                    for (int j = 0; j < 1; j++) {
                        JSONObject object = jarray.getJSONObject(j);
                        Cid = object.getString("cid");
                        Log.v("dk", Cid);
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersion tour = new AndroidVersion();
                        SharedPreferences preferences =getSharedPreferences("sopno_message",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        SharedPreferences.Editor editor2 = getSharedPreferences("sopno_message", MODE_PRIVATE).edit();
                        editor2.putString("messageJ", String.valueOf(jsono));
                        editor2.putString("aId", String.valueOf(jsono.length()));
                        editor2.putString("Cid", Cid);
                        editor2.apply();
                        // toursad.add(tour);
                    }
                    return true;
                }

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
            //   pDialog.dismiss();
            //  adapterAgentMessageList.notifyDataSetChanged();

            if (result == false)
                //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha", "Error on data load");

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvAdL:
                Rent();
                break;

            case R.id.tvAdL2:
                Rent();
                break;

            case R.id.tvsellAd:
                Sell();
                break;

            case R.id.sellAd:
                Sell();
                break;

            case R.id.ivmessage:
                goMessage();
                break;
            case R.id.tvMessage:
                goMessage();
                break;

            case R.id.tvBill:
                SendBill();
                break;
            case R.id.ivBill:
                SendBill();
                break;


            case R.id.tvNotice:
                NoticeBoard();
                break;
            case R.id.ivNotice:
                NoticeBoard();
                break;


            case R.id.tvProfile:
               Profile();
                break;
            case R.id.ivprofile:
                Profile();
                break;
            case R.id.tvRentAd:
                Rent();
                break;
            case R.id.ivrent:
                Rent();
                break;

            case R.id.ivnewrent:
                if (uVerify.equals("1")) {
                    adCategory();
                }else {

                    AlertMsg ="Sorry!! your are not verified yet. Please verify yourself.".toString();

                    showAlert(AlertMsg);
                   // Toast.makeText(AgentDashboard.this, (CharSequence) "Sorry!! your are not verified yet. Please verify yourself.", Toast.LENGTH_LONG).show();

                }

                break;

            case R.id.tvNewRent:
                if (uVerify.equals("1")) {
                    adCategory();
                }else{
                    AlertMsg ="Sorry!! your are not verified yet. Please verify yourself.".toString();

                    showAlert(AlertMsg);
                   // Toast.makeText(AgentDashboard.this, (CharSequence) "Sorry!! your are not verified yet. Please verify yourself.", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.ivallad:
                allAd();
                break;
            case R.id.tvAllAdd:
                allAd();
                break;

            case R.id.ivsearch:

                SearchDial();
                //searchAd();
                break;
            case R.id.tvSearch:

                SearchDial();
                // searchAd();
                break;

            case R.id.ivearn:
                userEarn();
                break;
            case R.id.tvEarn:
                userEarn();
                break;

         //   case R.id.ivverify:
           //    userVerify();
             //   break;
          //  case R.id.tvVerify:
            //   userVerify();
              //  break;

            case R.id.tvLogout:
               userLogout();
                break;

        }
    }

    private void NoticeBoard() {
        Intent intent4 = new Intent(getApplicationContext(), AllTenantRequest.class);
        startActivity(intent4);
    }

    private void AllTenant() {
        Intent intent4 = new Intent(getApplicationContext(), AllTenant.class);
        startActivity(intent4);
    }


    private void PostReport() {
    }

    private void SendBill() {
        Intent intent4 = new Intent(getApplicationContext(), AllTenantBill.class);
        startActivity(intent4);

    }


    private void showAlert(String message) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Notice")
                .setCancelable(false)
                .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // do nothing
                       // onBackPressed();
                        m_Textreq = "1".toString();

                        new JSONAsyncTask().execute(urlVerifyReq);
                       // dialog.cancel();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void userLogout() {
        SharedPreferences preferences =getSharedPreferences("sopno_details",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        SharedPreferences preferences2 =getSharedPreferences("sopno_message",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.commit();
        //  finish();
        Intent intent1 = new Intent(getApplicationContext(), Login.class);
        startActivity(intent1);
    }

    private void userSetting() {

        Intent intent4 = new Intent(getApplicationContext(), AgentSetting.class);
        startActivity(intent4);

    }

    private void userVerify() {

        VerifyReq();
    }

    private void userEarn() {

        Intent intent4 = new Intent(getApplicationContext(), AgentEarnPoint.class);
        startActivity(intent4);
    }


    private void VerifyReq() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("verify your self");
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        final AutoCompleteTextView input = new AutoCompleteTextView(this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        input.setVisibility(View.GONE);
        // input.setHint("লোকেশন যেমন-মতিঝিল, গুলশান");
        //  input.setText(String.valueOf(TypesS.getSelectedItem()));
        String[] countries = getResources().getStringArray(R.array.location);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        input.setAdapter(adapter);

        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Textreq = "1".toString();

                new JSONAsyncTask().execute(urlVerifyReq);

            }
        });




        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentDashboard.this);


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

                nameValuePairs.add(new BasicNameValuePair("userPass", uPass1));
                nameValuePairs.add(new BasicNameValuePair("Sopnoid1", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("Type", "agent"));
                nameValuePairs.add(new BasicNameValuePair("Req", m_Textreq));

                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlVerifyReq);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);


                    JSONArray jarray = jsono.getJSONArray("tour");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        message = object.getString("message");
                        //  pimage = object.getString("pimage");
                        // tvTour.setText(arent);


                        Log.e("success", message);
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

            if (result == null)
                Toast.makeText(AgentDashboard.this, "Unable to send data", Toast.LENGTH_LONG).show();

        }

        private void setData() {
            if(message.equals("success")){
               // sendMail();
             //   Intent Classr = new Intent(AgentDashboard.this, AgentEarnPoint.class);
               // startActivity(Classr);
                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }


    private void SearchDial() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("বাড়ির খোঁজে");
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        final AutoCompleteTextView input = new AutoCompleteTextView(this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        input.setHint("search home for rent");
        //  input.setText(String.valueOf(TypesS.getSelectedItem()));
        String[] countries = getResources().getStringArray(R.array.location);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        input.setAdapter(adapter);

        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                Intent intent1 = new Intent(getApplicationContext(), AgentSearch.class);
                intent1.putExtra("spot",m_Text);
                startActivity(intent1);
            }
        });

        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });

/**
        builder.setNegativeButton("বিস্তারিত খোঁজ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent1 = new Intent(getApplicationContext(), AgentSearch.class);
                //intent1.putExtra("spot",m_Text);
                startActivity(intent1);
            }
        });
**/
        builder.show();
    }


    private void searchAd() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_search, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Search",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                            String result = userInput.getText().toString();
                                Intent intent2 = new Intent(getApplicationContext(), AgentSearch.class);
                                intent2.putExtra("query", result);
                                startActivity(intent2);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();



    }

    private void allAd() {
        Intent intent2 = new Intent(getApplicationContext(), AgentAllAdd.class);
        startActivity(intent2);


    }

    private void adCategory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.adCategory));
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
        builder.setPositiveButton(getString(R.string.adRent), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent2 = new Intent(getApplicationContext(), AgentNewAdd.class);
                intent2.putExtra("Category","rent");
                startActivity(intent2);

            }
        });


        builder.setNegativeButton(getString(R.string.adSell), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(getApplicationContext(), AgentNewSellAd.class);
                intent2.putExtra("Category","sell");

                startActivity(intent2);
            }
        });

        builder.show();


    }

    private void Rent(){
        Intent intentl2 = new Intent(getApplicationContext(), AgentAllPost.class);
        intentl2.putExtra("Category","rent");
        startActivity(intentl2);
    }

    private void Sell(){
        Intent intentj = new Intent(getApplicationContext(), AgentAllPost.class);
        intentj.putExtra("Category","sell");
        startActivity(intentj);
    }

    private void Profile() {
        Intent intent4 = new Intent(getApplicationContext(), AgentProfile.class);
        startActivity(intent4);
    }

    public void updateMessageStatus() {
        tMsg=tvMessage.getText().toString();

        if(tMsg.equals ("")){
            msgMenu.setIcon(R.drawable.men_email);
        } else {
            msgMenu.setIcon(R.drawable.men_mail_orange);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agent, menu);
        msgMenu = menu.findItem(R.id.action_msg);
        updateMessageStatus();
        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //onBackHome();
            return true;
        }



        if (id == R.id.action_home) {

            Intent intent = new Intent(getApplicationContext(),
                    AgentDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_msg) {

            Intent intent = new Intent(getApplicationContext(),
                    AgentMessageAll.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return true;
        }

        if (id == R.id.menue) {

            Intent intent = new Intent(getApplicationContext(),
                    AgentMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);


            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
