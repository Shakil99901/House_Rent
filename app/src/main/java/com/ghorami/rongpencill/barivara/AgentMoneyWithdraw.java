package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.io.IOException;
import java.util.ArrayList;

public class AgentMoneyWithdraw extends AppCompatActivity implements View.OnClickListener {

    TextView mBkash;
    EditText mAmount, mKashCode, mPassword;
    Button btnMoneyOk, btnMoneyCancel;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String tMessage=null; String tShop, MoneyAmount, message, KashCode, sendAmount, userId, userName, userPassword, userPhone;

    String url = "http://barivara.com/api/aeWiPoint.php";
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_money_withdraw);

        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        initInstancesDrawer();
        getUserData();

        mBkash = (TextView)findViewById(R.id.mBkash);
        mAmount = (EditText)findViewById(R.id.mAmount);
       // mKashCode = (EditText)findViewById(R.id.mKashCode);
        mPassword = (EditText)findViewById(R.id.mPassword);

        Button btnMoneyOk = (Button)findViewById(R.id.btnMoneyOk);
        btnMoneyOk.setOnClickListener(this);
        btnMoneyCancel = (Button)findViewById(R.id.btnMoneyCancel);
        btnMoneyCancel.setOnClickListener(this);

        Intent i = getIntent();
        MoneyAmount = i.getExtras().getString("appDue");

        if(MoneyAmount!=""){
            mAmount.setText(MoneyAmount);
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
        toolh.setText("Withdraw Money");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnMoneyOk:
                sendAmount= mAmount.getText().toString();
                userPassword = mPassword.getText().toString();
            //    KashCode = mKashCode.getText().toString();
                checkValidation();
                // postDataToServer();

                break;
            case R.id.btnMoneyCancel:
                finish();
                break;
        }

    }

    private void checkValidation() {

        if(sendAmount.equals("")) {
            mAmount.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
      /**  if(KashCode.equals("")) {
            mKashCode.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
**/
        if (mAmount.equals("")|| userPassword.equals("")){
            Toast.makeText(AgentMoneyWithdraw.this, "All fields are required.", Toast.LENGTH_LONG).show();
        }else{
            postDataToServer();
        }

        //    else
        //  Toast.makeText(c, "Do SignUp.", Toast.LENGTH_SHORT)
        //      .show();
        //   upload();
        //  new PostHome().execute();

    }

    private void postDataToServer() {
        if (userPassword==uPass1){
            new JSONAsyncTask().execute(url);
        }  else{
            Toast.makeText(getApplicationContext(), "Your Password is wrong. please try again", Toast.LENGTH_LONG).show();
            //moveTaskToBack(true);
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);


        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentMoneyWithdraw.this);


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
                nameValuePairs.add(new BasicNameValuePair("KashCode", KashCode));
                nameValuePairs.add(new BasicNameValuePair("sendAmount", sendAmount));
                nameValuePairs.add(new BasicNameValuePair("userPassword", userPassword));

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
                Toast.makeText(AgentMoneyWithdraw.this, "Unable to send data", Toast.LENGTH_LONG).show();

        }

        private void setData() {
            if(message.equals("success")){
                sendMail();
            //    Intent Classr = new Intent(AgentMoneyWithdraw.this, AgentEarnPoint.class);
              //  startActivity(Classr);
                finish();
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }

    private void sendMail() {

        //  Snackbar snackbar = Snackbar.make(R.id.rootLayout,"ola",Snackbar.LENGTH_LONG);

        //  Snackbar.make(view, "Send Mail", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        String v2=("MyActivity"+ "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease).toString();

        String[] TO = {"tpucse@gmail.com"};
        Uri uri = Uri.parse("mailto:tpucse@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", "#barivara- Send Money report")
                .appendQueryParameter("body", "Please leave your feedback here.\nPlease do not edit the information below:\n"+sendAmount+"\n"+uName1+uMobile1+"\n"+v2)
                .build();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        startActivity(Intent.createChooser(emailIntent, "Send money report..."));

    }
    /**
     @Override
     public void onBackPressed(){

     Intent intent = new Intent(getApplicationContext(),
     AgentEarnPoint.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
     | Intent.FLAG_ACTIVITY_SINGLE_TOP);
     startActivity(intent);

     }
     **/
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


            if (uType1.equals("agent")) {
                Intent intent = new Intent(getApplicationContext(),
                        AgentMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (uType1.equals("general")) {
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
