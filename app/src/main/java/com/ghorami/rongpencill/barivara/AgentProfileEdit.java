package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AgentProfileEdit extends AppCompatActivity {

    ImageView profileImage;
    EditText tvuName, uAddress, uDistrict, uLocation, uEmail, uNID, uProfession, uCompany, uNationality;

    TextView uMobile, tvsid;
    Typeface custom_font;
    String  tMessage=null; String tShop, uImage1, uNationality1, uDistrict1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String mobilenumber, sopnoid, category, uname, uimage, uNID1, uProfession1, uCompany1, ulocation1, uaddress1, uemail1, encoded_string;
    ImageButton PickPicture;
    String sid, smobile, slocation, sDistrict, semail, saddress, iPass, snid, sname, sCompany, sProfession, sNationality;
    Context context;
    final Context c = this;
    Button upload, cancel;
    private File file;
    Bitmap  bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;


    String ServerURL = "http://barivara.com/aUpdateUserPro.php" ;
    private static final int SELECT_PHOTO = 100;

    MenuItem msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_profile_edit);
     //   new CommentKeyBoardFix(this);
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
        tvuName = (EditText) findViewById(R.id.tvuName);
        uDistrict = (EditText) findViewById(R.id.uDistrict);
        uNID = (EditText) findViewById(R.id.uNID);
        tvsid = (TextView)findViewById(R.id.tvsid);
        tvuName.setTypeface(tf);
        uMobile = (TextView) findViewById(R.id.uMobile);
        uLocation=(EditText) findViewById(R.id.uLocation);
        uLocation.setTypeface(tf);
        uEmail= (EditText) findViewById(R.id.uEmail);
        uAddress = (EditText) findViewById(R.id.uAddress);
        uAddress.setTypeface(tf);

        uProfession = (EditText)findViewById(R.id.uProfession);
        uProfession.setTypeface(tf);

        uCompany = (EditText)findViewById(R.id.uCompany);
        uCompany.setTypeface(tf);

        uNationality = (EditText)findViewById(R.id.uNationality);
        uNationality.setTypeface(tf);


        ImageButton  PickPicture = (ImageButton) findViewById(R.id.pickPhoto);
        PickPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                pickImage();
            }
        });

        upload = (Button)findViewById(R.id.btnConfirm);
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Intent intent = new Intent(AgentProfileEdit.this, AgentProfileEdit.class);
                //intent.putExtra("Sopnoid", Sopnoid1);
                //startActivity(intent);
                GetData();

                InsertData();
            }
        });

        setUserData();
    }

    private void setUserData() {

        if(uImage1.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.get().load(uImage1).transform(new CircleTransform()).into(profileImage);
        }

        uProfession.setText(uProfession1);
        uNationality.setText(uNationality1);
        uCompany.setText(uCompany1);
        tvuName.setText(uName1);
        tvsid.setText(Sopnoid1);
        uNID.setText(uNID1);
        uMobile.setText(uMobile1);
        uLocation.setText(uLocation1);
        uDistrict.setText(uDistrict1);
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
        sProfession = uProfession.getText().toString();
        sNationality = uNationality.getText().toString();
        sCompany = uCompany.getText().toString();
        smobile = uMobile.getText().toString();
        sname = tvuName.getText().toString();
        snid = uNID.getText().toString();
        slocation = uLocation.getText().toString();
        sDistrict = uDistrict.getText().toString();
        semail = uEmail.getText().toString();
        saddress = uAddress.getText().toString();


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
                + File.separator + Sopnoid1 + "43"
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
                    m.setRectToRect(new RectF(0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight()), new RectF(0, 0, 400,  400), Matrix.ScaleToFit.CENTER);
                    bitmap = Bitmap.createBitmap(yourSelectedImage, 0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight(), m, true);

                    profileImage.setImageBitmap(bitmap);// To display selected image in image view
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

                //       String sid, smobile, slocation, semail, saddress;

                nameValuePairs.add(new BasicNameValuePair("kid", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("kmobile", smobile));
                nameValuePairs.add(new BasicNameValuePair("klocation", slocation));
                nameValuePairs.add(new BasicNameValuePair("kdistrict", sDistrict));
                nameValuePairs.add(new BasicNameValuePair("kpassword", uPass1));
                nameValuePairs.add(new BasicNameValuePair("ktype", uType1));
                nameValuePairs.add(new BasicNameValuePair("knid", snid));
                nameValuePairs.add(new BasicNameValuePair("kprofession", sProfession));
                nameValuePairs.add(new BasicNameValuePair("knationality", sNationality));
                nameValuePairs.add(new BasicNameValuePair("kcompany", sCompany));
                nameValuePairs.add(new BasicNameValuePair("kname", sname));
                nameValuePairs.add(new BasicNameValuePair("kemail", semail));
                nameValuePairs.add(new BasicNameValuePair("kaddress", saddress));
                nameValuePairs.add(new BasicNameValuePair("kncoded_string", encoded_string));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://barivara.com/aUpdateUserPro.php");

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

                Toast.makeText(AgentProfileEdit.this, "Update Profile Successfully. After login again you can see updated profile", Toast.LENGTH_LONG).show();

                if(uType1.equals("agent")){
                    Intent intent = new Intent(getApplicationContext(),
                            AgentDashboard.class);
                    startActivity(intent);
                }else if(uType1.equals("general")){
                    Intent intent = new Intent(getApplicationContext(),
                            UserHome.class);
                    startActivity(intent);
                }


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(Sopnoid1, sNationality, sCompany, sProfession, smobile, slocation, semail, uPass1, uType1, sname, saddress, encoded_string);
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
            } else {
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
