package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class BasicHomeOffline extends AppCompatActivity implements View.OnClickListener {

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

    BasicRentAdapterBn adapterAgentRentbachelor;
    BasicRentAdapterBn adapterAgentRentfamily;
    BasicRentAdapterBn adapterAgentRentothers;
    BasicRentAdapterBn adapterAgentRentsell;
    BasicRentAdapterBn adapterAgentRentgirls;
    BasicRentAdapterBn adapterAgentRentstudent;
    BasicRentAdapterBn adapterAgentRentwomen;
    BasicRentAdapterBn BasicRentAdapterBn;
    private BasicRentAdapterBn adapter;

    private AdapterAgentBs adapterAgentBs;

    final Context context = this;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    String url = "http://barivara.com/api/arent.php?t_spot=bachelor&lang=BN&caten=rent";
    String url2 = "http://barivara.com/api/arent.php?t_spot=family&lang=BN&caten=rent";
    String url3 = "http://barivara.com/api/test.php";
    String urlagent = "http://barivara.com/api/getAllAgent.php";
    String urlbachelor = "http://barivara.com/api/arent.php?t_spot=bachelor&lang=BN&caten=rent";
    String urlfamily = "http://barivara.com/api/arent.php?t_spot=family&lang=BN&caten=rent";
    String urlgirls = "http://barivara.com/api/arent.php?t_spot=gstudent&lang=BN&caten=rent";
    String urlstudent = "http://barivara.com/api/arent.php?t_spot=student&lang=BN&caten=rent";
    String urlothers = "http://barivara.com/api/arent.php?t_spot=others&lang=BN&caten=rent";
    String urlsell = "http://barivara.com/api/test.php";
    String urlwomen = "http://barivara.com/api/arent.php?t_spot=women&lang=BN&caten=rent";
    ImageView imTop;
    ImageView ivBachelor, ivGirls, ivWomen, ivFamily, ivStudent, ivOthers;


    Button btnWomen, btnBachelor, btnAgent, btnFamily, btnGirls, btnStudent, btnOthers;
    TextView tvBachelor, tvAgent, tvFamily, tvGirls, tvStudent, tvWomen, tvOthers;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    SwipeRefreshLayout refreshView;

    JSONArray sellJ, womenJ, otherJ, studentJ, girlsJ,familyJ,bachelorJ;
    String sellJ1, womenJ1, otherJ1, studentJ1, girlsJ1,familyJ1,bachelorJ1;
    String sellJd, womenJd, otherJd, studentJd, girlsJd,familyJd,bachelorJd;

    EditText etSearch;
    ImageButton ibSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_all_add);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        //  getUserData();
        setUserData();
        ivBachelor = (ImageView)findViewById(R.id.ivBachelor);
        ivBachelor.setOnClickListener(this);
        ivBachelor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Sbachelor();
                return false;
            }
        });
        ivGirls = (ImageView)findViewById(R.id.ivGirls);
        ivGirls.setOnClickListener(this);
        ivGirls.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Sgstudent();
                return false;
            }
        });
        ivFamily = (ImageView)findViewById(R.id.ivFamily);
        ivFamily.setOnClickListener(this);
        ivFamily.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Sfamily();
                return false;
            }
        });
        ivWomen = (ImageView)findViewById(R.id.ivWomen);
        ivWomen.setOnClickListener(this);
        ivWomen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Swomen();
                return false;
            }
        });
        ivStudent = (ImageView)findViewById(R.id.ivStudent);
        ivStudent.setOnClickListener(this);
        ivStudent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Sstudent();
                return false;
            }
        });
        ivOthers = (ImageView)findViewById(R.id.ivOthers);
        ivOthers.setOnClickListener(this);
        ivOthers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Sothers();
                return false;
            }
        });

        etSearch = (EditText)findViewById(R.id.etSearch);
        ibSearch = (ImageButton)findViewById(R.id.ibSearch);
        ibSearch.setOnClickListener(this);
        ibSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String m_Text = etSearch.getText().toString();
                Intent intent1 = new Intent(getApplicationContext(), BasicSearch.class);
                intent1.putExtra("spot",m_Text);
                startActivity(intent1);
                return false;
            }
        });

        refreshView = (SwipeRefreshLayout) findViewById(R.id.refreshView);
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

                BasicRentAdapterBn.notifyDataSetChanged();


                adapterAgentBs.notifyDataSetChanged();

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

        btnWomen = (Button) findViewById(R.id.btnWomen);
        btnWomen.setOnClickListener(this);
        btnBachelor = (Button) findViewById(R.id.btnBachelor);
        btnBachelor.setOnClickListener(this);
        btnAgent = (Button) findViewById(R.id.btnAgent);
        btnAgent.setOnClickListener(this);
        btnFamily = (Button) findViewById(R.id.btnFamily);
        btnFamily.setOnClickListener(this);
        btnGirls = (Button) findViewById(R.id.btnGirls);
        btnGirls.setOnClickListener(this);
        btnStudent = (Button) findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(this);
        btnOthers = (Button) findViewById(R.id.btnOthers);
        btnOthers.setOnClickListener(this);


        toursad = new ArrayList<AndroidVersion>();
        rentAgent = new ArrayList<AndroidVersiona>();
        rentAgentbachelor = new ArrayList<AndroidVersiona>();
        rentAgentgirls = new ArrayList<AndroidVersiona>();
        rentAgentwomen = new ArrayList<AndroidVersiona>();
        rentAgentstudent = new ArrayList<AndroidVersiona>();
        rentAgentsell = new ArrayList<AndroidVersiona>();
        rentAgentothers = new ArrayList<AndroidVersiona>();
        rentAgentfamily = new ArrayList<AndroidVersiona>();

        adapterAgentRentbachelor = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentbachelor);
        adapterAgentRentgirls = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentgirls);
        adapterAgentRentwomen = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentwomen);
        adapterAgentRentstudent = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentstudent);
        adapterAgentRentsell = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentsell);
        adapterAgentRentothers = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentothers);
        adapterAgentRentfamily = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgentfamily);

        BasicRentAdapterBn = new BasicRentAdapterBn(BasicHomeOffline.this, rentAgent);


        adapterAgentBs = new AdapterAgentBs(BasicHomeOffline.this, toursad);

        // adapter = new DataAdapter(data);
        recyclerbachelor = (RecyclerView) findViewById(R.id.recyclerbachelor);
        recyclerbachelor.setAdapter(adapterAgentRentbachelor);

        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAgentBs);

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
        initInstancesDrawer();
        getAddData();
        //ParseAllData();
        runLayoutAnimation(recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls, recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void refreshData() {

        ParseAllData();
    }

    private void runLayoutAnimation(RecyclerView recyclerbachelor,
                                    RecyclerView recyclerAgent, RecyclerView recyclerFamily,
                                    RecyclerView recyclerGirls, RecyclerView recyclerStudent,
                                    RecyclerView recyclerWomen, RecyclerView recyclerOthers,
                                    RecyclerView recyclerViewSell) {

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);

        recyclerbachelor.setLayoutAnimation(controller);
        recyclerbachelor.getAdapter().notifyDataSetChanged();
        recyclerbachelor.scheduleLayoutAnimation();

        recyclerAgent.setLayoutAnimation(controller);
        recyclerAgent.getAdapter().notifyDataSetChanged();
        recyclerAgent.scheduleLayoutAnimation();


        recyclerFamily.setLayoutAnimation(controller);
        recyclerFamily.getAdapter().notifyDataSetChanged();
        recyclerFamily.scheduleLayoutAnimation();


        recyclerGirls.setLayoutAnimation(controller);
        recyclerGirls.getAdapter().notifyDataSetChanged();
        recyclerGirls.scheduleLayoutAnimation();


        recyclerWomen.setLayoutAnimation(controller);
        recyclerWomen.getAdapter().notifyDataSetChanged();
        recyclerWomen.scheduleLayoutAnimation();


        recyclerOthers.setLayoutAnimation(controller);
        recyclerOthers.getAdapter().notifyDataSetChanged();
        recyclerOthers.scheduleLayoutAnimation();


        recyclerViewSell.setLayoutAnimation(controller);
        recyclerViewSell.getAdapter().notifyDataSetChanged();
        recyclerViewSell.scheduleLayoutAnimation();



        recyclerStudent.setLayoutAnimation(controller);
        recyclerStudent.getAdapter().notifyDataSetChanged();
        recyclerStudent.scheduleLayoutAnimation();



    }

    private void getAddData() {
        /**
         *  SharedPreferences.Editor editor2 = getSharedPreferences("sopno_bache", MODE_PRIVATE).edit();
         *                 editor2.putString("bachelorJ", String.valueOf(bachelorJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_selle", MODE_PRIVATE).edit();
         *                 editor2.putString("sellJ", String.valueOf(sellJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_fame", MODE_PRIVATE).edit();
         *                 editor2.putString("familyJ", String.valueOf(familyJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_girle", MODE_PRIVATE).edit();
         *                 editor2.putString("girlsJ", String.valueOf(girlsJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_studente", MODE_PRIVATE).edit();
         *                 editor2.putString("studentJ", String.valueOf(studentJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_womene", MODE_PRIVATE).edit();
         *                 editor2.putString("womenJ", String.valueOf(womenJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_othere", MODE_PRIVATE).edit();
         *                 editor2.putString("otherJ", String.valueOf(otherJ));
         *
         * JSONArray sellJ, womenJ, otherJ, studentJ, girlsJ,familyJ,bachelorJ;
         *     String sellJ1, womenJ1, otherJ1, studentJ1, girlsJ1,familyJ1,bachelorJ1;
         */
        SharedPreferences prf1 = getSharedPreferences("sopno_bache",MODE_PRIVATE);
        SharedPreferences prf2 = getSharedPreferences("sopno_selle",MODE_PRIVATE);
        SharedPreferences prf3 = getSharedPreferences("sopno_fame",MODE_PRIVATE);
        SharedPreferences prf4 = getSharedPreferences("sopno_girle",MODE_PRIVATE);
        SharedPreferences prf5 = getSharedPreferences("sopno_studente",MODE_PRIVATE);
        SharedPreferences prf6 = getSharedPreferences("sopno_womene",MODE_PRIVATE);
        SharedPreferences prf7 = getSharedPreferences("sopno_othere",MODE_PRIVATE);

        if(prf1.contains("bachelorJ") && prf1.contains("aId")) {
            bachelorJ1  = prf1.getString("bachelorJ", null);
            bachelorJd  = prf1.getString("aId", null);

            if (bachelorJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = bachelorJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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


                    rentAgentbachelor.add(tourk);
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentbachelor.notifyDataSetChanged();
            }

        }

        if(prf2.contains("sellJ") && prf2.contains("aId")) {
            sellJ1  = prf2.getString("sellJ", null);
            sellJd  = prf2.getString("aId", null);
            if (sellJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = sellJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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

                        rentAgentsell.add(tourk);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentsell.notifyDataSetChanged();
            }
        }

        if(prf3.contains("familyJ") && prf3.contains("aId")) {
            familyJ1  = prf3.getString("familyJ", null);
            familyJd  = prf3.getString("aId", null);

            if (familyJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = familyJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentfamily.notifyDataSetChanged();
            }
        }

        if(prf4.contains("girlsJ") && prf4.contains("aId")) {
            girlsJ1  = prf4.getString("girlsJ", null);
            girlsJd  = prf4.getString("aId", null);
            if (girlsJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = girlsJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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

                        rentAgentgirls.add(tourk);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentgirls.notifyDataSetChanged();
            }
        }

        if(prf5.contains("studentJ") && prf5.contains("aId")) {
            studentJ1  = prf5.getString("studentJ", null);
            studentJd  = prf5.getString("aId", null);
            if (studentJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = studentJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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

                        rentAgentstudent.add(tourk);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentstudent.notifyDataSetChanged();
            }
        }

        if(prf6.contains("womenJ") && prf6.contains("aId")) {
            womenJ1  = prf6.getString("womenJ", null);
            womenJd  = prf6.getString("aId", null);

            if (womenJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = womenJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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


                        rentAgentwomen.add(tourk);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentwomen.notifyDataSetChanged();
            }

        }

        if(prf7.contains("bachelorJ") && prf7.contains("aId")) {
            otherJ1  = prf7.getString("otherJ", null);
            otherJd  = prf7.getString("aId", null);

            if (otherJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = otherJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentRentothers.notifyDataSetChanged();
            }
        }
    }

    private void ParseAllData() {

        /**
         *  SharedPreferences.Editor editor2 = getSharedPreferences("sopno_bache", MODE_PRIVATE).edit();
         *                 editor2.putString("bachelorJ", String.valueOf(bachelorJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_selle", MODE_PRIVATE).edit();
         *                 editor2.putString("sellJ", String.valueOf(sellJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_fame", MODE_PRIVATE).edit();
         *                 editor2.putString("familyJ", String.valueOf(familyJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_girle", MODE_PRIVATE).edit();
         *                 editor2.putString("girlsJ", String.valueOf(girlsJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_studente", MODE_PRIVATE).edit();
         *                 editor2.putString("studentJ", String.valueOf(studentJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_womene", MODE_PRIVATE).edit();
         *                 editor2.putString("womenJ", String.valueOf(womenJ));
         *                 SharedPreferences.Editor editor2 = getSharedPreferences("sopno_othere", MODE_PRIVATE).edit();
         *                 editor2.putString("otherJ", String.valueOf(otherJ));
         *
         *
         */


        ProgressDialog pDialog = new ProgressDialog(BasicHomeOffline.this);



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
            case R.id.ibSearch:
                String m_Text = etSearch.getText().toString();
                Intent intent1 = new Intent(getApplicationContext(), BasicSearch.class);
                intent1.putExtra("spot",m_Text);
                startActivity(intent1);
                break;

            case R.id.ivWomen:
                Swomen();
                break;

            case R.id.btnBachelor:
                Sbachelor();
                break;
            case R.id.ivBachelor:
                Sbachelor();
                break;
            case R.id.btnFamily:
                Sfamily();
                break; case R.id.ivFamily:
                Sfamily();
                break;
            case R.id.btnGirls:
                Sgstudent();
                break;  case R.id.ivGirls:
                Sgstudent();
                break;
            case R.id.btnStudent:
                Sstudent();
                break; case R.id.ivStudent:
                Sstudent();
                break;
            case R.id.btnOthers:
                Sothers();
                break;case R.id.ivOthers:
                Sothers();
                break;
            case R.id.btnAgent:
                SAgent();
                break;

        }
    }

    private void SAgent() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAgentAllList.class);
        intent1.putExtra("spot","agent");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Swomen() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","women");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sfamily() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","family");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sothers() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","others");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sbachelor() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","bachelor");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sgstudent() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","gstudent");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sstudent() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","student");
        intent1.putExtra("caten","rent");
        startActivity(intent1);
    }

    private void Sarent() {
        Intent intent1 = new Intent(getApplicationContext(), BasicAddList.class);
        intent1.putExtra("spot","all");
        intent1.putExtra("caten","rent");
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
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
                //Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
              //  Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
              //  Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
            adapterAgentBs.notifyDataSetChanged();
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
              //  Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
         //   Intent intent = new Intent(this, Login.class);
          //  startActivity(intent);
          //  finish();


        } else{
           // Toast.makeText(getApplicationContext(), "You are not signin yet! Please login again", Toast.LENGTH_LONG).show();

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Your are not signin yet", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BasicHomeOffline.this, Login.class);
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
                    BasicHomeOffline.class);
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
                        BasicHomeOffline.class);
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
