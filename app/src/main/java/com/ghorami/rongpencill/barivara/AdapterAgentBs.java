package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterAgentBs extends RecyclerView.Adapter<AdapterAgentBs.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic;



    public AdapterAgentBs(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agentlayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");


        if(android.get(i).getName().equals ("")){
            viewHolder.tvName.setVisibility(View.GONE);
        }else if (android.get(i).getName() .equals ("Negotiation"))
            viewHolder.tvName.setText(Html.fromHtml(android.get(i).getName()));

        else {
            viewHolder.tvName.setText(Html.fromHtml( android.get(i).getName()));
        }

        viewHolder.tvSid.setText(android.get(i).getSopnoid());

        if(android.get(i).getImage().equals ("")){
            viewHolder.img_android.setImageResource(R.drawable.ic_launcher);

        } else {
            viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getImage()));        agentId = android.get(i).getSopnoid();


        }

        agentId = android.get(i).getSopnoid();
        agentName = android.get(i).getName();
        agentPic = android.get(i).getImage();




    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvSid, tvImg;
      ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            tvImg = (TextView)view.findViewById(R.id.tvImg);
            tvName = (TextView)view.findViewById(R.id.tvName);

            tvSid = (TextView)view.findViewById(R.id.tvSid);
            if(tvSid !=null){
                tvSid.setVisibility(View.GONE);
            } else {
                tvSid.setVisibility(View.GONE);
                Toast toast = Toast.makeText(context, "Sorry No Data Found",
                        Toast.LENGTH_LONG);
                toast.show();
            }

            img_android = (ImageView)view.findViewById(R.id.ivbachelor);
            if(img_android !=null){
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }






        }



    }

}