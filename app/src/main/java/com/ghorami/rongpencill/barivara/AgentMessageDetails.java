package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentMessageDetails extends AppCompatActivity {
    JSONArray jarray, agentArray;
    String chatArray, oldk=null, newk=null;
    int chatlength;
    JSONArray jsrrayNew;
    ImageView ivSend;
    String newChatArray;
    private EditText editText;
    String  uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
String date;
    private ImageButton btnSend, btnAttach;
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

    String sAgentPic, getsAgentName, sAgentID, sCid;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Handler handler;
    Runnable runnable;
    String tMessage=null;
    String mType=null;
    String sUrl, encoded_string;
    TextView tvNam, tvId, tvTyp, tvIma;
    private static final int SELECT_PHOTO = 100;
    Button upload, cancel;
    private File filek;
    Bitmap  bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_chat);
        new CommentKeyBoardFix(this);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        tvId21 = (TextView)findViewById(R.id.tvId21);
        tvIma = (TextView)findViewById(R.id.tvIma);
        tvId = (TextView)findViewById(R.id.tvId);
        tvNam = (TextView)findViewById(R.id.tvNam);
        tvTyp = (TextView)findViewById(R.id.tvTyp);
        ivSend = (ImageView) findViewById(R.id.ivSend);

        Intent i = getIntent();
        sAgentID = i.getStringExtra("agentID");
        sAgentName = i.getStringExtra("agentN");
        sAgentPic = i.getStringExtra("imageAgent");
        mType = i.getStringExtra("mType");



        //tvId.setText(sAgentID);
        //tvNam.setText(sAgentName);
        //tvIma.setText(sAgentPic);
      //  tvTyp.setText(mType);
       // toursad.clear();
        //adapterReceiveMessage.notifyDataSetChanged();


        DateFormat df = new SimpleDateFormat("dMyyyyHHmm", Locale.ENGLISH);
        date = df.format(Calendar.getInstance().getTime());

        getUserData();

        toursad = new ArrayList<AndroidVersion>();
        adapterReceiveMessage = new AdapterReceiveMessage(AgentMessageDetails.this,toursad);
        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
      //  LinearLayoutManager layoutManager=
        //        new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        //layoutManager.setStackFromEnd(true);
        //recyclerAgent.setLayoutManager(layoutManager);
                  recyclerAgent.setAdapter(adapterReceiveMessage);
       // int resId = R.anim.layout_animation_from_bottom;
       // LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
        //recyclerAgent.setLayoutAnimation(animation);
        //recyclerAgent.smoothScrollToPosition(1);
       // recyclerAgent.scrollTo(1, 1);
        recyclerAgent.scrollToPosition(toursad.size() - 1);

        editText = (EditText) findViewById(R.id.editText);
        btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnAttach = (ImageButton)findViewById(R.id.btnAttach);

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getApplicationContext(), "your ID " +sAgentID, Toast.LENGTH_LONG).show();

                PostMessage();
            }
        });

        if(sAgentID.equals("")){
            Toast.makeText(getApplicationContext(), "woops something is wrong", Toast.LENGTH_LONG).show();

            finish();
        }else{
            initInstancesDrawer();
            tvId21.setText(sAgentID);
            toursad.clear();
            adapterReceiveMessage.notifyDataSetChanged();
            if(mType.equals("oldMessage")){
                new JSONAsyncTaskAgent().execute(urlagent);
                //handler.removeCallbacksAndMessages(null);

                handler = new Handler();
                runnable = new Runnable() {
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


            }else if(mType.equals("newMessage")){

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                       // Intent i = new Intent(AgentMessageDetails.this,AgentMessageDetails.class);
                      //  startActivity(i);
                       // finish();
                        new JSONAsyncTaskAgent().execute(urlagent);

                    }
                };
                handler.post(runnable);

// use this when you don't want callback to be called anymore
                handler.removeCallbacks(runnable);

            }
        }

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




    private void pickImage() {
        //   Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  getFileUri();
        //  i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
        // startActivityForResult(i, 10);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        getFileUri();
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }
    private void getFileUri() {

        //  getNumber = tvSid.getText().toString();
        //  String getSerial = String.valueOf(adsL.getSelectedItem()).toString();
        filek = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + date
        );

        file_uri = Uri.fromFile(filek);


    }
    // Check Validation Method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    yourSelectedImage = BitmapFactory.decodeStream(imageStream);


                    Matrix m = new Matrix();
                    m.setRectToRect(new RectF(0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight()), new RectF(0, 0, 800, 1280), Matrix.ScaleToFit.CENTER);
                    bitmap = Bitmap.createBitmap(yourSelectedImage, 0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight(), m, true);

                    ivSend.setImageBitmap(bitmap);// To display selected image in image view
                    ivSend.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // bitmap.recycle();

                    byte[] array = stream.toByteArray();
                    encoded_string = Base64.encodeToString(array, 0);
                    //  etAbout.setText(encoded_string);
                    //   new Encode_image().execute();

                }
        }
    }



    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        TextView toolid = (TextView)findViewById(R.id.tool_id);
        ImageView iProfile = (ImageView) findViewById(R.id.iPro);
        toolh.setText(sAgentName);
        toolid.setText(sAgentID);
        if(sAgentPic.isEmpty()){
            iProfile.setImageResource(R.drawable.ic_launcher);
        }else {
            Picasso.get().load(sAgentPic).transform(new CircleTransform()).into(iProfile);

        }

        iProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sAgentID.equals("0")||sAgentID.equals("")){
                    Toast.makeText(getApplicationContext(), "You have no active Message here", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent4 = new Intent(getApplicationContext(), AllOwnerProfile.class);
                    intent4.putExtra("LandLordId", sAgentID);
                    intent4.putExtra("tenantId", Sopnoid1);
                    startActivity(intent4);
                }
            }
        });

        toolh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sAgentID.equals("0")||sAgentID.equals("")){
                    Toast.makeText(getApplicationContext(), "You have no active Message here", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent4 = new Intent(getApplicationContext(), AllProfileGeneral.class);
                    intent4.putExtra("LandLordId", sAgentID);
                    intent4.putExtra("tenantId", Sopnoid1);
                    startActivity(intent4);
                }
            }
        });



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


    private void sendInfo() {
        final String urlRecord = "http://barivara.com/api/chatState_Post.php";
        final String AdSerial, DeviceIP, CurrentDate, UserID, adType, USID, DSID, UserMobile, VisitValue;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;
        DeviceIP = manufacturer + "-"+model;

        sCid=oldk;

        DSID = DeviceIP +"-"+version+"-"+versionRelease;
        UserMobile=uMobile1.toString();
        VisitValue="1".toString();
        DateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss", Locale.ENGLISH);
        CurrentDate = df.format(Calendar.getInstance().getTime());


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("ReceiverId", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("DeviceIP", DeviceIP));
                nameValuePairs.add(new BasicNameValuePair("DSID", DSID));
                nameValuePairs.add(new BasicNameValuePair("Cid", sCid));
                nameValuePairs.add(new BasicNameValuePair("SenderId", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("UserMobile", UserMobile));
                nameValuePairs.add(new BasicNameValuePair("ReceiveDate", CurrentDate));
                nameValuePairs.add(new BasicNameValuePair("StateValue", VisitValue));



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

                Log.e("Data", "Success");//  Toast.makeText(AgentAddDetails.this, "", Toast.LENGTH_LONG).show();
                /**     Intent intent = new Intent(getApplicationContext(),
                 AgentDashboard.class);
                 intent.putExtra("Password", uPass1);
                 intent.putExtra("MobileNumber", uMobile1);
                 startActivity(intent);
                 **/

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(newk, DeviceIP, CurrentDate, Sopnoid1, sAgentID, DSID, UserMobile, VisitValue);

    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    private void PostMessage() {
        agentId = tvId21.getText().toString();
        sMessage = editText.getText().toString();


        if(sMessage.equals("")) {
            editText.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }else{
            List<String> extractedUrls = extractUrls(String.valueOf((sMessage.replace("\\\n", System.getProperty("line.separator")))));

            for (String url : extractedUrls)
            {
                // System.out.println(url);
                Log.e("log_url", url);

                if(url!=("")){
                    if (Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

                    URLConnection connection = null;
                    try {
                        connection = new URL(url).openConnection();
                        String contentType = connection.getHeaderField("Content-Type");
                        if((contentType.startsWith("image/"))){
                            sUrl=url.toString();
                        }else{
                           sUrl="";
                        }
                        //boolean image = contentType.startsWith("image/");

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
            InsertData();
            editText.getText().clear(); //or you can use editText.setText("");
            //ivSend.clearFocus();

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
            tMessage = prf.getString("uMessage1", null);

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
                        if(object.getString("cid").equals("")){
                            oldk="0";
                        }else{
                            oldk = object.getString("cid");
                            Log.v("dk", oldk);
                        }

                    }

                    for (int i = 0; i < 60; i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        chatlength= jarray.length();
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");

                        AndroidVersion tour = new AndroidVersion();

                        tour.setMessage(object.getString("message"));
                        tour.setSender(object.getString("sender"));
                        tour.setCourse_code(object.getString("cid"));
                        tour.setReceiverFile(object.getString("send_file"));
                        tour.setSenderImage(sAgentPic);
                        tour.setSenderName(sAgentName);
                        tour.setReceiverImage(uImage1);
                        tour.setSenderTime(object.getString("send_times"));
                        tour.setReceiverTime(object.getString("receive_time"));
                       tour.setMessageState(object.getString("message_states"));
                        tour.setReceiver(Sopnoid1);
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
            sendInfo();
            if (result == false)
             //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha", "Error on data load");
        }
    }

    protected class JSONAsyncTaskAgentNew extends AsyncTask<String, Void, Boolean> {




        @Override
        protected Boolean doInBackground(String... ur) {
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
                    String datak = EntityUtils.toString(entity);
                    Log.v("kkk", datak);

                    JSONObject jsono = new JSONObject(datak);
                    agentArray = jsono.getJSONArray("resultLast");

                    if(agentArray.equals("")){
                        newk="0";
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 1; i++) {
                                    JSONObject object = null;
                                    try {
                                        object = agentArray.getJSONObject(i);
                                        if(object.getString("cid").equals("")){
                                            newk="0";
                                        }else{
                                            newk = object.getString("cid");
                                            Log.v("datk", newk);
                                        }

                                        Log.v("datk", newk);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
                        return true;
                    }
                    // jarray = jarray.replace("\\", "");


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
            setLastData();
            if (result == false)
             //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha2", "Error on data load2");

        }

        private void setLastData() {
            int newchatlength = agentArray.length();
            double newLen = Double.parseDouble(newk);
            double oldLen = Double.parseDouble(oldk);

            if (newLen > oldLen) {
                   toursad.clear();
                adapterReceiveMessage.notifyDataSetChanged();
                 new JSONAsyncTaskAgent().execute(urlagent);
                //new JSONAsyncTaskAgentLastN().execute(urlagent);
               // sendInfo();
            } else if(newLen < oldLen){
                toursad.clear();
                adapterReceiveMessage.notifyDataSetChanged();
                new JSONAsyncTaskAgent().execute(urlagent);
            } else if (newLen == oldLen){
                //
            }
/**
 if(newk.equals(oldk)||newk.equals("")){
 //

 }else{
 //    toursad.clear();
 adapterReceiveMessage.notifyDataSetChanged();
 //  new JSONAsyncTaskAgent().execute(urlagent);
 new JSONAsyncTaskAgentLastN().execute(urlagent);
 //  new JSONAsyncTaskAgentLastAdd().execute(urlagent);
 }
 **/
        }

    }



    protected class JSONAsyncTaskAgentLastN extends AsyncTask<String, Void, Boolean> {




        @Override
        protected Boolean doInBackground(String... ursr) {
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

                    for (int j = 0; j < 1; j++) {
                        JSONObject object = jarray.getJSONObject(j);
                        oldk = object.getString("cid");
                        Log.v("dk", oldk);
                    }

                    for (int k = 0; k < 1; k++) {
                        JSONObject object = jarray.getJSONObject(k);
                        chatlength= jarray.length();
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");

                        AndroidVersion tour = new AndroidVersion();
                        tour.setMessage(object.getString("message"));
                        tour.setSender(object.getString("sender"));
                        tour.setCourse_code(object.getString("cid"));
                        tour.setSenderImage(sAgentPic);
                        tour.setSenderName(sAgentName);
                        tour.setReceiverImage(uImage1);
                        tour.setSenderTime(object.getString("send_times"));
                        tour.setReceiver(Sopnoid1);
                        tour.setReceiverFile(object.getString("send_file"));
                        tour.setReceiverTime(object.getString("receive_time"));
                        tour.setMessageState(object.getString("message_states"));
                        toursad.add(0, tour);
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
          //  adapterReceiveMessage.add(0, userList.get(0));
            adapterReceiveMessage.notifyItemInserted(0);

           // adapterReceiveMessage.notifyDataSetChanged();

            if (result == false)
             //   Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Log.e("fetcha2", "Error on data load3");

        }
    }

    public void InsertData(){


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("receiverID", sAgentID));
                nameValuePairs.add(new BasicNameValuePair("message", sMessage));
                nameValuePairs.add(new BasicNameValuePair("rName", sAgentName));
                nameValuePairs.add(new BasicNameValuePair("rPicture", sAgentPic));
                nameValuePairs.add(new BasicNameValuePair("senderID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("sName", uName1));
                nameValuePairs.add(new BasicNameValuePair("sImage", uImage1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("sfile", sUrl));
                nameValuePairs.add(new BasicNameValuePair("encoded_string", encoded_string));



                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://barivara.com/aChatBot.php");

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

            //    Toast.makeText(AgentMessageDetails.this, "Send message", Toast.LENGTH_LONG).show();

                      //  adapterReceiveMessage.notifyItemRangeChanged();
                        //  android.clear(); //here items is an ArrayList populating the RecyclerView
                        // android.addAll(AndroidVersion.list);
                        toursad.clear();
                        adapterReceiveMessage.notifyDataSetChanged();
                        new JSONAsyncTaskAgent().execute(urlagent);


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sUrl, sMessage, sAgentID, encoded_string, Sopnoid1, uMobile1, sAgentName, sAgentPic, uName1, uImage1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     //  android.os.Process.killProcess(android.os.Process.myPid());
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new
                        Intent(AgentMessageDetails.this,AgentMessageAll.class);
                startActivity(i);
                finish();
            }
        };
        handler.post(runnable);

// use this when you don't want callback to be called anymore
        handler.removeCallbacks(runnable);
      finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_blink, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onStop();
            handler.removeCallbacks(runnable);
            android.os.Process.killProcess(android.os.Process.myPid());
            onBackPressed();
            finish();

            //onBackHome();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }
}
