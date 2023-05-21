package com.ghorami.rongpencill.barivara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterAllTentReq extends RecyclerView.Adapter<AdapterAllTentReq.ViewHolder> {
    private ArrayList<AndroidVersiona> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic, tentReqstates;
    String m_Text, receiver, receivername, receiverpic, sender,serndername,senderpic,sendernumber, PayStates;


    public AdapterAllTentReq(Context context, ArrayList<AndroidVersiona> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agent_tenant_request, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");




            viewHolder.tvName.setText(Html.fromHtml(android.get(i).getName()));
            viewHolder.tvTentStart.setText(android.get(i).getTentStart());
            viewHolder.tvTentID.setText(android.get(i).getTentID());
           // viewHolder.tvTentID.setText(android.get(i).getLandLordID());
            PayStates=(android.get(i).getTentID());
        tentReqstates=android.get(i).getTentreq();
        if(android.get(i).getTentreq().equals("0")){
            viewHolder.btnStates.setText("Pending");
            viewHolder.btnStates.setVisibility(View.VISIBLE);
        }else if(android.get(i).getTentreq().equals("1")){
            viewHolder.btnStates.setText("Accepted");
            viewHolder.btnStates.setVisibility(View.VISIBLE);
        }else if(android.get(i).getTentreq().equals("-1")){
            viewHolder.btnStates.setText("Decline");
            viewHolder.btnStates.setVisibility(View.VISIBLE);
        }else{
            viewHolder.btnStates.setVisibility(View.GONE);
        }

            viewHolder.tvImg.setText(Html.fromHtml(android.get(i).getImageT()));

        Picasso.get().load(android.get(i).getImage()).transform(new CircleTransform()).into(viewHolder.img_android);
        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AllTenantRequestDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("img", android.get(i).getImageT());
                intent.putExtra("uName", android.get(i).getName());
                intent.putExtra("tentId", android.get(i).getTentID());
                intent.putExtra("profession", android.get(i).getProfession());
                intent.putExtra("company", android.get(i).getCompany());
                intent.putExtra("nationality", android.get(i).getNationality());
                intent.putExtra("nid", android.get(i).getNID());
                intent.putExtra("email", android.get(i).getEmail());
                intent.putExtra("mobile", android.get(i).getMobileNumber());
                intent.putExtra("usopnoid", android.get(i).getSopnoid());
                intent.putExtra("district", android.get(i).getDistrict());
                intent.putExtra("address", android.get(i).getAddress());
                intent.putExtra("verify", android.get(i).getVerify());
                intent.putExtra("type", android.get(i).getType());
                intent.putExtra("referid", android.get(i).getReferId());
                intent.putExtra("ReqState", android.get(i).getTentreq());



                // etc.
                context.getApplicationContext().startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvSid, tvImg, tvTentStart, tvTentID;
        private TextView tvName1, tvSid1, tvImg1;
        private TextView tvName2, tvSid2, tvImg2;
        ImageView img_android;
        ImageView img_android1;
        ImageView img_android2;
        LinearLayout cardSend;
        LinearLayout cardRec;
        LinearLayout cardRec2;
        Button btnStates;
        public ViewHolder(View view) {
            super(view);


            tvImg = (TextView)view.findViewById(R.id.tvImg);
            tvName = (TextView)view.findViewById(R.id.tvName);

            tvTentID = (TextView)view.findViewById(R.id.tvTentID);
            if(tvTentID !=null){
                tvTentID.setVisibility(View.VISIBLE);
            } else {
                tvTentID.setVisibility(View.GONE);
            }
            btnStates = (Button) view.findViewById(R.id.btnStates);
            if(btnStates !=null){
                btnStates.setVisibility(View.VISIBLE);
            } else {
                btnStates.setVisibility(View.GONE);
            }

            tvTentStart = (TextView)view.findViewById(R.id.tvTentStart);
            if(tvTentStart !=null){
                tvTentStart.setVisibility(View.VISIBLE);
            } else {
                tvTentStart.setVisibility(View.GONE);
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