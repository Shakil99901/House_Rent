package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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


public class ImageGallery extends AppCompatActivity {

    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String spot, caten;
    String tMessage=null;
    private RecyclerView recyclerView;
    private ListView listview;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    CoordinatorLayout mRootLayout;
    GridView mGridView;
    ViewPager viewPager;
    private Button mbutton;
    private ProgressDialog dialog;

    int rent = 0;
    EditText e1, e2, e3, mTextEditer;
    TouchImageView selectedImage;
    TextView stringT, adS;
    Gallery gallery;
    private ArrayList<AndroidVersion> tours;
    ImageGalleryAdapter galleryImageAdapter = null;

    final Context context = this;

    TextView results;
    // URL of object to be parsed
    // String JsonURL = "http://pkb.rongpencill.com/property/imagep1818/kajol-19-6-17-18-22.php";
    // String JsonURL = "http://10.0.2.2/hot.php"; http://barivara.com/searchImg.php
    // This string will hold the results
    String data = "";
    String data2 = "";
    String [] formls = {"hello", "setbx", "ppk"};
    private final static String TAG = ImageGallery.class.getSimpleName();
    // private final static String url = "http://rongpencill.com/tour/search_poultry_CEn.php";
    // private final static String url = "http://rongpencill.com/tour/search_poultry_en.php";
    private SwipeRefreshLayout swipeRefreshLayout;

    // private final static String url = "http://pkb.rongpencill.com/tour/getresort.php";
    private final static String url = "http://barivara.com/page/searchImg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(height > width) setContentView(R.layout.image_gallery);
        else setContentView(R.layout.image_gallery_wide);

        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        getUserData();

        //setListViewAdapter();
        getDataFromInternet();
        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        selectedImage=(TouchImageView) findViewById(R.id.imageView);
        adS =(TextView) findViewById(R.id.adS);
        gallery.setSpacing(1);
        tours = new ArrayList<AndroidVersion>();
        galleryImageAdapter= new ImageGalleryAdapter(this, tours);
        gallery.setAdapter(galleryImageAdapter);

        Intent i = getIntent();
        String v2 = i.getExtras().getString("userpic");

        if (v2.equals ("")){
            selectedImage.setImageResource(R.drawable.noimage);
        } else{
            Picasso.get().load(v2).into(selectedImage);
        }



        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                AndroidVersion item = tours.get(position);

                if(item.getImage().equals ("")){
                    selectedImage.setImageDrawable(getResources().getDrawable(R.drawable.noimage));

                } else {
                    Picasso.get().load(item.getImage()).into(selectedImage);

                }

                // TouchImageView img = new TouchImageView(this);
                // img.setImageResource(R.drawable.ice_age_2);
                //img.setMaxZoom(4f);
                // setContentView(img);
                // selectedImage.setImageResource(galleryImageAdapter.mImageIds[position]);
                //  selectedImage.setImageResource((galleryImageAdapter.mImageIds[position].drawableId);
                //  selectedImage.setImageDrawable(ImageOperations(galleryImageAdapter.mImageIds[position]));
            }
        });
        return ;





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
        toolh.setText("all image");
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

    /**
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
                AgentAddDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
**/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void getDataFromInternet() {
        new GetJsonFromUrlTask(this, url).execute();
    }


/*
    private void setListViewAdapter() {
        tours = new ArrayList<AndroidVersion>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(context,tours);
        // adapter = new DataAdapter(data);
        recyclerView.setAdapter(adapter);

    }*/

    private void parseJsonResponse(String result) {

        Log.i(TAG, result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(json.getString("tour"));
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                AndroidVersion tour = new AndroidVersion();
                // tour.setTeacher(jObject.getString("day"));
               // tour.setAdSerial(jObject.getString("adserial"));
               // tour.setRental_type(jObject.getString("rental_type"));
                
                tour.setImage(jObject.getString("image"));


                tours.add(tour);
            }
            galleryImageAdapter.notifyDataSetChanged();
//            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GetJsonFromUrlTask extends AsyncTask<Void, Void, String> {

        private ImageGallery activity;
        private String url;
        private final String TAG = ImageGallery.class.getSimpleName();

        public GetJsonFromUrlTask(ImageGallery activity, String url) {
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
            ((ImageGallery)activity).parseJsonResponse(result);
            dialog.dismiss();
            Log.i(TAG, result);
        }

        public JSONObject loadJSON(String url) {
            // Creating JSON Parser instance
            JSONGetter jParser = new JSONGetter();

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

                    Intent i = getIntent();
                     String v1 = i.getExtras().getString("Ad11");
                    String v2 = i.getExtras().getString("sidMobile");
                    // String v3 = i.getExtras().getString("spot1");
                    // String v4 = i.getExtras().getString("email1");
                    adS.setText(v1);

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    //   nameValuePairs.add(new BasicNameValuePair("t_spot", v1));
                    nameValuePairs.add(new BasicNameValuePair("t_spot", v1));
                    nameValuePairs.add(new BasicNameValuePair("sidMobile", v2));
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




    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //onBackHome();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}