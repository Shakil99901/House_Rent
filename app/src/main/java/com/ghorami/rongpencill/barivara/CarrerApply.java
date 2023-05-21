package com.ghorami.rongpencill.barivara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL10;


public class CarrerApply extends AppCompatActivity implements View.OnClickListener {

    View view;
    EditText fullName, userEmail, mobileNumber,post, subject, degree, university, expectedDutyTime,
            expectedSalary, etNID, Nationality, thana, location, etAddress, password, confirmPassword, etRefer
           ;

    String fullName1, userEmail1, mobileNumber1,post1, subject1, degree1, university1, expectedDutyTime1,
            expectedSalary1, etNID1, Nationality1, thana1, location1, etAddress1, password1, confirmPassword1, etRefer1
            ;
     TextView login;
     Spinner sp_Post;
    Button signUpButton;
     CheckBox terms_conditions;
    ImageView profileImage;
    String url = "http://barivara.com/applyJob.php";
    private static final int SELECT_PHOTO = 100;
    Button upload, cancel;
    private File file;
    Bitmap bitmap;
    Bitmap yourSelectedImage; boolean bitmap1;
    private Uri file_uri, selectedImage;
    String uimage="", encoded_string, date, message;

    Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);

        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        signUpButton = (Button) findViewById(R.id.signUpBtn);
        signUpButton.setAnimation(myAnim);
      Button  Back = (Button)findViewById(R.id.btnBack);
        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent2 = new Intent(CarrerApply.this, BasicHome.class);
                startActivity(intent2);
            }
        });

        initViews();
        setListeners();
        profileImage = (ImageView)findViewById(R.id.profileImage);
        if(uimage.equals ("")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user));

        } else {
            Picasso.get().load(uimage).transform(new CircleTransform()).into(profileImage);
        }

        DateFormat df = new SimpleDateFormat("E,dMyyyy,HH:mm", Locale.ENGLISH);
        date = df.format(Calendar.getInstance().getTime());
    }

    // Initialize all views
    private void initViews() {

        sp_Post = (Spinner)findViewById(R.id.sp_Post);
        sp_Post.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        fullName = (EditText) findViewById(R.id.fullName);

        etRefer = (EditText) findViewById(R.id.etRefer);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        location = (EditText) findViewById(R.id.location);
        etAddress = (EditText)findViewById(R.id.etAddress);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        userEmail=(EditText) findViewById(R.id.userEmail);
        post=(EditText) findViewById(R.id.post);
        post.setVisibility(View.GONE);
        subject=(EditText) findViewById(R.id.subject);
        degree=(EditText) findViewById(R.id.degree);
        university=(EditText) findViewById(R.id.university);
        expectedDutyTime =(EditText) findViewById(R.id.expectedDutyTime);
                expectedSalary=(EditText) findViewById(R.id.expectedSalary);
                etNID=(EditText) findViewById(R.id.etNID);
        Nationality=(EditText) findViewById(R.id.Nationality);
        thana=(EditText) findViewById(R.id.thana);

        login = (TextView) findViewById(R.id.already_user);
        terms_conditions = (CheckBox) findViewById(R.id.terms_conditions);


    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
        ImageButton pickPhoto = (ImageButton)findViewById(R.id.pickPhoto);
        pickPhoto.setAnimation(myAnim);
        pickPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pickPhoto:
                // do something
                pickImage();
                break;

            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                Intent inte = new Intent(this, Login.class);
                startActivity(inte);
                break;
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
                + File.separator+date+"43"
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
                    m.setRectToRect(new RectF(0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight()), new RectF(0, 0, 400, 400), Matrix.ScaleToFit.CENTER);
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
    // Check Validation Method
    private void checkValidation() {

        fullName1 = fullName.getText().toString();
        userEmail1=userEmail.getText().toString();
        mobileNumber1=mobileNumber.getText().toString();
      //  post1=post.getText().toString();
        post1= String.valueOf(sp_Post.getSelectedItem()).toString();
        subject1=subject.getText().toString();
        degree1 = degree.getText().toString();
        university1= university.getText().toString();
        expectedDutyTime1 = expectedDutyTime.getText().toString();
      expectedSalary1 = expectedSalary.getText().toString();
      etNID1 = etNID.getText().toString();
      Nationality1 = Nationality.getText().toString();
      thana1 = thana.getText().toString();
      location1 = location.getText().toString();
      etAddress1 = etAddress.getText().toString();
      password1 = password.getText().toString();
      confirmPassword1 = confirmPassword.getText().toString();
      etRefer1 = etRefer.getText().toString();
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(userEmail1);

        // Check if all strings are null or not
        if (fullName1.equals("") || fullName1.length() == 0
                || mobileNumber1.equals("") || mobileNumber1.length() == 0
                || location1.equals("") || location1.length() == 0
                || password1.equals("") || password1.length() == 0
                || confirmPassword1.equals("")
                || confirmPassword1.length() == 0)

            new CustomToasta().Show_Toast(this, view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToasta().Show_Toast(this, view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!confirmPassword1.equals(password1))
            new CustomToasta().Show_Toast(this, view,
                    "Both password doesn't match.");

            // Make sure userp should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToasta().Show_Toast(this, view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else
         //   Toast.makeText(this, "Do SignUp.", Toast.LENGTH_SHORT)
           //         .show();
        postDataToServer();


    }

    private void postDataToServer() {
        new JSONAsyncTask().execute(url);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(CarrerApply.this);


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


                nameValuePairs.add(new BasicNameValuePair("userPass", password1));
                nameValuePairs.add(new BasicNameValuePair("nationality", Nationality1));

                nameValuePairs.add(new BasicNameValuePair("thana", thana1));
                nameValuePairs.add(new BasicNameValuePair("expectedSalary", expectedSalary1));
                nameValuePairs.add(new BasicNameValuePair("expectedDutyTime", expectedDutyTime1));
                nameValuePairs.add(new BasicNameValuePair("degree", degree1));
                nameValuePairs.add(new BasicNameValuePair("subject", subject1));
                nameValuePairs.add(new BasicNameValuePair("university", university1));


                nameValuePairs.add(new BasicNameValuePair("nid", etNID1));
                nameValuePairs.add(new BasicNameValuePair("refer", etRefer1));
                nameValuePairs.add(new BasicNameValuePair("profession", post1));
                nameValuePairs.add(new BasicNameValuePair("companyname", "barivara"));
                nameValuePairs.add(new BasicNameValuePair("fullName", fullName1));
                nameValuePairs.add(new BasicNameValuePair("MobileNumber", mobileNumber1));
                nameValuePairs.add(new BasicNameValuePair("email", userEmail1));
                nameValuePairs.add(new BasicNameValuePair("Location", location1));
                nameValuePairs.add(new BasicNameValuePair("Type", "agent"));
                nameValuePairs.add(new BasicNameValuePair("ConfirmPass", confirmPassword1));
                nameValuePairs.add(new BasicNameValuePair("kaddress", etAddress1));
                nameValuePairs.add(new BasicNameValuePair("kncoded_string", encoded_string));

                Log.e("adsrl", "adsrl");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
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
                Toast.makeText(CarrerApply.this, "Unable to send data", Toast.LENGTH_LONG).show();

        }

        private void setData() {
            if(message.equals("success")){
                Intent Classr = new Intent(CarrerApply.this, Login.class);
                startActivity(Classr);
            } else {

                Toast.makeText(getApplicationContext(),
                        message.toString(), Toast.LENGTH_SHORT)
                        .show();
                //Print Toast or open dialog

            }
        }

    }


}
