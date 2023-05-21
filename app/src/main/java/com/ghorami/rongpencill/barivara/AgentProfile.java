package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class AgentProfile extends AppCompatActivity {

    ImageView profileImage;
    Button upload, btnChange;
    Typeface custom_font;
    TextView tvuName, tvsid, uDistrict, uPass, uAddress, uLocation, uMobile, uEmail, uProfession, uNID, uCompany, uNationality;
    String  tMessage=null; String tShop, uPass1, uDistrict1, uProfession1, uNationality1, uNID1, uCompany1, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Context context;
    MenuItem msgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_profile);
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
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeFullPic();
            }


        });

        uNID = (TextView)findViewById(R.id.uNID);
        uProfession = (TextView)findViewById(R.id.uProfession);
        uProfession.setTypeface(tf);

        uNationality = (TextView)findViewById(R.id.uNationality);
        uNationality.setTypeface(tf);

        uCompany = (TextView)findViewById(R.id.uCompany);
        uCompany.setTypeface(tf);


        tvuName = (TextView)findViewById(R.id.tvuName);
        uPass = (TextView)findViewById(R.id.uPassword);
        uDistrict = (TextView)findViewById(R.id.uDistrict);
        tvsid = (TextView)findViewById(R.id.tvsid);
        tvuName.setTypeface(tf);
        uMobile = (TextView)findViewById(R.id.uMobile);
        uLocation=(TextView)findViewById(R.id.uLocation);
        uLocation.setTypeface(tf);
        uEmail= (TextView)findViewById(R.id.uEmail);
        uAddress = (TextView)findViewById(R.id.uAddress);
        uAddress.setTypeface(tf);

        upload = (Button)findViewById(R.id.btnConfirm);
        btnChange = (Button)findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AgentProfile.this, AgentProfileEdit.class);
                intent.putExtra("Sopnoid", Sopnoid1);
                startActivity(intent);

            }
        });

       setUserData();
    }

    private void seeFullPic() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_pic_dialogue, null);
        TouchImageView prImage = (TouchImageView) dialogView.findViewById(R.id.my_image);

        if(uImage1.equals ("")){
            prImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).into(prImage);
        }

        builder.setView(dialogView)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }



    private void setUserData() {

        if(uImage1.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(profileImage);
        }


        uProfession.setText(uProfession1);
        uCompany.setText(uCompany1);
        tvuName.setText(uName1);
        uNID.setText(uNID1);
        uNationality.setText(uNationality1);
        tvsid.setText(Sopnoid1);
        uMobile.setText(uMobile1);
        uLocation.setText(uLocation1);
        uDistrict.setText(uDistrict1);
        uPass.setText(uPass1);
        uEmail.setText(uEmail1);
        uAddress.setText(uAddress1);

    }

    private void getUserData() {
        SharedPreferences prf = getSharedPreferences("sopno_details",MODE_PRIVATE);
        if(prf.contains("uMobile1") && prf.contains("uPass1")) {

            uProfession1  = prf.getString("uProfession1", null);
            uCompany1 = prf.getString("uCompany1", null);
            uNID1 = prf.getString("uNID1", null);
            uNationality1 = prf.getString("uNationality1", null);

            uMobile1  = prf.getString("uMobile1", null);
            uPass1 = prf.getString("uPass1", null);
            uImage1 = prf.getString("uImage1", null);
            Sopnoid1 = prf.getString("Sopnoid1", null);
            uLocation1 = prf.getString("uLocation1", null);
            uDistrict1 = prf.getString("uDistrict1", null);
            uPass1 = prf.getString("uPass1", null);
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
            }


            return true;
        }

        if (id == R.id.menue) {


            if (uType1.equals("agent")) {
                Intent intent = new Intent(getApplicationContext(),
                        AgentMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (uType1.equals("general")) {
                Intent intent = new Intent(getApplicationContext(),
                        UserMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
