package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.File;
import java.net.URL;

public class AllTenantNewBill extends AppCompatActivity {
    ImageView profileImage;
    EditText uRent, uGas, uWater, uFood, uGTax, uService, uAdvance, uElectricity, uRentForDays;

    TextView uMobile, tvsid;
    Typeface custom_font;
    String  tMessage=null; String tShop, uImage1, uNationality1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uMobileNumber1, uNIDno1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String mobilenumber, sopnoid, category, uname, uimage,  uProfession1, uCompany1, ulocation1, uaddress1, uemail1, encoded_string;
    ImageButton PickPicture;
    String sid, sMobile, sLocation, sEmail, sAddress, iPass, sName, sCompany, sProfession, sNationality,
            sRent, sGas, sWater, sElectricity, sAdvance, sService, sFood, sRentForDays, sTenantImage, sMobileNumber, sNID, sGTax, sDistrict
            ;
    Context context;
    final Context c = this;
    Button upload, cancel;
    private File file;
    Bitmap bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;


   // String ServerURL = "http://barivara.com/aUpdateUserPro.php" ;
    private static final int SELECT_PHOTO = 100;

    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tenant_new_bill);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        initInstancesDrawer();
        getUserData();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
        profileImage = (ImageView)findViewById(R.id.imageView);
       // tvuName = (EditText) findViewById(R.id.tvuName);
        tvsid = (TextView)findViewById(R.id.tvsid);
       // tvuName.setTypeface(tf);
        uRent = (EditText) findViewById(R.id.etRent);
        uGas = (EditText) findViewById(R.id.etGas);
        uGTax = (EditText) findViewById(R.id.etGTax);
        uWater = (EditText) findViewById(R.id.etWater);
        uElectricity = (EditText) findViewById(R.id.etElectricity);
        uService = (EditText) findViewById(R.id.etService);
        uFood = (EditText) findViewById(R.id.etFood);
        uRentForDays = (EditText) findViewById(R.id.etRentForDays);
        uAdvance = (EditText) findViewById(R.id.etAdvance);



        upload = (Button)findViewById(R.id.btnNext);
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Intent intent = new Intent(AgentProfileEdit.this, AgentProfileEdit.class);
                //intent.putExtra("Sopnoid", Sopnoid1);
                //startActivity(intent);
                GetData();


            }
        });

        cancel = (Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                onBackPressed();
                finish();
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
        toolh.setText("Profile");
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
        sDistrict = i.getStringExtra("sDistrict");
        sNID = i.getStringExtra("sNID");
        sMobileNumber = i.getStringExtra("sMobileNumber");
        sTenantImage = i.getStringExtra("sTenantImage");

        sRent = uRent.getText().toString();
        sFood = uFood.getText().toString();
        sGas = uGas.getText().toString();
        sWater = uWater.getText().toString();
       sElectricity = uElectricity.getText().toString();
       sGTax = uGTax.getText().toString();
        sService = uService.getText().toString();
        sRentForDays = uRentForDays.getText().toString();
        sAdvance = uAdvance.getText().toString();

        InsertData();
    }

    private void InsertData() {
        Intent intent = new Intent(AllTenantNewBill.this, AllTenantNewFacility.class);
        intent.putExtra("sProfession", sProfession);
        intent.putExtra("sNationality", sNationality);
        intent.putExtra("sCompany", sCompany);
        intent.putExtra("sName", sName);
        intent.putExtra("sLocation", sLocation);
        intent.putExtra("sEmail", sEmail);
        intent.putExtra("sAddress", sAddress);
        intent.putExtra("sNID", sNID);
        intent.putExtra("sMobileNumber", sMobileNumber);
        intent.putExtra("sTenantImage", sTenantImage);
        intent.putExtra("sRent", sRent);
        intent.putExtra("sFood", sFood);
        intent.putExtra("sGas", sGas);
        intent.putExtra("sWater", sWater);
       intent.putExtra("sElectricity", sElectricity);
       intent.putExtra("sGTax", sGTax);
       intent.putExtra("sDistrict", sDistrict);
        intent.putExtra("sService", sService);
        intent.putExtra("sRentForDays", sRentForDays);
        intent.putExtra("sAdvance", sAdvance);
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
                    AllTenantNewBill.class);
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

