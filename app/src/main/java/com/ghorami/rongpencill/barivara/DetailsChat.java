package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsChat extends AppCompatActivity {
    JSONArray jarray, agentArray;
    String chatArray, oldk, newk;
    int chatlength;
    JSONArray jsrrayNew;
    String newChatArray;
    private EditText editText;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String tMessage=null;
    private ImageButton btnSend;
    String sMessage, sReceiver, sReceiverImg, agentId, sAgentName;
    private ArrayList<AndroidVersion> toursad;
    private ArrayList<AndroidVersion> newChatCar;

    private AdapterReceiveMessage adapterReceiveMessage;
    final Context context = this;
    String urlagent = "http://barivara.com/api/chatBotGet.php";
    String urllast = "http://barivara.com/api/chatBotGetLast.php";
    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    TextView tvId21;

    String sAgentPic, getsAgentName, sAgentID;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_chat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        tvId21 = (TextView)findViewById(R.id.tvId21);

        Intent k = getIntent();
        sAgentID = k.getStringExtra("agentID");
        sAgentName = k.getStringExtra("agentN");
        sAgentPic = k.getStringExtra("imageAgent");

        if(sAgentID.equals("")){
            Toast.makeText(getApplicationContext(), "woops something is wrong", Toast.LENGTH_LONG).show();

                finish();
        }else{
            initInstancesDrawer();
            tvId21.setText(sAgentID);

            toursad = new ArrayList<AndroidVersion>();
          //  newChatCar = new ArrayList<AndroidVersion>();
            adapterReceiveMessage = new AdapterReceiveMessage(DetailsChat.this,toursad);
            recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
            recyclerAgent.setAdapter(adapterReceiveMessage);
            int resId = R.anim.layout_animation_fall_down;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
            recyclerAgent.setLayoutAnimation(animation);

// set EndlessScrollListener to load more items with a 3 seconds delay when reached the end of the list


            SharedPreferences prf = getSharedPreferences("chat_details",MODE_PRIVATE);
            if(prf.contains(chatArray)) {

                String string = prf.getString(chatArray, null);
                try {
                    final JSONArray jsrray = new JSONArray(string);

                    for (int i = 0; i < 8; i++) {
                        JSONObject object = jsrray.getJSONObject(i);
                        chatlength= jsrray.length();
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");

                        AndroidVersion tour = new AndroidVersion();

                        tour.setComments(object.getString("Message"));
                        tour.setSopnoid(object.getString("Sender"));
                        tour.setCourse_code(object.getString("cid"));
                        tour.setImageUrl(sAgentPic);
                        tour.setNear(sAgentName);
                        tour.setImage(uImage1);
                        tour.setTimestamp(object.getString("sendtimes"));
                        tour.setCrent(Sopnoid1);
                        toursad.add(tour);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterReceiveMessage.notifyDataSetChanged();

            } else{
                new JSONAsyncTaskAgent().execute(urlagent);
                // Toast.makeText(getApplicationContext(), "your session is expired", Toast.LENGTH_LONG).show();
            }

              //  new JSONAsyncTaskAgent().execute(urlagent);
               // Toast.makeText(getApplicationContext(), "your session is expired", Toast.LENGTH_LONG).show();




            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // sendjsonrequest();
                    //toursad.clear();
                    // adapterReceiveMessage.notifyDataSetChanged();

                    new JSONAsyncTaskAgentNew().execute(urllast);

                    // toursad.clear();
                    //adapterReceiveMessage.notifyDataSetChanged();
                    //new JSONAsyncTaskAgent().execute(urlagent);

                    handler.postDelayed(this,1000);//60 second delay
                }
            };handler.postDelayed(runnable,1000);



        }



        getUserData();


        editText = (EditText) findViewById(R.id.editText);
        btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "your ID " +sAgentID, Toast.LENGTH_LONG).show();

                PostMessage();
            }
        });


       // mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
    /**    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

    }

    /**
     * This method is called when swipe refresh is pulled down
     */


// add a new portion of data, remove progress loader
    // must be run on UI thread
    // we use delay to simulate network call and display progress bar, data comes in 3 seconds





    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        TextView toolid = (TextView)findViewById(R.id.tool_id);
        ImageView iProfile = (ImageView) findViewById(R.id.iPro);
        toolh.setText(sAgentName);
        toolid.setText(sAgentID);
        Picasso.get().load(sAgentPic).transform(new CircleTransform()).into(iProfile);


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


    }

    private void PostMessage() {
        agentId = tvId21.getText().toString();
        sMessage = editText.getText().toString();
        if(sMessage.equals("")) {
            editText.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }else{
            InsertData();
            editText.getText().clear(); //or you can use editText.setText("");

        }
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





        } else{
            Toast.makeText(getApplicationContext(), "your session is expired. Please login again", Toast.LENGTH_LONG).show();
            //moveTaskToBack(true);
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);
               Intent intent = new Intent(this, Login.class);
             startActivity(intent);
            finish();

        }
    }



    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {




        @Override
        protected Boolean doInBackground(String... urlsa) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");
                nameValuePairs.add(new BasicNameValuePair("senderID", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("receiverID", Sopnoid1));
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
                    jarray = jsono.getJSONArray("resultPro");
                    // jarray = jarray.replace("\\", "");
                    SharedPreferences.Editor editorc = getSharedPreferences("chat_details", MODE_PRIVATE).edit();
                    editorc.putString(chatArray, jarray.toString()).commit();
                    editorc.putString("chatArraylength", String.valueOf(jarray.length())).commit();
                    editorc.apply();

                    for (int i = 0; i < 1; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        oldk = object.getString("cid");
                        Log.v("dk", oldk);
                    }

                    for (int i = 0; i < 8; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        chatlength= jarray.length();
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");

                        AndroidVersion tour = new AndroidVersion();

                        tour.setComments(object.getString("Message"));
                        tour.setSopnoid(object.getString("Sender"));
                        tour.setCourse_code(object.getString("cid"));
                        tour.setImageUrl(sAgentPic);
                        tour.setNear(sAgentName);
                        tour.setImage(uImage1);
                        tour.setTimestamp(object.getString("sendtimes"));
                        tour.setCrent(Sopnoid1);
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
            //  pDialog.dismiss();
            adapterReceiveMessage.notifyDataSetChanged();

            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    protected class JSONAsyncTaskAgentNew extends AsyncTask<String, Void, Boolean> {




        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");
                nameValuePairs.add(new BasicNameValuePair("senderID", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("receiverID", Sopnoid1));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urllast);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    agentArray = jsono.getJSONArray("resultLast");
                    // jarray = jarray.replace("\\", "");
                    SharedPreferences.Editor editorc = getSharedPreferences("chat_details", MODE_PRIVATE).edit();
                    editorc.putString(chatArray, jarray.toString()).commit();
                    editorc.putString("chatArraylength", String.valueOf(jarray.length())).commit();
                    editorc.apply();


                    for (int i = 0; i < 1; i++) {
                        JSONObject object = agentArray.getJSONObject(i);
                        newk = object.getString("cid");
                        Log.v("datk", newk);
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
            //  pDialog.dismiss();
           // adapterReceiveMessage.notifyDataSetChanged();
          //  pDialog.dismiss();
            setData();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }


    }

    private void setData() {
        int newchatlength = agentArray.length();
        if(newk.equals(oldk)||newk.equals("")){
            //

        }else{
           // toursad.clear();
           // adapterReceiveMessage.notifyDataSetChanged();
            new JSONAsyncTaskAgentLastAdd().execute(urlagent);
        }

    }


    protected class JSONAsyncTaskAgentLastAdd extends AsyncTask<String, Void, Boolean> {




        @Override
        protected Boolean doInBackground(String... urlsa) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.e("adsrl", "success");
                nameValuePairs.add(new BasicNameValuePair("senderID", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("receiverID", Sopnoid1));
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
                    jarray = jsono.getJSONArray("resultPro");
                    // jarray = jarray.replace("\\", "");
                    SharedPreferences.Editor editorc = getSharedPreferences("chat_details", MODE_PRIVATE).edit();
                    editorc.putString(chatArray, jarray.toString()).commit();
                    editorc.putString("chatArraylength", String.valueOf(jarray.length())).commit();
                    editorc.apply();

                    for (int i = 0; i < 1; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        oldk = object.getString("cid");
                        Log.v("dk", oldk);
                    }

                    for (int i = 0; i < 1; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        chatlength= jarray.length();
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");

                        AndroidVersion tour = new AndroidVersion();

                        tour.setComments(object.getString("Message"));
                        tour.setSopnoid(object.getString("Sender"));
                        tour.setCourse_code(object.getString("cid"));
                        tour.setImageUrl(sAgentPic);
                        tour.setNear(sAgentName);
                        tour.setImage(uImage1);
                        tour.setTimestamp(object.getString("sendtimes"));
                        tour.setCrent(Sopnoid1);
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
            //  pDialog.dismiss();
            adapterReceiveMessage.notifyDataSetChanged();

            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }




    public void InsertData(){


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("receiverID", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("message", sMessage));

                nameValuePairs.add(new BasicNameValuePair("senderID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));



                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://barivara.com/api/ChatBot.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                Log.e("Data", "Success");
                return "Data  send Successfully";

            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(DetailsChat.this, "Send message", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      //  adapterReceiveMessage.notifyItemRangeChanged();
                        //  android.clear(); //here items is an ArrayList populating the RecyclerView
                        // android.addAll(AndroidVersion.list);
                        toursad.clear();
                        adapterReceiveMessage.notifyDataSetChanged();
                        new JSONAsyncTaskAgent().execute(urlagent);

                    }
                }, 1000);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sMessage, sAgentID, Sopnoid1, uMobile1);
    }


}
