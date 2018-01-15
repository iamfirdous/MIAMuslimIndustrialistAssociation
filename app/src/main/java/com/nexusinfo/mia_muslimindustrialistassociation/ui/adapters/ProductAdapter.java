package com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ProductModel;

import java.util.List;

/**
 * Created by firdous on 1/15/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<ProductModel> mProducts;
    private Context mContext;

    public ProductAdapter(Context mContext, List<ProductModel> mProducts) {
        this.mProducts = mProducts;
        this.mContext = mContext;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.listitem_product, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProductPhoto;
        TextView tvProductName, tvCompanyName, tvProductDescription;


        public ProductViewHolder(View itemView) {
            super(itemView);

            ivProductPhoto = itemView.findViewById(R.id.imageView_productPhoto);
            tvProductName = itemView.findViewById(R.id.textView_productName);
            tvCompanyName = itemView.findViewById(R.id.textView_companyName);
            tvProductDescription = itemView.findViewById(R.id.textView_productDescription);
        }
    }
}
