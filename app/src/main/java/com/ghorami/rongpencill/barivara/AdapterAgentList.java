package com.ghorami.rongpencill.barivara;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;


public class AdapterAgentList extends RecyclerView.Adapter<AdapterAgentList.ViewHolder> {
    private ArrayList<AndroidVersiona> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic;
    String m_Text, receiver, receivername, receiverpic, sender,serndername,senderpic,sendernumber;


    public AdapterAgentList(Context context, ArrayList<AndroidVersiona> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agentlayout_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");


        if(android.get(i).getReceiver()!=("")){
            viewHolder.cardRec.setVisibility(View.GONE);
            viewHolder.cardSend.setVisibility(View.VISIBLE);
            viewHolder.cardRec2.setVisibility(View.GONE);
            viewHolder.tvName.setText(Html.fromHtml( android.get(i).getReceiverName()));

        viewHolder.tvSid.setText(android.get(i).getReceiver());

        if(android.get(i).getReceiverImage().equals ("")){
            viewHolder.img_android.setImageResource(R.drawable.ic_launcher);

        } else {
            viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getReceiverImage()));
            Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android);

        }

      //  agentId = android.get(i).getSopnoid();
    //    agentName = android.get(i).getName();
      //  agentPic = android.get(i).getImage();


        viewHolder.cardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                //    final AndroidVersion[] itemLabel = {android.get(i)};

               receiver = android.get(i).getReceiver();
               receivername=android.get(i).getReceiverName();
               receiverpic= android.get(i).getReceiverImage();
               sender=android.get(i).getSender();
               serndername=android.get(i).getSenderName();
               senderpic = android.get(i).getSenderImage();
               sendernumber = android.get(i).getCourse();




                AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Message");
                builder.setMessage("Are you want to send a message?");
                final EditText input = new EditText(view.getContext());

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Write here a message");
                //  input.setText(String.valueOf(TypesS.getSelectedItem()));

                builder.setView(input);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        // new RequestTask().execute("http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);

                        // Log.e("Delete Image: ", "http://www.barivara.com/api/deleteAnd.php?serial="+adserial2+"&usid="+usid);
                        m_Text = input.getText().toString();
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here

                        }


                        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                            @Override
                            protected String doInBackground(String... params) {


                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                                nameValuePairs.add(new BasicNameValuePair("receiverID",receiver));
                                nameValuePairs.add(new BasicNameValuePair("message", m_Text));
                                nameValuePairs.add(new BasicNameValuePair("rName", receivername));
                                nameValuePairs.add(new BasicNameValuePair("rPicture", receiverpic));
                                nameValuePairs.add(new BasicNameValuePair("senderID", sender));
                                nameValuePairs.add(new BasicNameValuePair("sName", serndername));
                                nameValuePairs.add(new BasicNameValuePair("sImage", senderpic));
                                nameValuePairs.add(new BasicNameValuePair("MobileNumber", sendernumber));


                                try {
                                    HttpClient httpClient = new DefaultHttpClient();

                                    HttpPost httpPost = new HttpPost("http://barivara.com/api/ChatBot.php");

                                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                                    HttpResponse httpResponse = httpClient.execute(httpPost);

                                    HttpEntity httpEntity = httpResponse.getEntity();


                                } catch (ClientProtocolException e) {

                                } catch (IOException e) {

                                }
                                Log.e("DataInsert", "Success");
                                return "Data  send Successfully";

                            }

                            @Override
                            protected void onPostExecute(String result) {

                                super.onPostExecute(result);

                                 //  Toast.makeText(view.getContext(), "Send message"+(android.get(i).getReceiver())+m_Text+(android.get(i).getReceiverName())+(android.get(i).getReceiverImage())+(android.get(i).getSender())+(android.get(i).getSenderName())+(android.get(i).getSenderImage())+(android.get(i).getCourse()), Toast.LENGTH_LONG).show();

                                //  adapterReceiveMessage.notifyItemRangeChanged();
                                //  android.clear(); //here items is an ArrayList populating the RecyclerView
                                // android.addAll(AndroidVersion.list);
                                // toursad.clear();
                                Log.e("Data", "Success");//  Toast.makeText(AgentAddDetails.this, "", Toast.LENGTH_LONG).show();



                                Intent intent = new Intent(view.getContext(), AgentMessageAll.class);
                                intent.putExtra("mType", "newMessage");
                                intent.putExtra("imageAgent", receiverpic);
                                intent.putExtra("agentN", receivername);
                                intent.putExtra("agentID", receiver);
                                // etc.
                              //  Toast.makeText(view.getContext(), agentId, Toast.LENGTH_LONG).show();
                                view.getContext().startActivity(intent);

                            }
                        }

                        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

                        sendPostReqAsyncTask.execute(m_Text, receiver, receivername, receiverpic, sender,serndername,senderpic,sendernumber );




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
        }else if(android.get(i).getReceiver().equals ("")){
            viewHolder.cardRec2.setVisibility(View.GONE);
            viewHolder.cardSend.setVisibility(View.GONE);
            viewHolder.cardRec.setVisibility(View.VISIBLE);
            viewHolder.tvName1.setText("Sorry! this number may be not registered in barivara");
            viewHolder.tvName1.setTextColor(R.color.gray);
            viewHolder.img_android1.setVisibility(View.INVISIBLE);
            viewHolder.tvSid1.setVisibility(View.GONE);
            viewHolder.tvSid1.setText("no");

        }else{
            viewHolder.cardRec.setVisibility(View.GONE);
            viewHolder.cardSend.setVisibility(View.GONE);
            viewHolder.cardRec2.setVisibility(View.GONE);


        }
    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvSid, tvImg;
        private TextView tvName1, tvSid1, tvImg1;
        private TextView tvName2, tvSid2, tvImg2;
        ImageView img_android;
        ImageView img_android1;
        ImageView img_android2;
        LinearLayout cardSend;
        LinearLayout cardRec;
        LinearLayout cardRec2;
        public ViewHolder(View view) {
            super(view);
            cardSend = (LinearLayout) view.findViewById(R.id.cardSen);
            cardRec = (LinearLayout) view.findViewById(R.id.cardRec);
            cardRec2 = (LinearLayout) view.findViewById(R.id.cardRec2);

            tvImg = (TextView)view.findViewById(R.id.tvImg);
            tvName = (TextView)view.findViewById(R.id.tvName);

            tvSid = (TextView)view.findViewById(R.id.tvSid);
            if(tvSid !=null){
                tvSid.setVisibility(View.VISIBLE);
            } else {
                tvSid.setVisibility(View.GONE);
            }

            img_android = (ImageView)view.findViewById(R.id.ivbachelor);
            if(img_android !=null){
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }

            tvImg1 = (TextView)view.findViewById(R.id.tvImg1);
            tvName1 = (TextView)view.findViewById(R.id.tvName1);

            tvSid1 = (TextView)view.findViewById(R.id.tvSid1);
            if(tvSid1 !=null){
                tvSid1.setVisibility(View.VISIBLE);
            } else {
                tvSid1.setVisibility(View.GONE);
            }

            img_android1 = (ImageView)view.findViewById(R.id.ivbachelor1);
            if(img_android1 !=null){
                img_android1.setVisibility(View.VISIBLE);
            } else {
                img_android1.setVisibility(View.GONE);
            }

            tvImg2 = (TextView)view.findViewById(R.id.tvImg2);
            tvName2 = (TextView)view.findViewById(R.id.tvName2);

            tvSid2 = (TextView)view.findViewById(R.id.tvSid2);
            if(tvSid2 !=null){
                tvSid2.setVisibility(View.VISIBLE);
            } else {
                tvSid2.setVisibility(View.GONE);
            }

            img_android2 = (ImageView)view.findViewById(R.id.ivbachelor2);
            if(img_android2 !=null){
                img_android2.setVisibility(View.VISIBLE);
            } else {
                img_android2.setVisibility(View.GONE);
            }

        }




    }

}