package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
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
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

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

import static android.os.Build.VERSION.SDK_INT;

public class BasicSearch extends AppCompatActivity {

    final Context context = this;
    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    private ArrayList<AndroidVersion> rentAgent;
    private ArrayList<AndroidVersiona> rentAgentfamily;
    AdapterAgentRentList adapterAgentRentList;
    private AdapterAgent adapterAgent;
    private ArrayList<AndroidVersion> toursag;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    String urlfamily = "http://barivara.com/api/Searchrent.php";
    String urlagent = "http://barivara.com/api/getAllAgent.php";
    String spot, caten;
    Button btnAgent;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_search);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        //getUserData();
       // setUserData();

        btnAgent = (Button)findViewById(R.id.btnAgent);
        btnAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SAgent();
            }
        });
        rentAgent = new ArrayList<AndroidVersion>();
        rentAgentfamily = new ArrayList<AndroidVersiona>();


        adapterAgentRentList = new AdapterAgentRentList(BasicSearch.this, rentAgentfamily);

        adapterAgent = new AdapterAgent(BasicSearch.this,rentAgent);


        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAgent);

        recyclerFamily = (RecyclerView) findViewById(R.id.recyclerFamily);
        recyclerFamily.setAdapter(adapterAgentRentList);

        new JSONAsyncTaskAgent().execute(urlagent);

        new JSONAsyncRentfamily().execute(urlfamily);
        Intent in = getIntent();
        spot = in.getExtras().getString("spot");
        // caten = in.getExtras().getString("caten");
        caten = "rent".toString();

        initInstancesDrawer();

    }

    private void SAgent() {
        Intent intent1 = new Intent(getApplicationContext(), AgentAllList.class);
        intent1.putExtra("spot","agent");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }


    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText(R.string.rent);
        /**
         ImageView proPic = (ImageView)findViewById(R.id.userImage);

         proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));

         **/

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //  getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


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
                            Intent intent = new Intent(BasicSearch.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
    }



    protected class JSONAsyncRentfamily extends AsyncTask<String, Void, Boolean> {


        ProgressDialog pDialog = new ProgressDialog(BasicSearch.this);
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
              //  httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

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
            adapterAgentRentList.notifyDataSetChanged();
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

        ProgressDialog pDialog = new ProgressDialog(BasicSearch.this);
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
           //     Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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
                Intent intent1 = new Intent(getApplicationContext(), BasicSearch.class);
                intent1.putExtra("spot",m_Text);
                startActivity(intent1);
                BasicSearch.this.finish();
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
