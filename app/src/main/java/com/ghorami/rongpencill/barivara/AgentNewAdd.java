package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AgentNewAdd extends AppCompatActivity implements View.OnClickListener {
    String[] districts = {"ঢাকা","বরিশাল","সিলেট","রাজশাহী","রংপুর","খুলনা","চট্টগ্রাম","গোপালগঞ্জ","Dhaka", "Comilla", "Chittagong", "Sylhet", "Gopalganj", "Rangpur", "Khulna", "Rajshahi", "Barishal"};
    CheckBox[] homeArray;
    CheckBox[] typeArray;
    CheckBox[] fArray;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    final Context c = this;
    TextView PostAd, EditPro, tvuName, tvSid;
    CheckBox parking, swimming, gym, intern, water, gas, generator, lift, ckFood, ckNFood;
    CheckBox  student, gstudent, bachelor, others, family,singlefamily, women;
    CheckBox flat, appartment, concord, commercial, otherh;
    TextView tvrent, tvsell, tvstudent, tvgstudent, tvbachelor, tvothers, tvfamily, tvwomen;
    EditText etFood_bill, etRoom, etPerson, etRentForDays, ettitle, etbed, etbath, etfloor, etsize, etabout, etexdate, etrent, etadvance, etutility, etowner, etmobile, etlocation, etaddress, etGas, etWater, etElectricity;
    String etAdtype1, uname1, uimage1,usid1,sopnoi1, facility, hometype, renttype, mobilenumber, sopnoid, category, uname, uimage;
    String days, file_url, getMobileNumber, adSl, srent, sbed, sbath, sfloor, sarea, sr_type, sabout,
            sowner, smobile, scity, slocation, sad_type, saddress, sid, sexdate, stitle, sadvance, sutility,
            shometype, srenttype, sfacility, ihometype, irenttype, ifacility,
            encoded_string, getNumber, iMobile, iPassword, date, food_bill, room, person;
    Double stRentForDays;

    String family2, sfamily2, others2, bachelor2, gstudent2, student2, all2, women2;
    String sfamily1, family1, others1, bachelor1, gstudent1, women1, student1, all1, withFood, withoutFood, withFood1, withoutFood1, gas1, water1, electricity1, foodtype;
    String sCaten, uEmail, uLocation, uAddress;

    AutoCompleteTextView etdistrict;
    Button pickimage, btnupload, btncancel,PickPicture;
    TextView tvF_bill;
    private static final int SELECT_PHOTO = 100;
    Button upload, cancel;
    private File file;
    Bitmap  bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;
    Context context;
    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;

    private int s = 1;
    private ProgressDialog pDialog;

    //  private static final String LOGIN_URL = "http://barivara.com/aInsertPro.php";
    String ServerURL = "http://barivara.com/aInsertPro.php" ;
    String urlPost ="http://barivara.com/aInsertPro.php";

    ImageView imageView;
    MenuItem msgMenu;
    String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_new_add);
       // new CommentKeyBoardFix(this);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        initInstancesDrawer();
        getTextId();

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, districts);
        //Getting the instance of AutoCompleteTextView
        etdistrict = (AutoCompleteTextView) findViewById(R.id.etdistrict);
        etdistrict.setThreshold(1);//will start working from first character
        etdistrict.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        etdistrict.setTextColor(Color.RED);
        imageView  = (ImageView) findViewById(R.id.imageView);

        tvuName = (TextView)findViewById(R.id.tvuName);
        tvF_bill = (TextView)findViewById(R.id.tvF_bill);
        tvSid = (TextView)findViewById(R.id.tvSid);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        DateFormat df = new SimpleDateFormat("dMyyyyHHmm", Locale.ENGLISH);
        date = df.format(Calendar.getInstance().getTime());
        getUserData();

        tvuName.setText(uName1);
        tvSid.setText(Sopnoid1);


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
            Intent intent = new Intent(this, BasicHomeOffline.class);
            startActivity(intent);
            finish();

        }
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("Post Rental");

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
        Intent intent = new Intent(getApplicationContext(),
                AgentDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void getTextId() {

        parking = (CheckBox)findViewById(R.id.ftcb1);
        gas = (CheckBox)findViewById(R.id.ftcb4);
        water = (CheckBox)findViewById(R.id.ftcb5);
        generator = (CheckBox)findViewById(R.id.ftcb6);
        intern = (CheckBox)findViewById(R.id.ftcb7);
        lift = (CheckBox)findViewById(R.id.ftcb8);
        ckFood = (CheckBox)findViewById(R.id.ckFood);
        ckNFood = (CheckBox)findViewById(R.id.ckNFood);

        etFood_bill = (EditText)findViewById(R.id.etFood_bill);
        etRoom = (EditText)findViewById(R.id.etRoom);
        etPerson = (EditText)findViewById(R.id.etPerson);

        etGas = (EditText)findViewById(R.id.etGas);
        etWater = (EditText)findViewById(R.id.etWater);
        etElectricity = (EditText)findViewById(R.id.etElectricity);
        ettitle = (EditText)findViewById(R.id.etTitle);
        ettitle = (EditText)findViewById(R.id.etTitle);
        etRentForDays = (EditText)findViewById(R.id.etRentForDays);
        etbed = (EditText)findViewById(R.id.etBed);
        etbath = (EditText)findViewById(R.id.etBath);
        etfloor = (EditText)findViewById(R.id.etFloor);
        etsize = (EditText)findViewById(R.id.etSize);
        etabout = (EditText)findViewById(R.id.etAbout);
        etrent = (EditText)findViewById(R.id.etRent);
        etadvance = (EditText)findViewById(R.id.etAdvance);
        etutility = (EditText)findViewById(R.id.etUtility);
        etowner = (EditText)findViewById(R.id.etOwner);
        etmobile = (EditText)findViewById(R.id.etMobile);

        etlocation = (EditText)findViewById(R.id.etLocation);
        etaddress = (EditText)findViewById(R.id.etAddress);




        PickPicture =(Button)findViewById(R.id.pickPhoto);
        PickPicture.setOnClickListener(this);
        btnupload = (Button)findViewById(R.id.btnupload);
        btnupload.setOnClickListener(this);
        btncancel = (Button)findViewById(R.id.btncancel);
        btncancel.setOnClickListener(this);



        typeArray = new CheckBox[7];
        typeArray[0] = (CheckBox)findViewById(R.id.rtcb1);
        //  typeArray[0].setOnClickListener(tListener);
        typeArray[1] = (CheckBox)findViewById(R.id.rtcb2);
        //  typeArray[1].setOnClickListener(tListener);
        typeArray[2] = (CheckBox)findViewById(R.id.rtcb3);
        //  typeArray[2].setOnClickListener(tListener);
        typeArray[3] = (CheckBox)findViewById(R.id.rtcb4);
        //  typeArray[3].setOnClickListener(tListener);
        typeArray[4] = (CheckBox)findViewById(R.id.rtcb5);
        //  typeArray[4].setOnClickListener(tListener);

        homeArray = new CheckBox[5];
        homeArray[0] = (CheckBox)findViewById(R.id.htcb1);
        // homeArray[0].setOnClickListener(mListener);
        homeArray[1] = (CheckBox)findViewById(R.id.htcb2);
        // homeArray[2].setOnClickListener(mListener);
        homeArray[3] = (CheckBox)findViewById(R.id.htcb4);
        // homeArray[4].setOnClickListener(mListener);

        fArray = new CheckBox[2];
        fArray[0] = (CheckBox)findViewById(R.id.ckFood);
        fArray[1] = (CheckBox)findViewById(R.id.ckNFood);

        //ettitle, etbed, etbath, etfloor, etsize, etcomment, etexdate, etrent, etadvance, etutility,
        // etowner, etmobile, etlocaation, etdistrict, etaddress;



        sfamily1= getString(R.string.Sfamily);
        family1= getString(R.string.Family);
        others1= getString(R.string.Others);
        bachelor1=getString(R.string.Bachelor);
        gstudent1= getString(R.string.Ghostel);
        student1= getString(R.string.Bhostel);
        women1=getString(R.string.wmHostel);




        renttype = " ";
        if(typeArray[0].isChecked()){ renttype = student1; }
        if(typeArray[1].isChecked()){ renttype = gstudent1; }
        if(typeArray[2].isChecked()){ renttype = bachelor1; }
        if(typeArray[3].isChecked()){ renttype = sfamily1; }
        if(typeArray[4].isChecked()){ renttype = family1; }
        if(typeArray[5].isChecked()){ renttype = women1; }
        if(typeArray[6].isChecked()){ renttype = others1; }



        hometype = " ";
        if(homeArray[0].isChecked()){ hometype = getString(R.string.Flat); }
        if(homeArray[1].isChecked()){ hometype = getString(R.string.Apartment); }
        if(homeArray[2].isChecked()){ hometype = getString(R.string.Concord); }
        if(homeArray[3].isChecked()){ hometype = getString(R.string.Commercial); }
        if(homeArray[4].isChecked()){ hometype = getString(R.string.Others); }

        foodtype =" ";
        if(fArray[0].isChecked()){ foodtype = "1";
        //etFood_bill.setVisibility(View.GONE);
          //  tvF_bill.setVisibility(View.GONE);
        }
        if(fArray[1].isChecked()){ foodtype = "0";

        }

        if(ckNFood.isChecked()){ etFood_bill.setVisibility(View.VISIBLE);
            tvF_bill.setVisibility(View.VISIBLE); }

        if(ckFood.isChecked())
        {etFood_bill.setVisibility(View.GONE);
              tvF_bill.setVisibility(View.GONE); }


    }

    public void GetData(){
        //ettitle, etbed, etbath, etfloor, etsize, etcomment, etexdate, etrent, etadvance, etutility,
        // etowner, etmobile, etlocaation, etdistrict, etaddress;

        if(s == 9) {
            Toast.makeText(context, "You have reached to maximum number", Toast.LENGTH_LONG).show();
            return ;
        }
        else {
            s = s+1;
        }

        if(irenttype==sfamily1){ sCaten="sfamily".toString(); }
        if(irenttype==family1){ sCaten="family".toString(); }
        if(irenttype==others1){ sCaten="others".toString(); }
        if(irenttype==bachelor1){ sCaten="bachelor".toString(); }
        if(irenttype==gstudent1){ sCaten="gstudent".toString(); }
        if(irenttype==student1){ sCaten="student".toString(); }
        if(irenttype==women1){ sCaten="women".toString(); }

        stitle = ((EditText)findViewById(R.id.etTitle)).getText().toString();
      String  sRentForDays = ((EditText)findViewById(R.id.etRentForDays)).getText().toString();
      stRentForDays = Double.valueOf(sRentForDays);


        sbed = ((EditText)findViewById(R.id.etBed)).getText().toString();
        gas1 = ((EditText)findViewById(R.id.etGas)).getText().toString();
        water1 = ((EditText)findViewById(R.id.etWater)).getText().toString();
        electricity1 = ((EditText)findViewById(R.id.etElectricity)).getText().toString();

        room = ((EditText)findViewById(R.id.etRoom)).getText().toString();
        person = ((EditText)findViewById(R.id.etPerson)).getText().toString();
        food_bill = ((EditText)findViewById(R.id.etFood_bill)).getText().toString();

        sbath = ((EditText)findViewById(R.id.etBath)).getText().toString();
        sarea = ((EditText)findViewById(R.id.etSize)).getText().toString();
        sfloor = ((EditText)findViewById(R.id.etFloor)).getText().toString();
        sabout = ((EditText)findViewById(R.id.etAbout)).getText().toString();
        srent = ((EditText)findViewById(R.id.etRent)).getText().toString();
        sadvance = ((EditText)findViewById(R.id.etAdvance)).getText().toString();
        sutility = ((EditText)findViewById(R.id.etUtility)).getText().toString();
        sowner = ((EditText)findViewById(R.id.etOwner)).getText().toString();
        smobile = ((EditText)findViewById(R.id.etMobile)).getText().toString();
        slocation = ((EditText)findViewById(R.id.etLocation)).getText().toString();
        scity = ((AutoCompleteTextView)findViewById(R.id.etdistrict)).getText().toString();
        saddress = ((EditText)findViewById(R.id.etAddress)).getText().toString();

        String park2, spool2, gym2, gas2, water2, generator2, wifi2, lift2;

        facility = " ";
        if(parking.isChecked()){
            facility += getString(R.string.Parking)+"\n";
        }
        if(swimming.isChecked()){
            facility += getString(R.string.Spool)+"\n"; //gas, water, generator, intern
        }
        if(ckNFood.isChecked()){ foodtype = "0"; }
        if(ckFood.isChecked()){ foodtype = "1"; }
        if(gym.isChecked()){ facility += getString(R.string.Gym)+"\n"; }
        if(gas.isChecked()){ facility += getString(R.string.GasSupply)+"\n"; }
        if(water.isChecked()){ facility += getString(R.string.WaterS)+"\n"; }
        if(generator.isChecked()){ facility += getString(R.string.GeneratorS)+"\n"; }
        if(intern.isChecked()){ facility += getString(R.string.Wifi)+"\n"; }

        if(lift.isChecked()){ facility += getString(R.string.Lift)+"\n";}

        ifacility=facility.toString();
        // ihometype = hometype.toString();

        adSl=(date).toString();



    }




    public void fcheck(View f) {
        boolean checked = ((CheckBox) f).isChecked();
        String str = "";

        final int checkedId = f.getId();
        for (int j = 0; j < fArray.length; j++) {
            final CheckBox currentf = fArray[j];
            if (currentf.getId() == checkedId) {
                currentf.setChecked(true);
          //     foodtype = currentf.getText().toString();


            } else {
                currentf.setChecked(false);
                // return;

            }
        }
    }

    public void apcheck(View k) {
        boolean checked = ((CheckBox) k).isChecked();
        String str = "";

        final int checkedId = k.getId();
        for (int j = 0; j < homeArray.length; j++) {
            final CheckBox current = homeArray[j];
            if (current.getId() == checkedId) {
                current.setChecked(true);
                ihometype = current.getText().toString();

            } else {
                current.setChecked(false);
                // return;

            }
        }
    }


    public void androidCheckBoxClicked(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        String str="";

        final int checkedId = v.getId();
        for (int i = 0; i < typeArray.length; i++) {
            final CheckBox current = typeArray[i];
            if (current.getId() == checkedId) {
                current.setChecked(true);
                irenttype=current.getText().toString();
            } else {
                current.setChecked(false);
            }
        }




    }

    public void androidCheckBoxflicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked gas, water, generator, intern
        switch(view.getId()) {
            case R.id.ftcb1:
                str = checked?getString(R.string.Parking)+"Selected":getString(R.string.Parking)+"Deselected";
                break;
            case R.id.ftcb4: str = checked?getString(R.string.GasSupply)+"Selected":getString(R.string.GasSupply)+"Deselected";
                break;
            case R.id.ftcb5: str = checked?getString(R.string.WaterS)+"Selected":getString(R.string.WaterS)+"Deselected";
                break;
            case R.id.ftcb6: str = checked?getString(R.string.GeneratorS)+"Selected":getString(R.string.GeneratorS)+"Deselected";
                break;
            case R.id.ftcb7: str = checked?getString(R.string.Wifi)+"Selected":getString(R.string.Wifi)+"Deselected";
                break;
            case R.id.ftcb8: str = checked?getString(R.string.Lift)+"Selected":getString(R.string.Lift)+"Deselected";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pickPhoto:
                // do something
                pickImage();
                break;

            case R.id.btnupload:
                // do something
                // upload();
                GetData();
                checkValidation();
               // InsertData();

                break;
            case R.id.btncancel:
                // do something String spot = mobileNumber.getText().toString();

                onBackPressed();

                break;
        }
    }

    private void checkValidation() {


        if(stitle.equals("")) {
            ettitle.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(stRentForDays.equals("")) {
            etRentForDays.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(stRentForDays.equals("0")) {
            etRentForDays.setError("অনুগ্রহ করে দিন ঠিক করুন");
            return;
        }
        if(stRentForDays>30){
            etRentForDays.setError("অনুগ্রহ করে দিন ঠিক করুন");
            return;
        }
        if(sbed.equals("")) {
            etbed.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sbath.equals("")) {
            etbath.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sarea.equals("")) {
            etsize.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sfloor.equals("")) {
            etfloor.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        /**
        if(sabout.equals("")) {
            etabout.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
         **/
        if(srent.equals("")) {
            etrent.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sadvance.equals("")) {
            etadvance.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sutility.equals("")) {
            etutility.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(sowner.equals("")) {
            etowner.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(smobile.equals("")) {
            etmobile.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(slocation.equals("")) {
            etlocation.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(scity.equals("")) {
            etdistrict.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }
        if(saddress.equals("")) {
            etaddress.setError("অনুগ্রহ করে শূণ্যস্থানটি পূরণ করুন");
            return;
        }

        if (sowner.equals("")|| saddress.equals("")|| smobile.equals("")){

            //  new CustomToast().Show_Toast(c, view,
            //    "All fields are required.");
            Toast.makeText(AgentNewAdd.this, "All fields are required.", Toast.LENGTH_LONG).show();
        }else{
            InsertData();
        }



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
                + File.separator + adSl
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


        String sTr = "rent".toString();
        new JSONAsyncTask().execute(urlPost);
/**
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

             //   String rentDays = String.valueOf(stRentForDays);

                nameValuePairs.add(new BasicNameValuePair("sad_type", sTr));
                nameValuePairs.add(new BasicNameValuePair("sid", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("rentForDays", String.valueOf(stRentForDays)));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("adSl", adSl));
                nameValuePairs.add(new BasicNameValuePair("title", stitle));

                nameValuePairs.add(new BasicNameValuePair("sbed", sbed));
                nameValuePairs.add(new BasicNameValuePair("sbath", sbath));
                nameValuePairs.add(new BasicNameValuePair("sarea", sarea));
                nameValuePairs.add(new BasicNameValuePair("sfloor", sfloor));
                nameValuePairs.add(new BasicNameValuePair("sabout", sabout));

                nameValuePairs.add(new BasicNameValuePair("shometype", ihometype));
                nameValuePairs.add(new BasicNameValuePair("srenttype", irenttype));
                nameValuePairs.add(new BasicNameValuePair("sfacility", ifacility));
                nameValuePairs.add(new BasicNameValuePair("sCaten", sCaten));

                nameValuePairs.add(new BasicNameValuePair("person", person));
                nameValuePairs.add(new BasicNameValuePair("room", room));
                nameValuePairs.add(new BasicNameValuePair("food_bill", food_bill));


                nameValuePairs.add(new BasicNameValuePair("srent", srent));
                nameValuePairs.add(new BasicNameValuePair("sadvance", sadvance));
                nameValuePairs.add(new BasicNameValuePair("sutility", sutility));
                nameValuePairs.add(new BasicNameValuePair("sfood", foodtype));
                nameValuePairs.add(new BasicNameValuePair("sgas", gas1));
                nameValuePairs.add(new BasicNameValuePair("swater", water1));
                nameValuePairs.add(new BasicNameValuePair("selectricity", electricity1));

                nameValuePairs.add(new BasicNameValuePair("sowner", sowner));
                nameValuePairs.add(new BasicNameValuePair("smobile", smobile));
                nameValuePairs.add(new BasicNameValuePair("slocation", slocation));
                nameValuePairs.add(new BasicNameValuePair("scity", scity));
                nameValuePairs.add(new BasicNameValuePair("saddress", saddress));
                nameValuePairs.add(new BasicNameValuePair("encoded_string", encoded_string));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://barivara.com/aInsertPro.php");

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

                Toast.makeText(AgentNewAdd.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),
                        AgentDashboard.class);
                intent.putExtra("Password", uPass1);
                intent.putExtra("MobileNumber", uMobile1);
                startActivity(intent);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sTr, uMobile1, String.valueOf(stRentForDays), Sopnoid1, adSl, stitle, sbed, sbath, sarea, sfloor, sabout, ihometype, irenttype, ifacility, sCaten, person, room, food_bill, srent, sadvance, sutility, foodtype, gas1, water1, electricity1, sowner, smobile, slocation, scity, saddress, encoded_string);
   **/
    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AgentNewAdd.this);


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

                String rentDays = String.valueOf(stRentForDays);

                nameValuePairs.add(new BasicNameValuePair("sad_type", "rent"));
                nameValuePairs.add(new BasicNameValuePair("sid", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("rentForDays", String.valueOf(stRentForDays)));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("adSl", adSl));
                nameValuePairs.add(new BasicNameValuePair("sour", "app"));
                nameValuePairs.add(new BasicNameValuePair("title", stitle));
                nameValuePairs.add(new BasicNameValuePair("tent_sl", "ST-"+s));

                nameValuePairs.add(new BasicNameValuePair("sbed", sbed));
                nameValuePairs.add(new BasicNameValuePair("sbath", sbath));
                nameValuePairs.add(new BasicNameValuePair("sarea", sarea));
                nameValuePairs.add(new BasicNameValuePair("sfloor", sfloor));
                nameValuePairs.add(new BasicNameValuePair("sabout", sabout));

                nameValuePairs.add(new BasicNameValuePair("shometype", ihometype));
                nameValuePairs.add(new BasicNameValuePair("srenttype", irenttype));
                nameValuePairs.add(new BasicNameValuePair("sfacility", ifacility));
                nameValuePairs.add(new BasicNameValuePair("sCaten", sCaten));

                nameValuePairs.add(new BasicNameValuePair("person", person));
                nameValuePairs.add(new BasicNameValuePair("room", room));
                nameValuePairs.add(new BasicNameValuePair("food_bill", food_bill));


                nameValuePairs.add(new BasicNameValuePair("srent", srent));
                nameValuePairs.add(new BasicNameValuePair("sadvance", sadvance));
                nameValuePairs.add(new BasicNameValuePair("sutility", sutility));
                nameValuePairs.add(new BasicNameValuePair("sfood", foodtype));
                nameValuePairs.add(new BasicNameValuePair("sgas", gas1));
                nameValuePairs.add(new BasicNameValuePair("swater", water1));
                nameValuePairs.add(new BasicNameValuePair("selectricity", electricity1));

                nameValuePairs.add(new BasicNameValuePair("sowner", sowner));
                nameValuePairs.add(new BasicNameValuePair("smobile", smobile));
                nameValuePairs.add(new BasicNameValuePair("slocation", slocation));
                nameValuePairs.add(new BasicNameValuePair("scity", scity));
                nameValuePairs.add(new BasicNameValuePair("saddress", saddress));
                nameValuePairs.add(new BasicNameValuePair("encoded_string", encoded_string));


                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlPost);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
                Toast.makeText(AgentNewAdd.this, "Unable to send data", Toast.LENGTH_LONG).show();


        }
    }

        private void setData() {
            if(message.equals("success")){

           /**     Intent intent = new Intent(getApplicationContext(),
                        AgentDashboard.class);
                intent.putExtra("Password", uPass1);
                intent.putExtra("MobileNumber", uMobile1);
                startActivity(intent);

            **/

                Intent intentl2 = new Intent(getApplicationContext(), AgentAllPost.class);
                intentl2.putExtra("Category","rent");
                startActivity(intentl2);
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
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
