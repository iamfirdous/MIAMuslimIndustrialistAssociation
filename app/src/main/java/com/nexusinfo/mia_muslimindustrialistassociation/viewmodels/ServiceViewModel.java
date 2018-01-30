package com.nexusinfo.mia_muslimindustrialistassociation.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.model.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;

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

    public void setServices(Context context) throws Exception{
        services = new ArrayList<>();

        UserModel user = LocalDatabaseHelper.getInstance(context).getUser();
        int memberID = user.getMemberId();

        DatabaseConnection connection = new DatabaseConnection(DatabaseConnection.MIA_DB_NAME);
        Connection conn = connection.getConnection();

        Statement stmt = conn.createStatement();

        String query = "SELECT ServiceId, Service, CompanyName, ServiceDescription, isActive, cmpid, brcode FROM View_MemberService WHERE MemberId = " + memberID;
        Log.e("ServicesQuery", query);

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            ServiceModel ServiceModel = new ServiceModel();

            ServiceModel.setServiceId(rs.getInt("ServiceId"));
            ServiceModel.setServiceName(rs.getString("Service"));

            ServiceModel.setMemberId(memberID);
            ServiceModel.setMemberName(user.getMemberName());
            ServiceModel.setCompanyName(rs.getString("CompanyName"));

            ServiceModel.setServiceDescription(rs.getString("ServiceDescription"));
//            ServiceModel.setPhoto(rs.getBytes("Photo"));
            ServiceModel.setActive(rs.getBoolean("isActive"));
            ServiceModel.setCompanyId(rs.getString("cmpid"));
            ServiceModel.setBranchCoce(rs.getString("brcode"));

            services.add(ServiceModel);
        }
    }

    public void setServices(List<ServiceModel> Services){
        this.services = Services;
    }

    public List<ServiceModel> getServices() {
        return services;
    }
}
