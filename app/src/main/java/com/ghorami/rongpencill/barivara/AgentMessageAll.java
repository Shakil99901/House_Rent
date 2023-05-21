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
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
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

public class AgentMessageAll extends AppCompatActivity {

    private ArrayList<AndroidVersion> toursad;

    private AdapterAgentMessageList adapterAgentMessageList;
    final Context context = this;
    String  Cid, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String tMessage=null;
    String urlagent = "http://barivara.com/api/getAllMessage.php";

    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    TextView tvNotify;
    SwipeRefreshLayout refreshView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_all_list);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        tvNotify = (TextView)findViewById(R.id.tvNotify);
        getUserData();
        setUserData();
        toursad = new ArrayList<AndroidVersion>();
        adapterAgentMessageList = new AdapterAgentMessageList(AgentMessageAll.this,toursad);
        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAgentMessageList);
        getMessageData();

        runLayoutAnimation(recyclerAgent);

        refreshView = (SwipeRefreshLayout) findViewById(R.id.refreshView);
        refreshView.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Load data to your RecyclerView
  adapterAgentMessageList.notifyDataSetChanged();

                toursad.clear();
                new JSONAsyncTaskAgent().execute(urlagent);
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerAgent) {
        final Context context = recyclerAgent.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerAgent.setLayoutAnimation(controller);
        recyclerAgent.getAdapter().notifyDataSetChanged();
        recyclerAgent.scheduleLayoutAnimation();
    }

    public void getMessageData(){
        SharedPreferences prfm = getSharedPreferences("sopno_message",MODE_PRIVATE);
        if(prfm.contains("messageJ") && prfm.contains("aId")) {
         String   messageJ1  = prfm.getString("messageJ", null);
          String   messageJId  = prfm.getString("aId", null);
          String   messageCId  = prfm.getString("Cid", null);

            if (messageJ1!="") {
                //  HttpEntity entity = response.getEntity();
                String data = messageJ1;
                Log.v("kkk", data);

                JSONObject jsonoj = null;
                try {
                    jsonoj = new JSONObject(data);

                    JSONArray jarrayj = jsonoj.getJSONArray("message");

                    for (int j = 0; j < 1; j++) {
                        JSONObject object = jarrayj.getJSONObject(j);
                        Cid = object.getString("cid");
                        Log.v("dk", Cid);
                    }
                    for (int i = 0; i < jarrayj.length(); i++) {
                        JSONObject object = jarrayj.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersion tour = new AndroidVersion();
                        tour.setSenderName(object.getString("sender_name"));
                        tour.setCourse(object.getString("cid"));
                        tour.setSenderImage(object.getString("sender_image"));
                        tour.setMessage(object.getString("message"));
                        tour.setSender(object.getString("sender"));
                      //  tour.setReceiverFile(object.getString("send_file"));
                        // tour.setReceiverName(uName1);
                        // tour.setReceiverImage(uImage1);
                        //tour.setReceiver(Sopnoid1);
                        tour.setSenderTime(object.getString("send_times"));
                       // tour.setReceiverTime(object.getString("receive_time"));
                        //tour.setMessageState(object.getString("message_states"));
                        tour.setCourse_code((Sopnoid1));
                        toursad.add(tour);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterAgentMessageList.notifyDataSetChanged();
            }

        }else{
            new JSONAsyncTaskAgent().execute(urlagent);
        }

    }

    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentMessageAll.this);
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
                nameValuePairs.add(new BasicNameValuePair("type", uType1));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("mobilenumber", uMobile1));
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
                    JSONArray jarray = jsono.getJSONArray("message");
                    for (int j = 0; j < 1; j++) {
                        JSONObject object = jarray.getJSONObject(j);
                        Cid = object.getString("cid");
                        Log.v("dk", Cid);
                    }
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersion tour = new AndroidVersion();
                        tour.setSenderName(object.getString("sender_name"));
                        tour.setCourse(object.getString("cid"));
                        tour.setSenderImage(object.getString("sender_image"));
                        tour.setMessage(object.getString("message"));
                        tour.setSender(object.getString("sender"));
                      //  tour.setReceiverFile(object.getString("send_file"));
                       // tour.setReceiverName(uName1);
                       // tour.setReceiverImage(uImage1);
                        //tour.setReceiver(Sopnoid1);
                        tour.setSenderTime(object.getString("send_times"));
                      //  tour.setReceiverTime(object.getString("receive_time"));
                       // tour.setMessageState(object.getString("message_states"));
                        tour.setCourse_code((Sopnoid1));
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
            pDialog.dismiss();
            adapterAgentMessageList.notifyDataSetChanged();

            if (result == false)
             //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha", "Error on data load");

        }
    }




    private void setUserData() {


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
        toolh.setText("all message");
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_message, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                Intent intent = new Intent(getApplicationContext(),
                        AgentMessageList.class);
                // String query = intent.getStringExtra(SearchManager.APP_DATA);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("sQuery", query);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }




    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            finish();

            //onBackHome();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }
}
