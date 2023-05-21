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
import android.widget.Toast;

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

public class BasicHome extends AppCompatActivity implements View.OnClickListener {

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
    ImageView ivBachelor, ivGirls, ivWomen, ivFamily, ivStudent, ivOthers;

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


    Button btnWomen, btnBachelor, btnAgent, btnFamily, btnGirls, btnStudent, btnOthers;
    TextView tvBachelor, tvAgent, tvFamily, tvGirls, tvStudent, tvWomen, tvOthers;
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    SwipeRefreshLayout refreshView;

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


        //  getUserData();
        setUserData();

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

        adapterAgentRentbachelor = new BasicRentAdapterBn(BasicHome.this, rentAgentbachelor);
        adapterAgentRentgirls = new BasicRentAdapterBn(BasicHome.this, rentAgentgirls);
        adapterAgentRentwomen = new BasicRentAdapterBn(BasicHome.this, rentAgentwomen);
        adapterAgentRentstudent = new BasicRentAdapterBn(BasicHome.this, rentAgentstudent);
        adapterAgentRentsell = new BasicRentAdapterBn(BasicHome.this, rentAgentsell);
        adapterAgentRentothers = new BasicRentAdapterBn(BasicHome.this, rentAgentothers);
        adapterAgentRentfamily = new BasicRentAdapterBn(BasicHome.this, rentAgentfamily);

        BasicRentAdapterBn = new BasicRentAdapterBn(BasicHome.this, rentAgent);


        adapterAgentBs = new AdapterAgentBs(BasicHome.this, toursad);

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

        ParseAllData();
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

    private void ParseAllData() {

        ProgressDialog pDialog = new ProgressDialog(BasicHome.this);



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
            if (result == false){

            }
            //    Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
            if (result == false){

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
            if (result == false){

            }
              //  Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
            if (result == false){

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
            if (result == false){

            }
               // Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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

            }
             //   Toast.makeText(getApplicationContext(), "oops! data network is slow", Toast.LENGTH_LONG).show();

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
