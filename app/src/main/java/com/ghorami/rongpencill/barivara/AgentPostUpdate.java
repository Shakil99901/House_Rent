package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
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
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AgentPostUpdate extends AppCompatActivity implements View.OnClickListener{

    private EditText  etRent,  etArea, etAbout, etOwner, etMobile, etAddress, etLocation, etFacility, etFloor, etAdvance, etUtility, etHometype,etTitle,etRenttype;
    private EditText etR_type, etBed, etBath, etCity;
    TextView etAdtype;
    ImageView Profile, img_android, imageView;
    final Context c = this;
    Button upload, cancel;
    private File file;
    Bitmap  bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;
    private Button buttonUpload, uploadPicture, PickPicture;
    TextView PostAd, EditPro, tvSid, tvAdserial, tvuName;
    String ServerURL = "http://barivara.com/aUpdatePro.php" ;
    private static final int SELECT_PHOTO = 100;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String Lang, name, sym, img1, owner1, rent1, location1, bed1, bath1,etAdtype1, uname1, uimage1,usid1,sopnoi1,
            adserial1, phone1, address1, about1, hsize1, Exdate1, facility1, rental_type1, title1, advance1, utility1, floor1, home_type1;;
    String days, file_url, getMobileNumber, adSl, srent, sbed, sbath, sarea, sr_type, sabout, sowner, smobile, scity, slocation, sad_type, saddress, sid, sexdate, encoded_string, getNumber,stitle, sadvance, sutility, sfloor, srenttype, shometype, sfacility;


    Button btnAddPic, btnAllPic;
    final Context context = this;
    String tMessage=null; String tShop,  uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_post_update);
      //  new CommentKeyBoardFix(this);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        getUserData();
        setUserData();
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");

        tvuName = (TextView)findViewById(R.id.tvidName);
        // tvsid = (TextView)findViewById(R.id.tvsid);

        tvSid= (TextView) findViewById(R.id.tvSoid);

        btnAddPic = (Button)findViewById(R.id.btnAddPic);
        btnAddPic.setOnClickListener(this);

        btnAllPic = (Button)findViewById(R.id.btnAllPic);
        btnAllPic.setOnClickListener(this);

        tvAdserial = (TextView) findViewById(R.id.tvAdserial);
        // edittext.setOnClickListener(this);
        etRent = (EditText) findViewById(R.id.etRent);
        etRent.setTypeface(tf);
        etArea = (EditText) findViewById(R.id.etArea);
        etArea.setTypeface(tf);
        etAbout = (EditText) findViewById(R.id.etAbout);
        etAbout.setTypeface(tf);
        etOwner = (EditText) findViewById(R.id.etOwner);
        etOwner.setTypeface(tf);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etMobile.setTypeface(tf);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setTypeface(tf);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etLocation.setTypeface(tf);

        etFacility = (EditText)findViewById(R.id.etFacility);
        etFloor=(EditText)findViewById(R.id.etFloor);
        etAdvance= (EditText)findViewById(R.id.etAdvance);
        etUtility=(EditText)findViewById(R.id.etUtility);
        etHometype=(EditText)findViewById(R.id.etHometype);
        etTitle=(EditText)findViewById(R.id.etTitle);


        upload = (Button) findViewById(R.id.btnConfirm);
        cancel = (Button) findViewById(R.id.btnCancel);
        upload.setOnClickListener(this);
        cancel.setOnClickListener(this);
        PickPicture = (Button) findViewById(R.id.pickPhoto);
        PickPicture.setOnClickListener(this);
        //    uploadPicture.setOnClickListener(this);
        etRenttype = (EditText)findViewById(R.id.etRtype);
        etRenttype.setTypeface(tf);
        etBed = (EditText)findViewById(R.id.etBed);
        etBed.setTypeface(tf);
        etBath = (EditText) findViewById(R.id.etBath);
        etBath.setTypeface(tf);
        etCity = (EditText) findViewById(R.id.etCity);
        etCity.setTypeface(tf);
        etAdtype = (TextView)findViewById(R.id.etAdt);
        etAdtype.setTypeface(tf);
        imageView  = (ImageView) findViewById(R.id.imageView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent i = getIntent();
        img1 = i.getStringExtra("img");
        rent1 = i.getStringExtra("rent");
        location1 = i.getStringExtra("location");
        bed1 = i.getStringExtra("bed");
        owner1 = i.getStringExtra("owner");
        bath1 = i.getStringExtra("bath");
        phone1 = i.getStringExtra("phone");
        address1 = i.getStringExtra("address");
        about1 = i.getStringExtra("about");
        hsize1 = i.getStringExtra("hsize");
        facility1 = i.getStringExtra("facility");
        rental_type1 = i.getStringExtra("rental_type");
        uname1 = i.getStringExtra("uname");
        uimage1 = i.getStringExtra("uimage");
        usid1 = i.getStringExtra("usid");
        sopnoi1 = i.getStringExtra("sopnoi");
        adserial1 = i.getStringExtra("adserial");
        etAdtype1 = i.getStringExtra("ad_type");



        title1 = i.getStringExtra("title");
        advance1 = i.getStringExtra("advance");
        utility1 = i.getStringExtra("utility");
        floor1 = i.getStringExtra("floor");
        home_type1 = i.getStringExtra("home_type");

        etFacility.setText(facility1);
        etFloor.setText(floor1);
        etAdvance.setText(advance1);
        etUtility.setText(utility1);
        etHometype.setText(home_type1);
        etTitle.setText(title1);



        // private String adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
        //    sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;


        //tvImg1.setText(img1);

        tvuName.setText(uname1);
        tvSid.setText(sopnoi1);

        etRent.setText(rent1);
        etBed.setText(bed1);
        etBath.setText(bath1);
        etArea.setText(hsize1);
        // tvLocation.setText("");
        //tvLocation.setTypeface(CustomFonts.typefaceCondensed(this));
        etCity.setText(location1);
        tvAdserial.setText(adserial1);
        etMobile.setText(phone1);

        etAddress.setText(address1);
        // tvFacility.setText(facility1);
        etAbout.setText(about1);
        etOwner.setText(owner1);
        etLocation.setText(location1);
        etRenttype.setText(rental_type1);
        etAdtype.setText(etAdtype1);

        //  String imglink = tvImg1.getText().toString();

        Picasso.get().load(img1).into(imageView);
    }

    public void GetData(){
        sid = tvSid.getText().toString();
        stitle = etTitle.getText().toString();
        srent = etRent.getText().toString();
        sadvance= etAdvance.getText().toString();
        sutility= etUtility.getText().toString();



        sowner = etOwner.getText().toString();
        smobile = etMobile.getText().toString();
        slocation = etLocation.getText().toString();
        scity = etCity.getText().toString();
        saddress = etAddress.getText().toString();

        sbed = etBed.getText().toString();
        sbath = etBath.getText().toString();
        sfloor = etFloor.getText().toString();
        sarea = etArea.getText().toString();
        sabout = etAbout.getText().toString();

        srenttype= etRenttype.getText().toString();
        shometype= etHometype.getText().toString();
        sfacility= etFacility.getText().toString();

        sad_type= etAdtype.getText().toString();
        adSl=tvAdserial.getText().toString();

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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pickPhoto:
                // do something
                pickImage();
                break;

                case R.id.btnAddPic:
                // do something
                pickMoreImage();
                break;

            case R.id.btnAllPic:
                // do something
                seeAllImage();
                break;

            case R.id.btnConfirm:
                // do something
                // upload();
                GetData();
                //  checkValidation();
                //   new Encode_image().execute();// TODO Auto-generated method stub

                InsertData();

                break;
            case R.id.btnCancel:
                // do something String spot = mobileNumber.getText().toString();

                onBackPressed();

                break;
        }
    }

    private void seeAllImage() {
        Intent intent2 = new Intent(this, AgentPostUpdatePicture.class);
        intent2.putExtra("rent", rent1);
        intent2.putExtra("location", location1);
        intent2.putExtra("bed", bed1);
        intent2.putExtra("usid", usid1);
        intent2.putExtra("adserial", adserial1);
        intent2.putExtra("sopnoi", sopnoi1);
        intent2.putExtra("ad_type", etAdtype1);
        intent2.putExtra("userpic", img1);
        intent2.putExtra("userpic", img1);
        intent2.putExtra("Ad11", adserial1);
        intent2.putExtra("crImage", img1);
        startActivity(intent2);

    }


    private void pickMoreImage() {
        Intent intent2 = new Intent(this, AgentPostMorePic.class);
        intent2.putExtra("rent", rent1);
        intent2.putExtra("location", location1);
        intent2.putExtra("bed", bed1);
        intent2.putExtra("usid", usid1);
        intent2.putExtra("adserial", adserial1);
        intent2.putExtra("sopnoi", sopnoi1);
        intent2.putExtra("ad_type", etAdtype1);
        startActivity(intent2);

    }

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
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + adserial1
        );

        file_uri = Uri.fromFile(file);


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

                    imageView.setImageBitmap(bitmap);// To display selected image in image view
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

    public void InsertData(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("sad_type", sad_type));
                nameValuePairs.add(new BasicNameValuePair("sid", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("adSl", adSl));
                nameValuePairs.add(new BasicNameValuePair("stitle", stitle));
                nameValuePairs.add(new BasicNameValuePair("srent", srent));
                nameValuePairs.add(new BasicNameValuePair("sadvance", sadvance));
                nameValuePairs.add(new BasicNameValuePair("sutility", sutility));

                nameValuePairs.add(new BasicNameValuePair("srenttype", srenttype));
                nameValuePairs.add(new BasicNameValuePair("sfacility", sfacility));
                nameValuePairs.add(new BasicNameValuePair("shometype", shometype));



                nameValuePairs.add(new BasicNameValuePair("sbed", sbed));
                nameValuePairs.add(new BasicNameValuePair("sbath", sbath));
                nameValuePairs.add(new BasicNameValuePair("sarea", sarea));
                nameValuePairs.add(new BasicNameValuePair("sfloor", sfloor));
                nameValuePairs.add(new BasicNameValuePair("sabout", sabout));

                nameValuePairs.add(new BasicNameValuePair("sowner", sowner));
                nameValuePairs.add(new BasicNameValuePair("smobile", smobile));
                nameValuePairs.add(new BasicNameValuePair("scity", scity));
                nameValuePairs.add(new BasicNameValuePair("slocation", slocation));
                nameValuePairs.add(new BasicNameValuePair("saddress", saddress));
                nameValuePairs.add(new BasicNameValuePair("encoded_string", encoded_string));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

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

                Toast.makeText(AgentPostUpdate.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),
                        AgentDashboard.class);
                intent.putExtra("Password", uPass1);
                intent.putExtra("MobileNumber", uMobile1);
                startActivity(intent);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(adSl, srent, sbed, sbath, sarea, sr_type, sabout, sowner, smobile, scity, sad_type, saddress, sid, sexdate, encoded_string);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),
                AgentAllPost.class);
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