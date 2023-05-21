package com.ghorami.rongpencill.barivara;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ImageGalleryUpdateAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<AndroidVersion> tours =new ArrayList<AndroidVersion>();
    Activity activity;


    public ImageGalleryUpdateAdapter(Activity activity, ArrayList<AndroidVersion> tours)
    {
        this.activity = activity;
        this.tours = tours;
    }

    public int getCount() {
        return tours.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }



    // Override this method according to your need
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(activity);
           // imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = new ImageView(activity);
            // imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Drawable d = ContextCompat.getDrawable(activity, R.drawable.myhomes);
            d.setBounds(0, 0, imageView.getWidth(), imageView.getHeight());
          //  imageView.setIndeterminateDrawable(d);
          //  imageView.setIndeterminate(true);
            imageView.requestLayout();
        }

        if(tours.get(position).getImage().equals ("")){
            imageView.setImageDrawable(ContextCompat.getDrawable(activity,(R.drawable.noimage)));

        } else {
            Picasso.get().load(tours.get(position).getImage()).into(imageView);

        }



        //Bitmap bitmap = BitmapFactory.decodeFile(tours);
       // Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(tours.get(position)));
      //  imageView.setImageBitmap(bitmap);
        return imageView;

     /*   ImageView i = new ImageView(mContext);

        //i.setImageResource(mImageIds[index]);
       // i.setImageDrawable(ImageOperations(mImageIds[index]));
        Picasso.with(mContext).load(mImageIds[index]).into(i);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));

        i.setScaleType(ImageView.ScaleType.FIT_XY);


        return i;
        */
    }

    public Drawable ImageOperations(String mImageIds) {
        try {
            InputStream is = (InputStream) new URL(mImageIds).getContent();
            Drawable drawable = Drawable.createFromStream(is, "src");
            return drawable;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    public String[] mImageIds = {
            "http://catchvilla.com/villa/property/mosarrof.jpg",

            "http://www.telugucinema.com/sites/default/files/news/kajalitemsong.jpg",
            "https://www.25cineframes.com/images/gallery/2016/08/kajal-agarwal-pakka-local-janatha-garage-item-song-ultra-hd-pics/12-Kajal-Agarwal-Pakka-Local-Janatha-Garage-Item-Song-ULTRA-HD-Pics-from-JR-NTR-Movie-Photos-Kajal-Aggarwal-Images.jpg",
            "http://media.santabanta.com/gallery/indian celebrities(f)/kajal agarwal/kajal-agarwal-162-h.jpg",
            "https://www.25cineframes.com/images/gallery/2015/02/rakul-preet-singh-hot-in-red-saree-latest-photos-hd-stills/13-Rakul_Preet_Singh_Hot_in_Red_Saree_Latest_Photos_HD_Stills.jpg",
            "http://catchvilla.com/villa/property/mosarrof.jpg"


    };
}
