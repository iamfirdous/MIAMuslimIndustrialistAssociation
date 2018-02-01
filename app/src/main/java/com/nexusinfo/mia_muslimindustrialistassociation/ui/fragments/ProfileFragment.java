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

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.model.MemberModel;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;
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

        FetchProfile task = new FetchProfile(getContext(), holder, viewModel);
        task.execute("ThisMember");

        return view;
    }

    public static class FetchProfile extends AsyncTask<String, String, MemberModel> {

        private Context context;
        private ProfileViewHolder holder;
        private MemberViewModel viewModel;

        public FetchProfile(Context context, ProfileViewHolder holder, MemberViewModel viewModel){
            this.context = context;
            this.holder = holder;
            this.viewModel = viewModel;
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
                    if(strings[0].equals("OtherMember")){
                        viewModel.setMember(context, true, Integer.parseInt(strings[1]));
                        member = viewModel.getMember();
                    }
                    else {
                        viewModel.setMember(context, false, 0);
                        member = viewModel.getMember();
                    }
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
                showCustomToast(context, "Some error occurred.",1);
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

                if (companyName != null && !companyName.equals(""))
                    holder.tvCompanyName.setText(companyName);
                else
                    holder.tvCompanyName.setText("-");

                if (memberName != null && !memberName.equals(""))
                    holder.tvMemberName.setText(memberName);
                else
                    holder.tvMemberName.setText("-");

                if (memberDesignation != null && !memberDesignation.equals(""))
                    holder.tvMemberDesignation.setText(memberDesignation);
                else
                    holder.tvMemberDesignation.setText("-");

                if (ratings != 0f)
                    holder.tvRatings.setText("" + ratings);
                else
                    holder.tvRatings.setText("-");

                if (email != null && !email.equals(""))
                    holder.tvEmail.setText(email);
                else
                    holder.tvEmail.setText("-");

                if (mobile != null && !mobile.equals(""))
                    holder.tvMobile.setText(mobile);
                else
                    holder.tvMobile.setText("-");

                if (address != null && !address.equals(""))
                    holder.tvAddress.setText(address);
                else
                    holder.tvAddress.setText("-");

                holder.tvProductCount.setText("" + productCount);
                holder.tvServiceCount.setText("" + serviceCount);

                UserModel user = LocalDatabaseHelper.getInstance(context).getUser();

                if (member.getMemberId() == user.getMemberId()){
                    holder.tvAllDetails.setVisibility(View.VISIBLE);
                    holder.tvProfileEdit.setVisibility(View.VISIBLE);

                    holder.tvAllDetails.setOnClickListener(view -> {
                        showCustomToast(context, holder.tvAllDetails.getText().toString(),1);
                    });

                    holder.tvProfileEdit.setOnClickListener(view -> {
                        showCustomToast(context, holder.tvProfileEdit.getText().toString(),1);
                    });
                }
                else {
                    holder.tvAllDetails.setVisibility(View.INVISIBLE);
                    holder.tvProfileEdit.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}

