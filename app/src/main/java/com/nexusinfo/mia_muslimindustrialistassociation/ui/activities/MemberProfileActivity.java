package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProfileFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProfileViewHolder;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.MemberViewModel;

public class MemberProfileActivity extends AppCompatActivity {

    private ProfileViewHolder holder;
    private MemberViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        holder = new ProfileViewHolder(this);
        viewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

        int memberId = getIntent().getExtras().getInt("memberId");

        ProfileFragment.FetchProfile task = new ProfileFragment.FetchProfile(this, holder, viewModel);
        task.execute("OtherMember", "" + memberId);
    }
}
