package com.nexusinfo.mia_muslimindustrialistassociation.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.model.MemberModel;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by firdous on 1/13/2018.
 */

public class MemberViewModel extends ViewModel {

    private MemberModel member;

    public void setMember(Context context) throws Exception{

        member = new MemberModel();

        UserModel user = LocalDatabaseHelper.getInstance(context).getUser();
        int memberId = user.getMemberId();

        DatabaseConnection connection = new DatabaseConnection(DatabaseConnection.MIA_DB_NAME);
        Connection conn = connection.getConnection();

        Statement stmt = conn.createStatement();

        String query = "SELECT * FROM View_Member WHERE MemberID = " + memberId;
        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){
            member.setMemberId(rs.getInt("MemberID"));
            member.setName(rs.getString("Name"));
            member.setMobile(rs.getString("Mobile"));
            member.setEmail(rs.getString("Email"));
            member.setResidentialAddress(rs.getString("ResidentialAddress"));
            member.setOfficeAddress(rs.getString("OfficeAddress"));

            member.setDesignationId(rs.getInt("DesignationId"));
            member.setDesignation(rs.getString("Designation"));

            member.setDepartmentId(rs.getInt("DepartmentId"));
            member.setDepartmentName(rs.getString("DepartmentName"));

            member.setCompanyName(rs.getString("CompanyName"));

            member.setCategoryId(rs.getInt("CategoryId"));
            //

            member.setContactPersonName(rs.getString("ContactPerson"));
            member.setContactPersonMobile(rs.getString("ContactPersonPhone"));

            member.setPhoto(rs.getBytes("Photo"));
            member.setGender(rs.getString("Gender"));
            member.setManufacture(rs.getString("Manufacture"));
            member.setMSMELarge(rs.getString("MSMELarge"));
            member.setService(rs.getString("Service"));
            member.setSSMELarge(rs.getString("SSMELarge"));
            member.setProfessionKnowledge(rs.getString("Profession-knowledge"));
            member.setAssociation(rs.getString("Association"));
            member.setOthers(rs.getString("Others"));
            member.setNoOfManagers(rs.getInt("NoofManagers"));
            member.setNoOfEmployees(rs.getInt("NoofEmployees"));
            member.setOtherInformation(rs.getString("OtherInformation"));
            member.setIdCardNo(rs.getString("IDCardNo"));
            member.setMembershipDate(rs.getDate("MembershipDate"));
            member.setYearOfEstablishment(rs.getString("YearOfEstablishment"));
            member.setMembershipNo(rs.getString("MembershipNo"));
            member.setMembershipType(rs.getString("MembershipType"));
            member.setRegistrationNo(rs.getString("RegistrationNo"));
            member.setProfMembershipNo(rs.getString("ProfMembershipNo"));
        }

        Statement sPr, sSe;
        sPr = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        sSe = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String qPr, qSe;
        qPr = "SELECT MemberId FROM MMemberProduct WHERE MemberId = " + memberId;
        qSe = "SELECT MemberId FROM MMemberService WHERE MemberId = " + memberId;

        ResultSet rsPr, rsSe;

        rsPr = sPr.executeQuery(qPr);
        rsPr.last();
        member.setProductCount(rsPr.getRow());

        rsSe = sSe.executeQuery(qSe);
        rsSe.last();
        member.setServiceCount(rsSe.getRow());
    }

    public MemberModel getMember() {
        return member;
    }
}
