package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

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

public class AgentAllAddSell2 extends AppCompatActivity implements View.OnClickListener {

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
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    String url = "http://barivara.com/api/asell.php?t_spot=bachelor&lang=BN&caten=sell";
    String url2 = "http://barivara.com/api/asell.php?t_spot=family&lang=BN&caten=sell";
    String url3 = "http://barivara.com/api/test.php";
    String urlagent = "http://barivara.com/api/getAllAgent.php";
    String urlbachelor = "http://barivara.com/api/asell.php?t_spot=bachelor&lang=BN&caten=sell";
    String urlfamily = "http://barivara.com/api/asell.php?t_spot=family&lang=BN&caten=sell";
    String urlgirls = "http://barivara.com/api/asell.php?t_spot=gstudent&lang=BN&caten=sell";
    String urlstudent = "http://barivara.com/api/asell.php?t_spot=student&lang=BN&caten=sell";
    String urlothers = "http://barivara.com/api/asell.php?t_spot=others&lang=BN&caten=sell";
    String urlsell = "http://barivara.com/api/test.php";
    String urlwomen = "http://barivara.com/api/asell.php?t_spot=women&lang=BN&caten=sell";
    ImageView imTop;


    Button btnSell, btnRent;
    Button btnWomen, btnBachelor, btnAgent, btnFamily, btnGirls, btnStudent, btnOthers;
    TextView tvBachelor, tvAgent, tvFamily, tvGirls, tvStudent, tvWomen, tvOthers;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_all_add_sell);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);

        getUserData();
        setUserData();

        btnWomen = (Button)findViewById(R.id.btnWomen);
        btnWomen.setOnClickListener(this);
        btnBachelor = (Button)findViewById(R.id.btnBachelor);
        btnBachelor.setOnClickListener(this);
        btnAgent = (Button)findViewById(R.id.btnAgent);
        btnAgent.setOnClickListener(this);
        btnFamily = (Button)findViewById(R.id.btnFamily);
        btnFamily.setOnClickListener(this);
        btnGirls = (Button)findViewById(R.id.btnGirls);
        btnGirls.setOnClickListener(this);
        btnStudent = (Button)findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(this);
        btnOthers = (Button)findViewById(R.id.btnOthers);
        btnOthers.setOnClickListener(this);

        imTop = (ImageView)findViewById(R.id.imageView);
        imTop.setBackgroundResource(R.drawable.ic_launcher_e);
        btnRent = (Button)findViewById(R.id.btnRent1);
        btnSell = (Button)findViewById(R.id.btnSell1);
        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //   imTop.setBackgroundResource(R.drawable.rent_pan);
          //      imTop.setBackground(R.drawable.rent_pan);
                Intent Classr = new Intent(AgentAllAddSell2.this, AgentAllAdd.class);
                startActivity(Classr);
            }
        });

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // imTop.setBackgroundResource(R.drawable.sell_pan);
             //   Intent Classr = new Intent(AgentAllAddSell.this, AgentAllAddSell.class);
               // startActivity(Classr);
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

        adapterAgentRentbachelor = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentbachelor);
        adapterAgentRentgirls = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentgirls);
        adapterAgentRentwomen = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentwomen);
        adapterAgentRentstudent = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentstudent);
        adapterAgentRentsell = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentsell);
        adapterAgentRentothers = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentothers);
        adapterAgentRentfamily = new AdapterAgentRent(AgentAllAddSell2.this, rentAgentfamily);

        adapterAgentRent = new AdapterAgentRent(AgentAllAddSell2.this, rentAgent);


        adapterAgent = new AdapterAgent(AgentAllAddSell2.this,toursad);

        // adapter = new DataAdapter(data);
        recyclerbachelor = (RecyclerView) findViewById(R.id.recyclerbachelor);
        recyclerbachelor.setAdapter(adapterAgentRentbachelor);

        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAgent);

        recyclerFamily = (RecyclerView) findViewById(R.id.recyclerFamily);
        recyclerFamily.setAdapter(adapterAgentRentfamily);

        recyclerGirls = (RecyclerView) findViewById(R.id.recyclerGirls);
        recyclerGirls.setAdapter(adapterAgentRentgirls);

        recyclerStudent = (RecyclerView) findViewById(R.id.recyclerStudent);
        recyclerStudent.setAdapter(adapterAgentRentstudent);

        recyclerWomen = (RecyclerView) findViewById(R.id.recyclerWomen);
        recyclerWomen.setAdapter(adapterAgentRentwomen);

        recyclerOthers = (RecyclerView) findViewById(R.id.recyclerOthers);
        recyclerOthers.setAdapter(adapterAgentRentothers);

        recyclerViewSell = (RecyclerView) findViewById(R.id.recyclerViewSell);
        recyclerViewSell.setAdapter(adapterAgentRentsell);


    //    new JSONAsyncTask2().execute(url2);

//        new JSONAsyncTask3().execute(url3);
  //      new JSONAsyncTask1().execute(url);

        ParseAllData();
    }

    private void ParseAllData() {

        ProgressDialog pDialog = new ProgressDialog(AgentAllAddSell2.this);



            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //  pDialog.dismiss();

            new JSONAsyncTaskAgent().execute(urlagent);
            new JSONAsyncRentBachelor().execute(urlbachelor);
            new JSONAsyncRentSell().execute(urlsell);
            new JSONAsyncRentfamily().execute(urlfamily);
            new JSONAsyncRentGirls().execute(urlgirls);
            new JSONAsyncRentStudent().execute(urlstudent);
            new JSONAsyncRentOthers().execute(urlothers);
            new JSONAsyncRentWomens().execute(urlwomen);

        pDialog.dismiss();

    }

    @Override
    public void onClick(View  v) {

        switch (v.getId()) {
            case R.id.btnWomen:
                Swomen();
                break;
            case R.id.btnBachelor:
                Sbachelor();
                break;
            case R.id.btnFamily:
                Sfamily();
                break;
            case R.id.btnGirls:
                Sgstudent();
                break;
            case R.id.btnStudent:
                Sstudent();
                break;
            case R.id.btnOthers:
                Sothers();
                break;
            case R.id.btnAgent:
                SAgent();
                break;

        }
    }

    private void SAgent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAllList.class);
        intent1.putExtra("spot","agent");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Swomen() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","women");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sfamily() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","family");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sothers() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","others");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sbachelor() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","bachelor");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sgstudent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","gstudent");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sstudent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","student");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
    }

    private void Sarent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAddRent.class);
        intent1.putExtra("spot","all");
        intent1.putExtra("caten","sell");
        startActivity(intent1);
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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();
                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));
                        rentAgentbachelor.add(tourk);
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
            adapterAgentRentbachelor.notifyDataSetChanged();
           // adapterAgentRentfamily.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();

                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));

                        rentAgentsell.add(tourk);
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
            adapterAgentRentsell.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
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
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();

                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));

                        rentAgentgirls.add(tourk);
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
            adapterAgentRentgirls.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();
                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));
                        rentAgentstudent.add(tourk);
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
            adapterAgentRentstudent.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();

                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));
                        rentAgentwomen.add(tourk);
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
            adapterAgentRentwomen.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                    JSONArray jarrayj = jsonoj.getJSONArray("tour");

                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject objectj = jarrayj.getJSONObject(i);

                        AndroidVersiona tourk = new AndroidVersiona();

                        tourk.setRent(objectj.getString("rent"));
                        tourk.setBed(objectj.getString("bed"));
                        tourk.setBath(objectj.getString("bath"));
                        tourk.setPimage(objectj.getString("pimage"));
                        tourk.setAdSerial(objectj.getString("adserial"));
                        tourk.setSopnoid(objectj.getString("sopnoid"));
                        tourk.setTimestamp(objectj.getString("timestamp"));
                        tourk.setLocation(objectj.getString("location"));
                        tourk.setAirport(objectj.getString("sad_type"));
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
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }



    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... urlsa) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

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
                    JSONArray jarray = jsono.getJSONArray("tour");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersion tour = new AndroidVersion();

                        tour.setName(object.getString("name"));
                        tour.setSopnoid(object.getString("sopnoid"));
                        tour.setImage(object.getString("uPicture"));
                        tour.setTimestamp(object.getString("timestamp"));

                        toursad.add(tour);
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
            adapterAgent.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
        toolh.setText("property for sell");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agent, menu);


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
