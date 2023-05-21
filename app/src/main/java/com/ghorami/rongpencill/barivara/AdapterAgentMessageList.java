package com.ghorami.rongpencill.barivara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterAgentMessageList extends RecyclerView.Adapter<AdapterAgentMessageList.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic;
    String oldKr, NewSr;


    public AdapterAgentMessageList(Context context, ArrayList<AndroidVersion> android) {
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/NotoSansBengali.ttf");


        if(android.get(i).getSender().equals(android.get(i).getCourse_code())) {
            viewHolder.cardRec.setVisibility(View.GONE);
            viewHolder.cardSend.setVisibility(View.GONE);
            viewHolder.cardRec2.setVisibility(View.GONE);
            viewHolder.tvName2.setTextColor(R.color.gray);
        }else if(android.get(i).getSender()==("")){
            viewHolder.cardRec2.setVisibility(View.VISIBLE);
            viewHolder.tvName2.setText("Currently No message Available");

            viewHolder.tvName2.setTextColor(R.color.gray);
          //  viewHolder.tvName2.setTypeface(custom_font);
        } else {
            if(android.get(i).getSender()!=("")) {

         //     if(android.get(i).getReceiver()!=(android.get(i).getCourse_code())& android.get(i).getSender()!=(android.get(i).getCourse_code())){

                viewHolder.cardRec.setVisibility(View.VISIBLE);

                if (android.get(i).getSenderName().equals("")) {
                    //viewHolder.tvName.setVisibility(View.GONE);
                    //viewHolder.tvName.setVisibility(View.GONE);
                    viewHolder.img_android1.setVisibility(View.INVISIBLE);
                    viewHolder.tvSid1.setVisibility(View.GONE);
                } else {
                    viewHolder.tvName1.setText(Html.fromHtml(android.get(i).getSenderName()));
                   // viewHolder.tvName1.setTypeface(custom_font);

                }

                //  viewHolder.tvSid.setText(android.get(i).getSopnoid());
                viewHolder.tvSid1.setText(android.get(i).getMessage());

                if (android.get(i).getSenderImage().equals("")) {
                    viewHolder.img_android1.setImageResource(R.drawable.ic_launcher);

                } else {
                    viewHolder.tvImg1.setText(Html.fromHtml(android.get(i).getSenderImage()));
                    Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android1);

                }

                viewHolder.cardRec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, AgentMessageDetails.class);
                        intent.putExtra("mType", "oldMessage");
                        intent.putExtra("imageAgent", android.get(i).getSenderImage());
                        intent.putExtra("agentN", android.get(i).getSenderName());
                        intent.putExtra("agentID", android.get(i).getSender());
                        intent.putExtra("cId", android.get(i).getCourse());
                        intent.putExtra("aMessage", android.get(i).getMessage());
                        intent.putExtra("aTime", android.get(i).getSenderTime());
                        //intent.putExtra("arTime", android.get(i).getReceiverTime());
                       // intent.putExtra("aStates", android.get(i).getMessageState());
                      //  intent.putExtra("aFile", android.get(i).getReceiverFile());
                        // etc.
                        //  Toast.makeText(context, agentId, Toast.LENGTH_LONG).show();

                        context.startActivity(intent);

                    }
                });



             // }

            }

            /**

            if(android.get(i).getReceiver().equals(android.get(i).getCourse_code())){

              //  if(android.get(i).getSender()!=(android.get(i).getCourse_code())&android.get(i).getReceiver()!=(android.get(i).getCourse_code())) {
               if(android.get(i).getSender()==(oldKr) || android.get(i).getReceiver()==(oldKr)){

               }else{
                   viewHolder.cardSend.setVisibility(View.VISIBLE);
                   if (android.get(i).getSenderName().equals("")) {
                       //viewHolder.tvName.setVisibility(View.GONE);
                       //viewHolder.tvName.setVisibility(View.GONE);
                       viewHolder.img_android.setVisibility(View.INVISIBLE);
                       viewHolder.tvSid.setVisibility(View.GONE);
                   } else {
                       viewHolder.tvName.setText(Html.fromHtml(android.get(i).getSenderName()));
                   }

                   //  viewHolder.tvSid.setText(android.get(i).getSopnoid());
                   viewHolder.tvSid.setText(android.get(i).getMessage());

                   if (android.get(i).getSenderImage().equals("")) {
                       viewHolder.img_android.setImageResource(R.drawable.ic_launcher);

                   } else {
                       viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getSenderImage()));
                       Picasso.with(context).load(android.get(i).getSenderImage()).transform(new CircleTransform()).into(viewHolder.img_android);

                   }

                   viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           Intent intent = new Intent(context, AgentMessageDetails.class);
                           intent.putExtra("mType", "oldMessage");
                           intent.putExtra("imageAgent", android.get(i).getSenderImage());
                           intent.putExtra("agentN", android.get(i).getSenderName());
                           intent.putExtra("agentID", android.get(i).getSender());
                           // etc.
                           //  Toast.makeText(context, agentId, Toast.LENGTH_LONG).show();

                           context.startActivity(intent);

                       }
                   });
               }


             //   }

            }
**/



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