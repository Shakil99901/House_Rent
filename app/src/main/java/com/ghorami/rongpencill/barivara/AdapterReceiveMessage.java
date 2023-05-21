package com.ghorami.rongpencill.barivara;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Build.VERSION.SDK_INT;


public class AdapterReceiveMessage extends RecyclerView.Adapter<AdapterReceiveMessage.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, msgCode, yourID, uname, uimage;
    String agentName, agentId, agentPic, currentUser, userMobile;
    View view;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds


    public AdapterReceiveMessage(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
      // LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout_send, viewGroup, false);

        return new ViewHolder(view);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    /**
     * Returns a list with all links contained in the input
     */
    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {


       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");


        if(android.get(i).getSender().equals(android.get(i).getReceiver())){
            viewHolder.cardRec.setVisibility(View.GONE);
            viewHolder.cardSend.setVisibility(View.VISIBLE);
            if(android.get(i).getMessage().equals ("")){
                viewHolder.tvName.setVisibility(View.GONE);}

            else {
                if(android.get(i).getReceiverFile().equals("")) {
                    viewHolder.messageImage.setVisibility(View.GONE);
                }else{
                    viewHolder.messageImage.setVisibility(View.VISIBLE);
                    Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.messageImage);

                }
                if(android.get(i).getMessageState().equals ("1")) {

                    viewHolder.tvName.setText(Html.fromHtml(android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))));
                    Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.ivstate);

                    viewHolder.tvSid.setText(android.get(i).getSenderTime());


                }else if(android.get(i).getMessageState().equals ("")){
                    viewHolder.tvName.setText(Html.fromHtml(android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))));
                    viewHolder.tvSid.setText(android.get(i).getSenderTime());


                }else if(android.get(i).getMessageState().equals ("0")){
                    viewHolder.tvName.setText(Html.fromHtml(android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))));
                    viewHolder.tvSid.setText(android.get(i).getSenderTime());

                }


                List<String> extractedUrls = extractUrls(String.valueOf((Html.fromHtml(android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))))));

                for (String url : extractedUrls)
                {
                   // System.out.println(url);
                    Log.e("log_url", url);
                    if(url!=("")){
                        if (Build.VERSION.SDK_INT > 9) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }

                        URLConnection connection = null;
                        try {
                            connection = new URL(url).openConnection();
                            String contentType = connection.getHeaderField("Content-Type");
                            //boolean image = contentType.startsWith("image/");
                            if((contentType.startsWith("image/"))){
                                viewHolder.messageImage.setVisibility(View.VISIBLE);
                                viewHolder.messageImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(view.getContext(), SopnoWeb.class);
                                        intent1.putExtra("WebUrl",(url));
                                        view.getContext().startActivity(intent1);
                                    }
                                });
                                Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.messageImage);

                            }else{
                                viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(view.getContext(), SopnoWeb.class);
                                        intent1.putExtra("WebUrl",(url));
                                        view.getContext().startActivity(intent1);
                                    }
                                });
                                viewHolder.messageImage.setVisibility(View.GONE);
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

// Set the view to fade in
           // setScaleAnimation(viewHolder.tvName);


          //  setScaleAnimation(viewHolder.tvSid);
            if(android.get(i).getReceiverImage().equals ("")){
                viewHolder.img_android.setImageResource(R.drawable.ic_launcher);

            } else {
                viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getReceiverImage()));
                Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android);

            }
        }else{

            viewHolder.cardRec.setVisibility(View.VISIBLE);
            viewHolder.cardSend.setVisibility(View.GONE);

            List<String> extractedUrls = extractUrls(String.valueOf((Html.fromHtml(android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))))));

            for (String url : extractedUrls)
            {
               // System.out.println(url);
                Log.e("log_url", url);


                if(url!=("")){
                    if (Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }


                    URLConnection connection = null;
                    try {
                        connection = new URL(url).openConnection();
                        String contentType = connection.getHeaderField("Content-Type");
                      //  boolean image = contentType.startsWith("image/");
                        if((contentType.startsWith("image/"))){
                            viewHolder.messageImage2.setVisibility(View.VISIBLE);
                            viewHolder.messageImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(view.getContext(), SopnoWeb.class);
                                    intent1.putExtra("WebUrl",(url));
                                    view.getContext().startActivity(intent1);
                                }
                            });
                            Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.messageImage2);

                        }else{
                            viewHolder.tvName1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(view.getContext(), SopnoWeb.class);
                                    intent1.putExtra("WebUrl",(url));
                                    view.getContext().startActivity(intent1);
                                }
                            });
                            viewHolder.messageImage2.setVisibility(View.GONE);
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            if(android.get(i).getMessage().equals ("")){
                viewHolder.tvName1.setVisibility(View.GONE);}
            else {

              viewHolder.tvName1.setText(Html.fromHtml( android.get(i).getMessage().replace("\\\n", System.getProperty("line.separator"))));


            }
          //  setScaleAnimation(viewHolder.tvName1);
            viewHolder.tvSid1.setText(android.get(i).getSenderTime());
          //  setScaleAnimation(viewHolder.tvSid1);
            viewHolder.tvSrName.setText(android.get(i).getSenderName());
           // setScaleAnimation(viewHolder.tvSrName);
            if(android.get(i).getReceiverFile().equals("")){
                viewHolder.messageImage2.setVisibility(View.GONE);
            }else{
                viewHolder.messageImage2.setVisibility(View.VISIBLE);
                Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.messageImage2);

            }



            if(android.get(i).getSenderImage().equals ("")){
                viewHolder.img_android1.setImageResource(R.drawable.ic_launcher);
            } else {
                viewHolder.tvImg1.setText(Html.fromHtml(android.get(i).getSenderImage()));
                Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android1);

            }
        }



        agentId = android.get(i).getSender();
        agentName = android.get(i).getSenderName();
        agentPic = android.get(i).getSenderImage();
        msgCode = android.get(i).getCourse_code();
        yourID = android.get(i).getReceiver();


        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
            //    final AndroidVersion[] itemLabel = {android.get(i)};





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

                        nameValuePairs.add(new BasicNameValuePair("cid", msgCode));
                        nameValuePairs.add(new BasicNameValuePair("senderID", yourID));
                        nameValuePairs.add(new BasicNameValuePair("receiverID", agentId));

                        try
                        {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.barivara.com/api/ChatBotDel.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();

                            Log.e("log_tag", "connection success ");
                         //   Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Log.e("log_tag", "Error in http connection "+e.toString());
                        //    Toast.makeText(context, "Connection fail", Toast.LENGTH_SHORT).show();

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

                        //    Toast.makeText(context, w, Toast.LENGTH_SHORT).show();

                        }
                        catch(JSONException e)
                        {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                        //    Toast.makeText(context, "JsonArray fail", Toast.LENGTH_SHORT).show();
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

        // OnTouchListener for holder/vector color change
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //some code....
                        v.performClick();

                        break;
                    case MotionEvent.ACTION_UP:

                        final String urlRecord = "http://barivara.com/api/chatState_Post.php";
                        final String AdSerial, sCid, Sopnoid1, sAgentID, DeviceIP, CurrentDate, UserID, adType, USID, DSID, UserMobile, VisitValue;
                        String manufacturer = Build.MANUFACTURER;
                        String model = Build.MODEL;
                        int version = Build.VERSION.SDK_INT;
                        String versionRelease = Build.VERSION.RELEASE;
                        DeviceIP = manufacturer + "-"+model;

                        DSID = DeviceIP +"-"+version+"-"+versionRelease;
                        UserMobile="";
                        VisitValue="1".toString();
                        DateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss", Locale.ENGLISH);
                        CurrentDate = df.format(Calendar.getInstance().getTime());


                        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                            @Override
                            protected String doInBackground(String... params) {


                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                                nameValuePairs.add(new BasicNameValuePair("ReceiverId", android.get(i).getReceiver()));
                                nameValuePairs.add(new BasicNameValuePair("DeviceIP", DeviceIP));
                                nameValuePairs.add(new BasicNameValuePair("DSID", DSID));
                                nameValuePairs.add(new BasicNameValuePair("Cid", android.get(i).getCourse_code()));
                                nameValuePairs.add(new BasicNameValuePair("SenderId", android.get(i).getSender()));
                                nameValuePairs.add(new BasicNameValuePair("UserMobile", UserMobile));
                                nameValuePairs.add(new BasicNameValuePair("ReceiveDate", CurrentDate));
                                nameValuePairs.add(new BasicNameValuePair("StateValue", VisitValue));



                                try {
                                    HttpClient httpClient = new DefaultHttpClient();

                                    HttpPost httpPost = new HttpPost(urlRecord);

                                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                                    HttpResponse httpResponse = httpClient.execute(httpPost);

                                    HttpEntity httpEntity = httpResponse.getEntity();


                                } catch (ClientProtocolException e) {

                                } catch (IOException e) {

                                }
                                Log.e("Data", "Success");
                                return "";

                            }

                            @Override
                            protected void onPostExecute(String result) {

                                super.onPostExecute(result);

                                Log.e("Data", "Success");//  Toast.makeText(AgentAddDetails.this, "", Toast.LENGTH_LONG).show();
                                /**     Intent intent = new Intent(getApplicationContext(),
                                 AgentDashboard.class);
                                 intent.putExtra("Password", uPass1);
                                 intent.putExtra("MobileNumber", uMobile1);
                                 startActivity(intent);
                                 **/

                            }
                        }

                        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

                        sendPostReqAsyncTask.execute(android.get(i).getCourse_code(), DeviceIP, CurrentDate, android.get(i).getReceiver(), android.get(i).getSender(), DSID, UserMobile, VisitValue);


                        break;
                    default:
                        break;
                }
                return true;
            }
                // Define setting holder

                // ImageView for changing color

        });



        /**    if (i == 0) {
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
    **/

    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvName, tvSrName, tvSid, tvImg, tvName1, tvSid1, tvImg1;
        RelativeLayout cardSend;
        RelativeLayout cardRec;
      ImageView img_android, img_android1, ivstate, messageImage, messageImage2;
        public ViewHolder(View view) {
            super(view);
            cardSend = (RelativeLayout) view.findViewById(R.id.cardSend);
            cardRec = (RelativeLayout) view.findViewById(R.id.cardRec);
            tvImg1 = (TextView)view.findViewById(R.id.tvImg1);
            tvName1 = (TextView)view.findViewById(R.id.tvMessage1);

            tvSrName = (TextView)view.findViewById(R.id.tvName1);
            if(tvSrName !=null){
                tvSrName.setVisibility(View.GONE);
            } else {
                tvSrName.setVisibility(View.GONE);
            }

            tvSid1 = (TextView)view.findViewById(R.id.tvTime1);
            if(tvSid1 !=null){
                tvSid1.setVisibility(View.VISIBLE);
            } else {
                tvSid1.setVisibility(View.GONE);
            }

            img_android1 = (ImageView)view.findViewById(R.id.ivbachelor1);
            ivstate = (ImageView)view.findViewById(R.id.ivstate);
           messageImage = (ImageView)view.findViewById(R.id.messageImage);
            messageImage2 = (ImageView)view.findViewById(R.id.messageImage2);

            if(img_android1 !=null){
                img_android1.setVisibility(View.VISIBLE);
            } else {
                img_android1.setVisibility(View.GONE);
            }
             if(messageImage2 !=null){
                 messageImage2.setVisibility(View.VISIBLE);
                        } else {
                 messageImage2.setVisibility(View.GONE);
                        }

                        if(messageImage !=null){
                            messageImage2.setVisibility(View.VISIBLE);
                        } else {
                            messageImage2.setVisibility(View.GONE);
                        }

                        if(ivstate !=null){
                 ivstate.setVisibility(View.VISIBLE);
                        } else {
                 ivstate.setVisibility(View.GONE);
                        }

            tvImg = (TextView)view.findViewById(R.id.tvImg);
            tvName = (TextView)view.findViewById(R.id.tvMessage);

            tvSid = (TextView)view.findViewById(R.id.tvTime);
            if(tvSid !=null){
                tvSid.setVisibility(View.VISIBLE);
            } else {
                tvSid.setVisibility(View.GONE);
            }

            img_android = (ImageView)view.findViewById(R.id.ivbachelor);
            if(img_android !=null){
                img_android.setVisibility(View.GONE);
            } else {
                img_android.setVisibility(View.GONE);
            }



         //   view.setOnClickListener(this);



        }



        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailsChat.class);
            intent.putExtra("img", agentPic);
            intent.putExtra("agentName", agentName);
            intent.putExtra("agentId", agentId);
            // etc.
            v.getContext().startActivity(intent);

        }
    }

}