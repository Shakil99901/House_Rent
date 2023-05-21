package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllTenantNewDetails extends AppCompatActivity {
    ImageView profileImage;
    TextView tenantSl, tvtName, tvtEmail, tvtPhone, tvtProfession, tvtCompany, tvtNID, tvtNationality, tvtAddress,
            tvtDistrict, tvtThana, tentSl, tvtRent, tvtRentForDays, tvtGas, tvtWater, tvtFood, tvtService, tvtAdvance, tvtGTax,
            tentImage, tvtPerson, tvtBedRoom, tvtKitchen, tvtBathroom, tvtBed, tvtType, tvtFloor, tvtFacility, tvtElectricity;

    Typeface custom_font;
    String  tMessage=null; String tShop, uImage1, uNationality1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uMobileNumber1, uNIDno1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String mobilenumber, sopnoid, category, uname, uimage,  uProfession1, uCompany1, ulocation1, uaddress1, uemail1, encoded_string;
    ImageButton PickPicture;
    String sid, sMobile, sLocation, sEmail, sAddress, iPass, sName, sCompany, sProfession, sNationality,
            sRent, sGas, sWater, sElectricity, sAdvance, sService, sFood, sRentForDays, sTenantImage, sMobileNumber, sNID,
            sPersonProom, sBed, sBath, sKitchen, sBedRoom, sFloor, sTentType, sFacility, sRoomImage, sTent, sTenant, sSID, sGTax1, tType, message, sDistrict
            ;
    int visitCount;
    Context context;
    final Context c = this;
    Button upload, cancel;
    private File file;
    Bitmap bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;


    String urlbr = "http://barivara.com/aTenantInsert.php" ;
    private static final int SELECT_PHOTO = 100;

    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tenant_new_details);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        initInstancesDrawer();
        getUserData();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
       // profileImage = (ImageView)findViewById(R.id.imageView);
       // tvuName = (EditText) findViewById(R.id.tvuName);



        tenantSl=(TextView)findViewById(R.id.tvTentSl);
        tvtName=(TextView)findViewById(R.id.tvtName);
        tvtEmail=(TextView)findViewById(R.id.tvtEmail);
        tvtPhone=(TextView)findViewById(R.id.tvtPhone);
        tvtProfession=(TextView)findViewById(R.id.tvtProfession);
        tvtCompany=(TextView)findViewById(R.id.tvtCompany);
        tvtNID=(TextView)findViewById(R.id.tvtNID);
        tvtNationality=(TextView)findViewById(R.id.tvtNationality);
        tvtAddress=(TextView)findViewById(R.id.tvtAddress);
        tvtDistrict=(TextView)findViewById(R.id.tvtDistrict);
        tvtThana=(TextView)findViewById(R.id.tvtThana);
        tentSl=(TextView)findViewById(R.id.tvTentSl);
        tvtRent=(TextView)findViewById(R.id.tvtRent);
        tvtRentForDays=(TextView)findViewById(R.id.tvtRentForDays);
        tvtGas=(TextView)findViewById(R.id.tvtGas);
        tvtWater=(TextView)findViewById(R.id.tvtWater);
        tvtFood=(TextView)findViewById(R.id.tvtFood);
        tvtService=(TextView)findViewById(R.id.tvtService);
        tvtAdvance=(TextView)findViewById(R.id.tvtAdvance);
        tvtGTax=(TextView)findViewById(R.id.tvtGTax);
        tvtFloor=(TextView)findViewById(R.id.tvtFloor);
        tvtElectricity=(TextView)findViewById(R.id.tvtElectricity);
        tvtGTax=(TextView)findViewById(R.id.tvtGTax);

        tentImage=(TextView)findViewById(R.id.tentImage);
        tvtPerson=(TextView)findViewById(R.id.tvtPerson);
        tvtBedRoom=(TextView)findViewById(R.id.tvtBedRoom);
        tvtKitchen=(TextView)findViewById(R.id.tvtKitchen);
        tvtBathroom=(TextView)findViewById(R.id.tvtBathroom);
        tvtBed=(TextView)findViewById(R.id.tvtBed);
        tvtType=(TextView)findViewById(R.id.tvtType);
        tvtFacility = (TextView)findViewById(R.id.tvtFacility);

        GetData();

        cancel = (Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                onBackPressed();
                finish();
            }
        });

        upload = (Button)findViewById(R.id.btnNext);
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Intent intent = new Intent(AgentProfileEdit.this, AgentProfileEdit.class);
                //intent.putExtra("Sopnoid", Sopnoid1);
                //startActivity(intent);
                SharedPreferences prefs= getPreferences(MODE_PRIVATE);
               visitCount = prefs.getInt("VISIT_COUNT", 0);
                visitCount++;

                if(visitCount == 10){
                    visitCount = 0;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
               sid = sdf.format(Calendar.getInstance().getTime()) +visitCount;



//Store visit count in SharedPreferences
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("VISIT_COUNT",visitCount);
                editor.apply();
                sSID= String.valueOf(visitCount);
                new JSONAsyncTask().execute(urlbr);             //   InsertData();
            }
        });

       // setUserData();

    }



    private void getUserData() {
        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {
            uProfession1  = prf.getString("uProfession1", null);
            uNationality1  = prf.getString("uNationality1", null);
            uCompany1 = prf.getString("uCompany1", null);
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
        toolh.setText("Details");
        try {
            URL url = new URL(uImage1);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Drawable image2 = new BitmapDrawable(context.getResources(), bitmap);
            //  YOUR_LAYOUT.image.setBackground(image);
            //   toolh.setCompoundDrawablesWithIntrinsicBounds(image2, 0, 0, 0);
            //  toolh.setBackgroundDrawable(image2);
            toolh.setCompoundDrawables(image2, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
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


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

    public void GetData(){

        //  String sid, smobile, slocation, semail, saddress;

        //sid = sopnoid.toString();

        Intent i = getIntent();
        sProfession = i.getStringExtra("sProfession");
        sNationality = i.getStringExtra("sNationality");
        sCompany = i.getStringExtra("sCompany");
        sName = i.getStringExtra("sName");
        sLocation = i.getStringExtra("sLocation");
        sEmail = i.getStringExtra("sEmail");
        sAddress = i.getStringExtra("sAddress");
        sNID = i.getStringExtra("sNID");
        sMobileNumber = i.getStringExtra("sMobileNumber");
        sTenantImage = i.getStringExtra("sTenantImage");
        sRent = i.getStringExtra("sRent");
        sFood = i.getStringExtra("sFood");
        sGas = i.getStringExtra("sGas");
        sWater = i.getStringExtra("sWater");
        sElectricity = i.getStringExtra("sElectricity");
        sService = i.getStringExtra("sService");
        sRentForDays = i.getStringExtra("sRentForDays");
        sAdvance = i.getStringExtra("sAdvance");
        sPersonProom = i.getStringExtra("sPersonProom");
        sBed = i.getStringExtra("sBed");
        sBath = i.getStringExtra("sBath");
        sKitchen = i.getStringExtra("sKitchen");
        sBedRoom = i.getStringExtra("sBedRoom");
        sFloor = i.getStringExtra("sFloor");
        sTentType = i.getStringExtra("sTentType");
        sFacility = i.getStringExtra("sFacility");
        sGTax1 = i.getStringExtra("sGTax");
        sDistrict = i.getStringExtra("sDistrict");
        sRoomImage = i.getStringExtra("sRoomImage");
        //uPersonProom, uBed, uBath, uKitchen, uBedRoom, uFloor, uTentType, uFacility

/**
 *  //    tenantSl.setText("");
 *     tvtName.setText(""+sName);
 *
 *         tvtEmail.setText(""+sEmail);
 *         tvtPhone.setText(""+sMobileNumber);
 *         tvtProfession.setText(""+sProfession);
 *         tvtCompany.setText(""+sCompany);
 *         tvtNID.setText(""+sNID);
 *         tvtNationality.setText(""+sNationality);
 *         tvtAddress.setText(""+sAddress);
 *         tvtDistrict.setText(sDistrict);
 *         tvtThana.setText(""+sLocation);
 *      //   tentSl.setText("");
 *         tvtRent.setText(""+sRent);
 *         tvtRentForDays.setText(""+sRentForDays);
 *         tvtGas.setText(""+sGas);
 *         tvtElectricity.setText(""+sElectricity);
 *         tvtGTax.setText(""+sGTax1);
 *         tvtWater.setText(""+sWater);
 *         tvtFood.setText(""+sFood);
 *         tvtService.setText(""+sService);
 *         tvtAdvance.setText(""+sAdvance);
 *         tvtGTax.setVisibility(View.GONE);
 *         tentImage.setText("");
 *         tvtPerson.setText(""+sPersonProom);
 *         tvtBedRoom.setText(""+sBedRoom);
 *         tvtKitchen.setText(""+sKitchen);
 *         tvtBathroom.setText(""+sBath);
 *         tvtBed.setText(""+sBed);
 *         tvtFloor.setText(""+sFloor);
 *         tvtType.setText(""+sTentType);
 *                 tvtFacility.setText(""+sFacility);
 *
 */
    //    tenantSl.setText("");
    tvtName.setText("Name: "+sName);

        tvtEmail.setText("Email: "+sEmail);
        tvtPhone.setText("Mobile: "+sMobileNumber);
        tvtProfession.setText("Profession: "+sProfession);
        tvtCompany.setText("Company: "+sCompany);
        tvtNID.setText("NID: "+sNID);
        tvtNationality.setText("nationality: "+sNationality);
        tvtAddress.setText("Address: "+sAddress);
        tvtDistrict.setText("District: "+sDistrict);
        tvtThana.setText("Thana: "+sLocation);
     //   tentSl.setText("");
        tvtRent.setText("Rent: "+sRent);
        tvtRentForDays.setText("RentForDays: "+sRentForDays);
        tvtGas.setText("Gas: "+sGas);
        tvtElectricity.setText("Electricity: "+sElectricity);
        tvtGTax.setText("Govt.Tax: "+sGTax1);
        tvtWater.setText("Water Bill: "+sWater);
        tvtFood.setText("Food Bill: "+sFood);
        tvtService.setText("Service Charge: "+sService);
        tvtAdvance.setText("Advance: "+sAdvance);
        tvtGTax.setVisibility(View.VISIBLE);
        tentImage.setText("");
        tvtPerson.setText("Person Per Room: "+sPersonProom);
        tvtBedRoom.setText("Bedroom: "+sBedRoom);
        tvtKitchen.setText("Kitchen: "+sKitchen);
        tvtBathroom.setText("Bath: "+sBath);
        tvtBed.setText("Bed: "+sBed);
        tvtFloor.setText("Floor: "+sFloor);
        tvtType.setText("Tent type: "+sTentType);
                tvtFacility.setText("Facility: "+sFacility);
tType="Tenant".toString();

    }




    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AllTenantNewDetails.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... url) {

            try {



                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("sad_type", tType));
                nameValuePairs.add(new BasicNameValuePair("sid", sid));
                nameValuePairs.add(new BasicNameValuePair("landlordId", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("landlordMobile", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("sSID", sSID));
                nameValuePairs.add(new BasicNameValuePair("sgTax", sGTax1));

                nameValuePairs.add(new BasicNameValuePair("sProfession", sProfession));
                nameValuePairs.add(new BasicNameValuePair("sNationality", sNationality));
                nameValuePairs.add(new BasicNameValuePair("sCompany", sCompany));
                nameValuePairs.add(new BasicNameValuePair("sName", sName));
                nameValuePairs.add(new BasicNameValuePair("sLocation", sLocation));
                nameValuePairs.add(new BasicNameValuePair("sEmail", sEmail));
                nameValuePairs.add(new BasicNameValuePair("sAddress", sAddress));
                nameValuePairs.add(new BasicNameValuePair("sNID", sNID));
                nameValuePairs.add(new BasicNameValuePair("sMobileNumber",sMobileNumber));
                nameValuePairs.add(new BasicNameValuePair("sTenantImage", sTenantImage));

                nameValuePairs.add(new BasicNameValuePair("sRent", sRent));
                nameValuePairs.add(new BasicNameValuePair("sFood", sFood));
                nameValuePairs.add(new BasicNameValuePair("sGas", sGas));
                nameValuePairs.add(new BasicNameValuePair("sWater", sWater));
                nameValuePairs.add(new BasicNameValuePair("sElectricity", sElectricity));
                nameValuePairs.add(new BasicNameValuePair("sService", sService));
                nameValuePairs.add(new BasicNameValuePair("sRentForDays", sRentForDays));
                nameValuePairs.add(new BasicNameValuePair("sAdvance", sAdvance));
                nameValuePairs.add(new BasicNameValuePair("sPersonProom", sPersonProom));
                nameValuePairs.add(new BasicNameValuePair("sBed", sBed));
                nameValuePairs.add(new BasicNameValuePair("sBath", sBath));
                nameValuePairs.add(new BasicNameValuePair("sKitchen", sKitchen));
                nameValuePairs.add(new BasicNameValuePair("sBedRoom", sBedRoom));
                nameValuePairs.add(new BasicNameValuePair("sFloor", sFloor));
                nameValuePairs.add(new BasicNameValuePair("sTentType", sTentType));
                nameValuePairs.add(new BasicNameValuePair("sFacility", sFacility));
                nameValuePairs.add(new BasicNameValuePair("sRoomImage", "Image"));

                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlbr);
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
                Toast.makeText(AllTenantNewDetails.this, "Unable to send data", Toast.LENGTH_LONG).show();



        }

        private void setData() {
            if(message.equals("success")){
                Intent Classr = new Intent(AllTenantNewDetails.this, Login.class);
                startActivity(Classr);
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }

  /**  private void InsertData() {

        //sPersonProom, sBed, sBath, sKitchen, sBedRoom, sFloor, sTentType, sFacility

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

  nameValuePairs.add(new BasicNameValuePair("sgTax", tvtGTax.getText().toString()));

  nameValuePairs.add(new BasicNameValuePair("sProfession", tvtProfession.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sNationality", tvtNationality.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sCompany", tvtCompany.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sName", tvtName.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sLocation", tvtThana.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sEmail", tvtEmail.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sAddress", tvtAddress.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sNID", tvtNID.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sMobileNumber", tvtPhone.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sTenantImage", sTenantImage));

  nameValuePairs.add(new BasicNameValuePair("sRent", tvtRent.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sFood", tvtFood.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sGas", tvtGas.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sWater", tvtWater.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sElectricty", tvtElectricity.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sService", tvtService.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sRentForDays", tvtRentForDays.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sAdvance", tvtAdvance.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sPersonProom", tvtPerson.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sBed", tvtBed.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sBath", tvtBathroom.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sKitchen", tvtKitchen.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sBedRoom", tvtBedRoom.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sFloor", tvtFloor.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sTentType", tvtType.getText().toString()));
  nameValuePairs.add(new BasicNameValuePair("sFacility", tvtFacility.getText().toString()));

                //   String rentDays = String.valueOf(stRentForDays);

                nameValuePairs.add(new BasicNameValuePair("sad_type", tType));
                nameValuePairs.add(new BasicNameValuePair("sid", sid));
                nameValuePairs.add(new BasicNameValuePair("landlordId", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("landlordMobile", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("sSID", sSID));
                nameValuePairs.add(new BasicNameValuePair("sgTax", sGTax1));

                nameValuePairs.add(new BasicNameValuePair("sProfession", sProfession));
                nameValuePairs.add(new BasicNameValuePair("sNationality", sNationality));
                nameValuePairs.add(new BasicNameValuePair("sCompany", sCompany));
                nameValuePairs.add(new BasicNameValuePair("sName", sName));
                nameValuePairs.add(new BasicNameValuePair("sLocation", sLocation));
                nameValuePairs.add(new BasicNameValuePair("sEmail", sEmail));
                nameValuePairs.add(new BasicNameValuePair("sAddress", sAddress));
                nameValuePairs.add(new BasicNameValuePair("sNID", sNID));
                nameValuePairs.add(new BasicNameValuePair("sMobileNumber", sMobileNumber));
                nameValuePairs.add(new BasicNameValuePair("sTenantImage", sTenantImage));

                nameValuePairs.add(new BasicNameValuePair("sRent", sRent));
                nameValuePairs.add(new BasicNameValuePair("sFood", sFood));
                nameValuePairs.add(new BasicNameValuePair("sGas", sGas));
                nameValuePairs.add(new BasicNameValuePair("sWater", sWater));
                nameValuePairs.add(new BasicNameValuePair("sElectricty", sElectricty));
                nameValuePairs.add(new BasicNameValuePair("sService", sService));
                nameValuePairs.add(new BasicNameValuePair("sRentForDays", sRentForDays));
                nameValuePairs.add(new BasicNameValuePair("sAdvance", sAdvance));
                nameValuePairs.add(new BasicNameValuePair("sPersonProom", sPersonProom));
                nameValuePairs.add(new BasicNameValuePair("sBed", sBed));
                nameValuePairs.add(new BasicNameValuePair("sBath", sBath));
                nameValuePairs.add(new BasicNameValuePair("sKitchen", sKitchen));
                nameValuePairs.add(new BasicNameValuePair("sBedRoom", sBedRoom));
                nameValuePairs.add(new BasicNameValuePair("sFloor", sFloor));
                nameValuePairs.add(new BasicNameValuePair("sTentType", sTentType));
                nameValuePairs.add(new BasicNameValuePair("sFacility", sFacility));
                nameValuePairs.add(new BasicNameValuePair("sRoomImage", sRoomImage));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://barivara.com/aTenantInsert.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                Log.e("Data", "Success");
                return "Data Inserted Successfully";

            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(AllTenantNewDetails.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),
                        AllTenant.class);
                intent.putExtra("Password", uPass1);
                intent.putExtra("MobileNumber", uMobile1);
                startActivity(intent);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(tType, sid, Sopnoid1, uMobile1,
                sSID, sGTax1, sProfession, sNationality,sCompany, sName,sLocation, sEmail, sAddress, sNID,
                sMobileNumber, sTenantImage,
                sRent, sGas, sWater, sElectricty, sService, sFood, sRentForDays,sAdvance,sPersonProom, sBed, sBath, sKitchen,sBedRoom, sFloor,sTentType, sFacility,sRoomImage
               );



    }
**/
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
        inflater.inflate(R.menu.menu_tenant, menu);
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

        if (id == R.id.action_new) {
            Intent intent = new Intent(getApplicationContext(),
                    AllTenantNewDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

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
            }else if(uType1.equals("general")){
                Intent intent = new Intent(getApplicationContext(),
                        UserHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),
                        BasicHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


            return true;
        }

        if (id == R.id.menue) {

            if(uType1.equals("agent")){
                Intent intent = new Intent(getApplicationContext(),
                        AgentMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }else if(uType1.equals("general")){
                Intent intent = new Intent(getApplicationContext(),
                        UserMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),
                        BasicAdContact.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

