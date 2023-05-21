package com.ghorami.rongpencill.barivara;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AgentPostMorePic extends AppCompatActivity {
    private static final int CAMERA_PICTURE = 1;
    private static final int GALLERY_PICTURE = 2;
    String filePath1 = null;
    String filePath2 = null;
    String filePath3 = null;
    Bitmap chosenImage;
    File destination = null;
    String ServerURL = "http://barivara.com/aUpdateMorePic.php" ;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    String img1Selected1 = null, img2Selected1 = null, img3Selected1 = null;
    String img1Selected2 = null, img2Selected2 = null, img3Selected2 = null;
    LinearLayout llImgHolder;

    String encoded_pic1, encoded_pic2, encoded_pic3;



    Button btnAddPic;
    final Context context = this;
    String  tMessage=null; String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    TextView tvPic1, tvPic2, tvPic3;
    ImageView ivPic1, ivPic2, ivPic3;
    Button btnPick1, btnPick2, btnPick3;
    Context context2;
    Bitmap yourSelectedImage; boolean bitmap1;
    Bitmap bitmap;

    String usid, adserial, sopnoi, ad_type;
    String usid1, adserial1, sopnoi1, ad_type1;
    TextView txtPercentage;
    ProgressBar progressBar;

    MenuItem msgMenu;


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

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        tvPic1 = (TextView)findViewById(R.id.tvPic1);
        tvPic2 = (TextView)findViewById(R.id.tvPic2);
        tvPic3 = (TextView)findViewById(R.id.tvPic3);


        ivPic1 = (ImageView)findViewById(R.id.ivPic1);
        ivPic2 = (ImageView)findViewById(R.id.ivPic2);
        ivPic3 = (ImageView)findViewById(R.id.ivPic3);
        btnPick1 = (Button)findViewById(R.id.btnPick1);
        btnPick2 = (Button)findViewById(R.id.btnPick2);
        btnPick3 = (Button)findViewById(R.id.btnPick3);
        btnAddPic = (Button)findViewById(R.id.btnAddPic);

        Intent i = getIntent();
        usid1 = i.getStringExtra("usid");
        sopnoi1 = i.getStringExtra("sopnoi");
        adserial1 = i.getStringExtra("adserial");
        ad_type1 = i.getStringExtra("ad_type");


        btnAddPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UploadPic();

               // String url="http://barivara.com/aUpdateMorePic.php";

                //new uploadAsynTask().execute(url);
            }
        });

 btnPick1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                img1Selected1 = "fulfilled";

                choosePictureAction();
            }
        });

        btnPick2.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                if (img1Selected2 != null) {
                    img2Selected1 = "fulfilled";
                    choosePictureAction();
                }else {
                    Log.e("Please select first image", "Please select first image");
                }
            }
        });

        btnPick3.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {



                if (img2Selected2 != null) {
                    img3Selected1 = "fulfilled";
                    choosePictureAction();
                }else {
                    Log.e("Please select second image", "Please select second image");
                }
            }
        });

        getUserData();
        setUserData();
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/NotoSansBengali.ttf");
    }



/**
    private class uploadAsynTask extends AsyncTask<String, Void, String>{
            ProgressDialog dialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(context2, null, null);
                ProgressBar spinner = new android.widget.ProgressBar(context2, null,android.R.attr.progressBarStyle);
                spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#009689"), android.graphics.PorterDuff.Mode.SRC_IN);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(spinner);
                dialog.setCancelable(false);
                dialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                Log.e("FilePathBin1", filePath1);

                File file1 = new File(filePath1);
                File file2 = null;
                File file3 = null;

                if(img2Selected2 != null){
                    Log.e("FilePathBin2", filePath2);
                    file2 = new File(filePath2);
                }
                if(img3Selected2 != null){
                    Log.e("FilePathBin3", filePath3);
                    file3 = new File(filePath3);
                }

                MultipartEntity reqEntity;
                HttpEntity resEntity;
                try {
                    HttpClient client = new DefaultHttpClient();
                    String postURL = params[0];
                    HttpPost post = new HttpPost(postURL);

                    FileBody bin1 = new FileBody(file1);
                    FileBody bin2 = null;
                    FileBody bin3 = null;

                    if(img2Selected2 != null){

                        bin2 = new FileBody(file2);
                    }
                    if(img3Selected2 != null){

                        bin3 = new FileBody(file3);
                    }

                    reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



                    reqEntity.addPart("parking_title", new StringBody("Test13:26"));
                    reqEntity.addPart("latitude", new StringBody("10.12313213"));
                    reqEntity.addPart("longtitude", new StringBody("12.123213213"));
                    reqEntity.addPart("note", new StringBody("12"));

                    reqEntity.addPart("filename[0]", bin1);

                    if(img2Selected2 != null){
                        img2Selected2 = null;
                        reqEntity.addPart("filename[1]", bin2);
                    }
                    if(img3Selected2 != null){
                        img3Selected2 = null;
                        reqEntity.addPart("filename[2]", bin3);
                    }
                    reqEntity.addPart("spot_types[0]", new StringBody("1"));
                    reqEntity.addPart("spot_types[1]", new StringBody("2"));
                    reqEntity.addPart("spot_types[2]", new StringBody("3"));
                    reqEntity.addPart("spot_properties[0]", new StringBody("1"));
                    reqEntity.addPart("spot_properties[1]", new StringBody("2"));
                    reqEntity.addPart("spot_properties[2]", new StringBody("3"));

                    post.setEntity(reqEntity);
                    HttpResponse response = client.execute(post);
                    resEntity = response.getEntity();
                    String entityContentAsString = EntityUtils.toString(resEntity);
                    Log.d("stream:", entityContentAsString);



                    return entityContentAsString;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

            }



            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);


                if(result != null){
                    dialog.dismiss();
                    Log.e("addPhotoResult", result);

                    filePath1 = null;
                    filePath2 = null;
                    filePath3 = null;

                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        String error = jsonObject.getString("Error");
                        String message = jsonObject.getString("message");

                        Log.e("error", error);
                        Log.e("message", message);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }

        }

            **/

    private void UploadPic() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("sad_type", ad_type1));
                nameValuePairs.add(new BasicNameValuePair("sid", usid1));
                nameValuePairs.add(new BasicNameValuePair("sidMobile", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("adSl", adserial1));
                nameValuePairs.add(new BasicNameValuePair("sopnoid", sopnoi1));
                nameValuePairs.add(new BasicNameValuePair("encoded_string1", (encoded_pic1)));
                nameValuePairs.add(new BasicNameValuePair("encoded_string2", (encoded_pic2)));
                nameValuePairs.add(new BasicNameValuePair("encoded_string3", (encoded_pic3)));


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

                Toast.makeText(AgentPostMorePic.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                showAlert(result);


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(adserial1, ad_type1, sopnoi1, usid1, uMobile1, encoded_pic1, encoded_pic2, encoded_pic3);
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

    private void showAlert(String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // do nothing
                        onBackPressed();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText("add Photo");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PICTURE && resultCode == RESULT_OK) {
            chosenImage = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            chosenImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            //filePath1 = destination.toString();

            if(img1Selected1 != null){

                byte[] array1 = bytes.toByteArray();
                encoded_pic1 = Base64.encodeToString(array1, 0);
                filePath1 = destination.toString();
                tvPic1.setText(filePath1);
            }else if(img2Selected1 != null){
                byte[] array2 = bytes.toByteArray();
                encoded_pic2 = Base64.encodeToString(array2, 0);
                filePath2 = destination.toString();
                tvPic2.setText(filePath2);
            }else if(img3Selected1 != null){
                byte[] array3 = bytes.toByteArray();
                encoded_pic3 = Base64.encodeToString(array3, 0);
                tvPic3.setText(filePath3);
                filePath3 = destination.toString();
            }

            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(chosenImage != null){
                if(img1Selected1 != null){
                   //
                    // llImgHolder.setVisibility(View.VISIBLE);
                    ivPic1.setVisibility(View.VISIBLE);

                    img1Selected1 = null;
                    img1Selected2 = "fulfilled";
                    ivPic1.setImageBitmap(chosenImage);
                }else if(img2Selected1 != null){
                    ivPic2.setVisibility(View.VISIBLE);

                    img2Selected1 = null;
                    img2Selected2 = "fulfilled";
                    ivPic2.setImageBitmap(chosenImage);
                }else if(img3Selected1 != null){
                    ivPic3.setVisibility(View.VISIBLE);

                    img3Selected1 = null;
                    img3Selected2 = "fulfilled";
                    ivPic3.setImageBitmap(chosenImage);
                }

            }


        } else if (requestCode == CAMERA_PICTURE
                && resultCode == RESULT_CANCELED) {

        } else if (requestCode == GALLERY_PICTURE
                && resultCode == RESULT_OK) {


            Uri selectedImage = data.getData();
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
            chosenImage=bitmap;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            // bitmap.recycle();

            byte[] array = stream.toByteArray();
          String  encoded_string = Base64.encodeToString(array, 0);
            /*

            if(img1Selected1 != null){
                filePath1 = selectedImagePath;
                tvPic1.setText(encoded_string);
            }else if(img2Selected1 != null){
                tvPic2.setText(filePath2);
                filePath2 = selectedImagePath;
            }else if(img3Selected1 != null){
                filePath3 = selectedImagePath;
                tvPic3.setText(filePath3);
            }


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            chosenImage = BitmapFactory.decodeFile(selectedImagePath, options);
**/
            if(chosenImage!=null){

                if(img1Selected1 != null){
                    //llImgHolder.setVisibility(View.VISIBLE);
                    encoded_pic1=encoded_string;
                    ivPic1.setVisibility(View.VISIBLE);
                    tvPic1.setText(img1Selected1);
                    img1Selected1 = null;
                    img1Selected2 = "fulfilled";
                    ivPic1.setImageBitmap(chosenImage);
                }else if(img2Selected1 != null){
                    ivPic2.setVisibility(View.VISIBLE);
                    encoded_pic2=encoded_string;
                    tvPic2.setText(img2Selected1);
                    img2Selected1 = null;
                    img2Selected2 = "fulfilled";
                    ivPic2.setImageBitmap(chosenImage);
                }else if(img3Selected1 != null){
                    ivPic3.setVisibility(View.VISIBLE);
                    encoded_pic3=encoded_string;
                    tvPic3.setText(img3Selected1);
                    img3Selected1 = null;
                    img3Selected2 = "fulfilled";
                    ivPic3.setImageBitmap(chosenImage);
                }

            }

        } else if (requestCode == GALLERY_PICTURE
                && resultCode == RESULT_CANCELED) {

        }
    }

    private void choosePictureAction(){

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AgentPostMorePic.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_PICTURE);
                }else if(items[which].equals("Gallery")){
                  String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_PICTURE);
                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }

            }
        });

        builder.show();

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