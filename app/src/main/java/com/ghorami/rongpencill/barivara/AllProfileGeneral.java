package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AllProfileGeneral extends AppCompatActivity {
    Button btnAccept, btnDecline;
    String Request;
    ImageView profileImage;
    Button upload;
    Typeface custom_font;
    TextView tvuName, tvsid, uAddress, uLocation, uMobile, uEmail, uProfession, uNID, uCompany, uNationality;
    String  tMessage=null; String tShop, uProfession1,LandLordID, TenantId, uNationality1, uNID1, uCompany1, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Context context;
    MenuItem msgMenu;
    LinearLayout layerEmail, layerPhone, layerProfession, layerCompany, layerNID, layerPassport, layerDistrict, layerNation, layerThana, layerAddress;
    CardView cardButton;
    String  tMessage2=null; String uPropertySl, tShop2, uProfession12, uNationality12, uNID12, uCompany12, uImage12, Sopnoid12,uReferId12, uLocation12, uRentId12, uSellAd12, uName12, uEmail12, uType12, uVerify12, uMobile12,uAddress12, uPass12;
    String urlReport = "http://barivara.com/api/getAllurlReport.php";
    String urlagent = "http://barivara.com/api/getAllGeneralProfile.php";

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
        cardButton.setVisibility(View.GONE);


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
        tvsid = (TextView)findViewById(R.id.tvsid);
        tvsid.setVisibility(View.GONE);
        tvuName.setTypeface(tf);
        uMobile = (TextView)findViewById(R.id.uMobile);
        uLocation=(TextView)findViewById(R.id.uLocation);
        uLocation.setTypeface(tf);
        uEmail= (TextView)findViewById(R.id.uEmail);
        uAddress = (TextView)findViewById(R.id.uAddress);
        uAddress.setTypeface(tf);


        btnDecline = (Button)findViewById(R.id.btnDecline);
        btnDecline.setVisibility(View.GONE);
        btnDecline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Request="-1";
           //     sendRequest();

            }
        });

        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnAccept.setVisibility(View.GONE);
        btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Request="1";
               // sendRequest();
            }
        });


        new JSONAsyncTaskProfile().execute(urlagent);
//       setUserData();
    }

    private void seeFullPic() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_pic_dialogue, null);
        TouchImageView prImage = (TouchImageView) dialogView.findViewById(R.id.my_image);

        if(uImage12.equals ("")){
            prImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage12).into(prImage);
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

    protected class JSONAsyncTaskProfile extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AllProfileGeneral.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //  pDialog.dismiss();
        }



        @Override
        protected Boolean doInBackground(String... urlsa) {
            try {
                Intent inter = getIntent();
                LandLordID = inter.getStringExtra("LandLordId");
               TenantId = inter.getStringExtra("tenantId");

                Log.e("Land", LandLordID);
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("LandLordID", LandLordID));
              //  nameValuePairs.add(new BasicNameValuePair("mobilenumber", uMobile1));
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
                    JSONArray jarray = jsono.getJSONArray("tenant_id");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject Jarray = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersiona tour = new AndroidVersiona();

                     //  tour.setTentID(object.getString("TentID"));
                       // tour.setTentStart(object.getString("TimeStamp"));
                        //tour.setTenantID(object.getString("TenantID"));

                        uImage12 = (Jarray.getString("uPicture"));
                        uName12 = (Jarray.getString("userName"));
                        uProfession12 =(Jarray.getString("uProfession"));
                        uCompany12 =(Jarray.getString("CompanyName"));
                        uNationality12 = (Jarray.getString("uNationality"));
                        //  uNID12 = (Jarray.getString("uNID"));
                        uEmail12 =(Jarray.getString("userEmail"));
                        // uMobile12 =(Jarray.getString("MobileNumber"));
                        Sopnoid12 =(Jarray.getString("SopnoID"));
                        uLocation12 = (Jarray.getString("pLocation"));
                        uAddress12 =(Jarray.getString("Address"));
                        uVerify12 =(Jarray.getString("verify"));
                        uType12 = (Jarray.getString("Type"));
                        uReferId12 =(Jarray.getString("uReferID"));

                    }
                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            if(Sopnoid12.isEmpty()||Sopnoid12.equals("")){
                Toast.makeText(getApplicationContext(), "Woops! something is wrong", Toast.LENGTH_LONG).show();

            }else{
                setUserData();

            }
            if (result == false){

            }
              //  Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }


    private void setUserData() {
        if(uImage12.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage12).transform(new CircleTransform()).into(profileImage);
        }


        tvuName.setText(uName12);
        tvsid.setText(Sopnoid12);
        tvsid.setVisibility(View.GONE);



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

        if(uLocation12.equals("")){
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
