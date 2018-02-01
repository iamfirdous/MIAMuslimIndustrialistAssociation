package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;

public class ProfileViewHolder {
    public LinearLayout linearLayout;
    public TextView tvCompanyName, tvMemberName, tvMemberDesignation,
            tvProductCount, tvServiceCount, tvRatings,
            tvEmail, tvMobile, tvAddress, tvAllDetails, tvProfileEdit;
    public ImageView ivMemberPhoto;
    public ProgressBar progressBar;

    public ProfileViewHolder(View view) {
        linearLayout = view.findViewById(R.id.linearLayout_profile);
        tvCompanyName = view.findViewById(R.id.textView_companyName_profile);
        tvMemberName = view.findViewById(R.id.textView_memberNameProfile);
        tvMemberDesignation = view.findViewById(R.id.textView_memberDesignationProfile);
        tvProductCount = view.findViewById(R.id.textView_productCountProfile);
        tvServiceCount = view.findViewById(R.id.textView_serviceCountProfile);
        tvRatings = view.findViewById(R.id.textView_ratingsProfile);
        tvEmail = view.findViewById(R.id.textView_memberEmailProfile);
        tvMobile = view.findViewById(R.id.textView_memberMobileProfile);
        tvAddress = view.findViewById(R.id.textView_memberAddressProfile);
        tvAllDetails = view.findViewById(R.id.textView_allDetails);
        tvProfileEdit = view.findViewById(R.id.textView_profileEdit);
        ivMemberPhoto = view.findViewById(R.id.imageView_memberPhotoProfile);
        progressBar = view.findViewById(R.id.progressBar_profile);
    }

    public ProfileViewHolder(Activity activity) {
        linearLayout = activity.findViewById(R.id.linearLayout_profile);
        tvCompanyName = activity.findViewById(R.id.textView_companyName_profile);
        tvMemberName = activity.findViewById(R.id.textView_memberNameProfile);
        tvMemberDesignation = activity.findViewById(R.id.textView_memberDesignationProfile);
        tvProductCount = activity.findViewById(R.id.textView_productCountProfile);
        tvServiceCount = activity.findViewById(R.id.textView_serviceCountProfile);
        tvRatings = activity.findViewById(R.id.textView_ratingsProfile);
        tvEmail = activity.findViewById(R.id.textView_memberEmailProfile);
        tvMobile = activity.findViewById(R.id.textView_memberMobileProfile);
        tvAddress = activity.findViewById(R.id.textView_memberAddressProfile);
        tvAllDetails = activity.findViewById(R.id.textView_allDetails);
        tvProfileEdit = activity.findViewById(R.id.textView_profileEdit);
        ivMemberPhoto = activity.findViewById(R.id.imageView_memberPhotoProfile);
        progressBar = activity.findViewById(R.id.progressBar_profile);
    }
}
