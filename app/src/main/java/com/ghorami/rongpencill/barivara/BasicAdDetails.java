package com.ghorami.rongpencill.barivara;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.app.AlertDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.util.DisplayMetrics;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BasicAdDetails extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

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

    String url = "http://barivara.com/api/arent_del.php";
    String agentName, agentID, agentPhone, agentEmail, agentAddress, agentPic,
            homeSerial, homeRent, homeAdvance, homeOthers, homeCity, homeAddress, homeLocation, appCommission, totalCost;


    final Context context = this;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String spot, caten;
    Button btnReq;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ImageView ifacebook, itwitter, iwhatsapp, imessenger;
    String homeInfoTek;
    String homeInfo;
    String homeImagePath;
    File imageFile;
    Uri uri;
    Bitmap bm;
    File filesDir;
    MessageDialog messageDialog;
    LinearLayout LinCard, LinFrag;
    double longitude, latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(height > width) setContentView(R.layout.agent_add_details);
        else setContentView(R.layout.agent_add_details_wide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);


        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(BasicAdDetails.this);
        messageDialog = new MessageDialog(BasicAdDetails.this);
      //  getUserData();
        getTextId();
        initInstancesDrawer();
        sendInfo();
       // setUserData();
    }
    private void sendInfo() {
        final String urlRecord = "http://barivara.com/api/post_record.php";
        final String AdSerial, DeviceIP, CurrentDate, UserID, adType, USID, DSID, UserMobile, VisitValue;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;
        DeviceIP = manufacturer + "-"+model;
        DSID = version+"-"+versionRelease;
        USID = sopnoi.toString();
        AdSerial=adserial.toString();
        adType="rent".toString();
        UserID="untitle".toString();
        UserMobile="untitle".toString();
        VisitValue="1".toString();
        DateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss", Locale.ENGLISH);
        CurrentDate = df.format(Calendar.getInstance().getTime());


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("ad_type", adType));
                nameValuePairs.add(new BasicNameValuePair("DeviceIP", DeviceIP));
                nameValuePairs.add(new BasicNameValuePair("DSID", DSID));
                nameValuePairs.add(new BasicNameValuePair("USID", USID));
                nameValuePairs.add(new BasicNameValuePair("AdSerial", AdSerial));
                nameValuePairs.add(new BasicNameValuePair("UserID", UserID));
                nameValuePairs.add(new BasicNameValuePair("UserMobile", UserMobile));
                nameValuePairs.add(new BasicNameValuePair("CurrentDate", CurrentDate));
                nameValuePairs.add(new BasicNameValuePair("VisitValue", VisitValue));



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

              //  Toast.makeText(BasicAdDetails.this, "", Toast.LENGTH_LONG).show();
                /**     Intent intent = new Intent(getApplicationContext(),
                 AgentDashboard.class);
                 intent.putExtra("Password", uPass1);
                 intent.putExtra("MobileNumber", uMobile1);
                 startActivity(intent);
                 **/
                Log.e("Data", "Success");
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(AdSerial, DeviceIP, CurrentDate, UserID, adType, USID, DSID, UserMobile, VisitValue);

    }
    private void getTextId() {

        ifacebook = (ImageView)findViewById(R.id.facebook);
        ifacebook.setOnClickListener(this);

        imessenger = (ImageView)findViewById(R.id.messenger);
        imessenger.setOnClickListener(this);

        iwhatsapp = (ImageView)findViewById(R.id.whatsapp);
        iwhatsapp.setOnClickListener(this);

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
        adserial = i.getStringExtra("adserial2");
        //aposterID = i.getStringExtra("posterSID");
      /**
        if(adserial.isEmpty()||caten.isEmpty()){
            Snackbar.make(findViewById(android.R.id.content), "Woops!! Your device may not support it. Please Contact with us.", Snackbar.LENGTH_LONG)
                    //   .setAction("Submit", mOnClickListener)
                    .setActionTextColor(Color.RED)
                    .show();

            Intent intent = new Intent(getApplicationContext(),
                    BasicHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
**/
        RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.custom22);
        final CardView cardDetails = (CardView) findViewById(R.id.cardDetails);
        final CardView cardPayment = (CardView) findViewById(R.id.cardPayment);

        tuhome_details_b = (TextView) findViewById(R.id.tuhome_details_b);
        tupayment_b = (TextView) findViewById(R.id.tupayment_details_b);
        tuMapCityb = (TextView) findViewById(R.id.tuMapCityb);
        tuMapCity = (TextView) findViewById(R.id.tuMapCity);
        LinCard = (LinearLayout) findViewById(R.id.innerL);
        LinFrag = (LinearLayout) findViewById(R.id.innerFra);


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
            // new JSONAsyncTask().execute(url);


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
        toolh.setText("Details");
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

            proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));
        proPic.setVisibility(View.GONE);

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
            ttitle.setVisibility(View.VISIBLE);
            ttitle.setText(Html.fromHtml(atitle));
        }
        if (arent.equals("")) {
            trent.setVisibility(View.GONE);
            tvrent.setVisibility(View.GONE);
        } else {
            trent.setText(arent + " টাকা");
            tvrent.setText(Html.fromHtml(arent));
        }
        if (aadvance.equals("")||aadvance.equals("0")) {
            tvadvance.setVisibility(View.GONE);
        } else {
            tvadvance.setText(aadvance);
        }
        if (autility.equals("")||autility.equals("0")) {
            tvutility.setVisibility(View.GONE);
        } else {
            tvutility.setText(autility);
        }
        if (abed.equals("")||abed.equals("0")) {
            tbed.setVisibility(View.GONE);
        } else {
            tbed.setText(abed);
            tvbed.setText(abed);
        }
        if (abath.equals("")||abath.equals("0")) {
            tbath.setVisibility(View.GONE);
        } else {
            tbath.setText(abath);
            tvbath.setText(abath);
        }
        if (afloor.equals("")||afloor.equals("0")) {
            tfloor.setVisibility(View.GONE);
        } else {
            tfloor.setText(afloor);
            tvfloor.setText(afloor);
        }
        if (asize.equals("")||asize.equals("0")) {
            tvsize.setVisibility(View.GONE);
        } else {
            tvsize.setText(asize);
        }
        if (asinfo.equals("")||asinfo.equals("0")) {
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

        homeInfoTek=atitle;
        homeInfo = atitle + "\n" +arent +"TK \n" + arent_type +"\n " + abed +(R.string.bed) +"," + abath +(R.string.bath) +"\n" + aadress + "," + athana + "," + acity ;
        homeImagePath="http://barivara.com/detailn.php?sid="+adserial+"&adt="+caten;

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
            Intent intent1 = new Intent(getApplicationContext(), OrderHome.class);
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
       // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.clear(); //clear old markers
     //   mMap.setMinZoomPreference(6.0f);
     //   mMap.setMaxZoomPreference(14.0f);



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
            List<Address> addresses= new ArrayList<>();


            try {
                addresses = geocoder.getFromLocationName(String.valueOf(locad), 1);
                Log.e("alop", String.valueOf(locad));
                Log.e("adser", String.valueOf(addresses));



            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null && addresses.size() > 0)
            {

                Address address= addresses.get(0);
               // LatLng latlng= new LatLng(address.getLatitude(), address.getLongitude());
                //  mMap.addMarker(new MarkerOptions().position(latlng).title(addresses[i]));
                // mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));

                longitude = address.getLongitude();
                latitude = address.getLatitude();
                Log.e("alatitue", String.valueOf((longitude + "/" + latitude)));
                // Toast.makeText(RenthomeDetails.this, (int) (longitude+latitude), Toast.LENGTH_LONG).show();
                LatLng sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title(athana+","+acity).snippet("It is located in Dhaka")
                        .rotation((float) 3.5)
                        .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_place_black_24dp)));
                //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
               // mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(sydney);
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }


            }else{
                tuMapCity.setVisibility(View.GONE);
                tuMapCityb.setVisibility(View.GONE);  //hide old button
                LinFrag.setVisibility(View.GONE);  //show layout2
                LinCard.setVisibility(View.GONE);  //show layout2
            }


        }
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

            case R.id.facebook:
                // do somethingbtnMsg
                shareHome();
                break;

            case R.id.whatsapp:
                // do somethingbtnMsg
                getBitFIle();
                shareHomeW();
                break;

            case R.id.messenger:
                // do somethingbtnMsg
                //  getBitFIle();
                shareHomeTw();
                break;

            case R.id.btnCall:
                // do somethingbtnMsg




                    reqSignIn();



                break;

            case R.id.btnReq:
                // do somethingbtnMsg

                    reqSignIn();


                break;

            case R.id.btnMsg:

            /**    // do somethingbtnMsg
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
**/
                reqSignIn();
                break;

            case R.id.ivhome:
                // do something

                reqSignIn();
             /**   String userpic1 = adserial.toString();
                String crImage = aihome.toString();
                String uimageg = aihome.toString();
                Intent intent2 = new Intent(v.getContext(), ImageGallery.class);

                intent2.putExtra("userpic", uimageg);
                intent2.putExtra("Ad11", uimageg);
                intent2.putExtra("crImage", crImage);

                v.getContext().startActivity(intent2);
                // finish();

              **/
                break;


        }
    }

    private void reqSignIn() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.req_signin_dialogue, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ImageView prImage = (ImageView) dialogView.findViewById(R.id.my_image);
        Button btnSignin = (Button) dialogView.findViewById(R.id.btnSignin);
        Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "Sorry sir!! you need to login first", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BasicAdDetails.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


    }

    private void shareHome(){

        String v4=("http://barivara.com/app").toString();
        String v1=("#barivara").toString();
        String v2=("ern money from your house easy way").toString();



        ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag("#barivara").build();
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setShareHashtag(shareHashTag)
                .setContentTitle(homeInfoTek)
                .setQuote(homeInfo)
                .setContentUrl(Uri.parse(homeImagePath))
                .build();

        shareDialog.show(BasicAdDetails.this,shareLinkContent);




    }



    private void getBitFIle(){

        ivhome.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) ivhome.getDrawable();
        bm = drawable.getBitmap();

        // Bitmap bm = BitmapFactory.decodeResource(Uri.parse(homeImagePath));

        filesDir = getApplicationContext().getFilesDir();
        imageFile = new File(filesDir, "barivara_home.png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, os); // 100% quality
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, imageFile);

    }

    private void shareHomeW(){
        String image_url = "http://images.cartradeexchange.com//img//800//vehicle//Honda_Brio_562672_5995_6_1438153637072.jpg";


        //  String imageFile = ("android.resource://com.barivara.rongpencill.barivara/" + R.drawable.nipa);

        Intent shareIntent = new Intent();
        // set flag to give temporary permission to external app to use your FileProvider


        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri urnumber = Uri.parse("smsto:" + "+8801790675227");
        // Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        shareIntent.setAction(Intent.ACTION_SEND);

// generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open


        //without the below line intent will show error
        shareIntent.setType("text/plain");
        //  new Download_GIF(url).execute();
        // Uri imageUri = Uri.parse("file:///storage/emulated/0/downloadedFile.gif");
        shareIntent.putExtra(Intent.EXTRA_TEXT, homeImagePath+"/n"+homeInfo);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        // shareIntent.putExtra("jid", "+8801790675227@s.whatsapp.net"); //phone number without "+" prefix

        // Target whatsapp:
        shareIntent.setPackage("com.whatsapp");

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BasicAdDetails.this,
                    "Whatsapp have not been installed.",
                    Toast.LENGTH_SHORT).show();
        }



/**
 PackageManager packageManager = context.getPackageManager();
 Intent i = new Intent(Intent.ACTION_VIEW);

 try {
 String url = "https://api.whatsapp.com/send?phone=+88"+"01686039720" +"&text="+homeInfoTek + URLEncoder.encode(homeImagePath, "UTF-8");
 i.setPackage("com.whatsapp");
 i.setData(Uri.parse(url));
 if (i.resolveActivity(packageManager) != null) {
 context.startActivity(i);
 }
 } catch (Exception e){
 e.printStackTrace();
 }


 String smsNumber = "01686039720";
 boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
 if (isWhatsappInstalled) {

 String toNumber = "+8801686039720"; // contains spaces.
 toNumber = toNumber.replace(" ", "");

 Intent sendIntent = new Intent("android.intent.action.MAIN");
 sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
 sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
 sendIntent.putExtra(Intent.EXTRA_TEXT, "message");
 sendIntent.setAction(Intent.ACTION_SEND);
 sendIntent.setPackage("com.whatsapp");
 sendIntent.setType("image/png");
 context.startActivity(sendIntent);
 } else {
 Uri uri = Uri.parse("market://details?id=com.whatsapp");
 Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
 Toast.makeText(this, "WhatsApp not Installed",
 Toast.LENGTH_SHORT).show();
 startActivity(goToMarket);
 }
 **/
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;

    }

    private void shareHomeTw(){

        /**
         Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, imageFile);

         Intent sendIntent = new Intent();
         sendIntent.setAction(Intent.ACTION_SEND);
         sendIntent
         .putExtra(Intent.EXTRA_TEXT,
         homeInfo);
         sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
         // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open

         sendIntent.setType("text/plain");
         // sendIntent.setType("image/*");
         sendIntent.setPackage("com.facebook.orca");
         sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

         try {
         startActivity(sendIntent);
         }
         catch (android.content.ActivityNotFoundException ex) {
         Toast.makeText(context,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
         }

         **/

        ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag("#barivara").build();

        ShareLinkContent.Builder shareLinkContentBuilder = new ShareLinkContent.Builder().setShareHashtag(shareHashTag)
                .setContentTitle(homeInfoTek)
                .setContentDescription(homeInfoTek)
                .setQuote(homeInfo)
                .setContentUrl(Uri.parse(homeImagePath));
        shareLinkContentBuilder.setImageUrl(Uri.parse(homeImagePath));

        messageDialog.show(shareLinkContentBuilder.build());

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
            dialog = new ProgressDialog(BasicAdDetails.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            finish();



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
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

            onBackPressed();

            finish();

            //onBackHome();
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
