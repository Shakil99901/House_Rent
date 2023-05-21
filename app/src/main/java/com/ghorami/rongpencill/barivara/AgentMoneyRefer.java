package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.facebook.share.BuildConfig;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.os.Build.VERSION.SDK_INT;

public class AgentMoneyRefer extends AppCompatActivity implements View.OnClickListener {

    String url = "http://barivara.com/api/getUReferDetails.php";
    String urlpost = "http://barivara.com/api/aeReferPoint.php";


    //getUReferDetails.php
TextView tvRcode, tvTsend, tvARefer, tvRPoint, tvRWithdraw, tvReferb;
    String tvRcode1, tvTsend1, Sid1, tvARefer1, tvRPoint1, tvRWithdraw1, tvReferb1, mAmount, sendTo;
    Button btnRefer, btnWithdraw, btnGo, btnBack;
    String  tMessage=null; String tShop, uReferId1, message, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
Typeface custom_font;
    EditText etRefer;
    Context context;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_money_refer);

        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/NotoSansBengali.ttf");
        initInstancesDrawer();
        getUserData();


        etRefer = (EditText)findViewById(R.id.etRefer);
        tvRcode = (TextView)findViewById(R.id.tvRcode);
        tvTsend = (TextView)findViewById(R.id.tvTsend);
        tvARefer = (TextView)findViewById(R.id.tvARefer);
        tvRPoint = (TextView)findViewById(R.id.tvRPoint);
        tvRWithdraw = (TextView)findViewById(R.id.tvRWithdraw);
        tvReferb = (TextView)findViewById(R.id.tvReferb);

        btnRefer = (Button)findViewById(R.id.btnRefer);
        btnRefer.setOnClickListener(this);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnWithdraw = (Button)findViewById(R.id.btnWithdraw);
        btnWithdraw.setOnClickListener(this);

        btnGo = (Button)findViewById(R.id.btnGo);
        btnGo.setOnClickListener(this);
setUserData();
    }

    private void setUserData() {
        if(uMobile1.equals("")){
            Toast.makeText(getApplicationContext(), "your session is expired. Please login again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else{
            new JSONAsyncTask().execute(url);
        }
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
            uReferId1 = prf.getString("uReferId1", null);
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
        toolh.setText("Referral");

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


    CollapsingToolbarLayout    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentMoneyRefer.this);

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
                     //   String tvRcode1, tvTsend1, Sid1, tvARefer1, tvRPoint1, tvRWithdraw1, tvReferb1, mAmount;


                       // tvRcode1=object.getString("ReferCode");
                        tvTsend1=object.getString("totalSend");
                        tvARefer1=object.getString("totalActive");
                        tvRPoint1=object.getString("totalPoint");
                        tvRWithdraw1=object.getString("Withdraw");
                        tvReferb1=object.getString("totalAmount");


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
                Toast.makeText(AgentMoneyRefer.this, "Unable to send data to server", Toast.LENGTH_LONG).show();
            }
        }

        private void setData() {
            if(Sid1.equals("")){
                Toast.makeText(AgentMoneyRefer.this, "Something is Wrong", Toast.LENGTH_LONG).show();
            } else{
                //   String tvRcode1, tvTsend1, Sid1, tvARefer1, tvRPoint1, tvRWithdraw1, tvReferb1, mAmount;
                tvRcode.setText(uReferId1);
                tvTsend.setText(tvTsend1);
                tvARefer.setText(tvARefer1);
                tvRPoint.setText(tvRPoint1);
                tvRWithdraw.setText(tvRWithdraw1);
                tvReferb.setText(uReferId1);
            }



        }


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
               AgentEarnPoint.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRefer:
                //  Intent in = new Intent(this, MoneyReport.class);
                //startActivity(in);

                sendTo = etRefer.getText().toString();
                if (sendTo.equals("")){
                    Toast.makeText(AgentMoneyRefer.this,"Please Enter a mobile number fast", Toast.LENGTH_LONG).show();

                }else {
                //    new JSONAsyncTask2().execute(urlpost);
                  //  ShareRef();
                    whatsApp();
                   // ShareRefer();
                }
                break;
            case R.id.btnWithdraw:
                mAmount=tvReferb.getText().toString();
                if(mAmount!="500"){
                    Toast.makeText(AgentMoneyRefer.this,"You are not eligable to withreaw this amount", Toast.LENGTH_LONG).show();
                }else {
                   // sendReqtoServer();
                    Intent in = new Intent(this, AgentMoneyWithdraw.class);
                    //   String tvRcode1, tvTsend1, Sid1, tvARefer1, tvRPoint1, tvRWithdraw1, tvReferb1, mAmount;

                    in.putExtra("mAmount", mAmount);
                    in.putExtra("tvRWithdraw1", tvRWithdraw1);
                    in.putExtra("tvRPoint1", tvRPoint1);

                    startActivity(in);
                }
                // ShareRefer();
                break;


            case R.id.btnBack:
                finish();
                // ShareRefer();
                break;
        }
    }

    private void sendReqtoServer() {

    }


    /**
    private void ShareRefer() {


        String v4=("https://goo.gl/5AgPiq").toString();
        String v1=("#Ghorami").toString();
        String v2=("Share your space & Earn Money").toString();
        String v3=("in Bangla").toString();
        sendTo = etRefer.getText().toString();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n"+v3;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, tvRcode1+"\n"+v2+"\n"+v4+"\n"+v1);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

        new JSONAsyncTask2().execute(urlpost);
    }
    **/

    public void  whatsApp() {
      //  String iCong = "android.resource://com.example.mm/drawable/ic_launcher";
        String smsNumber = sendTo;
        String v4=("https://play.google.com/store/apps/details?id=com.ghorami.rongpencill.barivara").toString();
        String v1=("#barivara").toString();
        String v2=("Share your space & Earn Money").toString();
        String v3=("in Bangla").toString();
        String smsText = "refer code-"+ uReferId1+"\n"+v2+"\n"+v4+"\n"+v1;


        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            String toNumber = sendTo; // contains spaces.
            toNumber = toNumber.replace("+", "").replace(" ", "");

         /**   Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                    b, "barivara", null);
            Uri imageUri =  Uri.parse(path);
**/

          //  sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            //sendIntent.putExtra(Intent.EXTRA_TEXT, smsText);
// create file from drawable image
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher_round);

            File filesDir = getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir, "barivara.png");

            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                bm.compress(Bitmap.CompressFormat.PNG, 100, os); // 100% quality
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }
          //  String imageFile = ("android.resource://com.barivara.rongpencill.barivara/" + R.drawable.nipa);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // set flag to give temporary permission to external app to use your FileProvider
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, imageFile);

            sendIntent.putExtra(Intent.EXTRA_TEXT, smsText);
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            sendIntent.setType("image/jpeg");
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(sendIntent, "send"));



        } else {
        /**    Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);

         **/
            Uri uri = Uri.parse("smsto:" + sendTo);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", smsText);
            startActivity(intent);

        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void ShareRefer() {


        String v4=("https://play.google.com/store/apps/details?id=com.ghorami.rongpencill.barivara").toString();
        String v1=("#barivara").toString();
        String v2=("Share your space & Earn Money").toString();
        String v3=("in Bangla").toString();

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n"+v3;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, tvRcode1+"\n"+v2+"\n"+v4+"\n"+v1);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            sharingIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(sendTo));//phone number without "+" prefix
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
    }



    class JSONAsyncTask2 extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentMoneyRefer.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... urlst) {

            try {



                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("userPass", uPass1));
                nameValuePairs.add(new BasicNameValuePair("Sopnoid1", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("Type", "agent"));
                nameValuePairs.add(new BasicNameValuePair("referCode", uReferId1));
                nameValuePairs.add(new BasicNameValuePair("tvTsend1", tvTsend1));
                nameValuePairs.add(new BasicNameValuePair("sned", "1"));
                nameValuePairs.add(new BasicNameValuePair("sendTo", sendTo));

                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlpost);
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
            setDatak();
            //adapter.notifyDataSetChanged();

            if (result == null)
                Toast.makeText(AgentMoneyRefer.this, "Unable to send data", Toast.LENGTH_LONG).show();

        }

        private void setDatak() {
            if(message.equals("success")){
              //  sendMail();
                Intent Classr = new Intent(AgentMoneyRefer.this, AgentMoneyRefer.class);
                startActivity(Classr);
                //new JSONAsyncTask().execute(url);

            } else {

                Toast.makeText(getApplicationContext(),
                       "woops! something is wrong".toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

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

            if(uType1.equals("agent")){
                Intent intent = new Intent(getApplicationContext(),
                        AgentDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }else if(uType1.equals("general")){
                Intent intent = new Intent(getApplicationContext(),
                        UserHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


            return true;
        }

        if (id == R.id.menue) {


            if(uType1.equals("agent")){
                Intent intent = new Intent(getApplicationContext(),
                        AgentMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }else if(uType1.equals("general")){
                Intent intent = new Intent(getApplicationContext(),
                        UserMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}