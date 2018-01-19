package com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters;

import android.content.Context;
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
import com.nexusinfo.mia_muslimindustrialistassociation.models.ServiceModel;

import java.util.List;

/**
 * Created by firdous on 1/9/2018.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    private List<ServiceModel> mServices;
    private Context mContext;

    public ServiceAdapter(Context mContext, List<ServiceModel> mServices) {
        this.mServices = mServices;
        this.mContext = mContext;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.listitem_service, null);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {

        ServiceModel service = mServices.get(position);

        byte[] photoData = service.getPhoto();
        String serviceName = service.getServiceName();
        String companyName = service.getCompanyName();
        String serviceDescription = service.getServiceDescription();

        Bitmap bmp;
        if(photoData != null)
            bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
        else
            bmp = ((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.ic_services)).getBitmap();

        holder.ivServicePhoto.setImageBitmap(bmp);

        if (serviceName != null && !serviceName.equals(""))
            holder.tvServiceName.setText(serviceName);
        else
            holder.tvServiceName.setText("-");

        if (companyName != null && !companyName.equals(""))
            holder.tvCompanyName.setText(companyName);
        else
            holder.tvCompanyName.setText("-");

        if (serviceDescription != null && !serviceDescription.equals(""))
            holder.tvServiceDescription.setText(serviceDescription);
        else
            holder.tvServiceDescription.setText("-");

    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{

        ImageView ivServicePhoto;
        TextView tvServiceName, tvCompanyName, tvServiceDescription;


        public ServiceViewHolder(View itemView) {
            super(itemView);

            ivServicePhoto = itemView.findViewById(R.id.imageView_servicePhoto);
            tvServiceName = itemView.findViewById(R.id.textView_serviceName);
            tvCompanyName = itemView.findViewById(R.id.textView_companyName_service);
            tvServiceDescription = itemView.findViewById(R.id.textView_serviceDescription);
        }
    }
}
