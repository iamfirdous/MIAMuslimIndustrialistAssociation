package com.nexusinfo.mia_muslimindustrialistassociation.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by firdous on 1/13/2018.
 */

public class ServiceViewModel extends ViewModel {

    private List<ServiceModel> services;
    private int memberId;

    public void setServices(Context context, boolean others, int memberId) throws Exception{
        services = new ArrayList<>();

        UserModel user = LocalDatabaseHelper.getInstance(context).getUser();

        if (others){
            this.memberId = memberId;
        }
        else {
            this.memberId = user.getMemberId();
        }


        DatabaseConnection connection = new DatabaseConnection(DatabaseConnection.MIA_DB_NAME);
        Connection conn = connection.getConnection();

        Statement stmt = conn.createStatement();

        String query = "SELECT ServiceId, Service, CompanyName, Name, Designation, ServiceDescription, isActive, cmpid, brcode FROM View_MemberService WHERE MemberId = " + this.memberId;
        Log.e("ServicesQuery", query);

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            ServiceModel serviceModel = new ServiceModel();

            serviceModel.setServiceId(rs.getInt("ServiceId"));
            serviceModel.setServiceName(rs.getString("Service"));

            serviceModel.setMemberId(this.memberId);
            serviceModel.setMemberName(rs.getString("Name"));
            serviceModel.setMemberDesignation(rs.getString("Designation"));
            serviceModel.setCompanyName(rs.getString("CompanyName"));

            serviceModel.setServiceDescription(rs.getString("ServiceDescription"));
//            serviceModel.setPhoto(rs.getBytes("Photo"));
            serviceModel.setActive(rs.getBoolean("isActive"));
            serviceModel.setCompanyId(rs.getString("cmpid"));
            serviceModel.setBranchCoce(rs.getString("brcode"));

            services.add(serviceModel);
        }
    }

    public void setServices(List<ServiceModel> Services){
        this.services = Services;
    }

    public List<ServiceModel> getServices() {
        return services;
    }
}
