package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.text.Html;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AgentPostDetailsSell extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

        TextView trent, tbed, tbath, tfloor, taddress, tposter;
        TextView tuMapCityb, tuMapCity, tubed, tuhome_details, tuhome_details_b, tupayment_b, tubath, tufloor, tusize, turent_type, tuhome_type, tufacility, tupayment;
        TextView ttitle, ttime, tvsinfo, tvbed, tvhome_details, tvbath, tvfloor, tvsize, tvrent_type, tvhome_type, tvfacility, tvrent, tvadvance, tvutility, tvposter, tvowner, tvphone, tvmail;
        ImageView ivhome, ivowner, ivposter;
        Button bCall, btnMsg, btnCall;
        String aposter, aserial, atitle, abed, abath, afloor, asize, arent_type, ahome_type, afacility, asinfo, arent, aadvance, autility,
        aowner, aphone, athana, acity, aadress, apphone, atime, apmail, aihome, aiposter;
        Toolbar toolbar;
        String sopnoi, adserial, locad, appHash, activity;
        Double lng, lat;
final Context c = this;
        Typeface custom_font;
        MapView roadMap;
private MapView mapView;
private GoogleMap gmap, mMap;
        SupportMapFragment mapFragment;

private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

        String url = "http://barivara.com/api/arent_del_sell.php";
        String agentName, agentID, agentPhone, agentEmail, agentAddress, agentPic,
        homeSerial, homeRent, homeAdvance, homeOthers, homeCity, homeAddress, homeLocation, appCommission, totalCost;


final Context context = this;
        String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
        String spot, caten;
        Button btnReq;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_post_details);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        getUserData();
        getTextId();
        // setUserData();
    }

    private void getTextId() {

        btnReq = (Button)findViewById(R.id.btnReq);
        btnReq.setOnClickListener(this);
        custom_font = Typeface.createFromAsset(c.getAssets(), "fonts/NotoSansBengali.ttf");

        ttitle = (TextView) findViewById(R.id.ttitle);
        trent = (TextView) findViewById(R.id.tvRentbn);
        tposter = (TextView) findViewById(R.id.tposter);
        tbed = (TextView) findViewById(R.id.tbed);
        tbath = (TextView) findViewById(R.id.tbath);
        tfloor = (TextView) findViewById(R.id.tfloor);
        ttime = (TextView) findViewById(R.id.ttime);
        taddress = (TextView) findViewById(R.id.taddress);
        tvsinfo = (TextView) findViewById(R.id.tvsinfo);
        tvbed = (TextView) findViewById(R.id.tvbed);
        tvbath = (TextView) findViewById(R.id.tvbath);
        tvfloor = (TextView) findViewById(R.id.tvfloor);
        tvsize = (TextView) findViewById(R.id.tvsize);
        tvrent_type = (TextView) findViewById(R.id.tvrent_type);
        tvhome_type = (TextView) findViewById(R.id.tvhome_type);
        tvfacility = (TextView) findViewById(R.id.tvfacility);
        tvrent = (TextView) findViewById(R.id.tvrent);
        tvadvance = (TextView) findViewById(R.id.tvadvance);
        tvutility = (TextView) findViewById(R.id.tvutility);
        tvposter = (TextView) findViewById(R.id.tvposter);
        tvphone = (TextView) findViewById(R.id.tvphone);
        tvmail = (TextView) findViewById(R.id.tvmail);

        tuhome_details = (TextView) findViewById(R.id.tuhome_details);
        tubed = (TextView) findViewById(R.id.tubed);
        tubath = (TextView) findViewById(R.id.tubath);
        tufloor = (TextView) findViewById(R.id.tufloor);
        tusize = (TextView) findViewById(R.id.tusize);
        turent_type = (TextView) findViewById(R.id.turent_type);
        tuhome_type = (TextView) findViewById(R.id.tuhome_type);
        tufacility = (TextView) findViewById(R.id.tufacility);
        tupayment = (TextView) findViewById(R.id.tupayment_details);

        ivhome = (ImageView) findViewById(R.id.ivhome);
        ivhome.setOnClickListener(this);
        ivposter = (ImageView) findViewById(R.id.ivposter);

        //     bCall=(Button)findViewById(R.id.bCall);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        btnMsg = (Button) findViewById(R.id.btnMsg);
        btnMsg.setOnClickListener(this);


        //   mapView = (MapView) findViewById(R.id.roadMap);
        //mapView.getMapAsync(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        Intent i = getIntent();
        sopnoi = i.getStringExtra("sopnoi");
        adserial = i.getStringExtra("adserial");
        caten = i.getStringExtra("ad_type");

        RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.custom22);
        final CardView cardDetails = (CardView) findViewById(R.id.cardDetails);
        final CardView cardPayment = (CardView) findViewById(R.id.cardPayment);

        tuhome_details_b = (TextView) findViewById(R.id.tuhome_details_b);
        tupayment_b = (TextView) findViewById(R.id.tupayment_details_b);
        tuMapCityb = (TextView) findViewById(R.id.tuMapCityb);
        tuMapCity = (TextView) findViewById(R.id.tuMapCity);
        final LinearLayout LinCard = (LinearLayout) findViewById(R.id.innerL);
        final LinearLayout LinFrag = (LinearLayout) findViewById(R.id.innerFra);


        tuMapCity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tuMapCity.setVisibility(View.GONE);
                tuMapCityb.setVisibility(View.VISIBLE);  //hide old button
                LinFrag.setVisibility(View.VISIBLE);  //show layout2
                LinCard.setVisibility(View.VISIBLE);  //show layout2

            }
        });

        tuMapCityb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tuMapCity.setVisibility(View.VISIBLE);
                tuMapCityb.setVisibility(View.GONE);  //hide old button
                LinFrag.setVisibility(View.GONE);  //show layout2
                LinCard.setVisibility(View.GONE);  //show layout2

            }
        });

        tupayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tupayment.setVisibility(View.GONE);
                tupayment_b.setVisibility(View.VISIBLE);  //hide old button
                cardPayment.setVisibility(View.VISIBLE);  //show layout2

            }
        });

        tupayment_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tupayment.setVisibility(View.VISIBLE);
                tupayment_b.setVisibility(View.GONE);  //hide old button
                cardPayment.setVisibility(View.GONE);  //show layout2

            }
        });

        tuhome_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tuhome_details.setVisibility(View.GONE);
                tuhome_details_b.setVisibility(View.VISIBLE);  //hide old button
                cardDetails.setVisibility(View.VISIBLE);  //show layout2
                //set Relativelayout 1 to half screen
                //   RelativaLayout.LayoutParams params = layout1.getLayoutParams();
                //   params.weight = 0.5;
                //  layout1.setLayoutParams(params);
            }
        });

        tuhome_details_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tuhome_details_b.setVisibility(View.GONE);  //hide old button
                tuhome_details.setVisibility(View.VISIBLE);  //hide old button
                cardDetails.setVisibility(View.GONE);  //show layout2

            }
        });

        int currentapiVersion = Build.VERSION.SDK_INT;

        new JSONAsyncTask().execute(url);

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

            //  Intent in = getIntent();
            //spot = in.getExtras().getString("spot");
            //caten = in.getExtras().getString("caten");

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
        toolh.setText(uName1);
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

    public void setData() {
        if (aiposter.equals("")) {
            ivposter.setImageDrawable(getResources().getDrawable(R.drawable.user));

        } else {
            Picasso.get().load(aiposter).transform(new CircleTransform()).into(ivposter);
        }

        if (atitle.equals("")) {
            ttitle.setVisibility(View.GONE);
        } else {
            ttitle.setText(Html.fromHtml(atitle));
        }
        if (arent.equals("")) {
            trent.setVisibility(View.GONE);
            tvrent.setVisibility(View.GONE);
        } else {
            trent.setText(arent + " টাকা");
            tvrent.setText(Html.fromHtml(arent));
        }
        if (aadvance.equals("")) {
            tvadvance.setVisibility(View.GONE);
        } else {
            tvadvance.setText(aadvance);
        }
        if (autility.equals("")) {
            tvutility.setVisibility(View.GONE);
        } else {
            tvutility.setText(autility);
        }
        if (abed.equals("")) {
            tbed.setVisibility(View.GONE);
        } else {
            tbed.setText(abed);
            tvbed.setText(abed);
        }
        if (abath.equals("")) {
            tbath.setVisibility(View.GONE);
        } else {
            tbath.setText(abath);
            tvbath.setText(abath);
        }
        if (afloor.equals("")) {
            tfloor.setVisibility(View.GONE);
        } else {
            tfloor.setText(afloor);
            tvfloor.setText(afloor);
        }
        if (asize.equals("")) {
            tvsize.setVisibility(View.GONE);
        } else {
            tvsize.setText(asize);
        }
        if (asinfo.equals("")) {
            tvsinfo.setVisibility(View.GONE);
        } else {
            tvsinfo.setText(asinfo);
        }
        if (ahome_type.equals("")) {
            tvhome_type.setVisibility(View.GONE);
        } else {
            tvhome_type.setText(ahome_type);
        }
        if (arent_type.equals("")) {
            tvrent_type.setVisibility(View.GONE);
        } else {
            tvrent_type.setText(arent_type);
        }
        if (afacility.equals("")) {
            tvfacility.setVisibility(View.GONE);
        } else {
            tvfacility.setText(afacility);
        }

        if (aihome.equals("")) {
            ivhome.setImageDrawable(getResources().getDrawable(R.drawable.noimage));

        } else {
            Picasso.get().load(aihome).into(ivhome);
        }

        if (acity.equals("")) {
            taddress.setVisibility(View.GONE);
        } else {
            taddress.setText(aadress + "\n" + athana + "\n" + acity);
        }
        if (atime.equals("")) {
            ttime.setVisibility(View.GONE);
        } else {
            ttime.setText(atime);
        }
        if (aposter.equals("")) {
            tposter.setVisibility(View.GONE);
        } else {
            tvposter.setText(aposter);
        }
        if (apphone.equals("")) {
            tvphone.setVisibility(View.GONE);
        } else {
            tvphone.setText(apphone);
        }
        if (apmail.equals("")) {
            tvmail.setVisibility(View.GONE);
        } else {
            tvmail.setText(apmail);
        }


        ttitle.setTypeface(custom_font);
        trent.setTypeface(custom_font);
        // tposter.setTypeface(custom_font);
        tbed.setTypeface(custom_font);
        tbath.setTypeface(custom_font);
        tfloor.setTypeface(custom_font);
        ttime.setTypeface(custom_font);
        taddress.setTypeface(custom_font);
        tvsinfo.setTypeface(custom_font);
        tvbed.setTypeface(custom_font);
        tvbath.setTypeface(custom_font);
        //  tvposter.setTypeface(custom_font);
        tvutility.setTypeface(custom_font);
        tvadvance.setTypeface(custom_font);
        tvrent.setTypeface(custom_font);
        tvfacility.setTypeface(custom_font);
        tvhome_type.setTypeface(custom_font);
        tvfloor.setTypeface(custom_font);
        tvsize.setTypeface(custom_font);
        tvrent.setTypeface(custom_font);

        mapFragment.getMapAsync(this);

    }

    private void reqHome() {

        //if(agentID==Sopnoid1){
        //  Toast.makeText(getApplicationContext(), "you couldnot send request own post", Toast.LENGTH_LONG).show();

        //} else{
        agentName = aposter.toString();
        agentPhone = apphone.toString();
        agentPic = aiposter.toString();
        agentEmail = apmail.toString();
        homeRent = arent.toString();
        homeAdvance = aadvance.toString();
        homeOthers = autility.toString();
        homeCity = acity.toString();
        homeAddress = aadress.toString();
        homeLocation = athana.toString();
        Intent intent1 = new Intent(getApplicationContext(), AgentOrderHome.class);
        intent1.putExtra("agentID",agentID);
        intent1.putExtra("homeSerial", homeSerial);
        intent1.putExtra("agentName", agentName);
        intent1.putExtra("agentPhone", agentPhone);
        intent1.putExtra("agentPic", agentPic);
        intent1.putExtra("agentEmail", agentEmail);
        intent1.putExtra("homeRent", homeRent);
        intent1.putExtra("homeAdvance", homeAdvance);
        intent1.putExtra("homeOthers", homeOthers);
        intent1.putExtra("homeCity", homeCity);
        intent1.putExtra("homeAddress", homeAddress);
        intent1.putExtra("homeLocation", homeLocation);
        startActivity(intent1);
        // }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        /**   gmap = googleMap;
         gmap.setMinZoomPreference(12);
         LatLng ny = new LatLng(40.7143528, -74.0059731);
         gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
         **/
        mMap = googleMap;

        locad = (aadress + "," + athana + "," + acity);

        if (acity != null || !acity.equals("")) {

            Locale loc = new Locale("bn", "BD");
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            // config.locale = loc;
            //getBaseContext().getResources().updateConfiguration(config,
            //      getBaseContext().getResources().getDisplayMetrics());


            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                config.setLocale(loc);
                getApplicationContext().createConfigurationContext(config);
            } else { //deprecated
                config.locale = loc;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            }

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(String.valueOf(locad), 1);
                Log.e("alop", String.valueOf(locad));
                Log.e("adser", String.valueOf(addresses));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addresses.get(0);
            double longitude = address.getLongitude();
            double latitude = address.getLatitude();
            Log.e("altitue", String.valueOf((longitude + "/" + latitude)));
            // Toast.makeText(RenthomeDetails.this, (int) (longitude+latitude), Toast.LENGTH_LONG).show();


            LatLng sydney = new LatLng(latitude, longitude);
            // LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title(aadress + "," + athana + "," + acity));

            //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.setMinZoomPreference(12);
            mMap.setIndoorEnabled(true);

            UiSettings uiSettings = mMap.getUiSettings();
            uiSettings.setIndoorLevelPickerEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setMapToolbarEnabled(true);
            uiSettings.setCompassEnabled(true);
            uiSettings.setZoomControlsEnabled(true);

            CameraPosition.Builder camBuilder = CameraPosition.builder();
            camBuilder.bearing(45);
            camBuilder.tilt(30);
            camBuilder.target(sydney);
            camBuilder.zoom(15);

            CameraPosition cp = camBuilder.build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
        }
    }


    public void onNormalMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onSatelliteMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void onTerrainMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void onHybridMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }


    // Add a marker in Sydney and move the camera
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCall:
                // do somethingbtnMsg

                callN();
                break;

            case R.id.btnReq:
                // do somethingbtnMsg
                reqHome();
                break;

            case R.id.btnMsg:
                // do somethingbtnMsg
                String userpic = adserial.toString();
                String uphone = aphone.toString();
                String uimage = aihome.toString();
                String uname = aowner.toString();
                String uemail = adserial.toString();

                Intent intent = new Intent(v.getContext(), AgentAddContact.class);
                intent.putExtra("username", uname);
                intent.putExtra("phone", uphone);
                intent.putExtra("serial", userpic);
                intent.putExtra("Ad11", uimage);
                intent.putExtra("userpic", uimage);


                // intent.putExtra("category",category);
                // etc.
                v.getContext().startActivity(intent);
                break;

            case R.id.ivhome:
                // do something

                String userpic1 = adserial.toString();
                String crImage = aihome.toString();
                String uimageg = aihome.toString();
                Intent intent2 = new Intent(v.getContext(), ImageGallery.class);

                intent2.putExtra("userpic", uimageg);
                intent2.putExtra("Ad11", uimageg);
                intent2.putExtra("crImage", crImage);

                v.getContext().startActivity(intent2);
                // finish();
                break;


        }
    }



    private void callN() {

        String ophone = apphone.toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + ophone));
        startActivity(intent);
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {


        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();


            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AgentPostDetailsSell.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
            dialog.dismiss();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {


                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("sopnoid", sopnoi));
                nameValuePairs.add(new BasicNameValuePair("adserial", adserial));
                nameValuePairs.add(new BasicNameValuePair("category", caten));
                Log.e("adsrl", adserial);
                // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                // HttpEntity httpEntity = httpResponse.getEntity();

                //  HttpGet httppost = new HttpGet(urls[0]);
                //   HttpClient httpclient = new DefaultHttpClient();
                //  HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
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
                        arent = object.getString("rent");
                        //  trent.setText(arent);
                        atitle = object.getString("title");
                        aposter = object.getString("postername");
                        apmail = object.getString("posterEmail");
                        apphone = object.getString("posterMobile");
                        aiposter = object.getString("posterimage");

                        aadvance = object.getString("advance");
                        autility = object.getString("utility");
                        abed = object.getString("bed");
                        abath = object.getString("bath");
                        afloor = object.getString("floor");
                        asize = object.getString("totalsize");
                        asinfo = object.getString("sinfo");
                        ahome_type = object.getString("home_type");
                        arent_type = object.getString("rental_type");
                        afacility = object.getString("facility");

                        aowner = object.getString("owner");
                        aphone = object.getString("phone");
                        athana = object.getString("location");
                        acity = object.getString("city");
                        aadress = object.getString("address");
                        aihome = object.getString("pimage");
                        atime = object.getString("timestamp");
                        agentID = object.getString("sopnoidR");
                        homeSerial = object.getString("adserial");








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
            //dialog.dismiss();
            setData();
            //adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

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

            Intent intent = new Intent(getApplicationContext(),
                    AgentDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return true;
        }

        if (id == R.id.menue) {

            Intent intent = new Intent(getApplicationContext(),
                    AgentMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);


            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
