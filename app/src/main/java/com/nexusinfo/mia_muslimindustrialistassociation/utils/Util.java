package com.nexusinfo.mia_muslimindustrialistassociation.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nexusinfo.mia_muslimindustrialistassociation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by firdous on 12/27/2017.
 */

public class Util {

    public static Date parseDate(String strDate) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static String dateToStringForDB(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static void showCustomToast(Context context, String message, int duration) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastLayout = inflater.inflate(R.layout.toast_custom, null);
        TextView tv = toastLayout.findViewById(R.id.textView_toast_custom);
        tv.setText(message);
        for(int i = 0; i < duration; i++) {
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastLayout);
            toast.show();
        }
    }

    public static void showCustomToast(Context context, String message, int duration, int gravity, int xOffset, int yOffset) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastLayout = inflater.inflate(R.layout.toast_custom, null);
        TextView tv = toastLayout.findViewById(R.id.textView_toast_custom);
        tv.setText(message);
        for(int i = 0; i < duration; i++) {
            Toast toast = new Toast(context);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastLayout);
            toast.show();
        }
    }
}
