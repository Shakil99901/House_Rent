package com.ghorami.rongpencill.barivara;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class CarrerJob extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private ListView listview;
    private ArrayList<AndroidVersion> tours;
    private CarrerAdapter adapter;
    final Context context = this;
    private Button mbutton;
    int rent = 0;
    EditText e1, e2, e3, mTextEditer;
    private TabLayout tabLayout;
    String jserial;
    //This is our viewPager
    private ViewPager viewPager;
    private final static String TAG = CarrerJob.class.getSimpleName();
   // private final static String url = "http://rongpencill.com/pkb.rongpencill.com/tour/search_poultry_ntime_bn.php";

    private final static String url = "http://barivara.com/api/get_all_job.php";

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        setContentView(R.layout.carrer_job);

        Intent i = getIntent();
        String v1w = i.getStringExtra("spot");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(v1w);
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        setListViewAdapter();
        getDataFromInternet();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CarrerApply.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
        }else{
            //no connection
            // Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
            //intent2.putExtra("spot",v1);
            //startActivity(intent2);
            Toast toast = Toast.makeText(context, "No Internet Connection",
                    Toast.LENGTH_LONG);
            toast.show();
            finishAffinity();


        }


    }



    private void getDataFromInternet() {
        new GetJsonFromUrlTask(this, url).execute();
    }

    private void setListViewAdapter() {
        tours = new ArrayList<AndroidVersion>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CarrerAdapter(CarrerJob.this,tours);
        // adapter = new DataAdapter(data);
        recyclerView.setAdapter(adapter);

    }

    public void parseJsonResponse(String result) {
        Log.i(TAG, result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(json.getString("job_cir"));
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                AndroidVersion tour = new AndroidVersion();
                tour.setCategory(jObject.getString("title"));
                tour.setBatch(jObject.getString("salary"));
                tour.setName(jObject.getString("condition"));
                tour.setBatch_2(jObject.getString("jserial"));
                tour.setType(jObject.getString("details"));
                tour.setBest_time(jObject.getString("expired_date"));
                tour.setImage(jObject.getString("apply_url"));
                //  tour.setImageUrl2(jObject.getString("image2"));
                //  tour.setImageUrl3(jObject.getString("image3"));
                //  tour.setImageUrl4(jObject.getString("image4"));
                //  tour.setImageUrl5(jObject.getString("image5"));
                // tour.setImageUrl6(jObject.getString("image6"));


                //  tour.setArea(jObject.getString("area"));
                // tour.setNear(jObject.getString("near"));
                // tour.setBest_time(jObject.getString("best_time"));
                // tour.setWeather(jObject.getString("weather"));
                // tour.setHealth(jObject.getString("health"));
                // tour.setL_thana(jObject.getString("l_thana"));
                // tour.setL_thana_2(jObject.getString("l_thana_2"));

                tours.add(tour);
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class GetJsonFromUrlTask extends AsyncTask<Void, Void, String> {

        private Activity activity;
        private String url;
        private ProgressDialog dialog;
        private final String TAG = CarrerJob.class.getSimpleName();

        public GetJsonFromUrlTask(Activity activity, String url) {
            super();
            this.activity = activity;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progress dialog
            dialog = new ProgressDialog(activity);
            // Set progress dialog title
            //dialog.setTitle("Please Wait......... ");
            // Set progress dialog message
            dialog.setMessage("Please Wait...");
            dialog.setIndeterminate(false);
            // Show progress dialog
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            // call load JSON from url method
            return loadJSON(this.url).toString();
        }

        @Override
        protected void onPostExecute(String result) {
            ((CarrerJob) activity).parseJsonResponse(result);
            dialog.dismiss();
            Log.i(TAG, result);
        }

        public JSONObject loadJSON(String url) {
            // Creating JSON Parser instance
            CarrerJob.GetJsonFromUrlTask.JSONGetter jParser = new CarrerJob.GetJsonFromUrlTask.JSONGetter();

            // getting JSON string from URL
            JSONObject json = jParser.getJSONFromUrl(url);

            return json;
        }

        private class JSONGetter {

            private InputStream is = null;
            private JSONObject jObj = null;
            private String json = "";

            // constructor
            public JSONGetter() {

            }



            public JSONObject getJSONFromUrl(String url) {

                // Making HTTP request
                try {
                   // Intent i = getIntent();
                    String v1 = "JOB".toString();
                    String v2 = "BN".toString();

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("t_spot", v1));
                    nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    Log.e("log_tag", "connection success ");
                    //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),
                            8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(json);
                    Log.e("log_tag", "Parsing success ");

                    //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                // return JSON String
                return jObj;

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void EnterApply(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable

            //  String v1=("poultry").toString();
            Intent intent2 = new Intent(getApplicationContext(), CarrerApply.class);
            //intent2.putExtra("spot",v1);
            intent2.putExtra("jserial",jserial);
            //  intent2.putExtra("spot",v1);
            startActivity(intent2);
        }
    }



    private void appShare() {


        String v4=("https://play.google.com/store/apps/details?id=com.ghorami.rongpencill.barivara").toString();
        String v1=("#barivara").toString();
        String v2=("earn money from your house in easy way").toString();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, v4+"\n"+v1+"\n"+v2+"\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));



    }

    private void sendFeedback() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        Log.e("MyActivity", "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease
        );
        String v2=("MyActivity"+ "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease).toString();

        String[] TO = {"tpucse@gmail.com"};
        Uri uri = Uri.parse("mailto:tpucse@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", String.valueOf("#barivara"))
                .appendQueryParameter("body", "Please leave your feedback here.\nPlease do not edit the information below:\n"+v2)
                .build();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signin) {

            Intent intent = new Intent(getApplicationContext(),
                    Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        } else if (id == R.id.home) {

            Intent intent = new Intent(getApplicationContext(),
                    BasicHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);



        } else if (id == R.id.nav_share) {

            appShare();


        } else if (id == R.id.nav_send) {

            sendFeedback();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}