package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.model.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;

public class ViewServiceActivity extends AppCompatActivity {

    private TextView tvServiceName, tvCompanyName,
                     tvDescription, tvAvailability,
                     tvAboutCompanyName, tvMemberName,
                     tvMemberDesignation, tvCEditService, tvCViewMemberProfile;
    private ImageView ivServicePhoto;
    
    private ServiceModel service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        service = (ServiceModel) getIntent().getSerializableExtra("service");

        tvServiceName = findViewById(R.id.textView_viewServiceName);
        tvCompanyName = findViewById(R.id.textView_viewCompanyName_service);
        tvDescription = findViewById(R.id.textView_viewServiceSpecification);
        tvAvailability = findViewById(R.id.textView_viewServiceAvailability);
        tvAboutCompanyName = findViewById(R.id.textView_serviceAboutCompanyLabel);
        tvMemberName = findViewById(R.id.textView_viewServiceMemberName);
        tvMemberDesignation = findViewById(R.id.textView_viewServiceMemberDesignation);
        tvCEditService = findViewById(R.id.textView_viewServiceEdit);
        tvCViewMemberProfile = findViewById(R.id.textView_viewServiceMemberProfile);
        ivServicePhoto = findViewById(R.id.imageView_viewServicePhoto);

        if (service != null) {
            byte[] photoData = service.getPhoto();

            Bitmap bmp;
            if(photoData != null)
                bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            else
                bmp = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_services)).getBitmap();

            ivServicePhoto.setImageBitmap(bmp);

            if (service.getServiceName() != null && !service.getServiceName().equals(""))
                tvServiceName.setText(service.getServiceName());
            else
                tvServiceName.setText("-");

            if (service.getCompanyName() != null && !service.getCompanyName().equals("")){
                tvCompanyName.setText(service.getCompanyName());
                tvAboutCompanyName.setText("About " + service.getCompanyName());
            }
            else{
                tvCompanyName.setText("-");
                tvAboutCompanyName.setText("-");
            }

            if (service.getServiceDescription() != null && !service.getServiceDescription().equals(""))
                tvDescription.setText(service.getServiceDescription());
            else
                tvDescription.setText("-");

            if (service.isActive() != null){
                if (service.isActive())
                    tvAvailability.setText("Available");
                else
                    tvAvailability.setText("Not available");
            }
            else
                tvAvailability.setText("-");

            if (service.getMemberName() != null && !service.getMemberName().equals(""))
                tvMemberName.setText(service.getMemberName());
            else
                tvMemberName.setText("-");

            if (service.getMemberDesignation() != null && !service.getMemberDesignation().equals(""))
                tvMemberDesignation.setText(service.getMemberDesignation());
            else
                tvMemberDesignation.setText("-");

            UserModel user = LocalDatabaseHelper.getInstance(this).getUser();

            if (service.getMemberId() == user.getMemberId())
                tvCEditService.setVisibility(View.VISIBLE);
            else
                tvCEditService.setVisibility(View.INVISIBLE);

            tvCEditService.setOnClickListener(view -> {

            });

            tvCViewMemberProfile.setOnClickListener(view -> {
                Intent viewProfileIntent = new Intent(this, MemberProfileActivity.class);
                viewProfileIntent.putExtra("memberId", service.getMemberId());
                startActivity(viewProfileIntent);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
