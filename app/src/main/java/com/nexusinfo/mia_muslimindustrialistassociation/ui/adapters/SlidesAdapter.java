package com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.ViewProductActivity;

import java.util.ArrayList;

/**
 * Created by firdous on 2/1/2018.
 */

public class SlidesAdapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public SlidesAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.slide_banners, container, false);
        ImageView ivSlide = view.findViewById(R.id.imageView_slideBanners);

        ivSlide.setImageResource(images.get(position));
        container.addView(view, 0);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
