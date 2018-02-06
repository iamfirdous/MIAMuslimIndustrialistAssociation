package com.nexusinfo.mia_muslimindustrialistassociation.models;

import java.io.Serializable;

/**
 * Created by firdous on 2/2/2018.
 */

public abstract class ItemModel implements Serializable{
    public static final String TYPE_PRODUCT = "ProductType";
    public static final String TYPE_SERVICE = "ServiceType";

    String itemType = null;

    public String getItemType() {
        return itemType;
    }

    public abstract void setItemType();
}
