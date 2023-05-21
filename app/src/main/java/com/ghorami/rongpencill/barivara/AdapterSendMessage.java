package com.ghorami.rongpencill.barivara;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


public class AdapterSendMessage extends RecyclerView.Adapter<AdapterSendMessage.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic, currentUser;
    View view;

    public AdapterSendMessage(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(android.get(i).getSopnoid().equals(android.get(i).getCrent())) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout_send, viewGroup, false);

        } else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);
            view.setBackgroundColor(Color.GREEN);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {



       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");


        if(android.get(i).getMessage().equals ("")){
            viewHolder.tvName.setVisibility(View.GONE);
        }else if (android.get(i).getMessage() .equals ("Negotiation"))
            viewHolder.tvName.setText(Html.fromHtml(android.get(i).getMessage()));

        else {
            viewHolder.tvName.setText(Html.fromHtml( android.get(i).getMessage()));
        }

        viewHolder.tvSid.setText(android.get(i).getSenderTime());

        if(android.get(i).getReceiverImage().equals ("")){
            viewHolder.img_android.setImageResource(R.drawable.ic_launcher);

        } else {
            viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getReceiverImage()));

            Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android);
        }

        agentId = android.get(i).getSender();
        agentName = android.get(i).getMessage();
        agentPic = android.get(i).getReceiverImage();

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
        private TextView tvName, tvSid, tvImg;
      ImageView img_android;
        public ViewHolder(View view) {
            super(view);

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
                img_android.setVisibility(View.VISIBLE);
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