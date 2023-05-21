package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.app.AlertDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AllTenantRequestDetails extends AppCompatActivity {
    ImageView profileImage;
    Button btnAccept, btnDecline;
    Typeface custom_font;
    TextView tvuName, tvMsgK, tvsid, uAddress, uLocation, uMobile, uEmail, uProfession, uNID, uCompany, uNationality;
    String  tMessage=null; String tShop, uProfession1, uNationality1, uNID1, uCompany1, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String  tMessage2=null; String tShop2, ReqState12, uProfession12, uNationality12, uNID12, uCompany12, uImage12, Sopnoid12,uReferId12, uLocation12, uRentId12, uSellAd12, uName12, uEmail12, uType12, uVerify12, uMobile12,uAddress12, uPass12;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Context context;
    MenuItem msgMenu;
    final String urlRecord = "http://barivara.com/api/update_request.php";
    String CurrentDate;
    String Request="";
    LinearLayout layerEmail, layerPhone, layerProfession, layerCompany, layerNID, layerPassport, layerDistrict, layerNation, layerThana, layerAddress;
    CardView cardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tenant_request_details);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        initInstancesDrawer();
        getUserData();

        layerAddress = (LinearLayout)findViewById(R.id.layerAddress);
        layerAddress.setVisibility(View.GONE);
        layerNation = (LinearLayout)findViewById(R.id.layerNational);
        layerNation.setVisibility(View.GONE);
        layerThana = (LinearLayout)findViewById(R.id.layerThana);
        layerThana.setVisibility(View.GONE);
        layerCompany = (LinearLayout)findViewById(R.id.layerCompany);
        layerCompany.setVisibility(View.GONE);
        layerDistrict = (LinearLayout)findViewById(R.id.layerDistrict);
        layerDistrict.setVisibility(View.GONE);
        layerNID = (LinearLayout)findViewById(R.id.layerNID);
        layerNID.setVisibility(View.GONE);
        layerPhone = (LinearLayout)findViewById(R.id.layerPhone);
        layerPhone.setVisibility(View.GONE);
        layerProfession = (LinearLayout)findViewById(R.id.layerProf);
        layerProfession.setVisibility(View.GONE);
        layerEmail = (LinearLayout)findViewById(R.id.layerEmail);
        layerEmail.setVisibility(View.GONE);
        cardButton = (CardView) findViewById(R.id.cardButton);
        cardButton.setVisibility(View.VISIBLE);



        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
        profileImage = (ImageView)findViewById(R.id.imageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeFullPic();
            }


        });

        uNID = (TextView)findViewById(R.id.uNID);
        uProfession = (TextView)findViewById(R.id.uProfession);
        uProfession.setTypeface(tf);

        uNationality = (TextView)findViewById(R.id.uNationality);
        uNationality.setTypeface(tf);

        uCompany = (TextView)findViewById(R.id.uCompany);
        uCompany.setTypeface(tf);


        tvuName = (TextView)findViewById(R.id.tvuName);
        tvMsgK = (TextView)findViewById(R.id.tvMsgK);
        tvsid = (TextView)findViewById(R.id.tvsid);
        tvuName.setTypeface(tf);
        uMobile = (TextView)findViewById(R.id.uMobile);
        uLocation=(TextView)findViewById(R.id.uLocation);
        uLocation.setTypeface(tf);
        uEmail= (TextView)findViewById(R.id.uEmail);
        uAddress = (TextView)findViewById(R.id.uAddress);
        uAddress.setTypeface(tf);

        btnDecline = (Button)findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Request="-1";
                sendRequest();

            }
        });

        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Request="1";
                sendRequest();
            }
        });

        setUserData();
    }



    private void sendRequest() {


        DateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss", Locale.ENGLISH);
        CurrentDate = df.format(Calendar.getInstance().getTime());


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("request", Request));
                nameValuePairs.add(new BasicNameValuePair("tenantId", Sopnoid12 ));
                nameValuePairs.add(new BasicNameValuePair("LandLordId", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("TentId", uRentId12));
                nameValuePairs.add(new BasicNameValuePair("LandLordMobile", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("TenantMobile", uMobile12));
                nameValuePairs.add(new BasicNameValuePair("currentDate", CurrentDate));




                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(urlRecord);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                Log.e("Data", "Success");
                return "";

            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Log.e("Data", "Success");
              //   Toast.makeText(AllTenantRequestDetails.this, "Success", Toast.LENGTH_LONG).show();
                /**     Intent intent = new Intent(getApplicationContext(),
                 AgentDashboard.class);
                 intent.putExtra("Password", uPass1);
                 intent.putExtra("MobileNumber", uMobile1);
                 startActivity(intent);
                 **/

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(Request, Sopnoid12, Sopnoid1, uRentId12, uMobile1, uMobile12, CurrentDate);

    }



    private void seeFullPic() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_pic_dialogue, null);
        TouchImageView prImage = (TouchImageView) dialogView.findViewById(R.id.my_image);

        if(uImage1.equals ("")){
            prImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).into(prImage);
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



    private void setUserData() {
        Intent i = getIntent();
        uRentId12 = i.getStringExtra("tentId");
        uReferId12 = i.getStringExtra("referid");
        uVerify12 = i.getStringExtra("verify");
        uType12 = i.getStringExtra("type");
        uProfession12 = i.getStringExtra("profession");
        uImage12 = i.getStringExtra("img");
        uCompany12 = i.getStringExtra("company");
        uName12 = i.getStringExtra("uName");
        uNID12 = i.getStringExtra("nid");
        uNationality12 = i.getStringExtra("nationality");
        Sopnoid12 = i.getStringExtra("usopnoid");
        uMobile12 = i.getStringExtra("mobile");
        uLocation12 = i.getStringExtra("district");
        uEmail12 = i.getStringExtra("email");
        uAddress12 = i.getStringExtra("address");
        ReqState12 = i.getStringExtra("ReqState");

        if(ReqState12.equals("0")){
            tvMsgK.setVisibility(View.VISIBLE);
            tvMsgK.setText("This Request is Pending");
            tvMsgK.setTextColor(Color.GREEN);
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
        }else if(ReqState12.equals("1")){
            tvMsgK.setVisibility(View.VISIBLE);
            tvMsgK.setText("This Request is Accepted");
            tvMsgK.setTextColor(Color.GREEN);
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
        }else if(ReqState12.equals("-1")){
            tvMsgK.setVisibility(View.VISIBLE);
            tvMsgK.setText("This Request is Decline");
            tvMsgK.setTextColor(Color.DKGRAY);
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
        }else{
            btnAccept.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.VISIBLE);        }



        if(uImage12.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(profileImage);
        }


        tvuName.setText(uName12);
        tvsid.setText(Sopnoid12);



        if(uProfession12.equals("")){
            layerProfession.setVisibility(View.GONE);
        }else{
            uProfession.setText(uProfession12);
            layerProfession.setVisibility(View.VISIBLE);

        }
        if(uCompany12.equals("")){
            layerCompany.setVisibility(View.GONE);
        }else{
            uCompany.setText(uCompany12);
            layerCompany.setVisibility(View.VISIBLE);

        }
        if(uNationality12.equals("")){
            layerNation.setVisibility(View.GONE);
        }else{
            uNationality.setText(uNationality12);
            layerNation.setVisibility(View.VISIBLE);

        }
        if(uMobile12.equals("")){
            layerPhone.setVisibility(View.GONE);
        }else{
            uMobile.setText(uMobile12);
            layerPhone.setVisibility(View.VISIBLE);

        } if(uLocation12.equals("")){
            layerDistrict.setVisibility(View.GONE);
        }else{
            uLocation.setText(uLocation12);
            layerDistrict.setVisibility(View.VISIBLE);

        } if(uEmail12.equals("")){
            layerEmail.setVisibility(View.GONE);
        }else{
            uEmail.setText(uEmail12);
            layerEmail.setVisibility(View.VISIBLE);

        }
        if(uAddress12.equals("")){
            layerAddress.setVisibility(View.GONE);
        }else{
            uAddress.setText(uAddress12);
            layerAddress.setVisibility(View.VISIBLE);

        }if(uNID12.equals("")){
            layerNID.setVisibility(View.GONE);
        }else{
            layerNID.setVisibility(View.VISIBLE);

            uNID.setText(uNID12);

        }

    }

    private void getUserData() {
        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {

            uProfession1  = prf.getString("uProfession1", null);
            uCompany1 = prf.getString("uCompany1", null);
            uNID1 = prf.getString("uNID1", null);
            uNationality1 = prf.getString("uNationality1", null);

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
        toolh.setText("Profile");
        try {
            URL url = new URL(uImage1);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Drawable image2 = new BitmapDrawable(context.getResources(), bitmap);
            //  YOUR_LAYOUT.image.setBackground(image);
            //   toolh.setCompoundDrawablesWithIntrinsicBounds(image2, 0, 0, 0);
            //  toolh.setBackgroundDrawable(image2);
            toolh.setCompoundDrawables(image2, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
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


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


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
