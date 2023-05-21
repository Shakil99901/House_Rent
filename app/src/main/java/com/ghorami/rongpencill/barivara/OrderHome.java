package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

public class OrderHome extends AppCompatActivity implements View.OnClickListener {

    Button btnConCollect;
    TextView tvhomePrice, tvhomeAdvance, tvhomeOthers, tvFeedPos, tvFeedNeg, tvtotalCost, tvagentCommission;
    ImageView ivAgentPic;
    TextView tvAgentName, tvAgentID, tvAgentPhone;

    final Context context = this;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String tMessage=null;
    String urlPost = "http://barivara.com/api/agentStatePost.php";
    String agentName, agentID, agentPhone, agentEmail, agentAddress, agentPic,
            homeSerial, homeRent, homeAdvance, homeOthers, homeCity, homeAddress, homeLocation, appCommission, totalCost;

    double totalC;
    String totalhomeC, feedPos, feedNeg, ConCollect, message, agentCommission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(height > width) setContentView(R.layout.order_home);
        else setContentView(R.layout.order_home_wide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);


        getUserData();

        btnConCollect = (Button)findViewById(R.id.btnConCollect);
        btnConCollect.setOnClickListener(this);
        btnConCollect.setAnimation(myAnim);

        tvhomePrice = (TextView)findViewById(R.id.tvhomePrice);
        tvhomeAdvance = (TextView)findViewById(R.id.tvhomeAdvance);
        tvhomeOthers = (TextView)findViewById(R.id.tvhomeOthers);
        tvtotalCost = (TextView)findViewById(R.id.tvtotalCost);
        tvagentCommission = (TextView)findViewById(R.id.tvagentCommission);



        tvFeedPos = (TextView)findViewById(R.id.tvFeedPos);
        tvFeedPos.setOnClickListener(this);

        tvFeedNeg = (TextView)findViewById(R.id.tvFeedNeg);
        tvFeedNeg.setOnClickListener(this);

        ivAgentPic = (ImageView)findViewById(R.id.ivAgentPic);

        tvAgentName = (TextView)findViewById(R.id.tvAgentName);
        tvAgentID = (TextView)findViewById(R.id.tvAgentID);
        tvAgentPhone = (TextView)findViewById(R.id.tvAgentPhone);

 getOrderData();



    }

    private void getOrderData() {


        Intent ing = getIntent();
        agentID = ing.getStringExtra("agentID");
        homeSerial = ing.getStringExtra("homeSerial");
        agentName = ing.getStringExtra("agentName");
        agentPhone =ing.getStringExtra("agentPhone");
        agentPic = ing.getStringExtra("agentPic");
        agentEmail = ing.getStringExtra("agentEmail");
        homeRent = ing.getStringExtra("homeRent");
        homeAdvance =ing.getStringExtra("homeAdvance");
        homeOthers = ing.getStringExtra("homeOthers");
        homeCity = ing.getStringExtra("homeCity");
        homeAddress = ing.getStringExtra("homeAddress");
        homeLocation = ing.getStringExtra("homeLocation");

        setOrderData();
    }

    private void setOrderData() {

        final int a = Integer.parseInt(homeRent);
        final int b = Integer.parseInt(homeAdvance);
        final int c = Integer.parseInt(homeOthers);


        final int d = Integer.parseInt("2");
        final int t = a+b+c;


        if(t<=15000){
            totalC = 0.07;
        }else if(t<100000){
            totalC = 0.05;
        }else if(t>=100000){
            totalC = 0.03;
        }

        final double ac = (a+b+c)*(totalC);

        agentCommission = String.valueOf(ac);
        tvagentCommission.setText("-"+ agentCommission);
        tvagentCommission.setVisibility(View.GONE);

        final double appc = ac/d;

        appCommission = String.valueOf(appc);

        final double totalPrice = (a+b+c)+(ac);
      //  totalC = Integer.parseInt(homeRent+homeAdvance+homeOthers);
        totalhomeC = String.valueOf(t).toString();
        Log.e("rr", totalhomeC);

        if (totalhomeC.equals("")) {
            tvtotalCost.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Woops!! Something is wrong", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            tvtotalCost.setText(Html.fromHtml(totalhomeC+"/-"));
            btnConCollect.setText("I have gave " +totalhomeC+" BDT");
        }

        if (homeOthers.equals("")) {
                    tvhomeOthers.setVisibility(View.GONE);
                } else {
                    tvhomeOthers.setText(Html.fromHtml(homeOthers+"/-"));
                }

        if (homeAdvance.equals("")) {
            tvhomeAdvance.setVisibility(View.GONE);
        } else {
            tvhomeAdvance.setText(Html.fromHtml(homeAdvance+"/-"));
        }
         if (homeRent.equals("")) {
            tvhomePrice.setVisibility(View.GONE);
        } else {
            tvhomePrice.setText(Html.fromHtml(homeRent+"/-"));
        }



        if (agentPic.equals("")) {
            ivAgentPic.setImageDrawable(getResources().getDrawable(R.drawable.user));

        } else {
            Picasso.get().load(agentPic).transform(new CircleTransform()).into(ivAgentPic);
        }

        if (agentName.equals("")) {
            tvAgentName.setVisibility(View.GONE);
        } else {
            tvAgentName.setText(Html.fromHtml(agentName));
        }
        if (agentID.equals("")) {
            tvAgentID.setVisibility(View.GONE);
        } else {
            tvAgentID.setText(Html.fromHtml(agentID));

        }
        if (agentPhone.equals("")) {
            tvAgentPhone.setVisibility(View.GONE);
        } else {
            tvAgentPhone.setText(Html.fromHtml(agentPhone));
            tvAgentPhone.setVisibility(View.GONE);

        }



    }

    @Override
    public void onClick(View  v) {

        switch (v.getId()) {
            case R.id.tvFeedPos:
             //   Swomen();
                feedPos="1".toString();
                tvFeedPos.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_thumb_yes_green,0,0);
                tvFeedNeg.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_thumb_no,0,0);
               // feedNeg="0".toString();
                break;
            case R.id.tvFeedNeg:
             //   Swomen();

                feedPos="-1".toString();
                tvFeedNeg.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_thumb_no_cyne,0,0);
               // feedPos="0".toString();
                tvFeedPos.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_thumb_yes,0,0);
                break;
             case R.id.btnConCollect:
             //   Swomen();
                 ConCollect="1".toString();
                btnConCollect.setBackgroundColor(Color.GREEN);
                 new JSONAsyncTask().execute(urlPost);
                break;
            case R.id.ivAgentPic:
             callN();
                break;

        }

    }

    private void callN() {

        String ophone = agentPhone.toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + ophone));
        startActivity(intent);
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
            // new JSONAsyncTask().execute(url);
            initInstancesDrawer();

        } else{
            Toast.makeText(getApplicationContext(), "You are not signin yet! Please login again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, BasicHome.class);
            startActivity(intent);
            finish();
        }
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(OrderHome.this);


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

              //  nameValuePairs.add(new BasicNameValuePair("userPass", getPassword));

                nameValuePairs.add(new BasicNameValuePair("Type", "user"));
                nameValuePairs.add(new BasicNameValuePair("AgentID", agentID));
                nameValuePairs.add(new BasicNameValuePair("UserID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("feedback", feedPos));
                nameValuePairs.add(new BasicNameValuePair("payStatus", ConCollect));
                nameValuePairs.add(new BasicNameValuePair("homeSerial", homeSerial));
                nameValuePairs.add(new BasicNameValuePair("homePrice", totalhomeC));
                nameValuePairs.add(new BasicNameValuePair("appCommission", appCommission));
                nameValuePairs.add(new BasicNameValuePair("uPass", uPass1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("agentCommission", agentCommission));



                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlPost);
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
                Toast.makeText(OrderHome.this, "Unable to send data", Toast.LENGTH_LONG).show();


        }

        private void setData() {
            if(message.equals("success")){

                if(uType1.equals("agent")){
                    Intent Classr = new Intent(OrderHome.this, AgentAllAdd.class);
                    startActivity(Classr);
                }else{
                    Intent Classr = new Intent(OrderHome.this, UserHome.class);
                    startActivity(Classr);
                }
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }


    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("Booking Home");
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

        if(uImage1.equals ("")){
            proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {

            Picasso.get().load(uImage1).transform(new CircleTransform()).into(proPic);
        }


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


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


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