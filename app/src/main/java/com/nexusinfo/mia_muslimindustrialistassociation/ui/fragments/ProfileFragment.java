package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.model.MemberModel;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.MemberViewModel;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private View view;
    private ProfileViewHolder holder;

    private MemberViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_view_amp_edit_profile);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        holder = new ProfileViewHolder(view);

        viewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

        FetchProfile task = new FetchProfile(getContext(), holder);
        task.execute();

        return view;
    }

    class FetchProfile extends AsyncTask<String, String, MemberModel> {

        private Context context;
        private ProfileViewHolder holder;

        public FetchProfile(Context context, ProfileViewHolder holder){
            this.context = context;
            this.holder = holder;
        }

        @Override
        protected void onPreExecute() {
            holder.linearLayout.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberModel doInBackground(String... strings) {

            MemberModel member = null;

            if(viewModel.getMember() == null){
                try {
                    viewModel.setMember(context);
                    member = viewModel.getMember();
                }
                catch (Exception e) {
                    Log.e("Error", e.toString());
                    publishProgress("Exception");
                    cancel(true);
                }
            }
            else
                member = viewModel.getMember();

            return member;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                showCustomToast(getContext(), "Some error occurred.",1);
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onPostExecute(MemberModel member) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);

            if (member != null){

                String companyName, memberName, memberDesignation, email, mobile, address;
                int productCount, serviceCount;
                float ratings;

                companyName = member.getCompanyName();
                memberName = member.getName();
                memberDesignation = member.getDesignation();
                ratings = member.getRatings();
                email = member.getEmail();
                mobile = member.getMobile();
                address = member.getOfficeAddress();
                productCount = member.getProductCount();
                serviceCount = member.getServiceCount();
                byte[] photoData = member.getPhoto();

                Bitmap bmp;
                if(photoData != null)
                    bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
                else
                    bmp = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.ic_profile_white)).getBitmap();

                holder.ivMemberPhoto.setImageBitmap(bmp);

                if (companyName != null || !companyName.equals(""))
                    holder.tvCompanyName.setText(companyName);
                else
                    holder.tvCompanyName.setText("-");

                if (memberName != null || !memberName.equals(""))
                    holder.tvMemberName.setText(memberName);
                else
                    holder.tvMemberName.setText("-");

                if (memberDesignation != null || !memberDesignation.equals(""))
                    holder.tvMemberDesignation.setText(memberDesignation);
                else
                    holder.tvMemberDesignation.setText("-");

                if (ratings != 0f)
                    holder.tvRatings.setText("" + ratings);
                else
                    holder.tvRatings.setText("-");

                if (email != null || !email.equals(""))
                    holder.tvEmail.setText(email);
                else
                    holder.tvEmail.setText("-");

                if (mobile != null || !mobile.equals(""))
                    holder.tvMobile.setText(mobile);
                else
                    holder.tvMobile.setText("-");

                if (address != null || !address.equals(""))
                    holder.tvAddress.setText(address);
                else
                    holder.tvAddress.setText("-");

                holder.tvProductCount.setText("" + productCount);
                holder.tvServiceCount.setText("" + serviceCount);
            }
        }
    }

    class ProfileViewHolder {
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
    }
}
