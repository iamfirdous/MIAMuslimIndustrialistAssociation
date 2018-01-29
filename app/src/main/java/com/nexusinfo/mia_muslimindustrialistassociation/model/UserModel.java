package com.nexusinfo.mia_muslimindustrialistassociation.model;

import java.io.Serializable;

/**
 * Created by firdous on 11/28/2017.
 */

public class UserModel implements Serializable{

    private String loginName, auth, memberMobile, memberEmail, memberName, cmpId, brCode;
    private int userId;

    public UserModel() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCmpId() {
        return cmpId;
    }

    public void setCmpId(String cmpId) {
        this.cmpId = cmpId;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }
}
