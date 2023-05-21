package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
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

import static android.os.Build.VERSION.SDK_INT;

public class UserMenu extends AppCompatActivity implements View.OnClickListener {


    ImageView ivprofile, ivrent, ivsell, ivnewrent, ivearn, ivallad, ivsearch, iverify, ivAbout, ivmessage, ivshop;
    TextView tvProfile, tvRentAd, tvSellAd, tvNewRent, tvEarn, tvAllAdd, tvSearch, tvVerify, tvSetting, tvLogout, tvAbout;
    TextView tvRentA, tvSellA, tvMessage, tvShop, tvMessageAd;
    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    Toolbar toolbar;
    public Context context;
    CollapsingToolbarLayout collapsingToolbarLayout;
    final Context c = this;
    TextView tvAdL2, EditPro, sellAd;
    ImageView imageView, profileImage;
    Typeface custom_font;
    String message, m_Textreq;
    String urlVerifyReq = "http://barivara.com/api/verifyReq.php";

    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/NotoSansBengali.ttf");
        initInstancesDrawer();
        getUserData();
        ivmessage= (ImageView)findViewById(R.id.ivmessage);
        ivmessage.setOnClickListener(this);
        tvMessage= (TextView)findViewById(R.id.tvMessage);
        tvMessage.setOnClickListener(this);
        tvMessageAd= (TextView)findViewById(R.id.tvMessageAd);
        tvMessageAd.setOnClickListener(this);

        ivprofile= (ImageView)findViewById(R.id.ivprofile);
        ivprofile.setOnClickListener(this);
        ivrent= (ImageView)findViewById(R.id.ivrent);
        ivrent.setOnClickListener(this);

        ivallad= (ImageView)findViewById(R.id.ivallad);
        ivallad.setOnClickListener(this);
        ivsearch= (ImageView)findViewById(R.id.ivsearch);
        ivsearch.setOnClickListener(this);
        iverify= (ImageView)findViewById(R.id.ivverify);
        iverify.setOnClickListener(this);
        ivAbout= (ImageView)findViewById(R.id.ivAbout);
        ivAbout.setOnClickListener(this);

        tvAbout= (TextView)findViewById(R.id.tvAbout);
        tvAbout.setOnClickListener(this);
        tvProfile= (TextView)findViewById(R.id.tvProfile);
        tvProfile.setOnClickListener(this);
        tvRentAd= (TextView)findViewById(R.id.tvRentAd);
        tvRentAd.setOnClickListener(this);

        tvAllAdd= (TextView)findViewById(R.id.tvAllAdd);
        tvAllAdd.setOnClickListener(this);
        tvSearch= (TextView)findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(this);
        tvVerify= (TextView)findViewById(R.id.tvVerify);
        tvVerify.setOnClickListener(this);
        tvSetting= (TextView)findViewById(R.id.tvSetting);
        tvSetting.setOnClickListener(this);
        tvLogout= (TextView)findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);

        tvRentA= (TextView)findViewById(R.id.tvRentA);
        setUserData();
    }

    private void setUserData() {



        if(uImage1.equals ("")){
            ivprofile.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(ivprofile);
        }

        if(tMessage.equals ("")){
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setText(tMessage);
            tvMessage.setVisibility(View.VISIBLE);
        }

        if(uRentAd1.equals ("")){
            tvRentA.setVisibility(View.GONE);
        } else {
            tvRentA.setText(uRentAd1);
            tvRentA.setVisibility(View.GONE);
        }

        if(uSellAd1.equals ("")){
            tvSellA.setVisibility(View.GONE);
        } else {
            tvSellA.setText(uSellAd1);
            tvSellA.setVisibility(View.GONE);
        }

        if(uVerify1.equals ("1")){
            iverify.setBackgroundResource(R.drawable.cerclebackgroundblue);
            tvVerify.setText("verified");

        } else {
            iverify.setBackgroundResource(R.drawable.cerclebackgroundgray);
            tvVerify.setText("not verified");
            iverify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userVerify();
                }
            });
            tvVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userVerify();
                }
            });

        }


        tvProfile.setText(uName1);

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
        toolh.setText("your menu");

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

    @Override
    public void onBackPressed(){
       onDestroy();
    }



    @Override
    public void onClick(View v) {

        //   MainActivity.this.finish();

        /**
         TextView tvProfile, tvRentAd, tvSellAd, tvNewRent, tvEarn, tvAllAdd, tvSearch, tvVerify, tvSetting, tvLogout;

         * **/


        switch (v.getId()) {

            case R.id.tvProfile:
                Profile();
                break;
            case R.id.ivprofile:
                Profile();
                break;

            case R.id.ivmessage:
                goMessage();
                break;
            case R.id.tvMessage:
                goMessage();
                break;
             case R.id.tvMessageAd:
                    goMessage();
                    break;

            case R.id.tvRentAd:
                Rent();
                break;
            case R.id.ivrent:
                Rent();
                break;

            case R.id.ivallad:
                allAd();
                break;
            case R.id.tvAllAdd:
                allAd();
                break;

            case R.id.ivsearch:
                SearchDial();
                break;
            case R.id.tvSearch:
                SearchDial();
                break;
            case R.id.ivAbout:
                userSetting();
                break;
            case R.id.tvAbout:
                userSetting();
                break;



            case R.id.tvLogout:
                userLogout();
                break;

        }
    }

    private void goMessage() {
        Intent intent = new Intent(getApplicationContext(),
                AgentMessageAll.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void userLogout() {
        SharedPreferences preferences =getSharedPreferences("sopno_details",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        //  finish();
        Intent intent1 = new Intent(getApplicationContext(), Login.class);
        startActivity(intent1);
    }

    private void userSetting() {

        Intent intent4 = new Intent(getApplicationContext(), AgentSetting.class);
        startActivity(intent4);

    }

    private void userVerify() {

        VerifyReq();
    }

    private void userStates() {

        Intent intent4 = new Intent(getApplicationContext(), UserStates.class);
        startActivity(intent4);
    }


    private void VerifyReq() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("verify your self");
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        final AutoCompleteTextView input = new AutoCompleteTextView(this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        input.setVisibility(View.GONE);
        // input.setHint("লোকেশন যেমন-মতিঝিল, গুলশান");
        //  input.setText(String.valueOf(TypesS.getSelectedItem()));
        String[] countries = getResources().getStringArray(R.array.location);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        input.setAdapter(adapter);

        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Textreq = "1".toString();

                new JSONAsyncTask().execute(urlVerifyReq);

            }
        });




        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(UserMenu.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... urls) {

            try {



                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("userPass", uPass1));
                nameValuePairs.add(new BasicNameValuePair("Sopnoid1", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("Type", "general"));
                nameValuePairs.add(new BasicNameValuePair("Req", m_Textreq));

                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlVerifyReq);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

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
                        message = object.getString("message");
                        //  pimage = object.getString("pimage");
                        // tvTour.setText(arent);


                        Log.e("success", message);
                        //  Toast.makeText(getApplicationContext(), arent, Toast.LENGTH_LONG).show();


                    }

                    //   Toast.makeText(MainActivity.this, (CharSequence) jarray, Toast.LENGTH_LONG).show();
                    return true;
                }


            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            setData();
            //adapter.notifyDataSetChanged();

            if (result == null)
                Toast.makeText(UserMenu.this, "Unable to send data", Toast.LENGTH_LONG).show();

        }

        private void setData() {
            if(message.equals("success")){
                // sendMail();
                //   Intent Classr = new Intent(AgentDashboard.this, AgentEarnPoint.class);
                // startActivity(Classr);
                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }


    private void SearchDial() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("বাড়ির খোঁজে");
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        final AutoCompleteTextView input = new AutoCompleteTextView(this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        input.setHint("search home for rent");
        //  input.setText(String.valueOf(TypesS.getSelectedItem()));
        String[] countries = getResources().getStringArray(R.array.location);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        input.setAdapter(adapter);

        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                Intent intent1 = new Intent(getApplicationContext(), AgentSearch.class);
                intent1.putExtra("spot",m_Text);
                startActivity(intent1);
            }
        });

        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });

/**
 builder.setNegativeButton("বিস্তারিত খোঁজ", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {

Intent intent1 = new Intent(getApplicationContext(), AgentSearch.class);
//intent1.putExtra("spot",m_Text);
startActivity(intent1);
}
});
 **/
        builder.show();
    }




    private void allAd() {
        Intent Classr = new Intent(UserMenu.this, UserAddRent.class);
        startActivity(Classr);


    }

    private void adCategory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.adCategory));
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        builder.setNeutralButton("বাতিল হবে", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });

// Set up the buttons
        builder.setPositiveButton(getString(R.string.adRent), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent2 = new Intent(getApplicationContext(), AgentNewAdd.class);
                intent2.putExtra("Category","rent");
                startActivity(intent2);

            }
        });


        builder.setNegativeButton(getString(R.string.adSell), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(getApplicationContext(), AgentNewSellAd.class);
                intent2.putExtra("Category","sell");

                startActivity(intent2);
            }
        });

        builder.show();


    }

    private void Rent(){
        Intent Classr = new Intent(UserMenu.this, UserAddRent.class);
        startActivity(Classr);
    }

    private void Sell(){
        Intent Classr = new Intent(UserMenu.this, UserAddSell.class);
        startActivity(Classr);
    }

    private void Profile() {
        Intent intent4 = new Intent(getApplicationContext(), AgentProfile.class);
        startActivity(intent4);
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
            UserMenu.this.finish();
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

            if(uType1.equals("agent")){
                Intent intent = new Intent(getApplicationContext(),
                        AgentDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                UserMenu.this.finish();
            }else if(uType1.equals("general")){
                Intent intent = new Intent(getApplicationContext(),
                        UserHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                UserMenu.this.finish();
            }else{
                Intent intent = new Intent(getApplicationContext(),
                        BasicHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                UserMenu.this.finish();
            }


            return true;
        }

        if (id == R.id.menue) {
            UserMenu.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
