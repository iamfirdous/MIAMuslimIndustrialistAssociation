package com.nexusinfo.mia_muslimindustrialistassociation.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ItemModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ServiceModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by firdous on 2/2/2018.
 */

public class ItemViewModel extends ViewModel {

    private List<ItemModel> items;

    public List<ItemModel> getSearchResults(Context context, String searchPhrase) throws Exception{

        items = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection(DatabaseConnection.MIA_DB_NAME);
        Connection conn = connection.getConnection();

        String[] splitSearchPhrase = searchPhrase.split("\\s+");

        Statement productStmt = conn.createStatement();
        Statement serviceStmt = conn.createStatement();

        StringBuilder productQueryBuilder = new StringBuilder("SELECT ProductId, PorductName, CompanyName, MemberID, Name, Designation, CategoryId, CategoryName, SubCategoryId, SubCategoryName, Specification, Photo, isActive, cmpid, brcode FROM View_MemberProduct WHERE PorductName");
        StringBuilder serviceQueryBuilder = new StringBuilder("SELECT ServiceId, Service, CompanyName, MemberID, Name, Designation, ServiceDescription, isActive, cmpid, brcode FROM View_MemberService WHERE Service");

        for (String s: splitSearchPhrase){
            productQueryBuilder.append(" LIKE '%").append(s).append("%' AND PorductName");
            serviceQueryBuilder.append(" LIKE '%").append(s).append("%' AND Service");
        }
        productQueryBuilder.setLength(productQueryBuilder.length() - 16);
        serviceQueryBuilder.setLength(serviceQueryBuilder.length() - 12);

        Log.e("ProductQuery", productQueryBuilder.toString());
        Log.e("ServiceQuery", serviceQueryBuilder.toString());

        ResultSet productRs = productStmt.executeQuery(productQueryBuilder.toString());
        ResultSet serviceRs = serviceStmt.executeQuery(serviceQueryBuilder.toString());

        while (productRs.next()) {
            ProductModel productModel = new ProductModel();
            productModel.setItemType();

            productModel.setProductId(productRs.getInt("ProductId"));
            productModel.setProductName(productRs.getString("PorductName"));

            productModel.setMemberId(productRs.getInt("MemberID"));
            productModel.setMemberName(productRs.getString("Name"));
            productModel.setMemberDesignation(productRs.getString("Designation"));
            productModel.setCompanyName(productRs.getString("CompanyName"));

            productModel.setCategoryId(productRs.getInt("CategoryId"));
            productModel.setCategoryName(productRs.getString("CategoryName"));

            productModel.setSubCategoryId(productRs.getInt("SubCategoryId"));
            productModel.setSubCategoryName(productRs.getString("SubCategoryName"));

            productModel.setSpecification(productRs.getString("Specification"));
            productModel.setPhoto(productRs.getBytes("Photo"));
            productModel.setActive(productRs.getBoolean("isActive"));
            productModel.setCompanyId(productRs.getString("cmpid"));
            productModel.setBranchCoce(productRs.getString("brcode"));

            items.add(productModel);
        }

        while (serviceRs.next()) {
            ServiceModel serviceModel = new ServiceModel();
            serviceModel.setItemType();

            serviceModel.setServiceId(serviceRs.getInt("ServiceId"));
            serviceModel.setServiceName(serviceRs.getString("Service"));

            serviceModel.setMemberId(serviceRs.getInt("MemberID"));
            serviceModel.setMemberName(serviceRs.getString("Name"));
            serviceModel.setMemberDesignation(serviceRs.getString("Designation"));
            serviceModel.setCompanyName(serviceRs.getString("CompanyName"));

            serviceModel.setServiceDescription(serviceRs.getString("ServiceDescription"));
//            serviceModel.setPhoto(serviceRs.getBytes("Photo"));
            serviceModel.setActive(serviceRs.getBoolean("isActive"));
            serviceModel.setCompanyId(serviceRs.getString("cmpid"));
            serviceModel.setBranchCoce(serviceRs.getString("brcode"));

            items.add(serviceModel);
        }

        return items;
    }
}
