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
import com.nexusinfo.mia_muslimindustrialistassociation.model.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;

public class ViewProductActivity extends AppCompatActivity {

    private TextView tvProductName, tvCompanyName,
                     tvSpecification, tvAvailability, tvCategory, tvSubCategory,
                     tvAboutCompanyName, tvMemberName, tvMemberDesignation, tvCEditProduct, tvCViewMemberProfile;
    private ImageView ivProductPhoto;

    private ProductModel product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        product = (ProductModel) getIntent().getSerializableExtra("product");

        tvProductName = findViewById(R.id.textView_viewProductName);
        tvCompanyName = findViewById(R.id.textView_viewCompanyName_product);
        tvSpecification = findViewById(R.id.textView_viewProductSpecification);
        tvAvailability = findViewById(R.id.textView_viewProductAvailability);
        tvCategory = findViewById(R.id.textView_viewProductCategory);
        tvSubCategory = findViewById(R.id.textView_viewProductSubCategory);
        tvAboutCompanyName = findViewById(R.id.textView_productAboutCompanyLabel);
        tvMemberName = findViewById(R.id.textView_viewProductMemberName);
        tvMemberDesignation = findViewById(R.id.textView_viewProductMemberDesignation);

        tvCEditProduct = findViewById(R.id.textView_viewProductEdit);
        tvCViewMemberProfile = findViewById(R.id.textView_viewProductMemberProfile);

        ivProductPhoto = findViewById(R.id.imageView_viewProductPhoto);

        if (product != null){
            byte[] photoData = product.getPhoto();

            Bitmap bmp;
            if(photoData != null)
                bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            else
                bmp = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_product)).getBitmap();

            ivProductPhoto.setImageBitmap(bmp);

            if (product.getProductName() != null && !product.getProductName().equals(""))
                tvProductName.setText(product.getProductName());
            else
                tvProductName.setText("-");

            if (product.getCompanyName() != null && !product.getCompanyName().equals("")){
                tvCompanyName.setText(product.getCompanyName());
                tvAboutCompanyName.setText("About " + product.getCompanyName());
            }
            else{
                tvCompanyName.setText("-");
                tvAboutCompanyName.setText("-");
            }

            if (product.getSpecification() != null && !product.getSpecification().equals(""))
                tvSpecification.setText(product.getSpecification());
            else
                tvSpecification.setText("-");

            if (product.isActive() != null){
                if (product.isActive())
                    tvAvailability.setText("Available");
                else
                    tvAvailability.setText("Not available");
            }
            else
                tvAvailability.setText("-");

            if (product.getCategoryName() != null && !product.getCategoryName().equals(""))
                tvCategory.setText(product.getCategoryName());
            else
                tvCategory.setText("-");

            if (product.getSubCategoryName() != null && !product.getSubCategoryName().equals(""))
                tvSubCategory.setText(product.getSubCategoryName());
            else
                tvSubCategory.setText("-");

            if (product.getMemberName() != null && !product.getMemberName().equals(""))
                tvMemberName.setText(product.getMemberName());
            else
                tvMemberName.setText("-");

            if (product.getMemberDesignation() != null && !product.getMemberDesignation().equals(""))
                tvMemberDesignation.setText(product.getMemberDesignation());
            else
                tvMemberDesignation.setText("-");

            UserModel user = LocalDatabaseHelper.getInstance(this).getUser();

            if (product.getMemberId() == user.getMemberId())
                tvCEditProduct.setVisibility(View.VISIBLE);
            else
                tvCEditProduct.setVisibility(View.INVISIBLE);

            tvCEditProduct.setOnClickListener(view -> {

            });

            tvCViewMemberProfile.setOnClickListener(view -> {
                Intent viewProfileIntent = new Intent(this, MemberProfileActivity.class);
                viewProfileIntent.putExtra("memberId", product.getMemberId());
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
