package com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ItemModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.ViewProductActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.ViewServiceActivity;

import java.util.List;

/**
 * Created by firdous on 2/2/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<ItemModel> mItems;
    private Context mContext;

    public ItemAdapter(Context mContext, List<ItemModel> mItems) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.listitem_product, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        if (mItems.get(position).getItemType().equals(ItemModel.TYPE_PRODUCT)) {
            ProductModel product = (ProductModel) mItems.get(position);

            byte[] photoData = product.getPhoto();
            String productName = product.getProductName();
            String companyName = product.getCompanyName();
            String productSpecification = product.getSpecification();

            Bitmap bmp;
            if(photoData != null)
                bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            else
                bmp = ((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.ic_product)).getBitmap();

            holder.ivItemPhoto.setImageBitmap(bmp);

            if (productName != null && !productName.equals(""))
                holder.tvItemName.setText(productName);
            else
                holder.tvItemName.setText("-");

            if (companyName != null && !companyName.equals(""))
                holder.tvCompanyName.setText(companyName);
            else
                holder.tvCompanyName.setText("-");

            if (productSpecification != null && !productSpecification.equals(""))
                holder.tvItemSpecification.setText(productSpecification);
            else
                holder.tvItemSpecification.setText("-");

            holder.itemView.setOnClickListener(view -> {
                Intent viewProduct = new Intent(mContext, ViewProductActivity.class);
                viewProduct.putExtra("product", product);
                mContext.startActivity(viewProduct);
            });
        }

        if (mItems.get(position).getItemType().equals(ItemModel.TYPE_SERVICE)) {
            ServiceModel service = (ServiceModel) mItems.get(position);

            byte[] photoData = service.getPhoto();
            String serviceName = service.getServiceName();
            String companyName = service.getCompanyName();
            String serviceDescription = service.getServiceDescription();

            Bitmap bmp;
            if(photoData != null)
                bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            else
                bmp = ((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.ic_services)).getBitmap();

            holder.ivItemPhoto.setImageBitmap(bmp);

            if (serviceName != null && !serviceName.equals(""))
                holder.tvItemName.setText(serviceName);
            else
                holder.tvItemName.setText("-");

            if (companyName != null && !companyName.equals(""))
                holder.tvCompanyName.setText(companyName);
            else
                holder.tvCompanyName.setText("-");

            if (serviceDescription != null && !serviceDescription.equals(""))
                holder.tvItemSpecification.setText(serviceDescription);
            else
                holder.tvItemSpecification.setText("-");

            holder.itemView.setOnClickListener(view -> {
                Intent viewService = new Intent(mContext, ViewServiceActivity.class);
                viewService.putExtra("service", service);
                mContext.startActivity(viewService);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView ivItemPhoto;
        TextView tvItemName, tvCompanyName, tvItemSpecification;
        View itemView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivItemPhoto = itemView.findViewById(R.id.imageView_productPhoto);
            tvItemName = itemView.findViewById(R.id.textView_productName);
            tvCompanyName = itemView.findViewById(R.id.textView_companyName_product);
            tvItemSpecification = itemView.findViewById(R.id.textView_productSpecification);
        }
    }
}
