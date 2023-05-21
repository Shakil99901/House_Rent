package com.ghorami.rongpencill.barivara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class CarrerAdapter extends RecyclerView.Adapter<CarrerAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    String adserial2, category, usid, ItemPos, sopnoi, uname, uimage;
    String agentName, agentId, agentPic;
    String m_Text, receiver, receivername, receiverpic, sender,serndername,senderpic,sendernumber;

    public String spotq;

    Pattern p = Pattern.compile("(\\d+)\\s+(.*?)s? ago");

    Map<String, Integer> fields = new HashMap<String, Integer>() {{
        put("second", Calendar.SECOND);
        put("minute", Calendar.MINUTE);
        put("hour",   Calendar.HOUR);
        put("day",    Calendar.DATE);
        put("week",   Calendar.WEEK_OF_YEAR);
        put("month",  Calendar.MONTH);
        put("year",   Calendar.YEAR);
    }};

    String[] tests = {
            "3 days ago",
            "1 minute ago",
            "2 years ago"
    };



    public CarrerAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       // final Place place = new PlaceData().placeList().get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/NotoSansBengali.ttf");

// final Place place = new PlaceData().placeList().get(position);
        viewHolder.tv_name.setText(Html.fromHtml(android.get(i).getName()));
        // viewHolder.tv_name.setTypeface(custom_font);
        viewHolder.tname.setText(android.get(i).getBatch_2());
        viewHolder.tid.setText("Salary-" + android.get(i).getBatch());
        viewHolder.tv_version.setText(Html.fromHtml(android.get(i).getType()));
        // viewHolder.tv_version.setTypeface(custom_font);
        viewHolder.tvCat.setText(Html.fromHtml(android.get(i).getCategory()));
        // viewHolder.tvCat.setTypeface(custom_font);
        viewHolder.timestamp.setText(Html.fromHtml("Expired Date-" + android.get(i).getBest_time()));
        // viewHolder.timestamp.setTypeface(custom_font);
  /*    spotq = viewHolder.times.getText().toString();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse("spotq");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();

        String timePassedString = String.valueOf(getRelativeTimeSpanString (epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        viewHolder.timestamp.setText(timePassedString);


      //  String timeAgo = getRelativeTimeSpanString(
        //        Long.parseLong("2017-02-25 02:41:09"),
          //      System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

      //  viewHolder.timestamp.setText(timeAgo);
*/
        Picasso.get().load(android.get(i).getPimage()).into(viewHolder.img_android);


    }


    @Override
    public int getItemCount() {

        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tvCat,tv_api_level, tid, timestamp, tname, times;

        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            if (tv_name != null) {
                tv_name.setVisibility(View.VISIBLE);
            } else {
                tv_name.setVisibility(View.GONE);
                Toast toast = Toast.makeText(context, "Sorry No Data Found",
                        Toast.LENGTH_LONG);
                toast.show();
            }

            tname = (TextView) view.findViewById(R.id.tname);
            if (tname != null) {
                tname.setVisibility(View.GONE);
            } else {
                tname.setVisibility(View.GONE);

            }

            tid = (TextView) view.findViewById(R.id.tid);
            if (tid != null) {
                tid.setVisibility(View.VISIBLE);
            } else {
                tid.setVisibility(View.GONE);

            }

            //  times = (TextView) view.findViewById(R.id.times);

            timestamp = (TextView) view.findViewById(R.id.timestamp);
            if (timestamp != null) {
                timestamp.setVisibility(View.VISIBLE);
            } else {
                timestamp.setVisibility(View.GONE);

            }


            tv_version = (TextView) view.findViewById(R.id.tv_version);
            if (tv_version != null) {
                tv_version.setVisibility(View.VISIBLE);
            } else {
                tv_version.setVisibility(View.GONE);

            }
            tvCat = (TextView) view.findViewById(R.id.tvCat);
            if (tvCat != null) {
                tvCat.setVisibility(View.VISIBLE);
            } else {
                tvCat.setVisibility(View.GONE);
            }
            img_android = (ImageView) view.findViewById(R.id.imageview);
            if (img_android != null) {
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }


        }
        }

}