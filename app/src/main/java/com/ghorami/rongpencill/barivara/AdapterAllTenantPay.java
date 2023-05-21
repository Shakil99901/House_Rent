package com.ghorami.rongpencill.barivara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterAllTenantPay extends RecyclerView.Adapter<AdapterAllTenantPay.ViewHolder> {
    private ArrayList<AndroidVersiona> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic;
    String m_Text, receiver, receivername, receiverpic, sender,serndername,senderpic,sendernumber, PayStates;


    public AdapterAllTenantPay(Context context, ArrayList<AndroidVersiona> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agent_tenant_bill_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");




            viewHolder.tvName.setText(Html.fromHtml(android.get(i).getName()));
            viewHolder.tvTentStart.setText(android.get(i).getTentStart());
            viewHolder.tvTentSl.setText(android.get(i).getLandLordID());
            viewHolder.tvTentID.setText(android.get(i).getTentID());
            viewHolder.tvRef.setText(android.get(i).getReferId());
            PayStates=(android.get(i).getPayStates());
        if(PayStates.equals('1')){
           // viewHolder.btnStates.setText("PAID");

        }else{
           // viewHolder.btnStates.setText("UNPAID");
        }
            viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getImageT()));



    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvSid, tvImg, tvTentStart, tvTentID;
        private TextView tvMonth, tvTentSl, tvAmount, tvRef;


        public ViewHolder(View view) {
            super(view);


            tvImg = (TextView)view.findViewById(R.id.tvImg);
            tvRef = (TextView)view.findViewById(R.id.tvRef);
            tvAmount = (TextView)view.findViewById(R.id.tvAmount);
            tvTentSl = (TextView)view.findViewById(R.id.tvTentSl);
            tvMonth = (TextView)view.findViewById(R.id.tvMonth);
            tvName = (TextView)view.findViewById(R.id.tvName);

            tvTentID = (TextView)view.findViewById(R.id.tvTentID);
            if(tvTentID !=null){
                tvTentID.setVisibility(View.VISIBLE);
            } else {
                tvTentID.setVisibility(View.GONE);
            }

            tvTentStart = (TextView)view.findViewById(R.id.tvTentStart);
            if(tvTentStart !=null){
                tvTentStart.setVisibility(View.VISIBLE);
            } else {
                tvTentStart.setVisibility(View.GONE);
            }




        }




    }

}