package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ParseException;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AgentAllAddSell extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<AndroidVersion> tours;
    private ArrayList<AndroidVersiona> toursa;
    private ArrayList<AndroidVersion> toursad;

    private ArrayList<AndroidVersiona> rentAgent;
    private ArrayList<AndroidVersiona> rentAgentfamily;
    private ArrayList<AndroidVersiona> rentAgentothers;
    private ArrayList<AndroidVersiona> rentAgentsell;
    private ArrayList<AndroidVersiona> rentAgentgirls;
    private ArrayList<AndroidVersiona> rentAgentstudent;
    private ArrayList<AndroidVersiona> rentAgentwomen;
    private ArrayList<AndroidVersiona> rentAgentbachelor;

    AdapterAgentRent adapterAgentRentbachelor;
    AdapterAgentRent adapterAgentRentfamily;
    AdapterAgentRent adapterAgentRentothers;
    AdapterAgentRent adapterAgentRentsell;
    AdapterAgentRent adapterAgentRentgirls;
    AdapterAgentRent adapterAgentRentstudent;
    AdapterAgentRent adapterAgentRentwomen;
    AdapterAgentRent adapterAgentRent;
    private BasicRentAdapterBn adapter;

    private AdapterAgent adapterAgent;

    final Context context = this;
    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    String url = "http://barivara.com/api/asell.php?t_spot=bachelor&lang=BN&caten=sell";
    String url2 = "http://barivara.com/api/asell.php?t_spot=family&lang=BN&caten=sell";
    String url3 = "http://barivara.com/api/test.php";
    String urlagent = "http://barivara.com/api/getAllAgent.php";
    String urlbachelor = "http://barivara.com/api/arent_all.php";
    String urlfamily = "http://barivara.com/api/asell.php?t_spot=family&lang=BN&caten=sell";
    String urlgirls = "http://barivara.com/api/asell.php?t_spot=gstudent&lang=BN&caten=sell";
    String urlstudent = "http://barivara.com/api/arent.php?t_spot=student&lang=BN&caten=sell";
    String urlothers = "http://barivara.com/api/arent_all.php";

    ImageView imTop;


    Button btnWomen, btnBachelor, btnAgent, btnFamily, btnGirls, btnStudent, btnOthers, btnSellto;
    TextView tvBachelor, tvAgent, tvFamily, tvGirls, tvStudent, tvWomen, tvOthers;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_all_add_sell);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        SwipeRefreshLayout refreshView = (SwipeRefreshLayout)findViewById(R.id.refreshView);
        refreshView.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Load data to your RecyclerView
                adapterAgentRentbachelor.notifyDataSetChanged();
                adapterAgentRentgirls.notifyDataSetChanged();
                adapterAgentRentwomen.notifyDataSetChanged();
                adapterAgentRentstudent.notifyDataSetChanged();
                adapterAgentRentsell.notifyDataSetChanged();
                adapterAgentRentothers.notifyDataSetChanged();
                adapterAgentRentfamily.notifyDataSetChanged();


                adapterAgentRent.notifyDataSetChanged();


                adapterAgent.notifyDataSetChanged();



                toursad.clear();
                rentAgent.clear();
                rentAgentbachelor.clear();
                rentAgentgirls.clear();
                rentAgentwomen.clear();
                rentAgentstudent.clear();
                rentAgentsell.clear();
                rentAgentothers.clear();
                rentAgentfamily.clear();


                refreshData();
            }
        });

        getUserData();
        setUserData();


        btnFamily = (Button)findViewById(R.id.btnFamily);
        btnFamily.setOnClickListener(this);

        btnOthers = (Button)findViewById(R.id.btnRento);
        btnOthers.setOnClickListener(this);

        btnSellto = (Button)findViewById(R.id.btnSellto);
        btnSellto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
                intent1.putExtra("spot","all");
                intent1.putExtra("caten","sell");
                startActivity(intent1);
            }
        });




        toursad = new ArrayList<AndroidVersion>();
        rentAgent = new ArrayList<AndroidVersiona>();
        rentAgentbachelor = new ArrayList<AndroidVersiona>();
        rentAgentgirls = new ArrayList<AndroidVersiona>();
        rentAgentwomen = new ArrayList<AndroidVersiona>();
        rentAgentstudent = new ArrayList<AndroidVersiona>();
        rentAgentsell = new ArrayList<AndroidVersiona>();
        rentAgentothers = new ArrayList<AndroidVersiona>();
        rentAgentfamily = new ArrayList<AndroidVersiona>();

        adapterAgentRentbachelor = new AdapterAgentRent(AgentAllAddSell.this, rentAgentbachelor);
        adapterAgentRentgirls = new AdapterAgentRent(AgentAllAddSell.this, rentAgentgirls);
        adapterAgentRentwomen = new AdapterAgentRent(AgentAllAddSell.this, rentAgentwomen);
        adapterAgentRentstudent = new AdapterAgentRent(AgentAllAddSell.this, rentAgentstudent);
        adapterAgentRentsell = new AdapterAgentRent(AgentAllAddSell.this, rentAgentsell);
        adapterAgentRentothers = new AdapterAgentRent(AgentAllAddSell.this, rentAgentothers);
        adapterAgentRentfamily = new AdapterAgentRent(AgentAllAddSell.this, rentAgentfamily);

        adapterAgentRent = new AdapterAgentRent(AgentAllAddSell.this, rentAgent);


        adapterAgent = new AdapterAgent(AgentAllAddSell.this,toursad);



        recyclerFamily = (RecyclerView) findViewById(R.id.recyclerFamily);
        recyclerFamily.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerFamily.setHasFixedSize(true);
        recyclerFamily.setAdapter(adapterAgentRentfamily);



        recyclerViewSell = (RecyclerView) findViewById(R.id.recyclerViewRent);
        recyclerViewSell.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewSell.setHasFixedSize(true);
        recyclerViewSell.setAdapter(adapterAgentRentothers);


    //    new JSONAsyncTask2().execute(url2);

//        new JSONAsyncTask3().execute(url3);
  //      new JSONAsyncTask1().execute(url);

        ParseAllData();
    }

    private void refreshData() {

        ParseAllData();
    }


    private void ParseAllData() {

        ProgressDialog pDialog = new ProgressDialog(AgentAllAddSell.this);

            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //  pDialog.dismiss();


            new JSONAsyncRentfamily().execute(urlfamily);

            new JSONAsyncRentOthers().execute(urlothers);

        pDialog.dismiss();

    }

    @Override
    public void onClick(View  v) {

        switch (v.getId()) {

            case R.id.btnFamily:
              //  Sfamily();
                break;

            case R.id.btnRento:
                Sothers();
                break;


        }
    }


    private void Sfamily() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","family");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sothers() {


        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","all");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }




    protected class JSONAsyncRentfamily extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlfamily);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    //  HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsonoj = new JSONObject(data);
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();
                        tourk.setRent(objectj.getString("rent"));
                        tourk.setAdvance(objectj.getString("advance"));
                        tourk.setUtility(objectj.getString("utility"));

                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setFloor(objectj.getString("floor"));
                        tourk.setTotalsize(objectj.getString("totalsize"));

                        tourk.setPimage(objectj.getString("pimage"));

                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTitle(objectj.getString("title"));

                        tourk.setSinfo(objectj.getString("sinfo"));
                        tourk.setHome_type(objectj.getString("home_type"));
                        tourk.setRental_type(objectj.getString("rental_type"));
                        tourk.setFacility(objectj.getString("facility"));


                        tourk.setOwner(objectj.getString("owner"));
                        tourk.setPhone(objectj.getString("phone"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setCity(objectj.getString("city"));
                        tourk.setAddress(objectj.getString("address"));

                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setAirport(objectj.getString("sad_type"));

                        // JSONObject objectk = new JSONObject(result);
                        JSONObject Jarray  = objectj.getJSONObject("Poster");

                        for (int l = 0; l < Jarray.length(); l++)
                        {
                            tourk.setPosterPic(Jarray.getString("posterPic"));
                            tourk.setPosterName(Jarray.getString("posterName"));
                            tourk.setPosterSID(Jarray.getString("posterSID"));
                            tourk.setPosterMobile(Jarray.getString("posterMobile"));
                            tourk.setPosterCity(Jarray.getString("posterCity"));
                            tourk.setPosterEmail(Jarray.getString("posterEmail"));
                            // tour.setTenantID(Jarray.getString("TenantID"));

                        }

                        rentAgentfamily.add(tourk);
                    }
                    return true;
                }

                //------------------>>

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
            //  setData();
            adapterAgentRentfamily.notifyDataSetChanged();
            if (result == false){

            }
           //     Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }

    protected class JSONAsyncRentOthers extends AsyncTask<String, Void, Boolean> {



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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();
                        tourk.setRent(objectj.getString("rent"));
                        tourk.setAdvance(objectj.getString("advance"));
                        tourk.setUtility(objectj.getString("utility"));

                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setFloor(objectj.getString("floor"));
                        tourk.setTotalsize(objectj.getString("totalsize"));

                        tourk.setPimage(objectj.getString("pimage"));

                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTitle(objectj.getString("title"));

                        tourk.setSinfo(objectj.getString("sinfo"));
                        tourk.setHome_type(objectj.getString("home_type"));
                        tourk.setRental_type(objectj.getString("rental_type"));
                        tourk.setFacility(objectj.getString("facility"));


                        tourk.setOwner(objectj.getString("owner"));
                        tourk.setPhone(objectj.getString("phone"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setCity(objectj.getString("city"));
                        tourk.setAddress(objectj.getString("address"));

                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setAirport(objectj.getString("sad_type"));

                        // JSONObject objectk = new JSONObject(result);
                        JSONObject Jarray  = objectj.getJSONObject("Poster");

                        for (int l = 0; l < Jarray.length(); l++)
                        {
                            tourk.setPosterPic(Jarray.getString("posterPic"));
                            tourk.setPosterName(Jarray.getString("posterName"));
                            tourk.setPosterSID(Jarray.getString("posterSID"));
                            tourk.setPosterMobile(Jarray.getString("posterMobile"));
                            tourk.setPosterCity(Jarray.getString("posterCity"));
                            tourk.setPosterEmail(Jarray.getString("posterEmail"));
                            // tour.setTenantID(Jarray.getString("TenantID"));

                        }
                        rentAgentothers.add(tourk);
                    }
                    return true;
                }

                //------------------>>

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
            //  setData();
            adapterAgentRentothers.notifyDataSetChanged();
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

        }
    }







    private void setUserData() {

    /**    if(uImage1.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
            ivprofile.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.with(this).load(uImage1).transform(new CircleTransform()).into(profileImage);
            Picasso.with(this).load(uImage1).transform(new CircleTransform()).into(ivprofile);
        }

        if(uRentAd1.equals ("")){
            tvRentA.setVisibility(View.GONE);
        } else {
            tvRentA.setText(uRentAd1);
            tvAdL.setText(uRentAd1);
            tvRentA.setVisibility(View.VISIBLE);
        }

        if(uSellAd1.equals ("")){
            tvSellA.setVisibility(View.GONE);
        } else {
            tvSellA.setText(uSellAd1);
            tvsellAd.setText(uSellAd1);
            tvSellA.setVisibility(View.VISIBLE);
        }

        if(uVerify1.equals ("")){
            iverify.setBackgroundResource(R.drawable.cerclebackgroundgray);
            tvVerify.setText("not verified");

        } else {
            iverify.setBackgroundResource(R.drawable.cerclebackgroundblue);
            tvVerify.setText("verified");
        }


        tvuName.setText(uName1);
        tvsid.setText(Sopnoid1);

**/
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
            initInstancesDrawer();

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
        toolh.setText(R.string.Sell);
       /** ImageView proPic = (ImageView)findViewById(R.id.userImage);



        if(uImage1.equals ("")){
            proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.with(this).load(uImage1).transform(new CircleTransform()).into(proPic);
        }
**/

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
        inflater.inflate(R.menu.menu_agent_sell, menu);
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
        if (id == R.id.action_rent) {


                Intent intent = new Intent(getApplicationContext(),
                        AgentAllAdd.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


            return true;
        }


        if (id == R.id.action_sell) {


            Intent intent = new Intent(getApplicationContext(),
                    AgentAllAddSell.class);
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
            }else{
                Intent intent = new Intent(getApplicationContext(),
                        BasicHome.class);
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
            }else{
                Intent intent = new Intent(getApplicationContext(),
                        BasicAdContact.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
