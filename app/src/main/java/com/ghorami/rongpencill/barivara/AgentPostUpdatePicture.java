package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AgentPostUpdatePicture extends AppCompatActivity {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    final Context c = this;
    TextView PostAd, EditPro;

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> tours;
    private AgentPostPicAdapter adapter;
    Context context = null;
    private Button mbutton;
    String mobilenumber, sopnoid, category, iPass, uname, uimage, ulocation1, uaddress1, uemail1, encoded_string;

    String url = "http://barivara.com/searchImg.php";
    ImageView profileImage;
    TextView UserId;
    ImageView uImageV;
    String Usid;
    String usid, adserial, sopnoi, ad_type;
    String usid1, adserial1, sopnoi1, ad_type1, productPic;

    TextView tvuName, tvsid, uAddress, uLocation, uMobile, uEmail;
    String   tMessage=null; String  tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(height > width) setContentView(R.layout.agent_all_post);
        else setContentView(R.layout.agent_all_post_wide);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);


        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
        profileImage = (ImageView)findViewById(R.id.imageView);
        tvuName = (TextView)findViewById(R.id.tvuName);
        tvsid = (TextView)findViewById(R.id.tvsid);
        tvuName.setTypeface(tf);
        initInstancesDrawer();
        getUserData();
        setListViewAdapter();
        Intent i = getIntent();
        usid1 = i.getStringExtra("usid");
        sopnoi1 = i.getStringExtra("sopnoi");
        adserial1 = i.getStringExtra("adserial");
        productPic = i.getExtras().getString("userpic");

        if(productPic.equals("")){
            Toast.makeText(getApplicationContext(), "Woops! something is wrong", Toast.LENGTH_LONG).show();

        }else{
            new JSONAsyncTask1().execute(url);
            setUserData();
        }

    }

    private void setUserData() {

        if(uImage1.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(profileImage);
        }

        tvuName.setText(uName1);
        tvsid.setText(Sopnoid1);


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
            //setUserData();

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
        toolh.setText("all picture");
        //toolh.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);

        /** try {
             URL url = new URL(uImage1);
             Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
             Drawable image2 = new BitmapDrawable(context.getResources(), bitmap);
             //  YOUR_LAYOUT.image.setBackground(image);
                toolh.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);
             //  toolh.setBackgroundDrawable(image2);
             //toolh.setCompoundDrawables(image2, null, null, null);
         } catch (Exception e) {
             e.printStackTrace();
         }
 **/
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


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

    protected class JSONAsyncTask1 extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentPostUpdatePicture.this);


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

                //------------------>>



                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("t_spot", productPic));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("sidMobile", uMobile1));
                Log.e("adsrl", "success");
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
                        //  arent = object.getString("rent");
                        AndroidVersion tour = new AndroidVersion();



                        tour.setPimage(object.getString("image"));

                      //  tour.setAdSerial(object.getString("adserial"));
                       // tour.setSopnoid(object.getString("sopnoid"));

                        tour.setBatch(usid1);
                        tour.setAdSerial(adserial1);
                        tour.setBatch_2(Sopnoid1);
                        tour.setName(uName1);
                        tour.setImageA(uImage1);
                        Log.e("success", uName1);

                        tours.add(tour);
                    }
                    return true;
                }

                //------------------>>

            } catch (android.net.ParseException e1) {
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
            //  setData();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }




    private void setListViewAdapter() {
        tours = new ArrayList<AndroidVersion>();

        adapter = new AgentPostPicAdapter(getApplicationContext(),tours);
        // adapter = new DataAdapter(data);
        tours.clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
                AgentDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
