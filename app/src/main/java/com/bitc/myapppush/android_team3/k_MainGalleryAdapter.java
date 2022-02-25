package com.bitc.myapppush.android_team3;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class k_MainGalleryAdapter extends BaseAdapter {
    Context context;

    public k_MainGalleryAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return bookCover.length;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    Integer[] bookCover = {R.drawable.gallery1, R.drawable.gallery2, R.drawable.gallery3, R.drawable.gallery4, R.drawable.gallery5, R.drawable.gallery6, R.drawable.gallery7};
    String[]  bookTitle = {"book1", "book2", "book3", "book4", "book5"};

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new Gallery.LayoutParams(400, 500));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5, 5, 5, 5);


        imageView.setImageResource(bookCover[position]);

        final int pos = position;

        return imageView;
    }
}
