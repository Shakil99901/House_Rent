package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
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

public class AgentEarnPoint extends AppCompatActivity implements View.OnClickListener {

    String   tMessage=null; String tShop, uImage1, Sid1, totalPay, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    TextView tvuName, tvsid, tvpay, tvAdL, tvsellAd, tvPostAd, tvNumber, tvThikana, tvEditPro;
    ImageView imageView, profileImage;
    Typeface custom_font;

    CardView moneyCard, cardMoneyReferral, cardDue, cardEarn, cardRefer;
    Button sendDue;
    TextView tvtearning, tvappdue, tvreview, tvlsell, tvlrent, tvreferralp;
    String tEarning, tAppDue, tReview, tSell, tRent, tRefer;

    String url = "http://barivara.com/api/getaepoint.php";
    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_earn_point);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
        tvuName = (TextView)findViewById(R.id.tvuName);
        tvsid = (TextView)findViewById(R.id.tvsid);
        profileImage = (ImageView)findViewById(R.id.imageView);
       // moneyCard = (CardView)findViewById(R.id.moneyCard);
       // moneyCard.setOnClickListener(this);

        cardDue = (CardView)findViewById(R.id.cardDue);
        cardDue.setOnClickListener(this);

        cardEarn= (CardView)findViewById(R.id.cardEarn);
        cardEarn.setOnClickListener(this);

        cardRefer= (CardView)findViewById(R.id.cardRefer);
        cardRefer.setOnClickListener(this);

        //  cardMoneyReferral = (CardView)findViewById(R.id.cardMoneyReferral);
        //cardMoneyReferral.setOnClickListener(this);

        tvtearning=(TextView)findViewById(R.id.tvtearning);
        tvappdue=(TextView)findViewById(R.id.tvappdue);
        tvreview= (TextView)findViewById(R.id.tvreview);
        tvlsell= (TextView)findViewById(R.id.tvlsell);
        tvpay= (TextView)findViewById(R.id.tvtPay);
        tvlrent= (TextView)findViewById(R.id.tvlrent);
        tvreferralp= (TextView)findViewById(R.id.tvreferralp);

        tvreferralp.setOnClickListener(this);
        tvtearning.setOnClickListener(this);
        tvappdue.setOnClickListener(this);
        // tAppDue=tvappdue.getText().toString();

        sendDue = (Button)findViewById(R.id.sendDue);
        sendDue.setOnClickListener(this);

     //   setData();
        initInstancesDrawer();
        getUserData();
        setUserData();
    }

    private void setUserData() {

        if(uImage1.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(profileImage);
        }


        tvuName.setText(uName1);
        tvsid.setText(Sopnoid1);

        new JSONAsyncTask().execute(url);

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

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentEarnPoint.this);

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
                nameValuePairs.add(new BasicNameValuePair("MobileNumber",uMobile1));
                nameValuePairs.add(new BasicNameValuePair("Password",uPass1));
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


                        Sid1= object.getString("sopnoid");
                        uType1 = object.getString("userType");
                        uVerify1 = object.getString("verify");


                        tEarning=object.getString("totalEarning");
                        tAppDue=object.getString("appDue");
                        totalPay=object.getString("totalPay");
                        tReview=object.getString("totalReview");
                        tSell=object.getString("totalSell");
                        tRent=object.getString("totalRent");
                        tRefer=object.getString("totalReferral");


                        Log.e("success", Sid1);
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
                Toast.makeText(AgentEarnPoint.this, "Unable to send data to server", Toast.LENGTH_LONG).show();
            }
        }

        private void setData() {
            if(Sid1.equals("")){
                Toast.makeText(AgentEarnPoint.this, "Something is Wrong", Toast.LENGTH_LONG).show();
            } else{
                tvtearning.setText(tEarning);
                tvappdue.setText(tAppDue);
                tvreview.setText(tReview);
                tvlsell.setText(tSell);
                tvlrent.setText(tRent);
                tvreferralp.setText(tRefer);
                tvpay.setText(totalPay);
            }



        }


    }

    private void setDataw() {
        //    TextView tvtearning, tvappdue, tvreview, tvlsell, tvlrent, tvreferralp;
     /**   tEarning="2400";
        tAppDue="400";
        tReview="0";
        tSell="5";
        tRent="3";
        tRefer="10";

        tvtearning.setText(tEarning);
        tvappdue.setText(tAppDue);
        tvreview.setText(tReview);
        tvlsell.setText(tSell);
        tvlrent.setText(tRent);
      **///  tvreferralp.setText(tRefer);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.cardDue:
                if(tAppDue=="0"){
                    Toast.makeText(AgentEarnPoint.this,"You are not eligable to withreaw this amount", Toast.LENGTH_LONG).show();
                }else {
                    Intent sendd = new Intent(this, AgentMoneySend.class);
                    sendd.putExtra("appDue", tAppDue);
                    startActivity(sendd);
                }
                break;
            case R.id.sendDue:
                if(tAppDue=="0"){
                    Toast.makeText(AgentEarnPoint.this,"You are not eligable to send this amount", Toast.LENGTH_LONG).show();
                }else {
                    Intent send = new Intent(this, AgentMoneySend.class);
                    send.putExtra("appDue", tAppDue);
                    startActivity(send);
                }
                break;
            case R.id.tvappdue:
                if(tAppDue=="0"){
                    Toast.makeText(AgentEarnPoint.this,"You are not eligable to send this amount", Toast.LENGTH_LONG).show();
                }else {
                    Intent senda = new Intent(this, AgentMoneySend.class);
                    senda.putExtra("appDue", tAppDue);
                    startActivity(senda);
                }
                break;
            case R.id.tvtearning:
                //  Intent report = new Intent(this, MoneyReport.class);
                //startActivity(report);
                break;
            case R.id.cardEarn:
                //  Intent reportc = new Intent(this, MoneyReport.class);
                //startActivity(reportc);
                break;
            case R.id.cardRefer:

                Intent referc = new Intent(this, AgentMoneyRefer.class);
                //refer.putExtra("appDue",tAppDue);
                startActivity(referc);


                break;

            case R.id.tvreferralp:

                Intent refer = new Intent(this, AgentMoneyRefer.class);
                //refer.putExtra("appDue",tAppDue);
                startActivity(refer);


                break;

        }
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("Earn Point");

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

     CollapsingToolbarLayout  collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
                AgentDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void updateMessageStatus() {
        if(tMessage.equals("")) {
            msgMenu.setIcon(R.drawable.men_email);
        } else{
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

        if (id == R.id.action_msg) {
            Intent intent = new Intent(getApplicationContext(),
                    AgentMessageAll.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

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
