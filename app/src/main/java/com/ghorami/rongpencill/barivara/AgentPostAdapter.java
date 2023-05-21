package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.os.Build.VERSION.SDK_INT;


public class AgentPostAdapter extends RecyclerView.Adapter<AgentPostAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String sRent, sLocation, sBed, sBath, sAdserial, SopnoID, adState;

    String img,hsize,adserial,about,Exdate;

    private String adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
            sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;




    public AgentPostAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agentlayout_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");

        if(android.get(i).getRent().equals ("")){
            viewHolder.tvRent.setVisibility(View.GONE);
        }else if (android.get(i).getRent().equals ("Negotiation"))
            viewHolder.tvRent.setText(Html.fromHtml(android.get(i).getRent()));
        else {
            viewHolder.tvRent.setText(Html.fromHtml( android.get(i).getRent())+"/-");
        }

        viewHolder.tvRent.setTypeface(custom_font);

        if(android.get(i).getBed().equals ("")){
            viewHolder.tvBed.setVisibility(View.GONE);
        } else {
            viewHolder.tvBed.setText(Html.fromHtml(android.get(i).getBed()));
        }

        viewHolder.tvBath.setTypeface(custom_font);

        if(android.get(i).getBed().equals ("")){
            viewHolder.tvBath.setVisibility(View.GONE);
        } else {
            viewHolder.tvBath.setText(Html.fromHtml(android.get(i).getBath()));
        }

        if(android.get(i).getCategory().equals ("rent")){
            viewHolder.tvHeadline.setText("home for rent");
        } else if(android.get(i).getCategory().equals ("sell")) {
            viewHolder.tvHeadline.setText("property for sell");
        }

        viewHolder.tvBath.setTypeface(custom_font);

        viewHolder.tvLocation.setText(Html.fromHtml(android.get(i).getLocation()));
        viewHolder.tvLocation.setTypeface(custom_font);

        if(android.get(i).getPimage().equals ("")){
            viewHolder.img_android.setImageResource(R.mipmap.ic_launcher);
        } else {
            Picasso.get().load(android.get(i).getPimage()).into(viewHolder.img_android);

        }


        if(android.get(i).getAdState().equals ("1")){
            viewHolder.btnAdState.setBackgroundResource(R.drawable.button_border_green);
            viewHolder.btnAdState.setEnabled(true);
            viewHolder.btnAdState.setText("PUBLISHED");
        } else {
            viewHolder.btnAdState.setBackgroundResource(R.drawable.button_border);
            viewHolder.btnAdState.setEnabled(true);
            viewHolder.btnAdState.setText("UNPUBLISHED");

        }

        if(android.get(i).getRentState().equals ("1")){
            viewHolder.btnRentState.setBackgroundResource(R.drawable.ic_visibility_off);
            viewHolder.btnAdState.setEnabled(false);

        } else {
            viewHolder.btnRentState.setBackgroundResource(R.drawable.ic_visibility);
            viewHolder.btnAdState.setEnabled(true);

        }

        // viewHolder.bottomView.setBackgroundColor(color_arr[i]);// this set background color
        if (i == 0) {
            viewHolder.bottomView.setBackgroundColor(Color.CYAN);
        }
        else if (i % 2 == 1) {
            viewHolder.bottomView.setBackgroundColor(Color.RED);
        }
        else if (i % 2 == 0) {
            viewHolder.bottomView.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.bottomView.setBackgroundColor(Color.BLUE);
        }

        viewHolder.btnRentState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                final AndroidVersion[] itemLabel = {android.get(i)};

                 AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setCancelable(true);
                if(android.get(i).getAdState().equals ("1")) {
                    builder.setTitle("Your home is occupied now");
                    builder.setMessage("Are you want to change it?");
                    // adState="0";

                }else{
                    builder.setTitle("Your home is not occupied yet");
                    builder.setMessage("Are you want to occupied it?");
                    //adState="1";
                }
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        // new RequestTask().execute("http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        // Log.e("Delete Image: ", "http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        if (SDK_INT > 8)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here

                        }

                        //  HttpPost httppost = new HttpPost("http://www.barivara.com/api/deleteAnd.php");


                        String result = null;
                        InputStream is = null;

                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("serial", android.get(i).getAdSerial()));
                        nameValuePairs.add(new BasicNameValuePair("usid", android.get(i).getMobileNumber()));
                        nameValuePairs.add(new BasicNameValuePair("category", android.get(i).getCategory()));
                        nameValuePairs.add(new BasicNameValuePair("sopnoid", android.get(i).getLandLordID()));
                        if(android.get(i).getRentState().equals ("1")) {
                            nameValuePairs.add(new BasicNameValuePair("rentState", "0"));
                        }else if(android.get(i).getRentState().equals ("0")){
                            nameValuePairs.add(new BasicNameValuePair("rentState", "1"));
                        }


                        try
                        {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.barivara.com/arent_rentState.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();

                            Log.e("log_tag", "connection success ");
                            Toast.makeText(context, "pass", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error in http connection "+e.toString());
                            Toast.makeText(context, "Connection fail", Toast.LENGTH_SHORT).show();

                        }
                        //convert response to string
                        try
                        {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");

                            }
                            is.close();

                            result=sb.toString();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error converting result "+e.toString());

                        }

                        try
                        {

                            JSONObject json_data = new JSONObject(result);

                            CharSequence w= (CharSequence) json_data.get("re");
                            notifyItemRangeChanged(i,android.size());

                            notifyDataSetChanged();

                            Toast.makeText(context, w, Toast.LENGTH_SHORT).show();

                        }
                        catch(JSONException e)
                        {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                            Toast.makeText(context, "JsonArray fail", Toast.LENGTH_SHORT).show();
                        }


//parse json data





                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });



        viewHolder.btnAdState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                final AndroidVersion[] itemLabel = {android.get(i)};


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setCancelable(true);
                if(android.get(i).getAdState().equals ("1")) {
                    builder.setTitle("Your Ad is Published now");
                    builder.setMessage("Are you want to UnPublsihed it?");
                   // adState="0";

                }else{
                    builder.setTitle("Your Ad is not Published yet");
                    builder.setMessage("Are you want to Publsihed it?");
                    //adState="1";
                }
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        // new RequestTask().execute("http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        // Log.e("Delete Image: ", "http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        if (SDK_INT > 8)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here

                        }

                        //  HttpPost httppost = new HttpPost("http://www.barivara.com/api/deleteAnd.php");


                        String result = null;
                        InputStream is = null;

                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("serial", android.get(i).getAdSerial()));
                        nameValuePairs.add(new BasicNameValuePair("usid", android.get(i).getMobileNumber()));
                        nameValuePairs.add(new BasicNameValuePair("category", android.get(i).getCategory()));
                        nameValuePairs.add(new BasicNameValuePair("sopnoid", android.get(i).getLandLordID()));
                        if(android.get(i).getAdState().equals ("1")) {
                            nameValuePairs.add(new BasicNameValuePair("adState", "0"));
                        }else if(android.get(i).getAdState().equals("0")){
                            nameValuePairs.add(new BasicNameValuePair("adState", "1"));
                        }


                        try
                        {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.barivara.com/arent_upState.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();

                            Log.e("log_tag", "connection success ");
                            Toast.makeText(context, "pass", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error in http connection "+e.toString());
                            Toast.makeText(context, "Connection fail", Toast.LENGTH_SHORT).show();

                        }
                        //convert response to string
                        try
                        {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");

                            }
                            is.close();

                            result=sb.toString();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error converting result "+e.toString());

                        }

                        try
                        {

                            JSONObject json_data = new JSONObject(result);

                            CharSequence w= (CharSequence) json_data.get("re");
                            notifyItemRangeChanged(i,android.size());

                            notifyDataSetChanged();

                            Toast.makeText(context, w, Toast.LENGTH_SHORT).show();

                        }
                        catch(JSONException e)
                        {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                            Toast.makeText(context, "JsonArray fail", Toast.LENGTH_SHORT).show();
                        }


//parse json data





                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.get(i).getCategory().equals("sell")){
                    Intent intent2 = new Intent(context, AgentPostDetailsSell.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("rent", android.get(i).getRent());
                    intent2.putExtra("location", android.get(i).getLocation());
                    intent2.putExtra("bed", android.get(i).getBed());
                    intent2.putExtra("bath", android.get(i).getBath());
                    intent2.putExtra("adserial", android.get(i).getAdSerial());
                    intent2.putExtra("sopnoi", android.get(i).getSopnoid());
                    intent2.putExtra("ad_type", android.get(i).getAirport());
                    context.getApplicationContext().startActivity(intent2);
                }else {
                    Intent intent2 = new Intent(context, AgentPostDetails.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("rent", android.get(i).getRent());
                    intent2.putExtra("location", android.get(i).getLocation());
                    intent2.putExtra("bed", android.get(i).getBed());
                    intent2.putExtra("bath", android.get(i).getBath());
                    intent2.putExtra("adserial", android.get(i).getAdSerial());
                    intent2.putExtra("sopnoi", android.get(i).getSopnoid());
                    intent2.putExtra("ad_type", android.get(i).getAirport());
                    context.getApplicationContext().startActivity(intent2);
                }

            }
        });

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgentPostUpdate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("img", android.get(i).getPimage());
                intent.putExtra("rent", android.get(i).getRent());
                intent.putExtra("location", android.get(i).getLocation());
                intent.putExtra("bed", android.get(i).getBed());
                intent.putExtra("owner", android.get(i).getOwner());
                intent.putExtra("bath", android.get(i).getBath());
                intent.putExtra("adserial", android.get(i).getAdSerial());
                intent.putExtra("phone", android.get(i).getPhone());
                intent.putExtra("address", android.get(i).getAddress());
                intent.putExtra("about", android.get(i).getSinfo());
                intent.putExtra("hsize", android.get(i).getTotalsize());
                intent.putExtra("facility", android.get(i).getFacility());
                intent.putExtra("ad_type", android.get(i).getAirport());
                intent.putExtra("rental_type", android.get(i).getRental_type());
                intent.putExtra("title", android.get(i).getTitle());
                intent.putExtra("advance", android.get(i).getAdvance());
                intent.putExtra("utility", android.get(i).getUtility());
                intent.putExtra("floor", android.get(i).getFloor());
                intent.putExtra("home_type", android.get(i).getHome_type());
                intent.putExtra("uname", android.get(i).getName());
                intent.putExtra("uimage", android.get(i).getImageA());
                intent.putExtra("usid", android.get(i).getBatch());
                intent.putExtra("sopnoi", android.get(i).getSopnoid());


                // etc.
                context.getApplicationContext().startActivity(intent);

            }
        });

        SopnoID = android.get(i).getSopnoid();
        sAdserial = android.get(i).getAdSerial();
        sBed = android.get(i).getBed();
        sBath = android.get(i).getBath();
        sLocation = android.get(i).getLocation();
        sRent = android.get(i).getRent();
        //String adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
        //        sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;

       // adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
     //           sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;

        rent = android.get(i).getRent();
        rental_type = android.get(i).getRental_type();
        facility = android.get(i).getFacility();
        bed = android.get(i).getBed();
        bath = android.get(i).getBath();
        hsize = android.get(i).getTotalsize();
        pimage = android.get(i).getPimage();
        sinfo = android.get(i).getSinfo();
        owner = android.get(i).getOwner();
        phone = android.get(i).getPhone();
        location = android.get(i).getLocation();
        city = android.get(i).getCity();
        address = android.get(i).getAddress();


        title=android.get(i).getTitle();
        advance=android.get(i).getAdvance();
        utility=android.get(i).getUtility();
        floor=android.get(i).getFloor();
        home_type=android.get(i).getHome_type();


        adserial2 = android.get(i).getAdSerial();
        category = android.get(i).getAirport();

        usid = android.get(i).getBatch();
        sopnoi = android.get(i).getBatch_2();
        uname = android.get(i).getName();
        uimage = android.get(i).getImageA();


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                final AndroidVersion[] itemLabel = {android.get(i)};


                adserial2 = android.get(i).getAdSerial();
                category = android.get(i).getCategory();

                usid = android.get(i).getMobileNumber();
                sopnoi = android.get(i).getLandLordID();
                uname = android.get(i).getName();
                uimage = android.get(i).getImageA();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setCancelable(true);
                builder.setTitle("Delete");
                builder.setMessage("Are you want to delete it");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        // new RequestTask().execute("http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        // Log.e("Delete Image: ", "http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        if (SDK_INT > 8)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here

                        }

 //  HttpPost httppost = new HttpPost("http://www.barivara.com/api/deleteAnd.php");


                        String result = null;
                        InputStream is = null;

                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("serial", android.get(i).getAdSerial()));
                        nameValuePairs.add(new BasicNameValuePair("usid", android.get(i).getMobileNumber()));
                        nameValuePairs.add(new BasicNameValuePair("category", android.get(i).getCategory()));
                        nameValuePairs.add(new BasicNameValuePair("sopnoid", android.get(i).getLandLordID()));


                        try
                        {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.barivara.com/arent_delete.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();

                            Log.e("log_tag", "connection success ");
                            Toast.makeText(context, "pass", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error in http connection "+e.toString());
                            Toast.makeText(context, "Connection fail", Toast.LENGTH_SHORT).show();

                        }
                        //convert response to string
                        try
                        {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");

                             //   android.remove(i);
                              //  notifyItemRangeChanged(i,android.size());
                                // Show the removed item label

                             //   Toast.makeText(context,"Removed : " ,Toast.LENGTH_SHORT).show();
                               // Intent k = new Intent(context,UserIn.class);
                               //context.startActivity(k);
                            }
                            is.close();

                            result=sb.toString();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error converting result "+e.toString());

                        }



                        //parse json data
                        try
                        {

                            JSONObject json_data = new JSONObject(result);


                            CharSequence w= (CharSequence) json_data.get("re");
                            //   android.remove(i);
                            //  notifyItemRangeChanged(i,android.size());
                            // Show the removed item label
                            android.remove(i);
                            notifyItemRangeChanged(i,android.size());
                          //  android.clear(); //here items is an ArrayList populating the RecyclerView
                           // android.addAll(AndroidVersion.list);
                            notifyDataSetChanged();

                        //    Toast.makeText(context,"Removed : " ,Toast.LENGTH_SHORT).show();
                            // Intent k = new Intent(context,UserIn.class);
                            //context.startActivity(k);

                            Toast.makeText(context, w, Toast.LENGTH_SHORT).show();

                        }
                        catch(JSONException e)
                        {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                            Toast.makeText(context, "JsonArray fail", Toast.LENGTH_SHORT).show();
                        }


//parse json data





                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button btnDelete, btnEdit, btnDetails, btnPublished, btnRentState, btnAdState;
        private TextView tvRent, tvBath, tvBed, tvLocation, tvHeadline;
        ImageView img_android;
        TextView bottomView;
        public ViewHolder(View view) {
            super(view);

            bottomView = (TextView)view.findViewById(R.id.bottomView);

            tvRent = (TextView)view.findViewById(R.id.tvRent);
            tvHeadline = (TextView)view.findViewById(R.id.tvHeadline);
            if(tvRent !=null){
                tvRent.setVisibility(View.VISIBLE);
            } else {
                tvRent.setVisibility(View.GONE);
                Toast toast = Toast.makeText(context, "Sorry No Data Found",
                        Toast.LENGTH_LONG);
                toast.show();
            }

            tvBath = (TextView)view.findViewById(R.id.tvBath);
            if(tvBath !=null){
                tvBath.setVisibility(View.GONE);
                tvBath.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tub_gray, 0, 0, 0);
            } else {
                tvBath.setVisibility(View.GONE);
            }


            tvBed = (TextView)view.findViewById(R.id.tvBed);
            if(tvBed !=null){
                tvBed.setVisibility(View.GONE);
                tvBed.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bed_gray, 0, 0, 0);
            } else {
                tvBed.setVisibility(View.GONE);
            }

            tvLocation = (TextView)view.findViewById(R.id.tvLocation);
            if(tvLocation !=null){
                tvLocation.setVisibility(View.VISIBLE);
            } else {
                tvLocation.setVisibility(View.GONE);

            }

            img_android = (ImageView)view.findViewById(R.id.ivPic);
            if(img_android !=null){
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }

            btnDelete = (Button)view.findViewById(R.id.btnDelete);
         //   btnDelete.setOnClickListener(this);
            btnEdit = (Button)view.findViewById(R.id.btnEdit);
            btnAdState = (Button)view.findViewById(R.id.btnAdState);
            btnRentState = (Button)view.findViewById(R.id.btnRentState);

            btnDetails = (Button)view.findViewById(R.id.btnDetails);
            //tvRent, tvBed, tvLocation, tvRType, tvAdserial, tvPhone,
            // tvAbout, tvArea, tvFacility, tvExdate, tvAddress,tvimg1;


           // view.setOnClickListener(this);



        }









    }

}