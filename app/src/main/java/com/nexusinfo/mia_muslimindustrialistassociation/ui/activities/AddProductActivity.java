package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nexusinfo.mia_muslimindustrialistassociation.R;

public class AddProductActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    class AddProductViewHolder{
        public EditText etProductName, etProductDescription, etProductKeywords;
        public Spinner spinnerCategory, spinnerSubCategory;
        public ImageView ivImageUpload;

        public AddProductViewHolder(AddProductActivity activity) {

            etProductName = activity.findViewById(R.id.editText_productName);
            etProductDescription = activity.findViewById(R.id.editText_productDescription);
            etProductKeywords = activity.findViewById(R.id.editText_productKeywords);
            spinnerCategory = activity.findViewById(R.id.spinner_productCategory);
            spinnerSubCategory = activity.findViewById(R.id.spinner_productSubCategory);
            ivImageUpload = activity.findViewById(R.id.imageView_uploadProductPhoto);

        }
    }
}
