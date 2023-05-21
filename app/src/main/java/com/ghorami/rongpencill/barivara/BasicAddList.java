package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.ArrayList;

public class BasicAddList extends AppCompatActivity {

    final Context context = this;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    private ArrayList<AndroidVersion> rentAgent;
    private ArrayList<AndroidVersiona> rentAgentfamily;
    BasicRentAdapterBnList BasicRentAdapterBnList;
    private AdapterAgent adapterAgent;
    private ArrayList<AndroidVersion> toursag;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    String urlfamily = "http://barivara.com/api/arent.php";
    String urlagent = "http://barivara.com/api/getAllAgent.php";
    String spot, caten;
    Button btnAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_add_list);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);


       // getUserData();
        setUserData();

        btnAgent = (Button)findViewById(R.id.btnAgent);
        btnAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SAgent();
            }
        });
        rentAgent = new ArrayList<AndroidVersion>();
        rentAgentfamily = new ArrayList<AndroidVersiona>();


        BasicRentAdapterBnList = new BasicRentAdapterBnList(BasicAddList.this, rentAgentfamily);

        adapterAgent = new AdapterAgent(BasicAddList.this,rentAgent);


        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAgent);

        recyclerFamily = (RecyclerView) findViewById(R.id.recyclerFamily);
        recyclerFamily.setAdapter(BasicRentAdapterBnList);

        new JSONAsyncTaskAgent().execute(urlagent);

        new JSONAsyncRentfamily().execute(urlfamily);

        initInstancesDrawer();
        runLayoutAnimation(recyclerAgent);
        runLayoutAnimationList(recyclerFamily);
        Intent in = getIntent();
        spot = in.getExtras().getString("spot");
        caten = in.getExtras().getString("caten");
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void runLayoutAnimationList(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void SAgent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAllList.class);
        intent1.putExtra("spot","agent");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }
    private void setUserData() {

        /**    if(uImage1.equals ("")){
         profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
         ivprofile.setImageDrawable(getResources().getDrawable(R.drawable.user));
         } else {
         Picasso.with(this).load(uImage1).transform(new CircleTransform()).into(profileImage);
         Picasso.with(this).load(uImage1).transform(new CircleTransform()).into(ivprofile);
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

         //   Intent intent = new Intent(this, Login.class);
           // startActivity(intent);
            //finish();




        } else{
          //  Toast.makeText(getApplicationContext(), "You are not signin yet! Please login again", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(this, BasicHome.class);
            //startActivity(intent);
            //finish();

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Your are not signin yet", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BasicAddList.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
    }


    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText(spot);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

    protected class JSONAsyncRentfamily extends AsyncTask<String, Void, Boolean> {


        ProgressDialog pDialog = new ProgressDialog(BasicAddList.this);
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
        protected Boolean doInBackground(String... urls) {
            try {


                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                 nameValuePairs.add(new BasicNameValuePair("t_spot", spot));
                   nameValuePairs.add(new BasicNameValuePair("lang", "BN"));
                nameValuePairs.add(new BasicNameValuePair("caten", caten));
                //    nameValuePairs.add(new BasicNameValuePair("sopnoid", sopnoi));
                //  nameValuePairs.add(new BasicNameValuePair("adserial", adserial));
                Log.e("adsrl", spot);
                // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
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

                    for (int j = 0; j < jarrayj.length(); j++) {
                        JSONObject objectj = jarrayj.getJSONObject(j);

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
            pDialog.dismiss();
            BasicRentAdapterBnList.notifyDataSetChanged();
            if (result == false){
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Network is slow", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
             //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }


    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(BasicAddList.this);
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

                        rentAgent.add(tour);
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
            adapterAgent.notifyDataSetChanged();
            if (result == false){
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Network is slow", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
               // Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_basic_sell, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

            //onBackHome();
            return true;
        }

        if (id == R.id.action_rent) {

            Intent intent = new Intent(getApplicationContext(),
                    BasicHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


            return true;
        }

        if (id == R.id.action_sell) {

            Intent intent = new Intent(getApplicationContext(),
                    BasicHomeSell.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


            return true;
        }

        if (id == R.id.action_home) {

            Intent intent = new Intent(getApplicationContext(),
                    BasicHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


            return true;
        }

        if (id == R.id.action_about) {

            Intent intent = new Intent(getApplicationContext(),
                    AgentSetting.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return true;
        }

        if (id == R.id.login) {



            Intent intent = new Intent(getApplicationContext(),
                    Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

