package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.StrictMode;
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


public class AgentPostPicAdapter extends RecyclerView.Adapter<AgentPostPicAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String sRent, sLocation, sBed, sBath, sAdserial, SopnoID;
    String res;

    String img,hsize,adserial,about,Exdate;

    private String adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
            sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;




    public AgentPostPicAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_picture, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");



        if(android.get(i).getPimage().equals ("")){
            viewHolder.img_android.setImageResource(R.mipmap.ic_launcher);
        } else {
            Picasso.get().load(android.get(i).getPimage()).into(viewHolder.img_android);

        }

        viewHolder.tvLocation.setText(android.get(i).getPimage());



        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgentPostUpdate.class);
                intent.putExtra("img", android.get(i).getPimage());

                intent.putExtra("adserial", android.get(i).getAdSerial());

                intent.putExtra("usid", android.get(i).getBatch());
                intent.putExtra("sopnoi", android.get(i).getSopnoid());


                // etc.
               context.startActivity(intent);

            }
        });

        SopnoID = android.get(i).getSopnoid();
        sAdserial = android.get(i).getAdSerial();

        //String adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
        //        sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;

       // adSerial, sopnoid, title, rent, advance, utility, bed, bath, floor, totalsize,
     //           sinfo, home_type, rental_type, facility, pimage, owner, phone, location, city, address, timestamp;


        pimage = android.get(i).getPimage();
        adserial2 = android.get(i).getAdSerial();
        usid = android.get(i).getBatch();
        sopnoi = android.get(i).getBatch_2();


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                final AndroidVersion[] itemLabel = {android.get(i)};



                adserial2 = android.get(i).getAdSerial();
                usid = android.get(i).getBatch();
                sopnoi = android.get(i).getBatch_2();
                if (adserial2==pimage){

                }

                AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
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
                        nameValuePairs.add(new BasicNameValuePair("usid", android.get(i).getBatch()));
                        nameValuePairs.add(new BasicNameValuePair("file", android.get(i).getPimage()));
                        nameValuePairs.add(new BasicNameValuePair("sopnoid", sopnoi));


                        try
                        {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://barivara.com/aPicDel.php");
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
                            res= String.valueOf((CharSequence) json_data.get("re"));
                            //   android.remove(i);
                            //  notifyItemRangeChanged(i,android.size());
                            // Show the removed item label
                        /**    if(res.equals("0")){
                                Toast.makeText(context, "Woops something is wrong"+(res), Toast.LENGTH_SHORT).show();
                            }else if(res.equals("1")){
                            android.remove(i);
                            notifyItemRangeChanged(i,android.size());
                            //  android.clear(); //here items is an ArrayList populating the RecyclerView
                            // android.addAll(AndroidVersion.list);
                            notifyDataSetChanged();
                                Toast.makeText(context, "Picture deleted successfully", Toast.LENGTH_SHORT).show();
                            }

                         **/
                            Intent intent = new Intent(context, AgentPostUpdatePicture.class);
                            intent.putExtra("userpic", android.get(i).getPimage());
                            intent.putExtra("adserial", android.get(i).getAdSerial());
                            intent.putExtra("usid", android.get(i).getBatch());
                            intent.putExtra("sopnoi", android.get(i).getSopnoid());


                            // etc.
                            context.startActivity(intent);


                        //    Toast.makeText(context,"Removed : " ,Toast.LENGTH_SHORT).show();
                            // Intent k = new Intent(context,UserIn.class);
                            //context.startActivity(k);



                        }
                        catch(JSONException e)
                        {
                            Log.e("log_tag", "Error getting data "+e.toString());
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

        Button btnDelete, btnEdit, btnDetails;
        private TextView tvRent, tvBath, tvBed, tvLocation;
        ImageView img_android;
        TextView bottomView;
        public ViewHolder(View view) {
            super(view);

            bottomView = (TextView)view.findViewById(R.id.bottomView);

            tvLocation = (TextView)view.findViewById(R.id.tv_version);

            img_android = (ImageView)view.findViewById(R.id.ivPic);
            if(img_android !=null){
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }

            btnDelete = (Button)view.findViewById(R.id.btnDelete);
         //   btnDelete.setOnClickListener(this);
            btnEdit = (Button)view.findViewById(R.id.btnEdit);





        }









    }

}