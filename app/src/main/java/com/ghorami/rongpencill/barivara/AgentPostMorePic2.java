package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class AgentPostMorePic2 extends AppCompatActivity {

    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    String selectedPath1 = "NONE";
    String selectedPath2 = "NONE";
    ProgressDialog progressDialog;


    Button btnAddPic;
    final Context context = this;
    String   uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    TextView tvPic1, tvPic2;
    ImageView ivPic1, ivPic2;
    Button btnPick1, btnPick2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_post_more_pic);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        tvPic1 = (TextView)findViewById(R.id.tvPic1);
        tvPic2 = (TextView)findViewById(R.id.tvPic2);
        tvPic1.setText(tvPic1.getText() + selectedPath1 + ",");
        tvPic2.setText(tvPic2.getText() + selectedPath2 + ",");

        ivPic1 = (ImageView)findViewById(R.id.ivPic1);
        ivPic2 = (ImageView)findViewById(R.id.ivPic2);
        btnPick1 = (Button)findViewById(R.id.btnPick1);
        btnPick2 = (Button)findViewById(R.id.btnPick2);
        btnAddPic = (Button)findViewById(R.id.btnAddPic);

        btnPick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE1);
            }
        });
        btnPick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE2);
            }
        });

        getUserData();
        setUserData();
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
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
            initInstancesDrawer();

        } else{
            Toast.makeText(getApplicationContext(), "You are not signin yet! Please login again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, BasicHome.class);
            startActivity(intent);
            finish();

        }
    }

    public void openGallery(int req_code){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), req_code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == SELECT_FILE1)
            {
                selectedPath1 = getPath(selectedImageUri);
                System.out.println("selectedPath1 : " + selectedPath1);
                tvPic1.setText("Selected File paths : "+"," + selectedPath1);
            }
            if (requestCode == SELECT_FILE2)
            {
                selectedPath2 = getPath(selectedImageUri);
                System.out.println("selectedPath2 : " + selectedPath2);
                tvPic2.setText("Selected File paths : "+"," + selectedPath2);
            }
           // tvPic1.setText("Selected File paths : " + selectedPath1 + "," + selectedPath2);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("all add");
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
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
                AgentAllPost.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agent, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //onBackHome();
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