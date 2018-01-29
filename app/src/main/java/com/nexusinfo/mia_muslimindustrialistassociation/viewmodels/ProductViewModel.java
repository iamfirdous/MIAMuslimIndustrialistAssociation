package com.nexusinfo.mia_muslimindustrialistassociation.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nexusinfo.mia_muslimindustrialistassociation.model.ProductModel;

import static java.lang.Thread.sleep;

/**
 * Created by firdous on 1/13/2018.
 */

public class ProductViewModel extends ViewModel {

    private ProductModel product;

    public void setProduct() {
        product = new ProductModel();
        product.setProductName("Hello");

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            Log.e("Exception", e.toString());
        }
    }

    public void setProduct(ProductModel product){
        this.product = product;
    }

    public ProductModel getProduct() {
        return product;
    }
}
