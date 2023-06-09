package com.ghorami.rongpencill.barivara;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllTenantPay extends AppCompatActivity implements View.OnClickListener {

    /**
     * For identifying current view mode read/create/listing/options
     *
     * @author androidsrc.net
     */
    interface CurrentView {
        int OPTIONS_LAYOUT = 1;
        int READ_LAYOUT = 2;
        int WRITE_LAYOUT = 3;
        int PDF_SELECTION_LAYOUT = 4;
    }

    /**
     * FrameLayout child views. We will manage our UI to one layout Hide/Show
     * these views as per requirement
     */
    LinearLayout optionsLayout;
    LinearLayout readLayout;
    LinearLayout writeLayout;
    LinearLayout pdfSelectionLayout;

    private static int currentView;

    // Pdf content will be generated with User Input Text
    EditText pdfContentView;
    // For navigating back
    MenuItem closeOption;
    // List view for showing pdf files
    ListView pdfList;
    // Background task to generate pdf file listing
    // PdfListLoadTask listTask;
    // Adapter to list view
    ArrayAdapter<String> adapter;
    // array of pdf files
    // File[]lass="crayon-h"> filelist;

    // index to track currentPage in rendered Pdf
    private static int currentPage = 0;
    // View on which Pdf content will be rendered
    ImageView pdfView;

    // Currently rendered Pdf file
    String openedPdfFileName;
    Button generatePdf;
    Button next;
    Button previous;

    // File Descriptor for rendered Pdf file
    private ParcelFileDescriptor mFileDescriptor;
    // For rendering a PDF document
    private PdfRenderer mPdfRenderer;
    // For opening current page, render it, and close the page
    private PdfRenderer.Page mCurrentPage;

    private String fileName = null;
    private LinearLayout llPdf;
    private Bitmap bitmap;
    private int WRITE_REQUEST_CODE =123;
    File outputFile;

    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
    String pdfName = "barivaraStatement"
            + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

    private ArrayList<AndroidVersiona> toursad;

    private AdapterAllTenantPay adapterAllTenantPay;
    final Context context = this;
    String  tMessage=null;
    String tShop, uImage1, Sopnoid1, uLocation1, uRentAd1, uSellAd1, uName1, uEmail1, uType1, uVerify1, uMobile1,uAddress1, uPass1;
    String  uPropertySl= String.valueOf("0");

   // String urlagent = "http://barivara.com/api/getAllAgent.php";
    String urlagent = "http://barivara.com/api/getAllTenantPay.php";
    String urlReport = "http://barivara.com/api/getAllurlReportTenant.php";

    RecyclerView recyclerbachelor, recyclerAgent, recyclerFamily, recyclerGirls,
            recyclerStudent, recyclerWomen, recyclerOthers, recyclerViewSell;
    MenuItem msgMenu;

    Button btnDownload, btnSend,  btnWithdraw;
    TextView tvTotalamount, tvBTotal, tvYouramount, tvBYou, tvDueamount, tvBDue, tvGasamount, tvBGas, tvWateramount,
            tvBWater, tvElectricityamount,
            tvElectricity, tvServiceamount, tvBService, tvRentamount, tvBrent,
            tvBalance, tvMonth, tvBbalance, tvPeriod;
   // String tvTotalamount, tvYouramount,tvDueamount,tvGasamount,tvWateramount,tvElectricityamount,tvServiceamount, tvRentamount;
   String Totalamount, Youramount,Dueamount,Gasamount, Wateramount, Electricityamount,Serviceamount, Rentamount, Foodamount, PreviousMonth, CurrentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tenant_pay);
        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        tvPeriod = (TextView)findViewById(R.id.tvPeriod);
        tvMonth = (TextView)findViewById(R.id.tvMonth);
        tvBalance = (TextView)findViewById(R.id.tvBalance);
        tvBbalance = (TextView)findViewById(R.id.tvBbalance);
        tvTotalamount = (TextView)findViewById(R.id.tvTotalamount);
                tvYouramount = (TextView)findViewById(R.id.tvYouramount);
                tvDueamount = (TextView)findViewById(R.id.tvDueamount);
                tvGasamount = (TextView)findViewById(R.id.tvGasamount);
                tvWateramount = (TextView)findViewById(R.id.tvWateramount);
                tvElectricityamount = (TextView)findViewById(R.id.tvElectricityamount);
                tvServiceamount = (TextView)findViewById(R.id.tvServiceamount);
                tvRentamount = (TextView)findViewById(R.id.tvRentamount);

        llPdf = (LinearLayout)findViewById(R.id.reportLayer);


        getUserData();
        setUserData();
        toursad = new ArrayList<AndroidVersiona>();
        adapterAllTenantPay = new AdapterAllTenantPay(AllTenantPay.this,toursad);
        recyclerAgent = (RecyclerView) findViewById(R.id.recyclerAgent);
        recyclerAgent.setAdapter(adapterAllTenantPay);
        new JSONAsyncTaskAgent().execute(urlagent);
        runLayoutAnimation(recyclerAgent);

        btnDownload = (Button)findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        new JSONAsyncTaskReport().execute(urlReport);


    }


    //@RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToWrite() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_REQUEST_CODE);
            }

        }
    }

    // Callback with the request from calling requestPermissions(...)
    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                            String permissions[],
                                            int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }

    }


    private void runLayoutAnimation(final RecyclerView recyclerAgent) {
        final Context context = recyclerAgent.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerAgent.setLayoutAnimation(controller);
        recyclerAgent.getAdapter().notifyDataSetChanged();
        recyclerAgent.scheduleLayoutAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSend:

                // postDataToServer();

                    // sendReqtoServer();
                    Intent in = new Intent(this, AgentMoneySend.class);
                    in.putExtra("appDue", Totalamount);
                    //   String tvRcode1, tvTsend1, Sid1, tvARefer1, tvRPoint1, tvRWithdraw1, tvReferb1, mAmount;

                    startActivity(in);

                break;
            case R.id.btnDownload:


                    Log.d("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
                    bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                    //    createPdf();

                    new PdfGenerationTask().execute();
                    v.setEnabled(false);



                break;
        }

    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

/**
 private void createPdf(){
 WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
 //  Display display = wm.getDefaultDisplay();
 DisplayMetrics displaymetrics = new DisplayMetrics();
 this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
 float hight = displaymetrics.heightPixels ;
 float width = displaymetrics.widthPixels ;

 int convertHighet = (int) hight, convertWidth = (int) width;

 //        Resources mResources = getResources();
 //        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

 PdfDocument document = null;
 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
 document = new PdfDocument();
 }
 PdfDocument.PageInfo pageInfo = null;
 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
 pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
 }
 PdfDocument.Page page = null;
 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
 page = document.startPage(pageInfo);
 }

 Canvas canvas = page.getCanvas();

 Paint paint = new Paint();
 canvas.drawPaint(paint);

 bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

 paint.setColor(Color.BLUE);
 canvas.drawBitmap(bitmap, 0, 0 , null);
 document.finishPage(page);

 File root = android.os.Environment.getExternalStorageDirectory();
 File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding");
 if (!file.exists()) {
 file.mkdirs();
 }

 // write the document content
 String targetPdf = root.getAbsolutePath()+"/VoiceRecorderSimplifiedCoding/pdffromlayout.pdf";
 File filePath;
 filePath = new File(targetPdf);
 try {
 document.writeTo(new FileOutputStream(filePath));

 } catch (IOException e) {
 e.printStackTrace();
 Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
 }

 // close the document
 document.close();
 Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

 openGeneratedPDF();

 }
 **/

    /**
     * Background task to generate pdf from users content
     *
     * @author androidsrc.net
     */
    private class PdfGenerationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            PdfDocument document = new PdfDocument();

            // repaint the user's text into the page
            View content = findViewById(R.id.reportLayer);

            // crate a page description
            int pageNumber = 1;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                    content.getHeight() - 20, pageNumber).create();

            // create a new page from the PageInfo
            PdfDocument.Page page = document.startPage(pageInfo);

            content.draw(page.getCanvas());

            // do final processing of the page
            document.finishPage(page);


            File root = android.os.Environment.getExternalStorageDirectory();

            File file = new File(root.getAbsolutePath() + "/PDFDemo_AndroidSRC");
            if (!file.exists()) {
                file.mkdirs();
            }
            outputFile = new File(root.getAbsolutePath()+"/PDFDemo_AndroidSRC/", pdfName);

            try {
                outputFile.createNewFile();
                OutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                document.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputFile.getPath();
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (filePath != null) {
               // btnSend.setEnabled(true);
                // pdfContentView.setText("");
                Toast.makeText(getApplicationContext(),
                        "Pdf saved at " + filePath, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(outputFile);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException e)
                {
                    Toast.makeText(AllTenantPay.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error in Pdf creation" + filePath, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGeneratedPDF(){

        File root = android.os.Environment.getExternalStorageDirectory();
        // File file = new File("/VoiceRecorderSimplifiedCoding/pdffromlayout.pdf");
        File file = new File(root.getAbsolutePath()+"/PDFDemo_AndroidSRC/", pdfName);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(AllTenantPay.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
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
        toolh.setText("Report");
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

    protected class JSONAsyncTaskReport extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AllTenantPay.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //  pDialog.dismiss();
        }


        @Override
        protected Boolean doInBackground(String... urlre) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("type", uType1));
                nameValuePairs.add(new BasicNameValuePair("LandLordID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("mobilenumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("TentID", uPropertySl));
                Log.e("adsrl", "success");
                // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlReport);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("report_query");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersiona tour = new AndroidVersiona();
                        tour.setTentID(object.getString("TentID"));
                        tour.setTentStart(object.getString("TentStart"));
                        tour.setPayStates(object.getString("PayStates"));
                        tour.setTenantID(object.getString("TenantID"));
                        tour.setLandLordID(object.getString("LandLordID"));
                        PreviousMonth=(object.getString("PreviousMonth"));
                        CurrentMonth=(object.getString("TentBillDate"));
                        // JSONObject objectk = new JSONObject(result);

                        JSONObject Jarray  = object.getJSONObject("report_bill");

                        for (int l = 0; l < Jarray.length(); l++)
                        {
                            //  tour.setImageT(Jarray.getString("uPicture"));
                            // tour.setName(Jarray.getString("userName"));
                            // tour.setTenantID(Jarray.getString("TenantID"));
                            Gasamount=(Jarray.getString("gas_due"));
                            Wateramount=(Jarray.getString("water_due"));
                            Electricityamount=(Jarray.getString("electricity_due"));
                            Dueamount=(Jarray.getString("bill_due"));
                            Foodamount=(Jarray.getString("food_due"));
                            Rentamount=(Jarray.getString("bill_total_money"));
                            Youramount=(Jarray.getString("bill_tenant_pay"));
                            Totalamount=(Jarray.getString("bill_tent"));
                            Serviceamount=(Jarray.getString("service_due"));


                            // tour.setTenantID(Jarray.getString("TenantID"));
                            String Totalamount, Youramount,Dueamount,Gasamount, Wateramount, Electricityamount,Serviceamount, Rentamount;

                        }

                        /** // JSONObject objectk = new JSONObject(result);
                         JSONObject billJarray  = object.getJSONObject("LandLordReport");

                         for (int b = 0; b < billJarray.length(); b++)
                         {
                         appCharge=(billJarray.getString("appCharge"));
                         endBalance=(billJarray.getString("endBalance"));
                         totalRent=(billJarray.getString("totalRent"));
                         totalCredit=(billJarray.getString("totalCredit"));
                         totalDebit=(billJarray.getString("totalDebit"));
                         totalVAT=(billJarray.getString("totalVAT"));
                         begainBalance=(billJarray.getString("begainBalance"));
                         BalancePeriod=(billJarray.getString("BalancePeriod"));

                         }
                         **/

                    }
                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            setUserData();
            //   adapterAllTenantBill.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    protected class JSONAsyncTaskAgent extends AsyncTask<String, Void, Boolean> {

        ProgressDialog pDialog = new ProgressDialog(AllTenantPay.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //  pDialog.dismiss();
        }



        @Override
        protected Boolean doInBackground(String... url) {
            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("type", uType1));
                nameValuePairs.add(new BasicNameValuePair("LandLordID", Sopnoid1));
                nameValuePairs.add(new BasicNameValuePair("mobilenumber", uMobile1));
                nameValuePairs.add(new BasicNameValuePair("TentID", uPropertySl));
                Log.e("adsrl", "success");
                // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlagent);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);

                int status = httpResponse.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.v("kkk", data);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("tenant_query");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //Inner object is their using below code:
                        //  arent = object.getString("rent");
                        AndroidVersiona tour = new AndroidVersiona();

                        tour.setTentID(object.getString("TentID"));
                        tour.setTentStart(object.getString("TentStart"));
                        tour.setPayStates(object.getString("PayStates"));
                        tour.setTenantID(object.getString("TenantID"));
                        tour.setLandLordID(object.getString("LandLordID"));


                        // JSONObject objectk = new JSONObject(result);
                        JSONObject Jarray  = object.getJSONObject("LandLord");

                        for (int l = 0; l < Jarray.length(); l++)
                        {
                            tour.setImageT(Jarray.getString("uPicture"));
                            tour.setName(Jarray.getString("userName"));
                            tour.setReferId(Jarray.getString("uReferID"));
                           // tour.setTenantID(Jarray.getString("TenantID"));

                        }


                        toursad.add(tour);
                    }
                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            adapterAllTenantPay.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }




    private void setUserData() {
        // String tvTotalamount, tvYouramount,tvDueamount,tvGasamount,tvWateramount,tvElectricityamount,tvServiceamount, tvRentamount;

     //   String Totalamount, Youramount,Dueamount,Gasamount, Wateramount, Electricityamount,Serviceamount, Rentamount;
        tvPeriod.setText(PreviousMonth+" to "+ CurrentMonth);
        tvTotalamount.setText(Totalamount);
        tvBalance.setText(Totalamount);
     //   tvYouramount.setText(Youramount);
        tvDueamount.setText(Dueamount);
        tvGasamount.setText(Gasamount);
        tvWateramount.setText(Wateramount);
        tvElectricityamount.setText(Electricityamount);
        tvServiceamount.setText(Serviceamount);
        tvRentamount.setText(Rentamount);
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