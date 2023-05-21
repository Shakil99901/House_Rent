package com.ghorami.rongpencill.barivara;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.ArrayList;


public class BasicRentAdapterBnOnline extends RecyclerView.Adapter<BasicRentAdapterBnOnline.ViewHolder> {
    private ArrayList<AndroidVersiona> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String sRent, sLocation, sBed, sBath, sAdserial, SopnoID;

   // int[] color_arr={Color.BLUE,Color.CYAN, Color.DKGRAY,Color.GREEN,Color.RED};

   // int[] color_arr={Color.CYAN,Color.RED,Color.GREEN};


    public BasicRentAdapterBnOnline(Context context, ArrayList<AndroidVersiona> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agentlayoutrent, viewGroup, false);

      //  View row;

   /**     view.setBackgroundColor(color_arr[i]);// this set background color
        if (i == 0) {
            view.setBackgroundColor(Color.BLUE);
        }
        else if (i % 2 == 1) {
            view.setBackgroundColor(Color.RED);
        }
        else if (i % 2 == 0) {
            view.setBackgroundColor(Color.GREEN);
        }
**/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");

        if(android.get(i).getRent().equals ("")){
           // viewHolder.tvRent.setVisibility(View.GONE);
            viewHolder.tvRent.setText(Html.fromHtml("Negotiation"));
        } else if(android.get(i).getRent().equals ("0")){
            viewHolder.tvRent.setText(Html.fromHtml("Negotiation"));
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

        viewHolder.tvBath.setTypeface(custom_font);

        viewHolder.tvLocation.setText(Html.fromHtml(android.get(i).getLocation()));
        viewHolder.tvLocation.setTypeface(custom_font);

        if(android.get(i).getPimage().equals ("")){
            viewHolder.img_android.setImageResource(R.mipmap.ic_launcher);
        } else {
        //    Picasso.with(context).load(android.get(i).getPimage()).into(viewHolder.img_android);
            new DownloadImageTask(viewHolder.img_android).execute(android.get(i).getPimage());


        }

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((android.get(i).getAirport()).equals("sell")){

                    Intent intent = new Intent(context, BasicAdDetailsSell.class);
                    intent.putExtra("rent", android.get(i).getRent());
                    intent.putExtra("location", android.get(i).getLocation());
                    intent.putExtra("bed", sBed);
                    intent.putExtra("bath", sBath);
                    intent.putExtra("adserial2", android.get(i).getAdSerial());
                    intent.putExtra("sopnoi", android.get(i).getSopnoid());
                    // etc.
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, BasicAdDetails.class);
                    intent.putExtra("rent", android.get(i).getRent());
                    intent.putExtra("location", android.get(i).getLocation());
                    intent.putExtra("bed", sBed);
                    intent.putExtra("bath", sBath);
                    intent.putExtra("adserial2", android.get(i).getAdSerial());
                    intent.putExtra("sopnoi", android.get(i).getSopnoid());
                    // etc.
                    context.startActivity(intent);
                }
            }
        });

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


        SopnoID = android.get(i).getSopnoid();
        sAdserial = android.get(i).getAdSerial();
        sBed = android.get(i).getBed();
        sBath = android.get(i).getBath();
        sLocation = android.get(i).getLocation();
        sRent = android.get(i).getRent();


    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvRent, tvBath, tvBed, tvLocation;
      ImageView img_android;
        TextView bottomView;
        public ViewHolder(View view) {
            super(view);

            bottomView = (TextView)view.findViewById(R.id.bottomView);

            tvRent = (TextView)view.findViewById(R.id.tvRent);
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
                tvBath.setVisibility(View.VISIBLE);
                tvBath.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tub_gray, 0, 0, 0);
            } else {
                tvBath.setVisibility(View.GONE);
            }


            tvBed = (TextView)view.findViewById(R.id.tvBed);
            if(tvBed !=null){
                tvBed.setVisibility(View.VISIBLE);
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
                img_android.setImageResource(R.drawable.ic_launcher_e);
            }






        }







    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage){ this.bmImage = bmImage;}


        @Override
        protected Bitmap doInBackground(String...urls) {

            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon;
        }
        protected void  onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }

}